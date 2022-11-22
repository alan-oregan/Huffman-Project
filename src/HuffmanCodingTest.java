package src;

public class HuffmanCodingTest {

    public static HuffmanCoding huffmanCoding;

    public static void testEncodeCharacters() {
        for (int i = 1; i < huffmanCoding.getLookupTable().size(); i++) {
            HuffmanEncodedSymbol symbol = (HuffmanEncodedSymbol) huffmanCoding.getLookupTable().get(i);
            System.out.printf("%c == %-10s %b\n", symbol.letter, symbol.binary, symbol.binary.equals(huffmanCoding.encodeCharacters(Character.toString(symbol.letter))));
        }
    }

    public static void testDecodeCharacters() {
        for (int i = 1; i < huffmanCoding.getLookupTable().size(); i++) {
            HuffmanEncodedSymbol symbol = (HuffmanEncodedSymbol) huffmanCoding.getLookupTable().get(i);
            System.out.printf("%-10s == %c %b\n", symbol.binary, symbol.letter, symbol.letter == huffmanCoding.decodeCharacters(symbol.binary).charAt(0));
        }
    }

    public static void main(String[] args) {
        huffmanCoding = new HuffmanCoding("assets/LetterCountAscending.txt", "\t");

        System.out.println("\nTesting encodeCharacters():");
        testEncodeCharacters();

        System.out.println("\nTesting decodeCharacters():");
        testDecodeCharacters();
    }
}
