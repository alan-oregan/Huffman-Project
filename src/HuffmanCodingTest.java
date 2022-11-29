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

    public static void testSortMethod() {
        ListArrayBased unsortedList = new ListArrayBased();
        int[] unsortedArray = {1,4,2,-1};
        int[] sortedArray = {-1,1,2,4};

        for (int i = 0; i < unsortedArray.length; i++) {
            unsortedList.add(i+1, unsortedArray[i]);
        }

        unsortedList.sort();

        for (int i = 0; i < sortedArray.length; i++) {
            assert (int)unsortedList.get(i+1) == sortedArray[i] : String.format("Array not sorted properly (%d shouldn't be at position %d, it should be %d)", (int)unsortedList.get(i+1), i+1, sortedArray[i]);
        }
    }

    public static void main(String[] args) {
        huffmanCoding = new HuffmanCoding("assets/LetterCountAscending.txt", "\t");

        System.out.println("\nTesting encodeCharacters()...");
        testEncodeCharacters();
        System.out.println("Passed");

        System.out.println("\nTesting decodeCharacters()...");
        testDecodeCharacters();
        System.out.println("Passed");

        System.out.println("Testing sorting algorithm...");
        testSortMethod();
        System.out.println("Passed");
    }
}
