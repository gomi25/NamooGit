package dto;

public class ProjectDto {
	private int projectIdx;    	// 프로젝트idx
	private String projectName;	// 프로젝트이름
	private String color;		// 프로젝트색상(참조하는 테이블 : color)
	private int colorIdx;		// 프로젝트색상(참조하는 테이블 : color)
	
	
	public ProjectDto(int projectIdx, String projectName, String color, int colorIdx) {
		super();
		this.projectIdx = projectIdx;
		this.projectName = projectName;
		this.color = color;
		this.colorIdx = colorIdx;
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



	public int getColorIdx() {
		return colorIdx;
	}



	public void setColorIdx(int colorIdx) {
		this.colorIdx = colorIdx;
	}



	@Override
	public String toString() {
		return "projectIdx: " + projectIdx + "\n"
				+ "projectName: " + projectName + "\n" 
				+ "colorIdx: " + colorIdx;
	}
}
