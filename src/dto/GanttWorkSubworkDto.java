package dto;

public class GanttWorkSubworkDto {
	private int work_state; //업무 상태
	private String subwork_name; //하위업무명
	private String finish_date; // 마감일
	private int priority; //우선순위
	private int comment_count; //하위업무 댓글수 
	private int select_member_count; //하위업무 담당자수
	public GanttWorkSubworkDto(int work_state, String subwork_name, String finish_date, int priority, int comment_count,
			int select_member_count) {
		super();
		this.work_state = work_state;
		this.subwork_name = subwork_name;
		this.finish_date = finish_date;
		this.priority = priority;
		this.comment_count = comment_count;
		this.select_member_count = select_member_count;
	}
	public int getWork_state() {
		return work_state;
	}
	public void setWork_state(int work_state) {
		this.work_state = work_state;
	}
	public String getSubwork_name() {
		return subwork_name;
	}
	public void setSubwork_name(String subwork_name) {
		this.subwork_name = subwork_name;
	}
	public String getFinish_date() {
		return finish_date;
	}
	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getSelect_member_count() {
		return select_member_count;
	}
	public void setSelect_member_count(int select_member_count) {
		this.select_member_count = select_member_count;
	}
	
	

    @Override
	public String toString() {
		return "work_state: " + work_state + "\n"
				+ "subwork_name: " + subwork_name + "\n" 
				+ "finish_date: " + finish_date + "\n" 
				+ "priority: " + priority + "\n" 
				+ "comment_count: " + comment_count + "\n" 
				+ "select_member_count: " + select_member_count;
	}

}
