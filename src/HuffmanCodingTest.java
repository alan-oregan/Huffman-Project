package src;

public class HuffmanCodingTest {

    public static HuffmanCoding huffmanCoding;

    public static void testEncodeCharacters() {
        for (int i = 1; i < huffmanCoding.getLookupTable().size(); i++) {
            HuffmanEncodedSymbol symbol = (HuffmanEncodedSymbol) huffmanCoding.getLookupTable().get(i);
            assert symbol.binary.equals(huffmanCoding.encodeCharacters(Character.toString(symbol.letter))) : String.format("%c should equal %s\n", symbol.letter, symbol.binary);
            System.out.printf("%c == %-10s\n", symbol.letter, symbol.binary);
        }
    }

    public static void testDecodeCharacters() {
        for (int i = 1; i < huffmanCoding.getLookupTable().size(); i++) {
            HuffmanEncodedSymbol symbol = (HuffmanEncodedSymbol) huffmanCoding.getLookupTable().get(i);
            assert symbol.letter == huffmanCoding.decodeCharacters(symbol.binary).charAt(0) : String.format("%s should equal %c\n", symbol.binary, symbol.letter);
            System.out.printf("%-10s == %c\n", symbol.binary, symbol.letter);
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
