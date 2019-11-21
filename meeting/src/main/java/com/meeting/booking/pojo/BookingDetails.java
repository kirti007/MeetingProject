package com.meeting.booking.pojo;

import java.util.List;

public class BookingDetails {
private int userId;
private int roomId;
private String startTime;
private String endTime;
private String purpose;
private List<String> attendee;


public BookingDetails() {
// TODO Auto-generated constructor stub
}



public BookingDetails(int userId, int roomId, String startTime, String endTime, String purpose,
List<String> attendee) {
super();
this.userId = userId;
this.roomId = roomId;
this.startTime = startTime;
this.endTime = endTime;
this.purpose = purpose;
this.attendee = attendee;
}



public int getUserId() {
return userId;
}



public void setUserId(int userId) {
this.userId = userId;
}



public int getRoomId() {
return roomId;
}



public void setRoomId(int roomId) {
this.roomId = roomId;
}



public String getStartTime() {
return startTime;
}



public void setStartTime(String startTime) {
this.startTime = startTime;
}



public String getEndTime() {
return endTime;
}



public void setEndTime(String endTime) {
this.endTime = endTime;
}



public String getPurpose() {
return purpose;
}



public void setPurpose(String purpose) {
this.purpose = purpose;
}



public List<String> getAttendee() {
return attendee;
}



public void setAttendee(List<String> attendee) {
this.attendee = attendee;
}


}
