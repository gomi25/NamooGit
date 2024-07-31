package dto;

public class DashboardChatroomAndTopicNameDto {
	private String name;
	private int teamIdx;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTemaIdx() {
		return teamIdx;
	}
	public void setTemaIdx(int temaIdx) {
		this.teamIdx = temaIdx;
	}
	public DashboardChatroomAndTopicNameDto(String name, int temaIdx) {
		super();
		this.name = name;
		this.teamIdx = temaIdx;
	}
	
}
