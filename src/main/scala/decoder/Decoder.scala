package decoder

import chisel3._
import chisel3.util.MuxCase
import chisel3.util.BitPat

class Decoder() extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val dout = Output(UInt(8.W))
  })

  io.dout := MuxCase(
    0x77.asUInt(),
    Seq(
      (io.din === 0.asUInt()) -> 0x7e.U(8.W),
      (io.din === 1.asUInt()) -> 0x30.U(8.W),
      (io.din === 2.asUInt()) -> 0x6d.U(8.W),
      (io.din === 3.asUInt()) -> 0x79.U(8.W),
      (io.din === 4.asUInt()) -> 0x33.U(8.W),
      (io.din === 5.asUInt()) -> 0x5b.U(8.W),
      (io.din === 6.asUInt()) -> 0x5f.U(8.W),
      (io.din === 7.asUInt()) -> 0x70.U(8.W),
      (io.din === 8.asUInt()) -> 0x7f.U(8.W),
      (io.din === 9.asUInt()) -> 0x7b.U(8.W)
    )
  )
}
