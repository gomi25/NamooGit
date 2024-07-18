package dto;

public class ServiceTalkShowProfilePicAndNameDto {
	private String profilePicUrl;
	private String name;
	

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


	public ServiceTalkShowProfilePicAndNameDto(String profilePicUrl, String name) {
		super();
		this.profilePicUrl = profilePicUrl;
		this.name = name;
	}


	@Override //참조변수를 String 타입으로 변하게 하는 거
    public String toString() {
        return "HelpShowPostCategoryDto { " +
                "profilePicUrl = " + profilePicUrl +
                ", name = " + name +
                '}';
    }
}
