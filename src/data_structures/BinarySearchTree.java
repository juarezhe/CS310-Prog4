/* 
 * Program 4 - Binary Search Tree
 * CS-310
 * 7 May 2019
 * @author Hannah Juarez cssc1481
 * 
 * Contains code adapted from "Lecture Notes & Supplementary Material" by Riggins, Alan
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.DictionaryADT;

public class BinarySearchTree<K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V> {
	private long modificationCounter;
	private int currentSize;
	private Node<K, V> root;

	@SuppressWarnings("hiding")
	private class Node<K, V> {
		private Node<K, V> left, right;
		private K key;
		private V value;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.left = this.right = null;
		}
	}

	// Constructor
	public BinarySearchTree() {
		this.root = null;
		this.currentSize = 0;
		this.modificationCounter = 0;
	}

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	@Override
	public boolean contains(K key) {
		return getValue(key) != null;
	}

	// Code adapted from "Lecture Notes & Supplementary Material" by Riggins, Alan
	@Override
	public boolean add(K key, V value) {
		if (this.isEmpty())
			this.root = new Node<K, V>(key, value);
		else if (!this.add(key, value, root, null, false))
			return false;
		this.currentSize++;
		this.modificationCounter++;
		return true;
	}

	// Adds the given key/value pair to the dictionary. Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	// Code adapted from "Lecture Notes & Supplementary Material" by Riggins, Alan
	private boolean add(K key, V value, Node<K, V> node, Node<K, V> parent, boolean wasLeft) {
		if (node == null) {
			if (wasLeft)
				parent.left = new Node<K, V>(key, value);
			else
				parent.right = new Node<K, V>(key, value);
			return true;
		}
		if (((Comparable<K>) key).compareTo((K) node.key) < 0)
			return add(key, value, node.left, node, true);
		if (((Comparable<K>) key).compareTo((K) node.key) > 0)
			return add(key, value, node.right, node, false);
		return false;
	}

	@Override
	public boolean delete(K key) {
		if (!this.delete(key, this.root, null, false))
			return false;
		this.currentSize--;
		this.modificationCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	private boolean delete(K key, Node<K, V> node, Node<K, V> parent, boolean wasLeft) {
		if (node == null)
			return false;
		if (((Comparable<K>) key).compareTo((K) node.key) < 0)
			return this.delete(key, node.left, node, true);
		if (((Comparable<K>) key).compareTo((K) node.key) > 0)
			return this.delete(key, node.right, node, false);
		if(parent == null) {
			node = null;
			return true;
		}
		if (node.left != null) {
			if (node.right != null) { // two children
				Node<K, V> currParent = node;
				Node<K, V> curr = node.left;
				
				while (curr.right != null) {
					currParent = curr;
					curr = curr.right;
				}
				currParent.right = curr.left;
				if (wasLeft) {
					parent.left.key = curr.key;
					parent.left.value = curr.value;
				}
				else {
					parent.right.key = curr.key;
					parent.right.value = curr.value;
				}
				return true;
			}
			if (wasLeft) // left child only
				parent.left = node.left;
			else
				parent.right = node.left;
			return true;
		}
		else if (node.right != null) { // right child only
			if (wasLeft)
				parent.left = node.right;
			else
				parent.right = node.right;
			return true;
		}
		if (wasLeft) // no children
			parent.left = null;
		else
			parent.right = null;
		return true;
	}

	// Code adapted from "Lecture Notes & Supplementary Material" by Riggins, Alan
	@Override
	public V getValue(K key) {
		return getValue(key, root);
	}

	// Returns the value associated with the parameter key. Returns
	// null if the key is not found or the dictionary is empty.
	// Code adapted from "Lecture Notes & Supplementary Material" by Riggins, Alan
	private V getValue(K key, Node<K, V> node) {
		if (node == null)
			return null;
		if (((Comparable<K>) key).compareTo((K) node.key) < 0)
			return getValue(key, node.left);
		if (((Comparable<K>) key).compareTo((K) node.key) > 0)
			return getValue(key, node.right);
		return (V) node.value;
	}

	@Override
	public K getKey(V value) {
		return getKey(value, root, null);
	}

	// Returns the key associated with the parameter value. Returns
	// null if the value is not found in the dictionary. If more
	// than one key exists that matches the given value, returns the
	// first one found.
	private K getKey(V value, Node<K, V> node, Node<K, V> parent) {		
		if (node == null)
			return null;
		if (((Comparable<V>) value).compareTo((V) node.value) == 0)
			return (K) node.key;
		K keyToReturn = getKey(value, node.left, node);
		if (keyToReturn == null)
			keyToReturn = getKey(value, node.right, node);
		return (K) keyToReturn;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	@Override
	public int size() {
		return this.currentSize;
	}

	// Returns true if the dictionary is at max capacity
	@Override
	public boolean isFull() {
		return false;
	}

	// Returns true if the dictionary is empty
	@Override
	public boolean isEmpty() {
		return this.root == null;
	}

	// Returns the Dictionary object to an empty state.
	@Override
	public void clear() {
		this.root = null;
		this.currentSize = 0;
		this.modificationCounter++;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order. The iterator must be fail-fast.
	@Override
	public Iterator<K> keys() {
		return new IteratorHelper<K>(IteratorHelper.KEYS);
	}

	// Returns an Iterator of the values in the dictionary. The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	@Override
	public Iterator<V> values() {
		return new IteratorHelper<V>(IteratorHelper.VALUES);
	}
	
	// IteratorHelper class allows for tracking of changes since Iterator creation.
	// Operates in fail-fast mode.
	private class IteratorHelper<T> implements Iterator<T> {
		private static final int KEYS = 0;
		private static final int VALUES = 1;
		private T[] auxArray;
		private int iterIndex, copyIndex, target;
		private long stateCheck;

		@SuppressWarnings("unchecked")
		public IteratorHelper(int target) {
			this.stateCheck = modificationCounter;
			this.iterIndex = this.copyIndex = 0;
			this.auxArray = (T[]) new Object[currentSize];
			this.target = target;
			copyInOrder(root);
		}
		
		// Code adapted from "Lecture Notes & Supplementary Material" by Riggins, Alan
		@SuppressWarnings("unchecked")
		private void copyInOrder(Node<K, V> node) {
			if (node != null) {
				copyInOrder(node.left);
				this.auxArray[this.copyIndex++] = this.target == KEYS ? (T) node.key : (T) node.value;
				copyInOrder(node.right);
			}
		}

		// Returns true if the list has a next item, false if not
		@Override
		public boolean hasNext() {
			if (this.stateCheck != modificationCounter)
				throw new ConcurrentModificationException();
			return this.iterIndex < this.auxArray.length;
		}

		// If the list has a next item, that item is returned
		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return (T) this.auxArray[this.iterIndex++];
		}

		// Unsupported operation for fail-fast iterator
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
