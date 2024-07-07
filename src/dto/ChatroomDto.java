package dto;

import java.util.ArrayList;

public class ChatroomDto {
	private int chatroomIdx;     // 채팅방idx
	private ArrayList<LaterProfileUrlColorDto> listProfileUrlColor;  // 프로필 이미지 or 색상
	private String chatroomName;	// 채팅방 이름
	private String information;		// 채팅방 설명 상세
	private String chatRecentDateTime; // 채팅방 최근 챗 일시
	private boolean bookmarkYn;   // 즐겨찾기 여부
	private int alarm;  // 1 or 0
	private int unread; // >=1 or 0

	public ChatroomDto(int chatroomIdx, ArrayList<LaterProfileUrlColorDto> listProfileUrlColor, String chatroomName,
			String information, String chatRecentDateTime, boolean bookmarkYn, int alarm, int unread) {
		this.chatroomIdx = chatroomIdx;
		this.listProfileUrlColor = listProfileUrlColor;
		this.chatroomName = chatroomName;
		this.information = information;
		this.chatRecentDateTime = chatRecentDateTime;
		this.bookmarkYn = bookmarkYn;
		this.alarm = alarm;
		this.unread = unread;
	}
	
	public int getChatroomIdx() {
		return chatroomIdx;
	}
	public void setChatroomIdx(int chatroomIdx) {
		this.chatroomIdx = chatroomIdx;
	}
	public ArrayList<LaterProfileUrlColorDto> getListProfileUrlColor() {
		return listProfileUrlColor;
	}
	public void setListProfileUrlColor(ArrayList<LaterProfileUrlColorDto> listProfileUrlColor) {
		this.listProfileUrlColor = listProfileUrlColor;
	}
	public String getChatroomName() {
		return chatroomName;
	}
	public void setChatroomName(String chatroomName) {
		this.chatroomName = chatroomName;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getChatRecentDateTime() {
		return chatRecentDateTime;
	}
	public void setChatRecentDateTime(String chatRecentDateTime) {
		this.chatRecentDateTime = chatRecentDateTime;
	}
	public boolean isBookmarkYn() {
		return bookmarkYn;
	}
	public void setBookmarkYn(boolean bookmarkYn) {
		this.bookmarkYn = bookmarkYn;
	}
	public int getAlarm() {
		return alarm;
	}
	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}
	public int getUnread() {
		return unread;
	}
	public void setUnread(int unread) {
		this.unread = unread;
	}
	
	
}
