package AoC.days;

import AoC.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day8 extends Day {
    private final String pixels;
    private final int layerHeight = 6, layerWidth = 25, pixelsInlayer = layerHeight * layerWidth;
    public day8(String fileStr) {
        super(fileStr);
        pixels = input.get(0);
    }

    public long part1() {
        List<String> layers = new ArrayList<>();
        for(int i = 0; i <= pixels.length()-150; i += pixelsInlayer) {
            layers.add(pixels.substring(i, i + pixelsInlayer));
        }

        Map<Long, String> layerMap = new HashMap<>();
        for (String layer : layers) {
            Map<String, Long> collect = Arrays.stream(String.valueOf(layer).split(""))
                    .collect(
                            Collectors.groupingBy(string -> string, Collectors.counting()));
            layerMap.put( collect.get("0"), layer);
        }
        Optional min = layerMap.keySet().stream().min(Comparator.comparing(Long::intValue));
        Map<String, Long> collect = Arrays.stream(String.valueOf(layerMap.get(min.get())).split(""))
                .collect(
                        Collectors.groupingBy(string -> string, Collectors.counting()));
        return collect.get("1") * collect.get("2");
    }

    public String part2() {
        List<String> layers = new ArrayList<>();
        for (int i = 0; i <= pixels.length() - pixelsInlayer; i += pixelsInlayer){
            layers.add(pixels.substring(i, i + pixelsInlayer));
        }
        List<Integer> image = IntStream.iterate(2, i -> 2).limit(pixelsInlayer).boxed().collect(Collectors.toList());
        for(int layer = 0; layer <= layers.size() - 1; layer++) {
            for(int pixel = 0; pixelsInlayer > pixel; pixel++) {
                if(image.get(pixel) != 2){
                    continue;
                }
                image.set(pixel, Character.getNumericValue(layers.get(layer).charAt(pixel)));
            }
        }

        StringBuilder output = new StringBuilder();
        for(int x = 0; x < layerHeight ; x++) {
            for(int y = 0; y < layerWidth; y++) {
                int currentPixel = image.get(x * layerWidth + y);
                if( currentPixel == 0) {
                    output.append("  ");
                } else if(currentPixel == 1) {
                    output.append("[]");
                } else {
                    output.append("  \n");
                }
            }
            output.append("\n");
        }

        // DON'T FORGET TO ADD THE ANSWER
        return output.toString();
    }

}
