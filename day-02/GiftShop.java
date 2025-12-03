import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class Range {
    long start;
    long end;

    Range(long start, long end) {
        this.start = start;
        this.end = end;
    }
}

public class GiftShop {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input.txt"));
        if (lines.isEmpty()) {
            System.out.println(0);
            return;
        }

        List<Range> ranges = new ArrayList<>();

        long minValue = Long.MAX_VALUE;
        long maxValue = Long.MIN_VALUE;
        int maxDigitLength = 0;

        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;

            for (String part : line.split(",")) {
                part = part.trim();
                if (part.isEmpty()) continue;

                String[] bounds = part.split("-");
                if (bounds.length != 2) continue;

                long a = Long.parseLong(bounds[0]);
                long b = Long.parseLong(bounds[1]);

                if (a > b) {
                    long temp = a;
                    a = b;
                    b = temp;
                }

                ranges.add(new Range(a, b));

                minValue = Math.min(minValue, a);
                maxValue = Math.max(maxValue, b);

                maxDigitLength = Math.max(maxDigitLength, digits(a));
                maxDigitLength = Math.max(maxDigitLength, digits(b));
            }
        }

        if (ranges.isEmpty()) {
            System.out.println(0);
            return;
        }

        ranges.sort(Comparator.comparingLong(r -> r.start));

        long sum = 0;
        int rangeIndex = 0;
        int maxHalfDigits = maxDigitLength / 2;

        long powerOfTen = 1;

        for (int k = 1; k <= maxHalfDigits; k++) {
            powerOfTen *= 10;       
            long block = powerOfTen;

            long smallestPrefix = block / 10;
            long largestPrefix = block - 1;  

            for (long prefix = smallestPrefix; prefix <= largestPrefix; prefix++) {

                long nr = prefix * block + prefix;

                if (nr > maxValue) {
                    System.out.println(sum);
                    return;
                }

                if (nr < minValue) {
                    continue;
                }

                while (rangeIndex < ranges.size() && ranges.get(rangeIndex).end < nr) {
                    rangeIndex++;
                }

                if (rangeIndex == ranges.size()) {
                    System.out.println(sum);
                    return;
                }

                Range current = ranges.get(rangeIndex);

                if (current.start <= nr && nr <= current.end) {
                    sum += nr;
                }
            }
        }

        System.out.println(sum);
    }

    private static int digits(long x) {
        return Long.toString(x).length();
    }
}
