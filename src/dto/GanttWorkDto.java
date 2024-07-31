package dto;

public class GanttWorkDto {
	private String work_name;
	private String name;
	private int work_state;
	private String start_date;
	private String finish_date;
	public GanttWorkDto(String work_name, String name, int work_state, String start_date, String finish_date) {
		super();
		this.work_name = work_name;
		this.name = name;
		this.work_state = work_state;
		this.start_date = start_date;
		this.finish_date = finish_date;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWork_state() {
		return work_state;
	}
	public void setWork_state(int work_state) {
		this.work_state = work_state;
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
	
	
	public String toString() {
		return "work_name: " + work_name + "\n"
				+ "name: " + name + "\n"
				+ "work_state: " + work_state + "\n"
				+ "start_date: " + start_date + "\n"
				+ "finish_date: " + finish_date;
	}
}
