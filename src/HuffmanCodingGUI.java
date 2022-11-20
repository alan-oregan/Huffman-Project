package src;

import com.sun.source.tree.Tree;

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

    static void printInorder(TreeNode node)
    {
        if (node == null)
            return;

        System.out.print(node.getItem()+"\n");
        printInorder(node.getLeft());
        printInorder(node.getRight());
    }

    public static void main(String[] args) {

        ListArrayBased frequencyTable = new ListArrayBased();
        TreeNode rootNode = null;

        try {
            Scanner scanner = new Scanner(new File("assets/LetterCountAscending.txt"));
            int i = 1;
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split("\t");

                char character = values[0].charAt(0);
                int frequency = Integer.parseInt(values[1]);
//                System.out.printf("Char: %c, Frequency: %d\n", character, frequency); // debug
                frequencyTable.add(i++, new TreeNode(new HuffmanSymbol(character, frequency)));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        frequencyTable.sort();
//        System.out.println(frequencyTable); // debug

        TreeNode first;
        TreeNode second;
        HuffmanSymbol newRootSymbol;
        while (frequencyTable.size() > 1) {

            // get the first two items from the table and cast them to a HuffmanSymbol
            first = (TreeNode)frequencyTable.get(1);
            second = (TreeNode)frequencyTable.get(2);

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

        printInorder(rootNode);


//        new HuffmanCodingGUI();
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
