package src;

public class HuffmanEncodedSymbol implements Comparable<HuffmanEncodedSymbol> {
    char letter;
    String binary;

    public HuffmanEncodedSymbol(char letter, String binary) {
        this.letter = letter;
        this.binary = binary;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    @Override
    public String toString() {
        return "HuffmanEncodedSymbol{" +
                "letter=" + letter +
                ", binary='" + binary + '\'' +
                '}';
    }

    @Override
    public int compareTo(HuffmanEncodedSymbol that) {
        return this.letter - that.letter;
    }
}
