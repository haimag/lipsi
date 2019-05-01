/*
 * Copyright: 2017, Technical University of Denmark, DTU Compute
 * Author: Martin Schoeberl (martin@jopdesign.com)
 * License: Simplified BSD License
 *
 * Test Lipsi.
 */

package lipsi

import lipsi.sim._
import chisel3._
import chisel3.iotesters.PeekPokeTester

class LipsiCoSim(dut: Lipsi, prg: Array[Int]) extends PeekPokeTester(dut) {

  val lsim = new LipsiSim(prg)

  var run = true
  var maxInstructions = 300
  step(2)

  while(run) {

    expect(dut.io.dbg.pc, lsim.pc, "PC shall be equal.\n")
    expect(dut.io.dbg.accu, lsim.accu, "Accu shall be equal.\n")

    step(1)
    lsim.step()
    maxInstructions -= 1
    run = peek(dut.io.dbg.exit) == 0 && maxInstructions > 0
  }
  expect(dut.io.dbg.accu, 0, "Accu shall be zero at the end of a test case.\n")
}

object LipsiCoSim {
  def main(args: Array[String]): Unit = {
    println("\n>>> Co-simulation of Lipsi")
    val prog = lipsi.util.Assembler.getProgram(args(0))

    iotesters.Driver.execute(Array[String](), () => new Lipsi(args(0))) {
      c => new LipsiCoSim(c, prog)
    }

    /* Chisel 2
    chiselMainTest(Array("--genHarness", "--test", "--backend", "c",
      "--compile", "--vcd", "--targetDir", "generated"),
      () => Module(new Lipsi(args(0)))) {
        f => new LipsiCoSim(f, args(0))
      }
      */
  }
}
