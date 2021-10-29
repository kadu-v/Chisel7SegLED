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
    0x77.asUInt(),
    Seq(
      (io.din === 0x30.asUInt()) -> 0x7e.U(8.W),
      (io.din === 0x31.asUInt()) -> 0x30.U(8.W),
      (io.din === 0x32.asUInt()) -> 0x6d.U(8.W),
      (io.din === 0x33.asUInt()) -> 0x79.U(8.W),
      (io.din === 0x34.asUInt()) -> 0x33.U(8.W),
      (io.din === 0x35.asUInt()) -> 0x5b.U(8.W),
      (io.din === 0x36.asUInt()) -> 0x5f.U(8.W),
      (io.din === 0x37.asUInt()) -> 0x70.U(8.W),
      (io.din === 0x38.asUInt()) -> 0x7f.U(8.W),
      (io.din === 0x39.asUInt()) -> 0x7b.U(8.W)
    )
  )
  io.dout := dout
}
