package restaurantManagement;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private int tableId;
    private Date reservationTime;
    private int partySize;
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reservation(int reservationId, int tableId, Date reservationTime, int partySize) {
		super();
		this.reservationId = reservationId;
		this.tableId = tableId;
		this.reservationTime = reservationTime;
		this.partySize = partySize;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public Date getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}
	public int getPartySize() {
		return partySize;
	}
	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}

   
}
