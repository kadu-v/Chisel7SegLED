package decoder

import chisel3._
import chisel3.util.MuxCase
import chisel3.util.BitPat

class Decoder() extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val dout = Output(UInt(8.W))
  })

  val dout = RegInit(0x77.asUInt())
  dout := MuxCase(
    0x30.asUInt(),
    Seq(
      (io.din === 0x61.asUInt()) -> 0x7e.U(8.W),
      (io.din === 0x62.asUInt()) -> 0x30.U(8.W),
      (io.din === 0x63.asUInt()) -> 0x6d.U(8.W),
      (io.din === 0x64.asUInt()) -> 0x79.U(8.W),
      (io.din === 0x65.asUInt()) -> 0x33.U(8.W),
      (io.din === 0x66.asUInt()) -> 0x5b.U(8.W),
      (io.din === 0x67.asUInt()) -> 0x5f.U(8.W),
      (io.din === 0x68.asUInt()) -> 0x70.U(8.W),
      (io.din === 0x69.asUInt()) -> 0x7f.U(8.W),
      (io.din === 0x70.asUInt()) -> 0x7b.U(8.W)
    )
  )
  io.dout := dout
}
