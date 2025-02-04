import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CipherEncoder cipherEncoder = new CipherEncoder();
        cipherEncoder.run();
    }
}

class CipherEncoder {

    private String message;

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String operation = promptOperation(scanner);
            StringBuilder title = new StringBuilder();
            StringBuilder message = new StringBuilder();

            switch (operation) {
                case "decode":
                    promptForInput("Input encoded string:", scanner);
                    title.append("Decoded string:");
                    message.append(ChuckNorris.decode(this.message));
                    break;
                case "encode":
                    promptForInput("Input string:", scanner);
                    title.append("Encoded string:");
                    message.append(ChuckNorris.encode(Binary.stringToBinary(this.message)));
                    break;
                default:
                    System.exit(0);
                    break;
            }

            displayMessage(title.toString(), message.toString());
            System.out.println();
        }

    }

    private String promptOperation(Scanner scanner) {
        System.out.println("Please input operation (encode/decode/exit):");

        String operation = scanner.nextLine();
        while(true) {
            if (Objects.equals(operation, "encode") || Objects.equals(operation, "decode") || Objects.equals(operation, "exit")) {
                return operation;
            }

            System.out.printf("There is no '%s' operation\n\n", operation);

            System.out.println("Please input operation (encode/decode/exit):");
            operation = scanner.nextLine();
        }
    }

    private void promptForInput(String title, Scanner scanner) {
        System.out.println(title);
        this.message = scanner.nextLine();
    }

    private void displayMessage(String title, String message) {
        System.out.println(title);
        System.out.println(message);
    }
}

class ChuckNorris {
    public static String encode(String bits) {
        if (bits.isEmpty()) {
            return "";
        }

        StringBuilder encoded = new StringBuilder();
        char currentChar = bits.charAt(0);
        int count = 1;

        for (int i = 1; i < bits.length(); i++) {
            if (bits.charAt(i) != currentChar) {
                encoded.append(getCode(currentChar, count)).append(" ");
                currentChar = bits.charAt(i);
                count = 1;
            } else {
                count++;
            }
        }
        encoded.append(getCode(currentChar, count)); // Add te last block

        return encoded.toString();
    }

    private static String getCode(char bit, int quantity) {
        StringBuilder code = new StringBuilder();

        code.append((bit == '0') ? "00 " : "0 ");

        code.append("0".repeat(quantity));

        return code.toString();
    }

    public static String decode(String encoded) {
        if (encoded.isEmpty()) {
            throw new IllegalArgumentException("Encoded string is not valid.");
        }

        if (!encoded.matches("[0 ]*")) {
            throw new IllegalArgumentException("Encoded string is not valid.");
        }

        if (!encoded.startsWith("0 ") && !encoded.startsWith("00 ")) {
            throw new IllegalArgumentException("Encoded string is not valid.");
        }

        StringBuilder text = new StringBuilder();
        ArrayList<String> binaries = toCodeBinary(encoded);

        for (String binary : binaries) {
            if(binary.length() != 7) {
                throw new IllegalArgumentException("Encoded string is not valid.");
            }

            text.append((char) Binary.toBinaryDecimal(binary));
        }

        return text.toString();
    }

    private static boolean validate(String encoded) {
        if (encoded.isEmpty()) {
            return false;
        }

        if (!encoded.matches("[0 ]*")) {
            return false;
        }

        if (!encoded.startsWith("0 ") && !encoded.startsWith("00 ")) {
            return false;
        }

        return true;
    }

    private static ArrayList<String> toCodeBinary(String encoded) {

        String[] encodedArray = encoded.split(" ");

        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < encodedArray.length - 1; ) {
            if (Objects.equals(encodedArray[i], "0")) {
                binary.append("1".repeat(encodedArray[i+1].length()));
            } else {
                binary.append("0".repeat(encodedArray[i+1].length()));
            }
            i += 2;
        }

        return split7bits(binary.toString());
    }

    private static ArrayList<String> split7bits(String encoded) {
        ArrayList<String> binaryList = new ArrayList<>();

        for (int i = 0; i < encoded.length();) {
            String substring = encoded.substring(i, Math.min(i + 7, encoded.length()));
            binaryList.add(substring);
            i += 7;
        }

        return binaryList;
    }
}

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