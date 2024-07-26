package dto;

public class DashboardProjectDto {
	private int projectIdx; 
	private int teamIdx; 
	private String projectName; 
	private int colorIdx;
	private int writer;
	private String registrationDate;

	public DashboardProjectDto(int projectIdx, int teamIdx, String projectName, int colorIdx, int writer,
			String registrationDate) {
		super();
		this.projectIdx = projectIdx;
		this.teamIdx = teamIdx;
		this.projectName = projectName;
		this.colorIdx = colorIdx;
		this.writer = writer;
		this.registrationDate = registrationDate;
	}
	public int getProjectIdx() {
		return projectIdx;
	}
	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getColorIdx() {
		return colorIdx;
	}
	public void setColorIdx(int colorIdx) {
		this.colorIdx = colorIdx;
	}
	public int getWriter() {
		return writer;
	}
	public void setWriter(int writer) {
		this.writer = writer;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override //참조변수를 String 타입으로 변하게 하는 거
    public String toString() {
        return "DashboardProjectDto { " +
                "projectIdx = " + projectIdx +
                ", teamIdx = " + teamIdx  +
                ", projectName = " + projectName  +
                ", colorIdx = " + colorIdx  +
                ", writer = " + writer  +
                ", registration_date = " + registrationDate +
                '}';
    }

}
