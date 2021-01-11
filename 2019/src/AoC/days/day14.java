package AoC.days;

import AoC.Day;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day14 extends Day {
    private static Map<String, Set<Resource>> resources;
    private static final Map<String, Long> productionMultiplicity = new HashMap<>();

    public day14(String fileStr) {
        super(fileStr);
        resources = input.stream()
                .map(StringUtils::deleteWhitespace)
                .collect(Collectors.toMap(day14::getResource, day14::getRequiredResources));
    }

    public String part1() {
        return String.valueOf(part1(1));
    }

    public String part2() {
        return String.valueOf(part2(1E12));
    }

    public static Long part1(int quantity) {
        return resources.get("FUEL").stream()
                .mapToLong(getORENeed(resources, new HashMap<>(), quantity))
                .sum();
    }

    private static long part2(double totalOre) {
        int low = 0, mid = -1, high = (int)1E12;
        while(low <= high) {
            mid = (low+high)>>>1;
            long midVal = part1(mid);
            if(midVal < totalOre) {
                low = mid+1;
            } else if (midVal > totalOre){
                high = mid-1;
            } else {
                return mid;
            }
        }
        return mid;
    }

    private static ToLongFunction<Resource> getORENeed(final Map<String, Set<Resource>> resources, final Map<String, Long> leftovers, int produceCount) {
        return resource -> {
            if ("ORE".equals(resource.getResource())) {
                return produceCount * resource.getNeed();
            } else {
                long quantityNeed = produceCount * resource.getNeed() - leftovers.getOrDefault(resource.getResource(), 0L);
                long productionMultiplicity = day14.productionMultiplicity.get(resource.getResource());
                int create = (int) Math.ceil(quantityNeed / (double) productionMultiplicity);
                long leftover = productionMultiplicity * create - quantityNeed;
                leftovers.compute(resource.getResource(), (key, oldValue) -> leftover == 0 ? null : leftover);
                return resources.get(resource.getResource()).stream().mapToLong(getORENeed(resources, leftovers, create)).sum();
            }
        };
    }

    private static Set<Resource> getRequiredResources(String g) {
        return Stream.of(g.split("=>")[0])
                .map(resource -> resource.split(","))
                .flatMap(Stream::of)
                .map(Resource::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static String getResource(String g) {
        Resource resource = new Resource(g.split("=>")[1]);
        productionMultiplicity.put(resource.getResource(), resource.getNeed());
        return resource.getResource();
    }

    private static class Resource {
        private final String resource;
        private final long need;

        private Resource(String resourceData) {
            this.need = Integer.parseInt(resourceData.replaceAll("[^\\d]", ""));
            this.resource = resourceData.replaceAll("[\\d]", "");
        }

        private String getResource() {
            return this.resource;
        }

        private long getNeed() {
            return this.need;
        }

        @Override
        public boolean equals(Object o) {
            Resource r = (Resource)o;
            return this.resource.equals(r.resource);
        }

        @Override
        public int hashCode() {
            return Objects.hash(resource, need);
        }
    }
}
