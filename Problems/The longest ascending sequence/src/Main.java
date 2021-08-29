import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int currentSequence = 0;
        int longestSequence = 0;
        int prev = Integer.MIN_VALUE;
        int current;
        int length = scanner.nextInt();
        for (int i = 0; i < length; i++) {
            current = scanner.nextInt();
            if (current > prev) {
                currentSequence++;
            } else {
                if (currentSequence > longestSequence) {
                    longestSequence = currentSequence;
                }

                currentSequence = 1;
            }
            prev = current;
        }
        if (currentSequence > longestSequence) {
            longestSequence = currentSequence;
        }

        System.out.println(longestSequence);
    }
}
