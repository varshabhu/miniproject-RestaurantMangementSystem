package restaurantManagement;
import java.util.Date;

public class Order {
    private int orderId;
    private int tableId;
    private Date orderTime;
    private double totalPrice;
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(int orderId, int tableId, Date orderTime, double totalPrice) {
		super();
		this.orderId = orderId;
		this.tableId = tableId;
		this.orderTime = orderTime;
		this.totalPrice = totalPrice;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
    
}
