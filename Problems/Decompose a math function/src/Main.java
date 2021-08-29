import java.util.Scanner;

class MultipleFunction {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x = scanner.nextDouble();
        System.out.println(f(x));
    }

    public static double f(double x) {
        double answer;
        if (x <= 0) {
            answer = f1(x);
        } else if (x > 0 && x < 1) {
            answer = f2(x);
        } else {
            answer = f3(x);
        }
        return answer;
    }

    //implement your methods here
    public static double f1(double x) {
        return x * x + 1;
    }

    public static double f2(double x) {
        return 1 / (x * x);
    }

    public static double f3(double x) {
        return x * x - 1;
    }

}
