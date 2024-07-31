package dto;

public class GanttWorkInformationDto {
	private int workIdx;
	private String workName; //업무명
	private String workState; //업무상태
	private String name; //멤버이름
	private String startDate; // 시작일
	private String finishDate; // 마감일
	private int duration;   // 듀레이션
	public GanttWorkInformationDto(int workIdx, String workName, String workState, String name, String startDate,
			String finishDate, int duration) {
		super();
		this.workIdx = workIdx;
		this.workName = workName;
		this.workState = workState;
		this.name = name;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.duration = duration;
	}
	public int getWorkIdx() {
		return workIdx;
	}
	public void setWorkIdx(int workIdx) {
		this.workIdx = workIdx;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	
	
}
