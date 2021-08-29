import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] array = new int[length];
        Stream.iterate(0, integer -> ++integer).limit(length).forEach(i -> array[i] = scanner.nextInt());
        int number = scanner.nextInt();
        int result = Arrays.stream(array).filter(i -> i > number).sum();
        System.out.println(result);
    }
}
