/* 
 * Program 4 - Hashtable
 * CS-310
 * 7 May 2019
 * @author Hannah Juarez cssc1481
 */

package data_structures;

import java.util.Iterator;
import data_structures.DictionaryADT;

public class Hashtable<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
	
	private Object[] table;

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(K key, V value) {
		// TODO Auto-generated method stub
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