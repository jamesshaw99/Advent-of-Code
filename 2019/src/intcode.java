import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* https://github.com/vytenisu/adventofcode-2019-intcode/blob/master/index.js */

public class intcode {
    Map<String, Integer> exitCodes = new HashMap<>();
    Map<String, Integer> errorCodes = new HashMap<>();
    Map<String, Integer> registers = new HashMap<>();

    List<Integer> data = new ArrayList<>();

    public intcode(){
        this.exitCodes.put("NEED_INPUT", 0);
        this.exitCodes.put("OUTPUT", 1);
        this.exitCodes.put("TERMINATED", 2);

        this.errorCodes.put("WRONG_COMMAND_MODE", 0);
        this.errorCodes.put("WRONG_COMMAND", 1);
        this.errorCodes.put("UNEXPECTED", 2);

        this.registers.put("base", 0);
    }

    public static List<Integer> parseProgram(String rawProgram) {
        return Stream.of(rawProgram.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void run(List<Integer> list, List<Integer> input){
        this.run(list, input, 0, false);
    }
    public void run(List<Integer> list, List<Integer> input, boolean debugPrint){
        this.run(list, input, 0, debugPrint);
    }
    public void run(List<Integer> list, List<Integer> input, int pos){
        this.run(list, input, pos, false);
    }

    public void run(List<Integer> data, List<Integer> input, int pos, boolean debugPrint){
        this.data = data;

        int cmd = 0;
        List<Integer> output = new ArrayList<>();

        while(cmd != 99) {
            Map<String, Integer> cmdInfo = parseCommand(this.data.get(pos));
            cmd = cmdInfo.get("cmd");

            if(debugPrint){
                printCommand(cmdInfo, pos);
            }

            int op1 = cmdInfo.get("op1"), op2 = cmdInfo.get("op2"), op3 = cmdInfo.get("op3");

            if (cmd == 1) {
                int a = resolveValue(op1, pos + 1),
                    b = resolveValue(op2, pos + 1);

                storeValue(op3, pos + 3, a + b);
                pos += 4;
            } else if(cmd == 2) {
                int a = resolveValue(op1, pos + 1),
                    b = resolveValue(op2, pos + 1);

                storeValue(op3, pos + 3, a * b);
                pos += 4;
            } else if(cmd == 3) {
                int a = input.get(0);
                input.remove(0);

                storeValue(op1, pos + 1, a);
                pos += 2;
            } else if(cmd == 4) {
                int a = resolveValue(op1, pos + 1);

                output.add(a);
                pos += 2;
            } else if(cmd == 5) {
                int a = resolveValue(op1, pos + 1),
                    b = resolveValue(op2, pos + 2);

                if(a != 0) {
                    pos = b;
                } else {
                    pos += 3;
                }
            } else if(cmd == 6) {
                int a = resolveValue(op1, pos + 1),
                    b = resolveValue(op2, pos + 2);

                if(a == 0) {
                    pos = b;
                } else {
                    pos += 3;
                }
            } else if(cmd == 7) {
                int a = resolveValue(op1, pos + 1),
                    b = resolveValue(op2, pos + 2);

                if(a < b) {
                    storeValue(op3, pos + 3, 1);
                } else {
                    storeValue(op3, pos + 3, 0);
                }
                pos += 4;
            } else if(cmd == 8) {
                int a = resolveValue(op1, pos + 1),
                    b = resolveValue(op2, pos + 2);

                if (a == b) {
                    storeValue(op3, pos + 3, 1);
                } else {
                    storeValue(op3, pos + 3, 0);
                }
                pos += 4;
            } else if(cmd == 9) {
                int a = resolveValue(op1, pos + 1);
                this.registers.put("base", this.registers.get("base") + a);

                pos += 2;
            } else if(cmd == 99) {
                System.out.println(output);
                return;
            } else {
                throw new Error("Wrong command: " + cmd + " at position: " + pos);
            }
        }

        throw new Error("Program did not terminate correctly");
    }

    public Map<String, Integer> parseCommand(int cmd) {
        String stringCmd = String.format("%" + (5 - String.valueOf(cmd).length()) + "s",cmd).replace(" ", "0");

        List<Integer> cmdParts = Stream.of(stringCmd.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int command = (cmdParts.get(3) * 10) + cmdParts.get(4),
                op1 = cmdParts.get(2),
                op2 = cmdParts.get(1),
                op3 = cmdParts.get(0);

        Map<String, Integer> parsed = new HashMap<>();
        parsed.put("cmd", command);
        parsed.put("op1", op1);
        parsed.put("op2", op2);
        parsed.put("op3", op3);

        return parsed;
    }

    public int resolveValue(int op, int pos) {
        if(op == 1) {
            if (this.data.get(pos) != null) {
                return this.data.get(pos);
            } else {
                return 0;
            }
        } else if(op == 0) {
            if(this.data.get(pos) != null && this.data.get(this.data.get(pos)) != null) {
                return this.data.get(this.data.get(pos));
            } else if (this.data.get(0) != null){
                return this.data.get(0);
            }
            else {
                return 0;
            }
        } else if (op == 2) {
            if(this.data.get(pos) != null && this.data.get(this.data.get(pos) + this.registers.get("base")) != null) {
                return this.data.get(this.data.get(pos) + this.registers.get("base"));
            } else if (this.data.get(this.registers.get("base")) != null){
                return this.data.get(this.registers.get("base"));
            }
            else {
                return 0;
            }
        } else {
            throw new Error("Wrong command mode: " + op);
        }
    }

    public void storeValue(int op, int pos, int value) {
        if (op == 2) {
            if (this.data.get(pos) != null){
                this.data.set(this.data.get(pos) + this.registers.get("base"), value);
            } else {
                this.data.set(this.registers.get("base"), value);
            }
        } else {
            this.data.set(this.data.get(pos), value);
        }
    }

    public void printCommand(Map<String, Integer> cmdInfo, int pos) {
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("name", "ADD"); tempMap.put("arg", 3); map.put(1, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "MUL"); tempMap.put("arg", 3); map.put(2, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "PUT"); tempMap.put("arg", 1); map.put(3, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "GET"); tempMap.put("arg", 1); map.put(4, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "J>0"); tempMap.put("arg", 2); map.put(5, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "J=0"); tempMap.put("arg", 2); map.put(6, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "CLE"); tempMap.put("arg", 3); map.put(7, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "CMP"); tempMap.put("arg", 3); map.put(8, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "BAS"); tempMap.put("arg", 1); map.put(9, tempMap);
        tempMap = new HashMap<>();
        tempMap.put("name", "END"); tempMap.put("arg", 0); map.put(99, tempMap);

        String o1 = "err";
        if(cmdInfo.get("op1") == 0){
            o1 = String.valueOf(this.data.get(pos + 1));
        } else if(cmdInfo.get("op1") == 1) {
            o1 = String.valueOf(this.data.get(pos + 1));
        } else if(cmdInfo.get("op1") == 2){
            o1 = String.valueOf(this.data.get(pos + 1) + this.registers.get("base"));
        }

        String o2 = "err";
        if(cmdInfo.get("op1") == 0){
            o1 = String.valueOf(this.data.get(pos + 2));
        } else if(cmdInfo.get("op1") == 1) {
            o1 = String.valueOf(this.data.get(pos + 2));
        } else if(cmdInfo.get("op1") == 2){
            o1 = String.valueOf(this.data.get(pos + 2) + this.registers.get("base"));
        }

        String posString = String.format("%" + (4 - String.valueOf(pos).length()) + "s",pos).replace(" ", "0");
        int arg = (Integer) map.get(cmdInfo.get("cmd")).get("arg");
        System.out.println(posString + ": " + map.get(cmdInfo.get("cmd")).get("name") + " " + (arg > 0 ? o1 : "") + (arg > 1 ? o2 : "") + (arg > 2 ? this.data.get(pos + 3) : ""));
    }
}
