class Binary {
    public static int toBinaryDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static String stringToBinary(String input) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            binaryString.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(c))));
        }
        return binaryString.toString();
    }
}
