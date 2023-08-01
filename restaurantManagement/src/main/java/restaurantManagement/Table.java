package restaurantManagement;


public class Table {
    private int tableId;
    private String tableName;
    private int capacity;
    private boolean isReserved;
	public Table() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Table(int tableId, String tableName, int capacity, boolean isReserved) {
		super();
		this.tableId = tableId;
		this.tableName = tableName;
		this.capacity = capacity;
		this.isReserved = isReserved;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public boolean isReserved() {
		return isReserved;
	}
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	
    
}
