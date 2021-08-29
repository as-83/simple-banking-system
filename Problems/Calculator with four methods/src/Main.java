class SimpleCalculator {
    // Implement your methods here
    public static void subtractTwoNumbers(long a, long b) {
        System.out.println(a - b);
    }


    public static void sumTwoNumbers(long a, long b) {
        System.out.println(a + b);
    }


    public static void divideTwoNumbers(long a, long b) {
        if (b != 0) {
            System.out.println(a / b);
        } else {
            System.out.println("Division by 0!");
        }
    }


    public static void multiplyTwoNumbers(long a, long b) {
        System.out.println(a * b);
    }
}
