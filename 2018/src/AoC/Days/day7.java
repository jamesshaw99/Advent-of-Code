package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class day7 extends Day {
    Map<Character, Step> steps = new TreeMap<>();

    public day7(String fileStr) {
        super(fileStr);
        for(String line: input) {
            String[] parts = line.split(" ");
            char req = parts[1].charAt(0);
            char next = parts[7].charAt(0);

            steps.putIfAbsent(req, new Step(req));
            steps.putIfAbsent(next, new Step(next));

            steps.get(next).addRequirement(steps.get(req));
        }
    }

    public String part1(){
        StringBuilder sb = new StringBuilder();
        boolean allDone = false;
        while(!allDone){
            allDone = true;
            for(Step step: steps.values()){
                if(step.isCompleted()){
                    continue;
                }

                if(step.isAvailable()){
                    sb.append(step.getName());
                    step.setCompleted();
                    allDone = false;
                    break;
                }
            }
        }

        return "Order of steps id: " + sb.toString();
    }

    public String part2() {
        StringBuilder sb = new StringBuilder();
        boolean allDone = false;

        for(Step step: steps.values()){
            step.resetCompleted();
        }

        Dispatcher dispatcher = new Dispatcher(5);
        int timeElapsed = 0;
        allDone = false;

        while(!allDone || !dispatcher.isAllWorkDone()){
            allDone = true;

            for(final Step step: steps.values()){
                if(step.isCompleted()){
                    continue;
                }
                if(step.isAvailable() && !step.isUndergoing()){
                    allDone = false;
                    dispatcher.addWork(step);
                }
            }

            if(!dispatcher.isAllWorkDone()){
                dispatcher.processQueuedWork();
                timeElapsed++;
                dispatcher.timeTick();
            }

            if(allDone) {
                for(Step step: steps.values()){
                    if(!step.isCompleted()){
                        allDone = false;
                        break;
                    }
                }
            }
        }

        return "Time required to complete all steps: " + timeElapsed;
    }
    class Step implements Comparable<Step> {
        private final char name;
        private Set<Step> requirements = new TreeSet<>();
        private boolean completed = false, undergoing = false;

        public Step(final char name) {
            this.name = name;
        }

        public char getName(){
            return this.name;
        }

        public void addRequirement(Step step) {
            requirements.add(step);
        }

        public boolean isAvailable() {
            if(requirements.isEmpty()) {
                return true;
            }

            for(Step step: requirements) {
                if(!step.isCompleted()) {
                    return false;
                }
            }

            return true;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted() {
            completed = true;
            undergoing = false;
        }

        public void resetCompleted() {
            completed = false;
        }

        public void setUndergoing() {
            undergoing = true;
        }

        public boolean isUndergoing() {
            return undergoing;
        }

        public Step getNextStep() {
            for(Step step: requirements){
                if(!step.isAvailable()){
                    return step;
                }
            }

            return null;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Step)){
                return false;
            }

            return ((Step) o).name == name;
        }

        @Override
        public int hashCode() {
            return name;
        }

        @Override
        public int compareTo(Step o) {
            return name - o.name;
        }
    }

    interface Callback {
        void onWorkDone();
    }

    class Worker {
        private int workRemaining = 0;
        private Step step;
        private Callback callback;

        public void setWorking(Step step, Callback callback) {
            workRemaining = step.getName() - 'A' + 1 + 60;
            this.step = step;
            this.callback = callback;
            step.setUndergoing();
        }

        public void timeTick() {
            if(workRemaining > 0) {
                workRemaining--;
            }
            if(workRemaining == 0) {
                if(step != null) {
                    step.setCompleted();
                }
                if(callback != null) {
                    callback.onWorkDone();
                    callback = null;
                }
            }
        }

        public boolean isAvailable() {
            return workRemaining == 0;
        }

        public char getWork() {
            return workRemaining > 0 ? step.getName() : '.';
        }
    }

    class Dispatcher {
        private Collection<Worker> workers = new ArrayList<>();
        private Set<Step> workToDo = new HashSet<>();

        public Dispatcher(final int numWorkers) {
            for(int i = 0; i < numWorkers; i++){
                workers.add(new Worker());
            }
        }

        public void addWork(Step step) {
            step.setUndergoing();
            workToDo.add(step);
        }

        public boolean isWorkerAvailable() {
            return getAvailableWorker() != null;
        }

        public Worker getAvailableWorker() {
            for(Worker worker: workers){
                if(worker.isAvailable()){
                    return worker;
                }
            }
            return null;
        }

        public void processQueuedWork() {
            if(!workToDo.isEmpty()) {
                Iterator<Step> iter = workToDo.iterator();
                Worker worker = getAvailableWorker();
                while(worker != null && iter.hasNext()) {
                    Step step = iter.next();
                    worker.setWorking(step, null);
                    iter.remove();
                    worker = getAvailableWorker();
                }
            }
        }

        public void timeTick() {
            for(Worker worker: workers) {
                worker.timeTick();
            }
        }

        public boolean isAllWorkDone() {
            if(!workToDo.isEmpty()) {
                return false;
            }

            for(Worker worker: workers) {
                if(!worker.isAvailable()){
                    return false;
                }
            }

            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for(Worker worker: workers) {
                if(first) {
                    first = false;
                }
                sb.append(" ");
                sb.append(worker.getWork());
            }

            return sb.toString();
        }
    }
}

