import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] intArray = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int rotationCount = scanner.nextInt();
        Arrays.stream(doRightRotation(intArray, rotationCount))
            .forEach(i -> System.out.print(i + " "));

    }

    public static int[] doRightRotation(int[] intArray, int rotationCount) {
        int[] rotatedArray = new int[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            rotatedArray[ (i + rotationCount) % intArray.length] = intArray[i];
        }

        return rotatedArray;
    }
}
