package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class HuffmanCodingGUI extends JFrame implements ActionListener {

    private final JButton encode = new JButton("Encode");
    private final JButton decode = new JButton("Decode");

    private final JTextArea inputTextArea = new JTextArea();
    private final JLabel resultTextArea = new JLabel("Enter text to Encode/Decode Below");

    private static HuffmanCoding huffmanCoding = null;

    public HuffmanCodingGUI() {

        super("Huffman Code GUI");

        Font primaryFont = new Font("Arial", Font.ITALIC, 24);
        Font secondaryFont = new Font("Arial", Font.BOLD, 24);
        Color primaryColor = new Color(0,128,128);
        Color secondaryColor = new Color(49,204,224);
        Color tertiaryColor = new Color(213,213,192);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 0));

        JPanel resultPanel = new JPanel();
        resultPanel.add(resultTextArea);

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputTextArea);
        inputPanel.setLayout(new GridLayout(1,1));
        inputPanel.setOpaque(true);
        inputPanel.setBackground(tertiaryColor);

        resultTextArea.setFont(primaryFont);
        resultTextArea.setBackground(null);

        inputTextArea.setFont(primaryFont);
        inputTextArea.setBackground(null);

        encode.setIcon(new ImageIcon("assets/compress.png"));
        decode.setIcon(new ImageIcon("assets/maximize.png"));

        buttonPanel.add(encode);
        encode.setFocusPainted(false);
        encode.setFont(secondaryFont);
        encode.setBackground(secondaryColor);
        encode.addActionListener(this);

        buttonPanel.add(decode);
        encode.setFocusPainted(false);
        decode.setFont(secondaryFont);
        decode.setBackground(secondaryColor);
        decode.addActionListener(this);


        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(buttonPanel).setBackground(null);
        getContentPane().add(Box.createGlue());
        getContentPane().add(resultPanel).setBackground(null);
        getContentPane().add(Box.createGlue());
        getContentPane().add(inputPanel);

        getContentPane().setBackground(primaryColor);
        setSize(900, 600);                // 3:2 aspect ratio
        setLocationRelativeTo(null);                  // center the window
        setVisible(true);                             // set everything to visible
        setDefaultCloseOperation(EXIT_ON_CLOSE);      // stop the program on close
    }

    public static void main(String[] args) {
        huffmanCoding = new HuffmanCoding("assets/LetterCountAscending.txt", "\t");
        new HuffmanCodingGUI();
    }

    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        String inputText = inputTextArea.getText();
        String result = "";

        if (source == encode) {

            if (!inputText.isBlank())
                result = huffmanCoding.encodeCharacters(inputText);
            else
                resultTextArea.setText("Empty Input");


            if (!result.isBlank()) {
                inputTextArea.setText(result);
                resultTextArea.setText("Encoded!");

                inputText = inputText.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]","");

                if (inputText.length() > 0) {
                    resultTextArea.setText(String.format("Encoded! (Compression Ratio: %.2f%%)", (((float)result.length())/(inputText.length()*7))*100));
                }
            }
            return;
        }

        if (source == decode) {

            if (!inputText.isBlank())
                result = huffmanCoding.decodeCharacters(inputText);
            else
                resultTextArea.setText("Empty Input");


            if (!result.isBlank()) {
                inputTextArea.setText(result);
                resultTextArea.setText("Decoded!");
            }
        }
    }
}
