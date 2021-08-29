class ArrayOperations {
    public static void reverseElements(int[][] twoDimArray) {
        for (int i = 0; i < twoDimArray.length; i++) {
            twoDimArray[i] = reverseSubArray(twoDimArray[i]);
        }
    }

    private static int[] reverseSubArray(int[] ints) {
        int temp;
        for (int i = 0; i < ints.length / 2; i++) {
            temp = ints[i];
            ints[i] = ints[ints.length - 1 - i];
            ints[ints.length - 1 - i] = temp;
        }
        return ints;
    }

}
