package AoC.helpers.intcode.opcodes;

import AoC.helpers.intcode.Opcode;
import AoC.helpers.intcode.OpcodeInfo;
import AoC.helpers.intcode.ParameterMode;
import AoC.helpers.intcode.ProgramExecutor;

public class Opcode3 extends Opcode {
    @Override
    public void run(ProgramExecutor executor) throws InterruptedException {
        int rawCode = (int) executor.getAtPointerAndIncrement();
        long argument = executor.getAtPointerAndIncrement();
        ParameterMode mode = ParameterMode.modesFromRaw(rawCode).get(0);

        executor.set(executor.getWriteAddress(mode, argument), executor.getIo().in());
    }

    @Override
    public OpcodeInfo getOpcodeInfo(){
        return OpcodeInfo.INPUT;
    }
}
