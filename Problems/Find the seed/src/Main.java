import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] values = scanner.nextLine().split("\\s+");
        final List<Integer> input  = Arrays.stream(values)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int a = input.get(0);
        int b = input.get(1);
        int n = input.get(2);
        int k = input.get(3);


        int minSeed = a;
        int minFromMax = k;

        for (int i = a; i <= b; i++) {
            Random random = new Random(i);
            int maxValue = 0;
            for (int j = 0; j < n; j++) {
                int rand = random.nextInt(k);
                if (rand > maxValue) {
                    maxValue = rand;
                }
            }
            if (minFromMax > maxValue) {
                minFromMax = maxValue;
                minSeed = i;
            } else if (minFromMax == maxValue && minSeed > i) {
                minSeed = i;
            }
        }

        System.out.println(minSeed);
        System.out.println(minFromMax);

    }
}
