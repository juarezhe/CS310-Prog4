import java.util.Iterator;
import data_structures.*;

public class ProductLookup {
	private DictionaryADT<String, StockItem> table;
	
	// Constructor. There is no argument-less constructor, or default size
	public ProductLookup(int maxSize) {
		this.table = new Hashtable<String, StockItem>(maxSize);
	}

	// Adds a new StockItem to the dictionary
	public void addItem(String SKU, StockItem item) {
		this.table.add(SKU, item);
	}

	// Returns the StockItem associated with the given SKU, if it is
	// in the ProductLookup, null if it is not.
	public StockItem getItem(String SKU) {
		return this.table.getValue(SKU);
	}

	// Returns the retail price associated with the given SKU value.
	// -.01 if the item is not in the dictionary
	public float getRetail(String SKU) {
		return this.table.getValue(SKU).getRetail();
	}

	// Returns the cost price associated with the given SKU value.
	// -.01 if the item is not in the dictionary
	public float getCost(String SKU) {
		return this.table.getValue(SKU).getCost();
	}

	// Returns the description of the item, null if not in the dictionary.
	public String getDescription(String SKU) {
		return this.table.getValue(SKU).getDescription();
	}

	// Deletes the StockItem associated with the SKU if it is
	// in the ProductLookup. Returns true if it was found and
	// deleted, otherwise false.
	public boolean deleteItem(String SKU) {
		return this.table.delete(SKU);
	}

	// Prints a directory of all StockItems with their associated
	// price, in sorted order (ordered by SKU).
	public void printAll() {
		this.table.values().toString();
	}

	// Prints a directory of all StockItems from the given vendor,
	// in sorted order (ordered by SKU).
	public void print(String vendor) {
		// for (String item : ((Iterable) this.table).keys())
	}

	// An iterator of the SKU keys.
	public Iterator<String> keys() {
		return this.table.keys();
	}

	// An iterator of the StockItem values.
	public Iterator<StockItem> values() {
		return this.table.values();
	}
}