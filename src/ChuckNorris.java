import java.util.ArrayList;

class ChuckNorris {
    public static String encode(String bits) {
        if (bits.isEmpty()) return "";

        StringBuilder encoded = new StringBuilder();
        char currentChar = bits.charAt(0);
        int count = 1;

        for (int i = 1; i < bits.length(); i++) {
            if (bits.charAt(i) != currentChar) {
                encoded.append(getEncodedBlock(currentChar, count)).append(" ");
                currentChar = bits.charAt(i);
                count = 1;
            } else {
                count++;
            }
        }
        encoded.append(getEncodedBlock(currentChar, count));

        return encoded.toString();
    }

    private static String getEncodedBlock(char bit, int quantity) {
        return (bit == '0' ? "00 " : "0 ") + "0".repeat(quantity);
    }

    public static String decode(String encoded) {
        if (!isValidEncodedString(encoded)) {
            throw new IllegalArgumentException("Encoded string is not valid.");
        }

        StringBuilder decodedText = new StringBuilder();
        try {
            for (String binary : convertToBinaryBlocks(encoded)) {
                if (binary.length() != 7) {
                    throw new IllegalArgumentException("Encoded string is not valid.");
                }
                decodedText.append((char) Binary.toBinaryDecimal(binary));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Encoded string is not valid.");
        }

        return decodedText.toString();
    }

    private static boolean isValidEncodedString(String encoded) {
        return encoded.matches("[0 ]*") && (encoded.startsWith("0 ") || encoded.startsWith("00 "));
    }

    private static ArrayList<String> convertToBinaryBlocks(String encoded) {
        String[] tokens = encoded.split(" ");
        StringBuilder binaryBuilder = new StringBuilder();

        for (int i = 0; i < tokens.length - 1; i += 2) {
            if (!tokens[i].equals("0") && !tokens[i].equals("00")) {
                throw new IllegalArgumentException("Encoded string is not valid.");
            }
            binaryBuilder.append(tokens[i].equals("0") ? "1".repeat(tokens[i + 1].length()) : "0".repeat(tokens[i + 1].length()));
        }

        return splitInto7BitChunks(binaryBuilder.toString());
    }

    private static ArrayList<String> splitInto7BitChunks(String binaryString) {
        ArrayList<String> binaryList = new ArrayList<>();
        for (int i = 0; i < binaryString.length(); i += 7) {
            binaryList.add(binaryString.substring(i, Math.min(i + 7, binaryString.length())));
        }
        return binaryList;
    }
}
