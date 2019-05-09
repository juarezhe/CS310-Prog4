/* 
 * Program 4 - Hashtable
 * CS-310
 * 7 May 2019
 * @author Hannah Juarez cssc1481
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import data_structures.DictionaryADT;

public class Hashtable<K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V> {
	private class DictionaryNode<K extends Comparable<K>, V> implements Comparable<DictionaryNode<K, V>> {
		K key;
		V value;
		
		DictionaryNode(K k, V v) {
			this.key = k;
			this.value = v;
		}

		@Override
		public int compareTo(DictionaryNode<K, V> node) {
			return this.key.compareTo(node.key);
		}
		
		
	}
	
	private class LinearList<E extends Comparable<E>> implements Iterable<E> {
		private Node<E> head, tail;
		private int currentSize;
		private long modificationCounter;

		private class Node<T> {
			private Node<T> next, previous;
			private T data;

			public Node(T dataToStore) {
				this.data = dataToStore;
				this.next = this.previous = null;
			}
		}

		// Constructor
		public LinearList() {
			this.head = this.tail = null;
			this.currentSize = 0;
			this.modificationCounter = 0;
		}

		/*
		 * Adds the Object obj to the beginning of list and returns true if the list is
		 * not full. returns false and aborts the insertion if the list is full.
		 */
		public boolean addFirst(E obj) {
			Node<E> newNode = new Node<E>(obj);
			newNode.next = this.head;

			if (this.head != null)
				this.head.previous = newNode;
			if (this.tail == null)
				this.tail = newNode;

			this.head = newNode;
			this.currentSize++;
			this.modificationCounter++;
			return true;
		}

		/*
		 * Adds the Object obj to the end of list and returns true if the list is not
		 * full. returns false and aborts the insertion if the list is full.
		 */
		public boolean addLast(E obj) {
			Node<E> newNode = new Node<E>(obj);
			newNode.previous = this.tail;

			if (this.tail != null)
				this.tail.next = newNode;
			if (this.head == null)
				this.head = newNode;

			this.tail = newNode;
			this.currentSize++;
			this.modificationCounter++;
			return true;
		}

		/*
		 * Removes and returns the parameter object obj in first position in list if the
		 * list is not empty, null if the list is empty.
		 */
		public E removeFirst() {
			if (this.head == null)
				return null;

			E dataToReturn = this.head.data;

			if (this.head == this.tail)
				this.head = this.tail = null;
			else {
				this.head = this.head.next;
				this.head.previous = null;
			}
			this.currentSize--;
			this.modificationCounter++;
			return (E) dataToReturn;
		}

		/*
		 * Removes and returns the parameter object obj in last position in list if the
		 * list is not empty, null if the list is empty.
		 */
		public E removeLast() {
			if (this.tail == null)
				return null;

			E dataToReturn = this.tail.data;

			if (this.tail == this.head)
				this.tail = this.head = null;
			else {
				this.tail = this.tail.previous;
				this.tail.next = null;
			}
			this.currentSize--;
			this.modificationCounter++;
			return (E) dataToReturn;
		}

		/*
		 * Removes and returns the parameter object obj from the list if the list
		 * contains it, null otherwise. The ordering of the list is preserved. The list
		 * may contain duplicate elements. This method removes and returns the first
		 * matching element found when traversing the list from first position. Note
		 * that you may have to shift elements to fill in the slot where the deleted
		 * element was located.
		 */
		public E remove(E obj) {
			Node<E> curr = this.head;
			E dataToReturn = null;

			while (curr != null) {
				if (curr.data.compareTo(obj) == 0) {
					dataToReturn = curr.data;
					break;
				}
				curr = curr.next;
			}
			if (dataToReturn == null)
				return null;
			if (curr == this.head)
				return (E) removeFirst();
			if (curr == this.tail)
				return (E) removeLast();

			curr.previous.next = curr.next;
			curr.next.previous = curr.previous;
			this.currentSize--;
			this.modificationCounter++;
			return (E) dataToReturn;
		}

		/*
		 * Returns the first element in the list, null if the list is empty. The list is
		 * not modified.
		 */
		public E peekFirst() {
			return (this.head == null) ? null : this.head.data;
		}

		/*
		 * Returns the last element in the list, null if the list is empty. The list is
		 * not modified.
		 */
		public E peekLast() {
			return (this.tail == null) ? null : this.tail.data;
		}

		/*
		 * Returns true if the parameter object obj is in the list, false otherwise. The
		 * list is not modified.
		 */
		public boolean contains(E obj) {
			return find(obj) != null;
		}

		/*
		 * Returns the element matching obj if it is in the list, null otherwise. In the
		 * case of duplicates, this method returns the element closest to front. The
		 * list is not modified.
		 */
		public E find(E obj) {
			Node<E> curr = head;

			while (curr != null) {
				if (curr.data.compareTo(obj) == 0)
					return (E) curr.data;
				curr = curr.next;
			}
			return null;
		}

		/*
		 * The list is returned to an empty state.
		 */
		public void clear() {
			this.head = this.tail = null;
			this.currentSize = 0;
			this.modificationCounter++;
		}

		/*
		 * Returns true if the list is empty, otherwise false
		 */
		public boolean isEmpty() {
			return this.currentSize == 0;
		}

		/*
		 * Returns true if the list is full, otherwise false
		 */
		public boolean isFull() {
			return false;
		}

		/*
		 * Returns the number of Objects currently in the list.
		 */
		public int size() {
			return this.currentSize;
		}

		/*
		 * Returns an Iterator of the values in the list, presented in the same order as
		 * the underlying order of the list. (front first, rear last)
		 */
		public Iterator<E> iterator() {
			return new IteratorHelper();
		}

		/*
		 * IteratorHelper class allows for tracking of changes since Iterator creation.
		 * Operates in fail-fast mode.
		 */
		private class IteratorHelper implements Iterator<E> {
			private Node<E> current;
			private long stateCheck;

			/*
			 * Constructs new IteratorHelper using the head as a starting point for
			 * iteration. Sets stateCheck to ensure modification has not occurred.
			 */
			public IteratorHelper() {
				this.current = head;
				this.stateCheck = modificationCounter;
			}

			/*
			 * Operates in fail-fast mode. Throws an exception if modification occurs mid
			 * iteration. Otherwise, returns true when the list has a next item.
			 */
			@Override
			public boolean hasNext() {
				if (this.stateCheck != modificationCounter)
					throw new ConcurrentModificationException();
				return this.current != null;
			}

			/*
			 * If the list has a next item, that item is returned.
			 */
			@Override
			public E next() {
				if (!hasNext())
					throw new NoSuchElementException();
				E dataToReturn = this.current.data;
				this.current = this.current.next;
				return (E) dataToReturn;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#remove()
			 * 
			 * Not a supported operation. Use the outer class's remove() instead.
			 */
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
	}

	private static final int DEFAULT_MAX_CAPACITY = 977;
	private LinearList<DictionaryNode<K, V>>[] table;
	private long modificationCounter;
	private int currentSize;
	
	// Default constructor
	public Hashtable() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	// Custom constructor
	@SuppressWarnings("unchecked")
	public Hashtable(int requestedSize) {
		this.table = (LinearList[]) new Object[requestedSize];
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
		table[key.hashCode()].addLast(new DictionaryNode<K, V>(key, value));
		this.currentSize++;
		this.modificationCounter++;
		return true;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
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
