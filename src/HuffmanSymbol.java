package src;

public class HuffmanSymbol {

    private char character;
    private int frequency;

    public HuffmanSymbol(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "HuffmanSymbol{" +
                "character=" + character +
                ", frequency=" + frequency +
                '}';
    }
}
