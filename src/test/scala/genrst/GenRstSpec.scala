package genrst

import chiseltest._
import org.scalatest._
import chisel3._

class GenRstSpec extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior of "generate xrest sginal"
  it should "generate xreset" in {
    test(new GenRst()) { c =>
      c.clock.step(1)
      c.io.rst.expect(false.B)
      c.clock.step(15)
      c.io.rst.expect(true.B)
      c.clock.step(0xf0)
      c.io.rst.expect(false.B)
    }
  }
}
