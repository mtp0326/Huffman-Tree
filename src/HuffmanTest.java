import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class HuffmanTest {
    private BinaryMinHeapImpl queue;
    private HashMap<Character, Integer> alphabetFreq;
    private HashMap<Character, Integer> map;
    private Huffman.TreeNode treeNode;
    private String seed;
    private String blankSeed;
    private String nullSeed;
    private String oneCharSeed;
    private int totalLengthString;

    @Before
    public void setUpHuffmanTest() {
        seed = "AAggggosAsb";
        blankSeed = "";
        nullSeed = null;
        oneCharSeed = "aaaaaa";
        map = new HashMap<>();
        map.put('1', 1);
        map.put('4', 4);
        map.put('8', 8);
        map.put('6', 6);
        map.put('7', 7);
    }

    //Huffman()
    @Test
    public void huffmanStringSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();
        assertEquals("gAsbo", treeNode.getAlphabets());
        assertEquals(11, treeNode.getFreq());
        assertEquals("g", treeNode.getLeft().getAlphabets());
        assertEquals(4, treeNode.getLeft().getFreq());
        assertEquals("0", treeNode.getLeft().getBits());
        assertEquals("Asbo", treeNode.getRight().getAlphabets());
        assertEquals(7, treeNode.getRight().getFreq());
    }

    @Test(expected = IllegalArgumentException.class)
    public void huffmanBlankSeed() {
        Huffman hf = new Huffman(blankSeed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void huffmanNullSeed() {
        Huffman hf = new Huffman(nullSeed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void huffmanOneCharSeed() {
        Huffman hf = new Huffman(oneCharSeed);
    }

    @Test
    public void huffmanMap() {
        Huffman hf = new Huffman(map);
        treeNode = hf.getTreeNode();
        assertEquals("14678", treeNode.getAlphabets());
        assertEquals(26, treeNode.getFreq());
        assertEquals("146", treeNode.getLeft().getAlphabets());
        assertEquals(11, treeNode.getLeft().getFreq());
        assertEquals("78", treeNode.getRight().getAlphabets());
        assertEquals(15, treeNode.getRight().getFreq());
    }

    @Test
    public void huffmanBitTestMap() {
        Huffman hf = new Huffman(map);
        treeNode = hf.getTreeNode();

        Huffman.TreeNode tn1 = treeNode.getLeft().getLeft().getLeft();
        assertEquals("1", tn1.getAlphabets());
        assertEquals("000", tn1.getBits());
        assertTrue(tn1.isLeaf());

        Huffman.TreeNode tn4 = treeNode.getLeft().getLeft().getRight();
        assertEquals("4", tn4.getAlphabets());
        assertEquals("001", tn4.getBits());
        assertTrue(tn4.isLeaf());

        Huffman.TreeNode tn6 = treeNode.getLeft().getRight();
        assertEquals("6", tn6.getAlphabets());
        assertEquals("01", tn6.getBits());
        assertTrue(tn6.isLeaf());

        Huffman.TreeNode tn7 = treeNode.getRight().getLeft();
        assertEquals("7", tn7.getAlphabets());
        assertEquals("10", tn7.getBits());
        assertTrue(tn7.isLeaf());

        Huffman.TreeNode tn8 = treeNode.getRight().getRight();
        assertEquals("8", tn8.getAlphabets());
        assertEquals("11", tn8.getBits());
        assertTrue(tn8.isLeaf());

    }

    @Test
    public void huffmanBitTestSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        Huffman.TreeNode tno = treeNode.getRight().getRight().getRight().getRight();
        assertEquals("o", tno.getAlphabets());
        assertEquals("1111", tno.getBits());
        assertTrue(tno.isLeaf());

        Huffman.TreeNode tnb = treeNode.getRight().getRight().getRight().getLeft();
        assertEquals("b", tnb.getAlphabets());
        assertEquals("1110", tnb.getBits());
        assertTrue(tnb.isLeaf());

        Huffman.TreeNode tns = treeNode.getRight().getRight().getLeft();
        assertEquals("s", tns.getAlphabets());
        assertEquals("110", tns.getBits());
        assertTrue(tns.isLeaf());

        Huffman.TreeNode tnA = treeNode.getRight().getLeft();
        assertEquals("A", tnA.getAlphabets());
        assertEquals("10", tnA.getBits());
        assertTrue(tnA.isLeaf());

        Huffman.TreeNode tng = treeNode.getLeft();
        assertEquals("g", tng.getAlphabets());
        assertEquals("0", tng.getBits());
        assertTrue(tng.isLeaf());

    }

    //compress()
    @Test
    public void compressSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals("101000001111110101101110", hf.compress(hf.getSeedString()));
    }

    @Test
    public void compressBlankSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals("", hf.compress(blankSeed));
    }

    @Test(expected = IllegalArgumentException.class)
    public void compressIncompressibleSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        StringBuilder sr = new StringBuilder();
        sr.append(seed);
        sr.append("f");

        assertEquals("101000001111110101101110", hf.compress(sr.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void compressNullSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals("101000001111110101101110", hf.compress(nullSeed));
    }

    //decompress()
    @Test
    public void decompressSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals(seed, hf.decompress("101000001111110101101110"));
    }

    @Test
    public void decompressBlankSeed() {
        Huffman hf = new Huffman(seed);
        assertEquals("", hf.decompress(blankSeed));
    }

    @Test(expected = IllegalArgumentException.class)
    public void decompressNonDecodableSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals(seed, hf.decompress("1010000011111101011011101"));
    }

    @Test
    public void compressDecompressSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals(seed, hf.decompress(hf.compress(hf.getSeedString())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void decompressNon01() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();

        assertEquals(seed, hf.decompress("105"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void decompressNullSeed() {
        Huffman hf = new Huffman(seed);

        assertEquals(seed, hf.decompress(null));
    }

    //compressionRatio()
    @Test
    public void compressionRatioSeedString() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();
        hf.compress(seed);
        double ratio = (double) 24.0 / (double) (11.0 * 16.0);
        assertEquals(ratio, hf.compressionRatio(), 0.0);
    }

    @Test(expected = IllegalStateException.class)
    public void compressionRatioNoCallBefore() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();
        double ratio = (double) 24.0 / (double) (11.0 * 16.0);
        assertEquals(ratio, hf.compressionRatio(), 0.0);

    }

    @Test(expected = IllegalStateException.class)
    public void compressionRatioEmptySeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();
        hf.setBinaryEncoder("");
        hf.compressionRatio();
    }

    @Test(expected = IllegalStateException.class)
    public void compressionRatioNullSeed() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();
        hf.setBinaryEncoder(null);
        double ratio = (double) 24.0 / (double) (11.0 * 16.0);
        assertEquals(ratio, hf.compressionRatio(), 0.0);
    }

    //expectedEncodingLength()
    @Test
    public void expectedEncodingLengthSeedString() {
        Huffman hf = new Huffman(seed);
        treeNode = hf.getTreeNode();
        double expectedEncoding = 2 * (3.0 / 11.0) + 1 * (4.0 / 11.0)
                + 4 * (1.0 / 11.0) + 3 * (2.0 / 11.0) + 4 * (1.0 / 11.0);
        assertEquals(expectedEncoding, hf.expectedEncodingLength(), 0.0);
    }

    @Test
    public void expectedEncodingLengthMap() {
        Huffman hf = new Huffman(map);
        treeNode = hf.getTreeNode();

        double ratio = 3 * (1.0 / 26.0) + 3 * (4.0 / 26.0)
                + 2 * (6.0 / 26.0) + 2 * (7.0 / 26.0) + 2 * (8.0 / 26.0);

        assertEquals(ratio, hf.expectedEncodingLength(), 0.0);
    }
}
