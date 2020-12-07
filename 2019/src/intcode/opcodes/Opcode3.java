package intcode.opcodes;

import intcode.Opcode;
import intcode.OpcodeInfo;
import intcode.ParameterMode;
import intcode.ProgramExecutor;

public class Opcode3 extends Opcode {
    @Override
    public void run(ProgramExecutor executor) {
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
