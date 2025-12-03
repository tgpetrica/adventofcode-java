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

public class GiftShopSeq {
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

        TreeSet<Long> invalids = new TreeSet<>();

        long power = 1; 

        for (int m = 1; m < maxDigitLength; m++) {
            power *= 10;

            long prefixStart;
            if (m == 1) {
                prefixStart = 1;
            } else {
                prefixStart = power / 10;
            }
            long prefixEnd = power - 1;

            for (long prefix = prefixStart; prefix <= prefixEnd; prefix++) {
                long value = prefix;

                for (int k = 2; ; k++) {
                    int totalDigits = m * k;
                    if (totalDigits > maxDigitLength) {
                        break;
                    }

                    value = value * power + prefix;

                    if (value > maxValue) {
                        break;
                    }

                    if (value >= minValue) {
                        invalids.add(value);
                    }
                }
            }
        }

        long sum = 0L;
        int rangeIndex = 0;

        for (long id : invalids) {
            while (rangeIndex < ranges.size() && id > ranges.get(rangeIndex).end) {
                rangeIndex++;
            }

            if (rangeIndex == ranges.size()) {
                break;
            }

            Range current = ranges.get(rangeIndex);

            if (id >= current.start) {
                sum += id;
            }
        }

        System.out.println(sum);
    }

    private static int digits(long x) {
        return Long.toString(x).length();
    }
}
