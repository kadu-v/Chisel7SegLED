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
    0.asUInt(),
    Seq(
      (io.din === 0.asUInt()) -> 0x00.U(8.W),
      (io.din === 1.asUInt()) -> 0x01.U(8.W),
      (io.din === 2.asUInt()) -> 0x02.U(8.W),
      (io.din === 3.asUInt()) -> 0x03.U(8.W),
      (io.din === 4.asUInt()) -> 0x04.U(8.W),
      (io.din === 5.asUInt()) -> 0x05.U(8.W),
      (io.din === 6.asUInt()) -> 0x06.U(8.W),
      (io.din === 7.asUInt()) -> 0x07.U(8.W),
      (io.din === 8.asUInt()) -> 0x08.U(8.W),
      (io.din === 9.asUInt()) -> 0x09.U(8.W)
    )
  )
}
