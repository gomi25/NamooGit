package dto;

public class TopicMemberDto {
	private int topicIdx;			// 토픽idx
	private int memberIdx;			// 회원idx
	private String profileUrl;		// 프로필
	private String name;			// 이름
	private String department;		// 부서
	private String position;		// 직책
	private String power;			// 권한
	private int manager;			// 매니저 여부 (0 또는 1)
	
	public TopicMemberDto(int topicIdx, int memberIdx, String profileUrl, String name, String department,
			String position, String power, int manager) {
		this.topicIdx = topicIdx;
		this.memberIdx = memberIdx;
		this.profileUrl = profileUrl;
		this.name = name;
		this.department = department;
		this.position = position;
		this.power = power;
		this.manager = manager;
	}

	public int getTopicIdx() {
		return topicIdx;
	}

	public void setTopicIdx(int topicIdx) {
		this.topicIdx = topicIdx;
	}

	public int getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}
	
	@Override
	public String toString() {
		return "topicIdx : " + topicIdx + "\n" +
   			   "memberIdx : " + memberIdx + "\n" +
   			   "profileUrl : " + profileUrl + "\n" +
   			   "name : " + name + "\n" +
   			   "department : " + department + "\n" +
   			   "position : " + position + "\n" +
   			   "power : " + power + "\n" +
   			   "manager : " + manager;
	}
}
