package uart

import chisel3._

class DetEdge extends Module {
  val io = IO(new Bundle {
    val sigin = Input(Bool())

    val posdet = Output(Bool())
    val negdet = Output(Bool())
  })

  val sig1 = RegInit(false.B)
  val sig2 = RegInit(false.B)

  sig2 := sig1
  sig1 := io.sigin

  io.posdet := (sig1 === true.B) && (sig2 === false.B)
  io.negdet := (sig1 === false.B) && (sig2 === true.B)

}
