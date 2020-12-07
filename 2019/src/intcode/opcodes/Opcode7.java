package intcode.opcodes;

import intcode.Opcode;
import intcode.OpcodeInfo;
import intcode.ParameterMode;
import intcode.ProgramExecutor;

import java.util.ArrayList;
import java.util.List;

public class Opcode7 extends Opcode {
    @Override
    public void run(ProgramExecutor executor) {
        int rawCode = (int) executor.getAtPointerAndIncrement();
        List<Long> arguments = new ArrayList<>();
        List<ParameterMode> modes = ParameterMode.modesFromRaw(rawCode);

        for(int i = 0; i < getOpcodeInfo().getArgsAmount(); i++) {
            arguments.add(executor.getAtPointerAndIncrement());
        }

        if(executor.get(modes.get(0), arguments.get(0)) < executor.get(modes.get(1), arguments.get(1))){
            executor.set(executor.getWriteAddress(modes.get(2), arguments.get(2)), 1);
        } else {
            executor.set(executor.getWriteAddress(modes.get(2), arguments.get(2)), 0);
        }
    }

    @Override
    public OpcodeInfo getOpcodeInfo() {
        return OpcodeInfo.LESS_THAN;
    }
}
