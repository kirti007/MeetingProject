package com.meeting.booking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.meeting.booking.enums.Status;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "purpose")
	private String purpose;
	@Column(name = "start_time")
	private Date startTime;
	@Column(name = "end_time")
	private Date endTime;
	@Column(name = "booked_room")
	private int bookedRoom;// room id
	@Column(name = "booked_by")
	private int bookedBy;// User id
	@Column(name = "status")
	private Status status;
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private NewBooking newBooking;
	@Column(name = "eId")
	private String eId;

	public Booking() {
		// TODO Auto-generated constructor stub
	}

	public Booking(int id, String purpose, Date startTime, Date endTime, int bookedRoom, int bookedBy,String eId, Status status) {
		super();
		this.id = id;
		this.purpose = purpose;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bookedRoom = bookedRoom;
		this.bookedBy = bookedBy;
		this.status = status;
		this.eId=eId;
	}

	public Booking(String purpose, Date startTime, Date endTime, int bookedRoom, int bookedBy,String eId, Status status,
			NewBooking newBooking) {
		super();
		this.purpose = purpose;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bookedRoom = bookedRoom;
		this.bookedBy = bookedBy;
		this.status = status;
		this.eId=eId;
		this.newBooking = newBooking;
	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getBookedRoom() {
		return bookedRoom;
	}

	public void setBookedRoom(int bookedRoom) {
		this.bookedRoom = bookedRoom;
	}

	public int getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(int bookedBy) {
		this.bookedBy = bookedBy;
	}

	public NewBooking getNewBooking() {
		return newBooking;
	}

	public void setNewBooking(NewBooking newBooking) {
		this.newBooking = newBooking;
	}

	public Status getStatus() {
		return StringUtils.isEmpty(status) || status == Status.CANCELLED ? Status.AVAILABLE : status;
	}

	public void setStatus(Status status) {
		if (status == null || !Status.isExist(status))
			throw new NullPointerException("Status could not be null");
		this.status = status;
	}

}
