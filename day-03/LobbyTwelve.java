import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LobbyTwelve {
    static final int DIGITS = 12;
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
            if (s.length() < DIGITS) continue;

            char[] digits = s.toCharArray();
            int n = digits.length;

            int remaining = DIGITS;
            int pos = 0;
            long number = 0;

            while (remaining > 0) {
                int maxDigit = -1;
                int maxPos = -1;
                int end = n - remaining;

                for (int i = pos; i <= end; i++) {
                    int d = digits[i] - '0';
                    if (d >maxDigit) {
                        maxDigit = d;
                        maxPos = i;
                    }
                }
                number = number * 10 + maxDigit;
                pos = maxPos + 1;
                remaining--;
            }
            sum += number;
        }
        System.out.println(sum);
    }
}
