package top

import chisel3._
import uart._
import decoder._
import genrst._

class Top() extends Module {
  val io = IO(new Bundle {
    // BLE interfeace
    val pmod2 = Output(Bool()) // RX data to RN4871
    val pmod3 = Input(Bool()) // TX data from RN4871
    val pmod4 = Output(Bool()) // clear to send
    val pmod8 = Output(Bool()) // reset of BLE

    val PIO0 = Output(UInt(8.W))
  })

  val genrst = Module(new GenRst())
  val urx = withReset(genrst.io.rst) { Module(new Rx(12, 115200)) }
  val dec = withReset(genrst.io.rst) { Module(new Decoder()) }
  io.pmod2 := true.B
  urx.io.rx := io.pmod3
  io.pmod4 := urx.io.rts
  io.pmod8 := true.B
  dec.io.din := urx.io.dout
  io.PIO0 := dec.io.dout
}

object Elaborate extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new Top(), args)
}
