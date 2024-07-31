package dto;

public class GanttGroupDto {
	private int ganttgroupIdx;
	private String ganttgroupName;
	public GanttGroupDto(int ganttgroupIdx, String ganttgroupName) {
		super();
		this.ganttgroupIdx = ganttgroupIdx;
		this.ganttgroupName = ganttgroupName;
	}
	public int getGanttgroupIdx() {
		return ganttgroupIdx;
	}
	public void setGanttgroupIdx(int ganttgroupIdx) {
		this.ganttgroupIdx = ganttgroupIdx;
	}
	public String getGanttgroupName() {
		return ganttgroupName;
	}
	public void setGanttgroupName(String ganttgroupName) {
		this.ganttgroupName = ganttgroupName;
	}
	
	


}
