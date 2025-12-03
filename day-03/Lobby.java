import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Lobby {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input.txt"));
        if (lines.isEmpty()) {
            System.out.println(0);
            return;
        }

        long sum = 0;

        for (String line : lines) {
            if (line == null) continue;
            String s = line.trim();
            if (s.length() < 2) continue;

            char[] digits = s.toCharArray();
            int n = digits.length;

            int[] suffixMax = new int[n];
            suffixMax[n - 1] = -1;

            for (int i = n - 2; i >= 0; i--) {
                int nextDigit = digits[i + 1] - '0';
                suffixMax[i] = Math.max(nextDigit, suffixMax[i + 1]);
            }

            int best = -1;

            for (int i = 0; i < n - 1; i++) {
                if (suffixMax[i] == -1) continue;

                int first = digits[i] - '0';
                int candidate = first * 10 + suffixMax[i];

                if (candidate > best) {
                    best = candidate;
                }
            }
            if (best >= 0) {
                sum += best;
            }
        }
        System.out.println(sum);
    }
}
