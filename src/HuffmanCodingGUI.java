package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class HuffmanCodingGUI extends JFrame implements ActionListener {

    private final JButton encode = new JButton("Encode");
    private final JButton decode = new JButton("Decode");

    private final JTextArea inputTextArea = new JTextArea();
    private final JTextArea resultTextArea = new JTextArea("Enter text to Encode/Decode Below");

    // global variable to store the lookupTable
    private static ListArrayBased lookupTable = new ListArrayBased();

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

        encode.setIcon(new ImageIcon("assets/compress.png"));
        decode.setIcon(new ImageIcon("assets/maximize.png"));

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

    public static ListArrayBased readLetterCount(String filePath, String delimiter) {

        ListArrayBased frequencyTable = new ListArrayBased();

        try {
            Scanner scanner = new Scanner(new File(filePath));
            int i = 1;
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(delimiter);

                char character = values[0].charAt(0);
                int frequency = Integer.parseInt(values[1]);
                frequencyTable.add(i++, new TreeNode(new HuffmanSymbol(character, frequency)));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        frequencyTable.sort();

        return frequencyTable;
    }


    private static TreeNode generateHuffmanTree(ListArrayBased frequencyTable) {
        TreeNode rootNode = null;

        TreeNode first;
        TreeNode second;
        HuffmanSymbol newRootSymbol;
        while (frequencyTable.size() > 1) {

            // get the first two items from the table and cast them to a HuffmanSymbol
            first = (TreeNode) frequencyTable.get(1);
            second = (TreeNode) frequencyTable.get(2);

            // create the new root symbol with a '*' character
            // and the sum of the first two items frequency
            newRootSymbol = new HuffmanSymbol(
                    '*',
                    ((HuffmanSymbol)first.getItem()).getFrequency()
                            + ((HuffmanSymbol)second.getItem()).getFrequency()
            );

            // replace the root node with the new root symbol node
            // and use the first two items as the left and right children
            rootNode = new TreeNode(newRootSymbol);
            rootNode.setLeft(first);
            rootNode.setRight(second);

            // remove the first two items as they are now in a tree
            frequencyTable.remove(1);
            frequencyTable.remove(1);

            frequencyTable.add(frequencyTable.size()+1, rootNode); // add new root symbol to the end of the table
            frequencyTable.sort(); // sort in order to place new root symbol in its proper place
        }
        return rootNode;
    }

    /**
     * A recursive post order traversal of the huffman tree
     * @param node the current node (first call should be the root node)
     * @param binary this parameter is used to pass the binary value throughout the tree (first call should be an empty string)
     */
    public static void postOrderTraverse(TreeNode node, String binary) {

        if (node == null) {
            return;
        }

        char character = ((HuffmanSymbol)node.getItem()).getCharacter();
        if (character != '*') {
            lookupTable.add(lookupTable.size()+1, new HuffmanEncodedSymbol(character, binary));
//            System.out.printf("Character: %c, Binary: %s\n", character, binary);
            return;
        }

        postOrderTraverse(node.getLeft(), binary+"0");
        postOrderTraverse(node.getRight(), binary+"1");
    }

    /**
     * Method to generate the lookup table
     * @param rootNode the root node of the huffman tree
     */
    public static void generateLookupTable(TreeNode rootNode) {
        postOrderTraverse(rootNode, "");
        lookupTable.sort();
        for (int i = 1; i <= lookupTable.size(); i++) {
            HuffmanEncodedSymbol symbol = (HuffmanEncodedSymbol) lookupTable.get(i);
//            System.out.printf("Character: %c, Binary: %s\n", symbol.letter, symbol.binary);
        }
    }


    /**
     * encoding method based on binary search.
     * @param characters the string of characters to be encoded
     * @return the encoded value of a given character
     */
    public static String encodeCharacters(String characters) {
        StringBuilder sb = new StringBuilder();

        for (char ch : characters.toUpperCase(Locale.ROOT).toCharArray()) {

            int low = 1, high = lookupTable.size(), mid;
            while (low != high) {
                mid = (low + high) / 2;
                HuffmanEncodedSymbol midItem = ((HuffmanEncodedSymbol)lookupTable.get(mid));

                if (midItem.letter == ch) {
                    sb.append(midItem.binary);
                    break; // value found exit loop
                }

                if (ch > midItem.letter)
                    low = mid + 1;
                else
                    high = mid - 1;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        ListArrayBased frequencyTable = readLetterCount("assets/LetterCountAscending.txt", "\t");

        TreeNode rootNode = generateHuffmanTree(frequencyTable);

        generateLookupTable(rootNode);

        // start GUI
        new HuffmanCodingGUI();
    }

    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == encode) {
            inputTextArea.setText(encodeCharacters(inputTextArea.getText()));
        } else if (source == decode) {
            inputTextArea.setText("Decode \""+ inputTextArea.getText() +"\" here");
        }
    }
}
