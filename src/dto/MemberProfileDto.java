package dto;
public class MemberProfileDto {
	private int memberIdx;
	private String profilePicUrl;
	private String memberName;
	private String teamName;
	private String position;
	private String state;
	private String stateMessage;
	private String power;
	private String birth;
	private String phoneNumber;
	private String email;
	
	public MemberProfileDto(int memberIdx, String profilePicUrl, String memberName, String teamName, String position,
			String state, String stateMessage, String power, String birth, String phoneNumber, String email) {
		this.memberIdx = memberIdx;
		this.profilePicUrl = profilePicUrl;
		this.memberName = memberName;
		this.teamName = teamName;
		this.position = position;
		this.state = state;
		this.stateMessage = stateMessage;
		this.power = power;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getProfilePicUrl() {
		return profilePicUrl;
	}
	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateMessage() {
		return stateMessage;
	}
	public void setStateMessage(String stateMessage) {
		this.stateMessage = stateMessage;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

	
	
}
