package AoC.helpers.intcode.opcodes;

import AoC.helpers.intcode.Opcode;
import AoC.helpers.intcode.OpcodeInfo;
import AoC.helpers.intcode.ProgramExecutor;

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
