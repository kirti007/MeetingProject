package com.meeting.booking.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.meeting.booking.enums.Status;

@Entity
@Table(name = "newbooking")
public class NewBooking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6875545820716482532L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;

	@Column(name = "startDate")
	private Integer startDate;
	@Column(name = "startTime")
	private Integer startTime;
	@Column(name = "endDate")
	private Integer endDate;
	@Column(name = "end_time")
	private Integer endTime;
	@Column(name = "booked_room")
	private int bookedRoom;// room id
	@Column(name = "status")
	private Status status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booking_id")
	private Booking booking;

	public NewBooking() {
		super();
	}

	public NewBooking(Integer startDate, Integer startTime, Integer endDate, Integer endTime, int bookedRoom,
			Status status, Booking booking) {
		super();
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.bookedRoom = bookedRoom;
		this.status = status;
		this.booking = booking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public int getBookedRoom() {
		return bookedRoom;
	}

	public void setBookedRoom(int bookedRoom) {
		this.bookedRoom = bookedRoom;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "NewBooking [id=" + id + ", startDate=" + startDate + ", startTime=" + startTime + ", endDate=" + endDate
				+ ", endTime=" + endTime + ", bookedRoom=" + bookedRoom + ", status=" + status + "]";
	}

}
