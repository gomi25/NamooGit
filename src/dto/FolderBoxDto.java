package dto;

// 토픽폴더 
public class FolderBoxDto {
	private int topicFolderIdx;	// 토픽폴더idx
	private int memberIdx;		// 회원idx
	private int teamIdx;		// 팀idx
	private String name;		// 폴더 이름

	public FolderBoxDto(int topicFolderIdx, int memberIdx, int teamIdx, String name) {
		this.topicFolderIdx = topicFolderIdx;
		this.memberIdx = memberIdx;
		this.teamIdx = teamIdx;
		this.name = name;
	}

	public int getTopicFolderIdx() {
		return topicFolderIdx;
	}

	public void setTopicFolderIdx(int topicFolderIdx) {
		this.topicFolderIdx = topicFolderIdx;
	}

	public int getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

	public int getTeamIdx() {
		return teamIdx;
	}

	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
