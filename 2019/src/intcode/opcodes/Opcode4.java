package intcode.opcodes;

import intcode.Opcode;
import intcode.OpcodeInfo;
import intcode.ParameterMode;
import intcode.ProgramExecutor;

public class Opcode4 extends Opcode {
    @Override
    public void run(ProgramExecutor executor) {
        int rawCode = (int) executor.getAtPointerAndIncrement();
        long argument = executor.getAtPointerAndIncrement();
        ParameterMode mode = ParameterMode.modesFromRaw(rawCode).get(0);

        executor.getIo().out(executor.get(mode, argument));
    }

    @Override
    public OpcodeInfo getOpcodeInfo() {
        return OpcodeInfo.OUTPUT;
    }
}
