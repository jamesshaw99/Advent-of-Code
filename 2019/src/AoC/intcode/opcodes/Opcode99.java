package AoC.intcode.opcodes;

import AoC.intcode.Opcode;
import AoC.intcode.OpcodeInfo;
import AoC.intcode.ProgramExecutor;

public class Opcode99 extends Opcode {
    @Override
    public void run(ProgramExecutor executor) {
        executor.stop();
    }

    @Override
    public OpcodeInfo getOpcodeInfo() {
        return OpcodeInfo.END;
    }
}
