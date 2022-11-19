package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HuffmanCodingGUI extends JFrame implements ActionListener {

    private final JButton encode = new JButton("Encode");
    private final JButton decode = new JButton("Decode");

    private final JTextArea inputTextArea = new JTextArea("Input here...");
    private final JTextArea resultTextArea = new JTextArea();

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
        resultTextArea.setEditable(false);

        inputTextArea.setFont(primaryFont);
        inputTextArea.setBackground(null);

        encode.setIcon(new ImageIcon("icons/compress.png"));
        decode.setIcon(new ImageIcon("icons/maximize.png"));

        buttonPanel.add(encode);
        encode.setFont(secondaryFont);
        encode.setBackground(secondaryColor);
        encode.addActionListener(this);

        buttonPanel.add(decode);
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
        setSize(900, 600); // 3:2 aspect ratio
        setLocationRelativeTo(null); // center the window
        setVisible(true); // set everything to visible
        setDefaultCloseOperation(EXIT_ON_CLOSE); // stop the program on close
    }

    public static void main(String[] args) {

        ListArrayBased frequencyTable = new ListArrayBased();
        int i = 1;

        try {
            Scanner scanner = new Scanner(new File("LetterCountAscending.txt"));
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split("\t");

                char character = values[0].charAt(0);
                int frequency = Integer.parseInt(values[1]);
//                System.out.printf("Char: %c, Frequency: %d\n", character, frequency); // debug
                frequencyTable.add(i++, new HuffmanSymbol(character, frequency));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        System.out.println(frequencyTable); // debug

        new HuffmanCodingGUI();
    }

    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == encode) {
            resultTextArea.setText("Encode \""+ inputTextArea.getText() +"\" here");
        } else if (source == decode) {
            resultTextArea.setText("Decode \""+ inputTextArea.getText() +"\" here");
        }
    }
}
