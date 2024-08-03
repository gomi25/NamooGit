package dto;

public class MemberDto {
	private String memberName;
	private String profilePicUrl;
	private String email;
	
	public MemberDto(String memberName, String profilePicUrl, String email) {
		this.memberName = memberName;
		this.profilePicUrl = profilePicUrl;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MemberDto [memberName=" + memberName + ", profilePicUrl=" + profilePicUrl + ", email=" + email + "]";
	}
}
