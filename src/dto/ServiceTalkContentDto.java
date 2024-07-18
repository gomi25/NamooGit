package dto;

public class ServiceTalkContentDto {
	private int memberIdx;			// 멤버 idx
	private int serviceTalkroomIdx;	// 서비스톡방 idx
	private int serviceTalkIdx;		// 톡 idx
	private String profilePicUrl;	// 프사 url
	private String name;			// 이름
	private String talkTime;		// 톡 시간
	private String message;			// 메시지
	private int read;
	
	
	public int getMemberIdx() {
		return memberIdx;
	}


	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}


	public int getServiceTalkroomIdx() {
		return serviceTalkroomIdx;
	}


	public void setServiceTalkroomIdx(int serviceTalkroomIdx) {
		this.serviceTalkroomIdx = serviceTalkroomIdx;
	}


	public int getServiceTalkIdx() {
		return serviceTalkIdx;
	}


	public void setServiceTalkIdx(int serviceTalkIdx) {
		this.serviceTalkIdx = serviceTalkIdx;
	}


	public String getProfilePicUrl() {
		return profilePicUrl;
	}


	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTalkTime() {
		return talkTime;
	}


	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getRead() {
		return read;
	}


	public void setRead(int read) {
		this.read = read;
	}


	public ServiceTalkContentDto(int memberIdx, int serviceTalkroomIdx, int serviceTalkIdx, String profilePicUrl,
			String name, String talkTime, String message, int read) {
		super();
		this.memberIdx = memberIdx;
		this.serviceTalkroomIdx = serviceTalkroomIdx;
		this.serviceTalkIdx = serviceTalkIdx;
		this.profilePicUrl = profilePicUrl;
		this.name = name;
		this.talkTime = talkTime;
		this.message = message;
		this.read = read;
	}


	@Override //참조변수를 String 타입으로 변하게 하는 거
    public String toString() {
        return "ServiceTalkContentDto { " +
                "memberIdx = " + memberIdx +
                " serviceTalkroomIdx = " + serviceTalkroomIdx +
                " serviceTalkIdx = " + serviceTalkroomIdx +
                ", name = " + name +
                ", talkTime = " + talkTime  +
                ", message = " + message +
                ", read = " + read +
                '}';
    }
}
