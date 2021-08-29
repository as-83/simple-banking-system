import java.util.Scanner;

class Main {
    private static int[][] matrix;
    public static void main(String[] args) {
        initMatrix();
        fillMatrix();
        printMatrix();
    }

    private static void initMatrix() {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        matrix = new int[size][size];
    }


    private static void fillMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = Math.abs(j - i);
            }
        }
    }

    private static void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
                if (j < matrix.length - 1) {
                    System.out.print(" ");
                }
            }
            if (i < matrix.length - 1) {
                System.out.println();
            }
        }
    }


}
