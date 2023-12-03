package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class day16 extends Day {
    List<Rule> rules;
    List<Ticket> tickets;
    Ticket myTicket;

    public day16(String fileStr) {
        super(fileStr);
        rules = input.stream().map(x -> {
            Matcher matcher = Pattern.compile("^([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)$").matcher(x);
            if(matcher.matches()){
                Range r1 = new Range(Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3)));
                Range r2 = new Range(Long.parseLong(matcher.group(4)), Long.parseLong(matcher.group(5)));

                return new Rule(matcher.group(1), Arrays.asList(r1, r2));
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        tickets = input.stream().map(x -> {
            Matcher matcher = Pattern.compile("(,|^)([0-9]+)").matcher(x);
            ArrayList<Long> values = new ArrayList<>();
            while(matcher.find()) {
                values.add(Long.parseLong(matcher.group(2)));
            }

            return new Ticket(values);
        }).filter(x -> !x.isEmpty()).collect(Collectors.toList());
        myTicket = tickets.get(0);
    }

    public Long part1() {
        return getErrorValuesCount(rules, tickets);
    }

    public Long part2() {
        findFieldsPosition(rules, tickets, myTicket);
        return rules.stream().filter(x -> x.getName().startsWith("departure")).map(x -> myTicket.getField(x)).reduce(1L, (a,b) -> a * b);
    }

    private Long getErrorValuesCount(List<Rule> rules, List<Ticket> tickets) {
        return tickets.stream().map(x -> x.getErrorValue(rules)).reduce(0L, (a, b) -> a + b);
    }

    private void findFieldsPosition(List<Rule> rules, List<Ticket> tickets, Ticket myTicket) {
        List<Ticket> validTickets = tickets.stream().filter(x -> x.getErrorValue(rules) == 0).collect(Collectors.toList());

        // Find all possible positions for each field
        for (Rule field : rules) {
            for (int i = 0; i < myTicket.getValuesNumber(); i++) {
                final int position = i;
                if (validTickets.stream().allMatch(x -> field.isValid(x.getField(position)))) {
                    field.addPosition(i);
                }
            }
        }

        ArrayList<Rule> notPositioned = new ArrayList<>(rules);
        while (!notPositioned.isEmpty()) {
            Rule toPosition = notPositioned.stream().filter(Rule::isPositioned).findAny().orElse(null);
            if (toPosition != null) {
                notPositioned.remove(toPosition);
                notPositioned.forEach(x -> x.removePosition(toPosition.getPosition()));
            } else {
                break;
            }
        }
    }

    private static class Rule {
        private String name;
        private List<Range> ranges;
        private final LinkedList<Integer> positions;

        public Rule (String name, List<Range> ranges) {
            this.name = name;
            this.ranges = ranges;
            this.positions = new LinkedList<>();
        }

        public LinkedList<Integer> getPositions() {
            return this.positions;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Range> getRanges() {
            return this.ranges;
        }

        public void setRanges(List<Range> ranges) {
            this.ranges = ranges;
        }

        public boolean isValid(long value) {
            return ranges.stream().anyMatch(x -> x.isValueInto(value));
        }

        public Integer getPosition() {
            return positions.getFirst();
        }

        public void addPosition(int position) {
            positions.add(position);
        }


        public void removePosition(Integer position) {
            positions.remove(position);
        }

        public boolean isPositioned() {
            return positions.size() == 1;
        }
    }

    private static class Range {
        private long min, max;

        public Range(long min, long max) {
            this.min = min;
            this.max = max;
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public void setMax(long max) {
            this.max = max;
        }

        public boolean isValueInto(long value) {
            return value >= min && value <= max;
        }
    }

    private static class Ticket {
        private final ArrayList<Long> values;

        public Ticket (ArrayList<Long> values){
            this.values = values;
        }

        public Long getErrorValue(List<Rule> rules){
            for (Long value: values) {
                if(rules.stream().noneMatch(x -> x.isValid(value))){
                    return value;
                }
            }
            return 0L;
        }

        public boolean isEmpty() {
            return values.isEmpty();
        }

        public Long getField(int fieldPosition) {
            return values.get(fieldPosition);
        }

        public Long getField(Rule rule) {
            return values.get(rule.getPosition());
        }

        public int getValuesNumber() {
            return values.size();
        }
    }
}