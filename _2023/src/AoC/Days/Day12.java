package AoC.Days;

import AoC.Helpers.Day;
import AoC.Helpers.Record;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 extends Day {
    private final Map<String, Long> cache = new HashMap<>();

    public Day12(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        return "Total arrangements: " + findResults(false);
    }

    public String part2() {
        return "Total arrangements: " + findResults(true);
    }

    public long findResults(boolean part2) {
        List<Record> records = buildRecordList();

        Optional<Long> result = records.stream().map(record -> {
            if(part2) {
                record.unfoldSpringData(5);
                return checkDamagedSpringData(record.getRecordData(), record.getRecordRules());
            } else {
                List<List<Character>> possibleValues = generateCombinations(record.getRecordData(), record.getRecordRules());
                return possibleValues.stream().filter(possibleRecord -> isRecordValidSoFar(possibleRecord, record.getRecordRules())).count();
            }
        }).reduce(Long::sum);

        return result.orElse(0L);
    }

    protected boolean isRecordValidSoFar(List<Character> record, LinkedList<Integer> damagedSprings) {
        LinkedList<Integer> localDamagedSpringRules = new LinkedList<>(damagedSprings);
        boolean validRecord = true;
        boolean inSpringConnection = false;
        Integer currentCheck = localDamagedSpringRules.isEmpty() ? null : localDamagedSpringRules.poll();

        for (Character character : record) {
            if (character == '?') return true;

            if (currentCheck == 0) {
                inSpringConnection = false;
                if (character == '.') {
                    if (!localDamagedSpringRules.isEmpty()) {
                        currentCheck = localDamagedSpringRules.poll();
                    }
                } else {
                    validRecord = false;
                    break;
                }
            } else {
                if (character == '.') {
                    if (inSpringConnection) {
                        validRecord = false;
                        break;
                    }
                    continue;
                }

                if (character == '#') {
                    currentCheck = currentCheck - 1;
                    inSpringConnection = true;
                } else {
                    validRecord = false;
                    break;
                }
            }
        }

        return validRecord && localDamagedSpringRules.isEmpty() && currentCheck == 0;
    }

    protected List<List<Character>> generateCombinations(List<Character> brokenRecordData, LinkedList<Integer> rules) {
        Optional<Integer> indexOfMissingValue = IntStream.range(0, brokenRecordData.size())
                .filter(i -> brokenRecordData.get(i) == '?')
                .boxed()
                .findFirst();

        if (indexOfMissingValue.isEmpty()) {
            return new ArrayList<>(List.of(brokenRecordData));
        }

        ArrayList<List<Character>> result = new ArrayList<>();
        if (isRecordValidSoFar(brokenRecordData, rules)) {
            List<List<Character>> result1 = generateCombinations(replaceChar(brokenRecordData, '.', indexOfMissingValue.get()), rules);
            List<List<Character>> result2 = generateCombinations(replaceChar(brokenRecordData, '#', indexOfMissingValue.get()), rules);
            result.addAll(result1);
            result.addAll(result2);
        }

        return result;
    }

    protected List<Character> replaceChar(List<Character> brokenRecordData, Character character, int i) {
        List<Character> copyRecordSpring = new ArrayList<>(brokenRecordData);
        copyRecordSpring.set(i, character);
        return copyRecordSpring;
    }

    protected List<Record> buildRecordList() {
        return input.stream().map(stringValue -> {
            String[] parts = stringValue.split(" ");
            List<Character> recordData = parts[0].chars().mapToObj(c -> (char) c).toList();

            LinkedList<Integer> recordRules = Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(LinkedList::new));

            return new Record(recordData, recordRules);
        }).toList();
    }

    protected long checkDamagedSpringData(List<Character> record, List<Integer> damagedSprings) {
        long result = 0;
        String cacheKey = record.toString() + damagedSprings.toString();

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        if (damagedSprings.isEmpty()) {
            long computedResult = record.contains('#') ? 0 : 1;
            cache.put(cacheKey, computedResult);
            return computedResult;
        }

        int current = damagedSprings.get(0);
        List<Integer> remainingSprings = damagedSprings.subList(1, damagedSprings.size());

        for (int i = 0; i < record.size() - sum(remainingSprings) - remainingSprings.size() - current + 1; i++) {
            if (record.subList(0, i).contains('#')) {
                break;
            }

            int nxt = i + current;
            if (nxt <= record.size() && record.subList(i, nxt).stream().noneMatch(ch -> ch == '.') &&
                    (nxt == record.size() || record.get(nxt) != '#')) {
                List<Character> nextRecord = nxt + 1 < record.size() ? record.subList(nxt + 1, record.size()) : List.of();
                result += checkDamagedSpringData(nextRecord, remainingSprings);
            }
        }

        cache.put(cacheKey, result);
        return result;
    }

    public static int sum(List<Integer> values) {
        return values.stream().reduce(Integer::sum).orElse(0);
    }


}