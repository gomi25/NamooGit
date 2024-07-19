package dto;

public class ProfileUrlImgDto {
	private int memberIdx;
	private String profilePicUrl1;
	private Integer profilePicUrl2;


	public ProfileUrlImgDto(int memberIdx, String profilePicUrl1, Integer profilePicUrl2) {
		this.memberIdx = memberIdx;
		this.profilePicUrl1 = profilePicUrl1;
		this.profilePicUrl2 = profilePicUrl2;
	}

	public int getMemberIdx() {
		return memberIdx;
	}


	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}


	public String getProfilePicUrl1() {
		return profilePicUrl1;
	}


	public void setProfilePicUrl1(String profilePicUrl1) {
		this.profilePicUrl1 = profilePicUrl1;
	}


	public Integer getProfilePicUrl2() {
		return profilePicUrl2;
	}


	public void setProfilePicUrl2(Integer profilePicUrl2) {
		this.profilePicUrl2 = profilePicUrl2;
	}


    @Override
    public String toString() {
        return "ProfileUrlColorDto{" +
               "memberIdx=" + memberIdx +
               ", profilePicUrl1='" + profilePicUrl1 + '\'' +
               ", profilePicUrl2=" + profilePicUrl2 +
               '}';
    }
}
