package dto;

public class ProjectBookmarkDto {
	private String projectName;
	private String color;
	private int memberIdxFrom;
	private int teamIdx;
	private int memberCount;
	public ProjectBookmarkDto(String projectName, String color, int memberIdxFrom, int teamIdx, int memberCount) {
		super();
		this.projectName = projectName;
		this.color = color;
		this.memberIdxFrom = memberIdxFrom;
		this.teamIdx = teamIdx;
		this.memberCount = memberCount;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getMemberIdxFrom() {
		return memberIdxFrom;
	}
	public void setMemberIdxFrom(int memberIdxFrom) {
		this.memberIdxFrom = memberIdxFrom;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	
	
	


	
	
}
