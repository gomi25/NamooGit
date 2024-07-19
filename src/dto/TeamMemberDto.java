package dto;

public class TeamMemberDto {
	private int teamIdx;			// 팀idx
	private int memberIdx;			// 회원idx
	private String profileUrl;		// 프로필 이미지
	private String name;			// 이름
	private String department;		// 부서
	private String position;		// 직책
	private String power;			// 권한
	
//	public TeamMemberDto() {
//		
//	}
	
	public TeamMemberDto(int teamIdx, int memberIdx, String profileUrl, String name, String department, String position,
			String power) {
		this.teamIdx = teamIdx;
		this.memberIdx = memberIdx;
		this.profileUrl = profileUrl;
		this.name = name;
		this.department = department;
		this.position = position;
		this.power = power;
	}
	
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
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
	@Override
	public String toString() {
		return "teamIdx : " + teamIdx + "\n" +
   			   "memberIdx : " + memberIdx + "\n" +
   			   "profileUrl : " + profileUrl + "\n" +
   			   "name : " + name + "\n" +
   			   "department : " + department + "\n" +
   			   "position : " + position + "\n" +
   			   "power : " + power + "\n";
	}
}
