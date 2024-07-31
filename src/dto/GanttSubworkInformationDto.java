package dto;

public class GanttSubworkInformationDto {
	private String subwork_name; //하위업무명
	private int work_state; //업무상태
	private String name; //멤버이름
	private String start_date; // 시작일
	private String finish_date; // 마감일
	private String ganttgroup_name; // 그룹명

	public GanttSubworkInformationDto(String subwork_name, int work_state, String name, String start_date,
			String finish_date, String ganttgroup_name) {
		super();
		this.subwork_name = subwork_name;
		this.work_state = work_state;
		this.name = name;
		this.start_date = start_date;
		this.finish_date = finish_date;
		this.ganttgroup_name = ganttgroup_name;
	}

	public String getSubwork_name() {
		return subwork_name;
	}

	public void setSubwork_name(String subwork_name) {
		this.subwork_name = subwork_name;
	}

	public int getWork_state() {
		return work_state;
	}

	public void setWork_state(int work_state) {
		this.work_state = work_state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}

	public String getGanttgroup_name() {
		return ganttgroup_name;
	}

	public void setGanttgroup_name(String ganttgroup_name) {
		this.ganttgroup_name = ganttgroup_name;
	}

	public String toString() {
		return "subwork_name: " + subwork_name + "\n"
				+ "work_state: " + work_state + "\n" 
				+ "name: " + name + "\n" 
				+ "start_date: " + start_date + "\n" 
				+ "finish_date: " + finish_date + "\n" 
				+ "ganttgroup_name: " + ganttgroup_name;
	}

}
