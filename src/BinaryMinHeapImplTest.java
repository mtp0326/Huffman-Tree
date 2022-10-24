import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class BinaryMinHeapImplTest<Key extends Comparable<Key>, V> {

    private List<BinaryMinHeap.Entry<Key, V>> map;
    private Map<V, Integer> mapIndex;
    private List<BinaryMinHeap.Entry<Key, V>> mapExample;
    private Map<V, Integer> mapIndexExample;

    @Before
    public void setUpBinaryMinHeap() {
        mapExample = new ArrayList<>();
        mapIndexExample = new HashMap<>();

        BinaryMinHeap.Entry entry1 = new BinaryMinHeap.Entry(3, 7);
        BinaryMinHeap.Entry entry2 = new BinaryMinHeap.Entry(5, 1);
        BinaryMinHeap.Entry entry3 = new BinaryMinHeap.Entry(8, 4);

        mapExample.add(entry1);
        mapExample.add(entry2);
        mapExample.add(entry3);

        mapIndexExample.put(mapExample.get(0).value, 0);
        mapIndexExample.put(mapExample.get(1).value, 1);
        mapIndexExample.put(mapExample.get(2).value, 2);
    }

    //size()
    @Test
    public void isSizeCorrectMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        assertEquals(3, bmhi.size());
    }

    //isEmpty()
    @Test
    public void isEmptyNotCorrectMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;
        assertFalse(bmhi.isEmpty());
    }

    @Test
    public void isEmptyCorrect() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        assertTrue(bmhi.isEmpty());
    }

    //containsValue()
    @Test
    public void containsValueNull() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        assertFalse(bmhi.containsValue(null));
    }

    @Test
    public void containsValueFalseMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        BinaryMinHeap.Entry entry4 = new BinaryMinHeap.Entry(3, 3);
        assertFalse(bmhi.containsValue(entry4.value));
    }

    @Test
    public void containsValueMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        BinaryMinHeap.Entry entry4 = new BinaryMinHeap.Entry(3, 7);
        assertTrue(bmhi.containsValue(entry4.value));
    }

    //peek()
    @Test
    public void peekMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        BinaryMinHeapImpl.Entry<Key, V> entryMinRoot = new BinaryMinHeapImpl.Entry(3, 7);
        BinaryMinHeapImpl.Entry<Key, V> entryPeek = bmhi.peek();

        assertEquals(entryMinRoot.key, entryPeek.key);
        assertEquals(entryMinRoot.value, entryPeek.value);
    }

    @Test(expected = NoSuchElementException.class)
    public void peekEmpty() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();

        bmhi.peek();
    }

    //decreaseKey()
    @Test
    public void decreaseKeyMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        map = new ArrayList<>();
        mapIndex = new HashMap<>();

        BinaryMinHeap.Entry entry1 = new BinaryMinHeap.Entry(1, 1);
        BinaryMinHeap.Entry entry2 = new BinaryMinHeap.Entry(3, 7);
        BinaryMinHeap.Entry entry3 = new BinaryMinHeap.Entry(8, 4);

        map.add(entry1);
        map.add(entry2);
        map.add(entry3);

        mapIndex.put(map.get(0).value, 0);
        mapIndex.put(map.get(1).value, 1);
        mapIndex.put(map.get(2).value, 2);

        bmhi.decreaseKey(1, 1);

        BinaryMinHeapImpl.Entry<Key, V> entryMinRoot = new BinaryMinHeapImpl.Entry(1, 1);
        BinaryMinHeapImpl.Entry<Key, V> entryPeek = bmhi.peek();

        assertEquals(mapIndex, bmhi.getHeapIndex());
        assertEquals(2, bmhi.getHeapIndex().get(4));
        assertEquals(1, bmhi.getHeapIndex().get(7));
        assertEquals(0, bmhi.getHeapIndex().get(1));

        assertEquals(entryMinRoot.key, entryPeek.key);
        assertEquals(entryMinRoot.value, entryPeek.value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyErrorMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        map = new ArrayList<>();
        mapIndex = new HashMap<>();

        BinaryMinHeap.Entry entry1 = new BinaryMinHeap.Entry(3, 7);
        BinaryMinHeap.Entry entry2 = new BinaryMinHeap.Entry(5, 1);
        BinaryMinHeap.Entry entry3 = new BinaryMinHeap.Entry(8, 4);

        map.add(entry1);
        map.add(entry2);
        map.add(entry3);

        mapIndex.put(map.get(0).value, 0);
        mapIndex.put(map.get(1).value, 1);
        mapIndex.put(map.get(2).value, 2);

        bmhi.decreaseKey(1, 7);

        assertEquals(mapIndex, bmhi.getHeapIndex());
        assertEquals(2, bmhi.getHeapIndex().get(4));
        assertEquals(1, bmhi.getHeapIndex().get(7));
        assertEquals(0, bmhi.getHeapIndex().get(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void decreaseKeyDoesNotContainValue() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        bmhi.decreaseKey(2, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyNull() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        bmhi.decreaseKey(7, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyLargerNewKey() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        bmhi.decreaseKey(7, 9);
    }

    //add()
    @Test
    public void addEqualsMinHeap() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.add(5, 1);
        bmhi.add(8, 4);
        bmhi.add(3, 7);

        assertEquals(0, bmhi.getHeapIndex().get(7));
        assertEquals(1, bmhi.getHeapIndex().get(4));
        assertEquals(2, bmhi.getHeapIndex().get(1));
    }

    @Test
    public void addOrderChangeEqualsMinHeap() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.add(3, 7);
        bmhi.add(5, 1);
        bmhi.add(8, 4);

        assertEquals(0, bmhi.getHeapIndex().get(7));
        assertEquals(1, bmhi.getHeapIndex().get(1));
        assertEquals(2, bmhi.getHeapIndex().get(4));

        assertEquals(mapIndexExample, bmhi.getHeapIndex());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNull() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.add(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExistingValue() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.add(3, 7);
        bmhi.add(5, 1);
        bmhi.add(8, 4);
        bmhi.add(7, 4);
    }

    //extractMin()
    @Test
    public void extractMinOneMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        bmhi.extractMin();

        map = new ArrayList<>();
        mapIndex = new HashMap<>();

        BinaryMinHeap.Entry entry2 = new BinaryMinHeap.Entry(5, 1);
        BinaryMinHeap.Entry entry3 = new BinaryMinHeap.Entry(8, 4);

        map.add(entry2);
        map.add(entry3);

        mapIndex.put(map.get(0).value, 0);
        mapIndex.put(map.get(1).value, 1);

        BinaryMinHeapImpl.Entry<Key, V> entryMinRoot = new BinaryMinHeapImpl.Entry(5, 1);
        BinaryMinHeapImpl.Entry<Key, V> entryPeek = bmhi.peek();

        assertEquals(mapIndex, bmhi.getHeapIndex());
        assertEquals(1, bmhi.getHeapIndex().get(4));
        assertEquals(0, bmhi.getHeapIndex().get(1));

        assertEquals(entryMinRoot.key, entryPeek.key);
        assertEquals(entryMinRoot.value, entryPeek.value);
    }

    @Test
    public void extractMinBiggerMapExample() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();

        BinaryMinHeap.Entry entry4 = new BinaryMinHeap.Entry(9, 8);

        mapExample.add(entry4);
        mapIndexExample.put(mapExample.get(3).value, 3);

        bmhi.heap = mapExample;
        bmhi.heapIndex = mapIndexExample;

        bmhi.extractMin();

        map = new ArrayList<>();
        mapIndex = new HashMap<>();

        BinaryMinHeap.Entry entry2 = new BinaryMinHeap.Entry(5, 1);
        BinaryMinHeap.Entry entry3 = new BinaryMinHeap.Entry(8, 4);

        map.add(entry2);
        map.add(entry4);
        map.add(entry3);

        mapIndex.put(map.get(0).value, 0);
        mapIndex.put(map.get(1).value, 1);
        mapIndex.put(map.get(2).value, 2);

        BinaryMinHeapImpl.Entry<Key, V> entryMinRoot = new BinaryMinHeapImpl.Entry(5, 1);
        BinaryMinHeapImpl.Entry<Key, V> entryPeek = bmhi.peek();

        assertEquals(mapIndex, bmhi.getHeapIndex());
        assertEquals(2, bmhi.getHeapIndex().get(4));
        assertEquals(1, bmhi.getHeapIndex().get(8));
        assertEquals(0, bmhi.getHeapIndex().get(1));

        assertEquals(entryMinRoot.key, entryPeek.key);
        assertEquals(entryMinRoot.value, entryPeek.value);
    }

    @Test(expected = NoSuchElementException.class)
    public void extractMinEmpty() {
        BinaryMinHeapImpl bmhi = new BinaryMinHeapImpl();
        bmhi.extractMin();
    }

}
