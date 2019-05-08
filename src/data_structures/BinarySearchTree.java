/* 
 * Program 4 - Binary Search Tree
 * CS-310
 * 7 May 2019
 * @author Hannah Juarez cssc1481
 */

package data_structures;

import java.util.Iterator;
import data_structures.DictionaryADT;

public class BinarySearchTree<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
	private long modificationCounter, entryNumber;
	private int currentSize;
	private Node<V> root = null;

	private class Node<T> {
		private Node<T> left, right;
		private K mKey;
		private V mValue;

		public Node(K key, V value) {
			this.mKey = key;
			this.mValue = value;
			this.left = this.right = null;
		}
	}

	// Default constructor
	public BinarySearchTree() {
		this.currentSize = 0;
		this.modificationCounter = this.entryNumber = 0;
	}

	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(K key, V value) {
		if (this.currentSize == 0)
		// TODO Auto-generated method stub
		this.currentSize++;
		this.modificationCounter++;
		return false;
	}

	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V getValue(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K getKey(V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.currentSize == 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
