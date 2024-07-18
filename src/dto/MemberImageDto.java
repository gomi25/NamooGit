package dto;

public class MemberImageDto {
	private int memberIdx;
	private String name;
	private String profilePicUrl;

	public MemberImageDto(int memberIdx, String name, String profilePicUrl) {
		this.memberIdx = memberIdx;
		this.name = name;
		this.profilePicUrl = profilePicUrl;
	}

	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfilePicUrl() {
		return profilePicUrl;
	}
	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}
}
