import java.util.*;

/**
 * @param <V>   {@inheritDoc}
 * @param <Key> {@inheritDoc}
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {

    List<Entry<Key, V>> heap;
    Map<V, Integer> heapIndex;

    // heap<Entry<Frequency, Value of Node>>
    // heapIndex<Value of Node, Index in the ArrayList>

    public BinaryMinHeapImpl() {
        heap = new ArrayList<>();
        heapIndex = new HashMap<>();
    }

    public List<Entry<Key, V>> getHeap() {
        return heap;
    }

    public Map<V, Integer> getHeapIndex() {
        return heapIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return heapIndex.containsKey(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (containsValue(value)) {
            throw new IllegalArgumentException();
        }

        heapIndex.put(value, size());
        heap.add(new Entry<>(key, value));
        decreaseKey(value, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException();
        }
        if (newKey == null) {
            throw new IllegalArgumentException();
        }
        if (newKey.compareTo(heap.get(heapIndex.get(value)).key) > 0) {
            throw new IllegalArgumentException();
        }
        int newIndex = heapIndex.get(value);
        Entry<Key, V> entry = new Entry<>(newKey, value);
        heapIndex.replace(value, newIndex);
        heap.set(newIndex, entry);
        while (newIndex > 0) {
            if (heap.get((newIndex - 1) / 2).key.compareTo(newKey) <= 0) {
                break;
            } else {
                heapIndex.replace(heap.get((newIndex - 1) / 2).value, newIndex);
                heap.set(newIndex, new Entry<>(heap.get((newIndex - 1) / 2).key,
                        heap.get((newIndex - 1) / 2).value));

                newIndex = (newIndex - 1) / 2;
                heapIndex.replace(value, newIndex);
                heap.set(newIndex, entry);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0);
    }

    private void minHeapify(int i) {
        int li = 2 * i + 1;
        int ri = 2 * i + 2;
        int smallest;

        if (li < heap.size() && heap.get(li).key.compareTo(heap.get(i).key) < 0) {
            smallest = li;
        } else {
            smallest = i;
        }

        if (ri < heap.size() && heap.get(ri).key.compareTo(heap.get(smallest).key) < 0) {
            smallest = ri;
        }

        if (smallest != i) {
            heapIndex.replace(heap.get(i).value, smallest);
            heapIndex.replace(heap.get(smallest).value, i);

            Entry entry = heap.get(i);
            heap.set(i, heap.get(smallest));
            heap.set(smallest, entry);

            minHeapify(smallest);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Entry<Key, V> removedEntry = heap.get(0);
        heapIndex.remove(heap.get(0).value);

        Entry<Key, V> entry = heap.get(size() - 1);
        Key entryKey = heap.get(size() - 1).key;
        V entryValue = heap.get(size() - 1).value;
        heapIndex.replace(entryValue, 0);
        heap.set(0, entry);
        heap.remove(size() - 1);

        minHeapify(0);
        return removedEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        return heapIndex.keySet();
    }
}