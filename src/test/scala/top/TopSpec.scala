package top

import chiseltest._
import org.scalatest._
import chisel3._

class TopSpec extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior of "top"
  it should "receive 0x08" in {
    test(new Top()) { c =>
      c.clock.step(0x0f) // xrst : L
      c.clock.step(0xf0) // xrst : H
      // 104 clock
      c.io.rx.poke(true.B)
      c.clock.step(100)
      c.io.rx.poke(false.B) // start bit
      c.clock.step(104)
      c.io.rx.poke(1.B) // 1 bit 1
      c.clock.step(104)
      c.io.rx.poke(0.B) // 2 bit 0
      c.clock.step(104)
      c.io.rx.poke(1.B) // 3 bit 1
      c.clock.step(104)
      c.io.rx.poke(0.B) // 4 bit 0
      c.clock.step(104)
      c.io.rx.poke(0.B) // 5 bit 0
      c.clock.step(104)
      c.io.rx.poke(0.B) // 6 bit 0
      c.clock.step(104)
      c.io.rx.poke(0.B) // 7 bit 0
      c.clock.step(104)
      c.io.rx.poke(0.B) // 8 bit 0
      c.clock.step(104)
      c.io.rx.poke(true.B) // stop bit
      c.clock.step(104)
      c.io.PIO0.expect(0x05.asUInt())
    }
  }
}
