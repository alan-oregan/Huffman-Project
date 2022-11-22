package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class HuffmanCoding {

    public final ListArrayBased frequencyTable = new ListArrayBased();
    public final ListArrayBased lookupTable = new ListArrayBased();
    private TreeNode rootNode = null;

    public HuffmanCoding(String filePath, String delimiter) {
        readLetterCount(filePath, delimiter);
        generateHuffmanTree();
        generateLookupTable();
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public ListArrayBased getLookupTable() {
        return lookupTable;
    }

    private void readLetterCount(String filePath, String delimiter) {
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
    }


    private void generateHuffmanTree() {

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
    }

    /**
     * A recursive post order traversal of the huffman tree
     * @param node the current node (first call should be the root node)
     * @param binary this parameter is used to pass the binary value throughout the tree (first call should be an empty string)
     */
    private void postOrderTraverse(TreeNode node, String binary) {

        if (node == null) {
            return;
        }

        char character = ((HuffmanSymbol)node.getItem()).getCharacter();
        if (character != '*') {
            lookupTable.add(lookupTable.size()+1, new HuffmanEncodedSymbol(character, binary));
            return;
        }

        postOrderTraverse(node.getLeft(), binary+"0");
        postOrderTraverse(node.getRight(), binary+"1");
    }

    /**
     * Method to generate the lookup table
     */
    private void generateLookupTable() {
        postOrderTraverse(rootNode, "");
        lookupTable.sort();
    }

    /**
     * encoding method based on binary search.
     * @param characters the string of characters to be encoded
     * @return the encoded value of given characters
     */
    public String encodeCharacters(String characters) {
        StringBuilder sb = new StringBuilder();
        // validate input as only capital letters from A-Z
        characters = characters.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]","");

        for (char ch : characters.toCharArray()) {

            int low = 1, high = lookupTable.size(), mid;
            while (low <= high) {
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

    /**
     * Decoding method based on binary search.
     * @param characters the string of characters to be decoded
     * @return the decoded value of given characters
     */
    public String decodeCharacters(String characters) {
        StringBuilder sb = new StringBuilder();
        TreeNode currentNode = rootNode;
        // validate characters as only numbers 0 and 1
        characters = characters.toUpperCase(Locale.ROOT).replaceAll("[^01]","");

        for (char ch : characters.toCharArray()) {

            char character = ((HuffmanSymbol) currentNode.getItem()).getCharacter();

            if (character != '*') {
                sb.append(character);
                currentNode = rootNode;
            }

            currentNode = switch (ch) {
                case '0' -> currentNode.getLeft();
                case '1' -> currentNode.getRight();
                default -> currentNode;
            };
        }
        if (((HuffmanSymbol) currentNode.getItem()).getCharacter() != '*')
            sb.append(((HuffmanSymbol) currentNode.getItem()).getCharacter());

        return sb.toString();
    }
}
