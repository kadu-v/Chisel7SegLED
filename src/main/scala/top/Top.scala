package top

import chisel3._
import uart._
import decoder._
import genrst._

class Top() extends Module {
  val io = IO(new Bundle {
    val rx = Input(Bool())
    // val rts = Input(Bool()) // active: L, inactive : H

    val PIO0 = Output(UInt(8.W))
  })

  val genrst = Module(new GenRst())
  val urx = withReset(genrst.io.rst) { Module(new Rx(12, 115200)) }
  val dec = withReset(genrst.io.rst) { Module(new Decoder()) }
  urx.io.rx := io.rx
  dec.io.din := urx.io.dout
  io.PIO0 := dec.io.dout
}

object Elaborate extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new Top(), args)
}
