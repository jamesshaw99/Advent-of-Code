package AoC.intcode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class IO {
    public static final Scanner scanner = new Scanner(System.in);
    private final List<Long> inputsLog = new ArrayList<>();
    private final List<Long> outputsLog = new ArrayList<>();
    private final ProgramExecutor executor;
    private boolean consoleInput;
    private boolean consoleOutput;
    private boolean outputInterrupt = false;
    private List<Long> inputs = new ArrayList<>();
    private Iterator<Long> inputsIterator = inputs.iterator();

    public IO(ProgramExecutor executor, boolean consoleInput, boolean consoleOutput) {
        this.executor = executor;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }

    public void out(long i) {
        if (consoleOutput) {
            System.out.println("OUT: " + i);
        }
        if(outputInterrupt) {
            executor.pause();
        }
        outputsLog.add(i);
    }

    public long in() {
        long input;
        if(!consoleInput && inputsIterator.hasNext()) {
            input = inputsIterator.next();
        } else if (!consoleInput){
            input = 99;
        } else {
            input = scanner.nextInt();
        }
        return input;
    }

    public void addInput(long input) {
        inputs.add(input);
        this.inputsIterator = this.inputs.iterator();
    }

    public void clearInputs() {
        inputs = new ArrayList<>();
    }

    public void setInputs(List<Long> inputs) {
        this.inputs = new ArrayList<>(inputs);
        this.inputsIterator = this.inputs.iterator();
    }

    public List<Long> getOutputsLog() {
        return outputsLog;
    }

    public List<Long> getInputsLog() {
        return inputsLog;
    }

    public ProgramExecutor getExecutor() {
        return executor;
    }

    public long getLastOutput() {
        return outputsLog.get(outputsLog.size() - 1);
    }

    public void enableConsoleInput(boolean consoleInput) {
        this.consoleInput = consoleInput;
    }

    public void enableConsoleOutput(boolean consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    public void enableOutputInterrupt(boolean outputInterrupt) {
        this.outputInterrupt = outputInterrupt;
    }
}
