package genrst

import chisel3._

class GenRst extends Module {
  val io = IO(new Bundle {
    val rst = Output(Bool())
  })

  val cntReg = RegInit(0.U(8.W))
  val rst = RegInit(false.B)
  io.rst := rst

  when(cntReg === 0xff.asUInt()) {
    rst := false.B
  }.elsewhen(cntReg === 0x0f.asUInt()) {
    rst := true.B
    cntReg := cntReg + 1.asUInt()
  }.otherwise {
    cntReg := cntReg + 1.asUInt()
  }
}
