package dto;

public class OrganizationalMemberListDto {
	private String profilePicUrl;
	private String memberName;
	private String teamName;
	private String position;
	private String state;
	private String stateMessage;
	private int teamIdx;
	private int memberIdx;
	private String bookmarkMember;
	private int memberIdxFrom;
	public OrganizationalMemberListDto(String profilePicUrl, String memberName, String teamName, String position,
			String state, String stateMessage, int teamIdx, int memberIdx, String bookmarkMember, int memberIdxFrom) {
		super();
		this.profilePicUrl = profilePicUrl;
		this.memberName = memberName;
		this.teamName = teamName;
		this.position = position;
		this.state = state;
		this.stateMessage = stateMessage;
		this.teamIdx = teamIdx;
		this.memberIdx = memberIdx;
		this.bookmarkMember = bookmarkMember;
		this.memberIdxFrom = memberIdxFrom;
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
	public String getBookmarkMember() {
		return bookmarkMember;
	}
	public void setBookmarkMember(String bookmarkMember) {
		this.bookmarkMember = bookmarkMember;
	}
	public int getMemberIdxFrom() {
		return memberIdxFrom;
	}
	public void setMemberIdxFrom(int memberIdxFrom) {
		this.memberIdxFrom = memberIdxFrom;
	}
	

	

}
