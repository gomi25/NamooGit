package dto;

public class TopicDto {
	private int topicIdx;		// 토픽방idx
	private String name;		// 토픽방 이름
	private String information; // 토픽방 설명
	private int teamIdx;		// 팀idx
	private int open;			// 공개 여부
	private int alarm;			// 알람 여부
	private int unread;			// 안 읽은 메시지
	private boolean bookmark;	// 북마크 여부
	
	public TopicDto(int topicIdx, String name, String information, int teamIdx, int open, int alarm, int unread, boolean bookmark) {
		this.topicIdx = topicIdx;
		this.name = name;
		this.information = information;
		this.teamIdx = teamIdx;
		this.open = open;
		this.alarm = alarm;
		this.unread = unread;
		this.bookmark = bookmark;
	}
	public int getTopicIdx() {
		return topicIdx;
	}
	public void setTopicIdx(int topicIdx) {
		this.topicIdx = topicIdx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
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
	public boolean isBookmark() {
		return bookmark;
	}
	public void setBookmark(boolean bookmark) {
		this.bookmark = bookmark;
	}
	
	
}
