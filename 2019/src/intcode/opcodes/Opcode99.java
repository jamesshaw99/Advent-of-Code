package intcode.opcodes;

import intcode.Opcode;
import intcode.OpcodeInfo;
import intcode.ProgramExecutor;

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
