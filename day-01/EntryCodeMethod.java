import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EntryCodeMethod {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input.txt"));

        int pos = 50;
        int count = 0;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] instructions = line.split("\\s+");
            for (String i : instructions) {
                if (i.isEmpty()) continue;

                char dir = Character.toUpperCase(i.charAt(0));
                int steps = Integer.parseInt(i.substring(1));

                int delta = 0;

                if (dir == 'L') {
                    delta = -1;
                } else if (dir == 'R') {
                    delta = 1;
                } 

                for (int j = 0; j < steps; j++) {
                    pos = (pos + delta) % 100;
                    if (pos < 0) {
                        pos += 100;
                    }

                    if (pos == 0) {
                        count++;
                    }
                }
            }
        }

        System.out.println("Password2 = " + count);
    }
}