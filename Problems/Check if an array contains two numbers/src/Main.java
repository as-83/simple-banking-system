import java.util.Arrays;
import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());
        int[] array = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] closeNumbers = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        boolean check = false;
        for (int i = 0; i < size - 1; i++) {
            boolean firstThenSecond = array[i] == closeNumbers[0] && array[i + 1] == closeNumbers[1];
            boolean secondThenFirst = array[i] == closeNumbers[1] && array[i + 1] == closeNumbers[0];
            if (firstThenSecond || secondThenFirst) {
                check = true;
                break;
            }
        }

        System.out.println(check);



    }
}
