/*
 * Copyright: 2017, Technical University of Denmark, DTU Compute
 * Author: Martin Schoeberl (martin@jopdesign.com)
 * License: Simplified BSD License
 *
 * Lipsi, a very minimalistic processor.
 */

package lipsi

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile

/**
 * The memory for Lipsi.
 *
 * 256 byte instructions and 256 bytes data, merge into a Single-ported SRAMs
 * TODO: Add a interface to write rom-data into spram.
 *
 * As we cannot express initialized memory in Chisel (yet) we have a multiplexer between
 * memory and the instruction ROM table. Shall be substituted by a BlackBox and generated VHDL or Verilog.
 */
class Memory extends Module {
  val io = IO(new Bundle {
    val spAddr = Input(UInt(9.W))
    val rdData = Output(UInt(8.W))
    val wrEna = Input(Bool())
    val wrData = Input(UInt(8.W))
  })

  val mem = Mem(256*2, UInt(8.W))
  val rdAddrReg = RegNext(io.spAddr)

  io.rdData := mem(rdAddrReg)
  when(io.wrEna) {
    mem(io.spAddr) := io.wrData
  }

  loadMemoryFromFile(mem, "rom.hex")
}
