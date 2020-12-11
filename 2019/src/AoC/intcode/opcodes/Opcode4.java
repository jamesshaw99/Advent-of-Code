package AoC.intcode.opcodes;

import AoC.intcode.Opcode;
import AoC.intcode.OpcodeInfo;
import AoC.intcode.ParameterMode;
import AoC.intcode.ProgramExecutor;

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
