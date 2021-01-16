package AoC.helpers.intcode.opcodes;

import AoC.helpers.intcode.Opcode;
import AoC.helpers.intcode.OpcodeInfo;
import AoC.helpers.intcode.ParameterMode;
import AoC.helpers.intcode.ProgramExecutor;

import java.util.ArrayList;
import java.util.List;

public class Opcode1 extends Opcode {
    @Override
    public void run(ProgramExecutor executor) {
        int rawCode = (int) executor.getAtPointerAndIncrement();
        List<Long> arguments = new ArrayList<>();
        List<ParameterMode> modes = ParameterMode.modesFromRaw(rawCode);

        for(int i = 0; i < getOpcodeInfo().getArgsAmount(); i++) {
            arguments.add(executor.getAtPointerAndIncrement());
        }

        executor.set(
                executor.getWriteAddress(modes.get(2), arguments.get(2)),
                executor.get(modes.get(0), arguments.get(0)) + executor.get(modes.get(1), arguments.get(1))
        );
    }

    @Override
    public OpcodeInfo getOpcodeInfo() {
        return OpcodeInfo.ADD;
    }
}
