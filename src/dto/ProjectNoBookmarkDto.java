package dto;

public class ProjectNoBookmarkDto {
	private int projectIdx;
	private String projectName;
	private String color;
	private int teamIdx;
	private int memberIdxFrom;
	private int projectMemberCount;
	public ProjectNoBookmarkDto(int projectIdx, String projectName, String color, int teamIdx, int memberIdxFrom,
			int projectMemberCount) {
		super();
		this.projectIdx = projectIdx;
		this.projectName = projectName;
		this.color = color;
		this.teamIdx = teamIdx;
		this.memberIdxFrom = memberIdxFrom;
		this.projectMemberCount = projectMemberCount;
	}
	public int getProjectIdx() {
		return projectIdx;
	}
	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
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
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	public int getMemberIdxFrom() {
		return memberIdxFrom;
	}
	public void setMemberIdxFrom(int memberIdxFrom) {
		this.memberIdxFrom = memberIdxFrom;
	}
	public int getProjectMemberCount() {
		return projectMemberCount;
	}
	public void setProjectMemberCount(int projectMemberCount) {
		this.projectMemberCount = projectMemberCount;
	}

	
	
	
}
