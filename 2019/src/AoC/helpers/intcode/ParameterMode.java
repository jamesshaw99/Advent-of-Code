package AoC.helpers.intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterMode {
    POSITION(0),
    IMMEDIATE(1),
    RELATIVE(2);

    public final int raw;

    ParameterMode(int raw) {
        this.raw = raw;
    }

    public static List<ParameterMode> modesFromRaw(int opcode){
        List<ParameterMode> modes = new ArrayList<>();
        OpcodeInfo info = OpcodeInfo.recognise(opcode);
        int amount = info.getArgsAmount();
        int modesOnly = (opcode - info.getOpcode())/100;
        int len = String.valueOf(modesOnly).length();
        List<Integer> digits = String.valueOf(modesOnly).chars().map(i -> i - '0').boxed().collect(Collectors.toList());

        for(int i = len - 1; i >= 0; i--){
            modes.add(fromRaw(digits.get(i)));
        }
        for(int i = 0; i < amount - len; i++) {
            modes.add(ParameterMode.POSITION);
        }

        return modes;
    }

    public static ParameterMode fromRaw(int raw){
        for(ParameterMode entry: ParameterMode.values()){
            if(entry.raw == raw) {
                return entry;
            }
        }
        return POSITION;
    }
}
