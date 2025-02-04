import java.util.Scanner;

class CipherEncoder {
    private String message;
    private final Scanner scanner;

    public CipherEncoder() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            String operation = promptOperation();

            if (operation.equals("exit")) {
                System.exit(0);
            }

            processOperation(operation);
            System.out.println();
        }
    }

    private void processOperation(String operation) {
        try {
            if (operation.equals("decode")) {
                promptForInput("Input encoded string:");
                String decodedMessage = ChuckNorris.decode(this.message);
                displayMessage("Decoded string:", decodedMessage);
            } else if (operation.equals("encode")) {
                promptForInput("Input string:");
                String encodedMessage = ChuckNorris.encode(Binary.stringToBinary(this.message));
                displayMessage("Encoded string:", encodedMessage);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private String promptOperation() {
        System.out.println("Please input operation (encode/decode/exit):");

        while (true) {
            String operation = scanner.nextLine().trim().toLowerCase();
            if (operation.equals("encode") || operation.equals("decode") || operation.equals("exit")) {
                return operation;
            }
            System.out.printf("There is no '%s' operation\n\n", operation);
            System.out.println("Please input operation (encode/decode/exit):");
        }
    }

    private void promptForInput(String title) {
        System.out.println(title);
        this.message = scanner.nextLine();
    }

    private void displayMessage(String title, String message) {
        System.out.println(title);
        System.out.println(message);
    }
}
