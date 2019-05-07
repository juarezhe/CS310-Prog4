import java.util.Iterator;
import data_structures.*;

public class StockItem implements Comparable<StockItem> {
	String SKU;
	String description;
	String vendor;
	float cost;
	float retail;

	// Constructor. Creates a new StockItem instance.
	public StockItem(String SKU, String description, String vendor, float cost, float retail) {

	}

	// Follows the specifications of the Comparable Interface.
	// The SKU is always used for comparisons, in dictionary order.
	public int compareTo(StockItem n) {
		return 0;

	}

	// Returns an int representing the hashCode of the SKU.
	public int hashCode() {
		return 0;

	}

	// standard get methods
	public String getDescription() {
		return description;

	}

	public String getVendor() {
		return vendor;

	}

	public float getCost() {
		return cost;

	}

	public float getRetail() {
		return retail;

	}

	// All fields in one line, in order
	public String toString() {
		return null;

	}
}