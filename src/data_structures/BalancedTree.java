/* 
 * Program 4 - Balanced Tree
 * CS-310
 * 7 May 2019
 * @author Hannah Juarez cssc1481
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.TreeMap;

import data_structures.DictionaryADT;

public class BalancedTree<K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V> {
	private TreeMap<K, V> tree;
	private long modificationCounter;

	// Constructor
	public BalancedTree() {
		this.tree = new TreeMap<K, V>();
		this.modificationCounter = 0;
	}

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	@Override
	public boolean contains(K key) {
		return this.tree.containsKey(key);
	}

	@Override
	public boolean add(K key, V value) {
		if (this.tree.putIfAbsent(key, value) != null)
			return false;
		this.modificationCounter++;
		return true;
	}

	@Override
	public boolean delete(K key) {
		if (this.tree.remove(key) == null)
			return false;
		this.modificationCounter++;
		return true;
	}

	@Override
	public V getValue(K key) {
		return this.tree.get(key);
	}

	@Override
	public K getKey(V value) {
		for (Entry<K, V> curr : this.tree.entrySet())
			if (curr.getValue().compareTo(value) == 0)
				return (K) curr.getKey();
		return null;
	}

	@Override
	public int size() {
		return this.tree.size();
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.tree.isEmpty();
	}

	@Override
	public void clear() {
		this.tree.clear();
	}

	@Override
	public Iterator<K> keys() {
		return new IteratorHelper<K>(IteratorHelper.KEYS);
	}

	@Override
	public Iterator<V> values() {
		return new IteratorHelper<V>(IteratorHelper.VALUES);
	}

	private class IteratorHelper<T> implements Iterator<T> {
		private static final int KEYS = 0;
		private static final int VALUES = 1;
		private Iterator<Entry<K, V>> iterator;
		private long stateCheck;
		private int target;

		public IteratorHelper(int target) {
			iterator = tree.entrySet().iterator();
			this.stateCheck = modificationCounter;
			this.target = target;
		}

		// Returns true if the list has a next item, false if not
		@Override
		public boolean hasNext() {
			if (this.stateCheck != modificationCounter)
				throw new ConcurrentModificationException();
			return this.iterator.hasNext();
		}

		// If the list has a next item, that item is returned
		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T valueToReturn = this.target == KEYS ? (T) this.iterator.next().getKey()
					: (T) this.iterator.next().getValue();
			return (T) valueToReturn;
		}

		// Unsupported operation for fail-fast iterator
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
