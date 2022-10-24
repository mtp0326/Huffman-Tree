import java.util.*;

/**
 * Implements construction, encoding, and decoding logic of the Huffman coding algorithm. Characters
 * not in the given seed or alphabet should not be compressible, and attempts to use those
 * characters should result in the throwing of an {@link IllegalArgumentException} if used in {@link
 * #compress(String)}.
 */
public class Huffman {

    private BinaryMinHeapImpl queue = new BinaryMinHeapImpl();
    private Map<Character, Integer> alphabetFreq = new HashMap<>();
    private TreeNode treeNode;
    private String seedString;
    private int totalLengthString;
    private String binaryEncoder;
    private Map<Character, String> alphabetBE = new HashMap<>();
    private int inputLengthString;
    private int binaryEncoderLengthString;

    /**
     * Constructs a {@code Huffman} instance from a seed string, from which to deduce the alphabet
     * and corresponding frequencies.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param seed the String from which to build the encoding
     * @throws IllegalArgumentException seed is null, seed is empty, or resulting alphabet only has
     *                                  1 character
     */
    public Huffman(String seed) {
        if (seed == null) {
            throw new IllegalArgumentException();
        }
        if (seed.length() == 0) {
            throw new IllegalArgumentException();
        }
        binaryEncoder = null;
        seedString = seed;

        for (int i = 0; i < seed.length(); i++) {
            char c = seed.charAt(i);
            if (alphabetFreq.containsKey(c)) {
                alphabetFreq.put(c, alphabetFreq.get(c) + 1);
            } else {
                alphabetFreq.put(c, 1);
            }
        }

        createTreeNode();
    }

    /**
     * Constructs a {@code Huffman} instance from a frequency map of the input alphabet.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param alphabet a frequency map for characters in the alphabet
     * @throws IllegalArgumentException if the alphabet is null, empty, has fewer than 2 characters,
     *                                  or has any non-positive frequencies
     */
    public Huffman(Map<Character, Integer> alphabet) {
        if (alphabet == null) {
            throw new IllegalArgumentException();
        }
        if (alphabet.isEmpty()) {
            throw new IllegalArgumentException();
        }
        binaryEncoder = null;
        alphabetFreq = alphabet;

        createTreeNode();

        Object[] alphabetArr = alphabetFreq.keySet().toArray();
        StringBuilder sString = new StringBuilder();
        for (Object o : alphabetArr) {
            if (alphabetFreq.get(o) < 1) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < alphabetFreq.get(o); j++) {
                sString.append(o);
            }
        }
        seedString = sString.toString();
    }

    private void createTreeNode() {
        Object[] alphabetArr = alphabetFreq.keySet().toArray();

        int count = 0;
        for (Object o : alphabetArr) {
            queue.add(alphabetFreq.get(o), new TreeNode(o.toString(), alphabetFreq.get(o)));
            totalLengthString += alphabetFreq.get(o);
            count++;
        }

        if (count < 2) {
            throw new IllegalArgumentException();
        }

        while (queue.size() > 1) {
            BinaryMinHeap.Entry<Integer, TreeNode> e1 = queue.extractMin();
            BinaryMinHeap.Entry<Integer, TreeNode> e2 = queue.extractMin();

            TreeNode e1t = e1.value;
            TreeNode e2t = e2.value;

            queue.add(e1.key + e2.key, new TreeNode(e1t, e2t, e1.key + e2.key));

            if (queue.size() == 1) {
                treeNode = new TreeNode(e1t, e2t, e1.key + e2.key);

                StringBuilder sb = new StringBuilder();
                recBits(treeNode, sb);
            }
        }
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public String getSeedString() {
        return seedString;
    }

    public void setBinaryEncoder(String be) {
        binaryEncoder = be;
    }

    private void recBits(TreeNode tn, StringBuilder bit) {
        if (tn.isLeaf()) {
            tn.setBit(bit.toString());
            alphabetBE.put(tn.alphabets.charAt(0), bit.toString());
        } else {
            recBits(tn.getLeft(), new StringBuilder(bit).append("0"));
            recBits(tn.getRight(), new StringBuilder(bit).append("1"));
        }
    }

    /**
     * Compresses the input string.
     *
     * @param input the string to compress, can be the empty string
     * @return a string of ones and zeroes, representing the binary encoding of the inputted String.
     * @throws IllegalArgumentException if the input is null or if the input contains characters
     *                                  that are not compressible
     */
    public String compress(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        inputLengthString += input.length();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (!alphabetFreq.containsKey(input.charAt(i))) {
                throw new IllegalArgumentException();
            }
            output.append(alphabetBE.get(input.charAt(i)));
        }

        binaryEncoderLengthString += output.toString().length();
        binaryEncoder = output.toString();
        return output.toString();
    }

    private int recCharDecompress(TreeNode tn, StringBuilder decInput, String input, int index) {
        if (tn.isLeaf()) {
            decInput.append(tn.getAlphabets());
            return index;
        } else {
            if (index == input.length()) {
                throw new IllegalArgumentException();
            }
            if (input.charAt(index) == '0') {
                return recCharDecompress(tn.getLeft(), decInput, input, index + 1);
            } else {
                return recCharDecompress(tn.getRight(), decInput, input, index + 1);
            }
        }
    }

    /**
     * Decompresses the input string.
     *
     * @param input the String of binary digits to decompress, given that it was generated by a
     *              matching instance of the same compression strategy
     * @return the decoded version of the compressed input string
     * @throws IllegalArgumentException if the input is null, or if the input contains characters
     *                                  that are NOT 0 or 1, or input contains a sequence of bits
     *                                  that is not decodable
     */
    public String decompress(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) - 48) < 0 || (input.charAt(i) - 48) > 1) {
                throw new IllegalArgumentException();
            }
        }
        TreeNode tn = treeNode;
        StringBuilder decInput = new StringBuilder();

        int index = 0;
        while (index < input.length()) {
            index = recCharDecompress(tn, decInput, input, index);
        }

        return decInput.toString();
    }

    /**
     * Computes the compression ratio so far. This is the length of all output strings from {@link
     * #compress(String)} divided by the length of all input strings to {@link #compress(String)}.
     * Assume that each char in the input string is a 16 bit int.
     *
     * @return the ratio of the total output length to the total input length in bits
     * @throws IllegalStateException if no calls have been made to {@link #compress(String)} before
     *                               calling this method
     */
    public double compressionRatio() {
        if (binaryEncoder == null || binaryEncoder.length() == 0) {
            throw new IllegalStateException();
        }
        return (double) binaryEncoderLengthString / (double) (inputLengthString * 16.0);
    }

    private double recExpectedEncodingLength(TreeNode tn) {
        double output = 0;
        if (tn.isLeaf()) {
            return (double) tn.bits.length() * ((double) tn.freq / (double) totalLengthString);
        }

        output += recExpectedEncodingLength(tn.getLeft());
        output += recExpectedEncodingLength(tn.getRight());

        return output;
    }

    /**
     * Computes the expected encoding length of an arbitrary character in the alphabet based on the
     * objective function of the compression.
     * <p>
     * The expected encoding length is simply the sum of the length of the encoding of each
     * character multiplied by the probability that character occurs.
     *
     * @return the expected encoding length of an arbitrary character in the alphabet
     */
    public double expectedEncodingLength() {
        return recExpectedEncodingLength(treeNode);
    }

    class TreeNode {
        private String alphabets;
        private TreeNode left, right;
        private boolean isLeaf;
        private String bits;
        private int freq;

        public TreeNode(String s, int freq) {
            isLeaf = true;
            alphabets = s;
            this.freq = freq;
        }

        public TreeNode(TreeNode left, TreeNode right, int freq) {
            isLeaf = false;
            this.left = left;
            this.right = right;
            StringBuilder sb = new StringBuilder();
            sb.append(left.alphabets);
            sb.append(right.alphabets);
            alphabets = sb.toString();
            this.freq = freq;
        }

        public void setBit(String input) {
            this.bits = input;
        }

        public String getBits() {
            return bits;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }

        public int getFreq() {
            return freq;
        }

        public String getAlphabets() {
            return alphabets;
        }

        public boolean isLeaf() {
            return isLeaf;
        }
    }
}