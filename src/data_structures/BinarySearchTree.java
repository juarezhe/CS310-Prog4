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

	// Default constructor
	public BinarySearchTree() {
		this.root = null;
		this.currentSize = 0;
		this.modificationCounter = 0;
	}

	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}

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
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Code adapted from [insert book and author]
	 */
	@Override
	public V getValue(K key) {
		return getValue(key, root);
	}

	/*
	 * Code adapted from [insert book and author]
	 */
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
		return this.root == null;
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
