package dto;

public class MemberDto {
	private String memberName;
	private String profilePicUrl;
	private String profileImgIdx;
	private String email;
	
	public MemberDto(String memberName, String profilePicUrl, String profileImgIdx, String email) {
		this.memberName = memberName;
		this.profilePicUrl = profilePicUrl;
		this.profileImgIdx = profileImgIdx;
		this.email = email;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public String getProfileImgIdx() {
		return profileImgIdx;
	}

	public void setProfileImgIdx(String profileImgIdx) {
		this.profileImgIdx = profileImgIdx;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MemberDto [memberName=" + memberName + ", profilePicUrl=" + profilePicUrl + ", profileImgIdx="
				+ profileImgIdx + ", email=" + email + "]";
	}
}
