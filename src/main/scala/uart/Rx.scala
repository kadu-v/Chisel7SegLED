package uart

import chisel3._
import chisel3.util._

class Rx(freq: Int, baudRate: Int) extends Module {
  val io = IO(new Bundle {
    val rx = Input(Bool()) // received serial data

    val busy = Output(Bool()) // busy flag
    val rts = Output(Bool()) // request to send
    val dout = Output(UInt(8.W)) // received data
  })

  val waitTime =
    ((freq * 1000000) / baudRate)
      .asUInt() // 50 MHz / 115200 = 50 * 10**6 / 115200
  val halfTime =
    ((freq * 1000000) / baudRate / 2)
      .asUInt() // 50 MHz / 115200 / 2 = 50 * 10**6 / 115200

  val sIDLE :: sWAIT :: sRDATA :: sEND :: Nil = Enum(4)
  val rxData = RegInit(0.U(9.W))
  val state = RegInit(sIDLE)
  val clkCnt = RegInit(0.U(15.W))
  val dataCnt = RegInit(0.U(4.W))
  val busy = RegInit(false.B)
  val rts = RegInit(true.B) // H: inactive, L: active
  val dout = RegInit(0.U(8.W))

  // detect positive edge of start bit rx
  val detedge0 = Module(new DetEdge())

  detedge0.io.sigin := io.rx
  io.busy := busy
  io.rts := rts
  io.dout := dout

  switch(state) {
    is(sIDLE) {
      when(detedge0.io.negdet) {
        state := sWAIT
        io.busy := true.B
        io.rts := false.B
      }
    }
    is(sWAIT) {
      when(clkCnt === halfTime) {
        state := sRDATA
        clkCnt := 0.asUInt()
      }.otherwise {
        clkCnt := clkCnt + 1.asUInt()
      }
    }
    is(sRDATA) {
      when(dataCnt === 9.asUInt()) {
        state := sEND
      }.elsewhen(clkCnt === waitTime) {
        clkCnt := 0.asUInt()
        rxData := Cat(io.rx, rxData(8, 1)) // LSB
        dataCnt := dataCnt + 1.asUInt()
      }.otherwise {
        clkCnt := clkCnt + 1.asUInt()
      }
    }
    is(sEND) {
      state := sIDLE
      clkCnt := 0.asUInt()
      dataCnt := 0.asUInt()
      dout := rxData(7, 0)
      busy := false.B
      rts := true.B
    }
  }
}
