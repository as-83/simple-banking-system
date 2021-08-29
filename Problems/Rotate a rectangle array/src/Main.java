import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

class Main {
    private static int[][] matrix;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        initMatrix();
        fillMatrix();
        printRotated();
    }

    private static void initMatrix() {
        int[] size = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int height = size[0];
        int width = size[1];
        matrix = new int[height][width];
    }


    private static void fillMatrix() {
        Stream.iterate(0, n -> ++n)
                .limit(matrix.length)
                .forEach(i -> matrix[i] = Arrays.stream(scanner.nextLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray());
    }

    private static void printRotated() {
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = matrix.length - 1; i > -1; i--) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}
