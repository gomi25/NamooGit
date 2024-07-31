package dto;

public class GanttkCommentLikeDto {
	private String profilePicUrl; // 멤버 프로필
	private String name; // 멤버이름
	
	public GanttkCommentLikeDto(String profilePicUrl, String name) {
		
		this.profilePicUrl = profilePicUrl;
		this.name = name;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

    @Override
    public String toString() {
        return "ProfilePicUrl: " + profilePicUrl + ", Name: " + name;
    }
	
	
	

}
