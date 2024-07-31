package dto;

public class GanttSubworkDetailsProjectWorkNameDto {
	private int color_idx; // 색상idx
	private String project_name; // 프로젝트명
	private String work_name; // 업무명
	public GanttSubworkDetailsProjectWorkNameDto(int color_idx, String project_name, String work_name) {
		super();
		this.color_idx = color_idx;
		this.project_name = project_name;
		this.work_name = work_name;
	}
	public int getColor_idx() {
		return color_idx;
	}
	public void setColor_idx(int color_idx) {
		this.color_idx = color_idx;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
    @Override
	public String toString() {
		return "color_idx: " + color_idx + "\n"
				+ "project_name: " + project_name + "\n" 
				+ "work_name: " + work_name;
	}


}
