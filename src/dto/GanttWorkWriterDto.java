package dto;

public class GanttWorkWriterDto {
	private String profile_pic_url; // 프로필사진
	private String name; // 멤버이름
	private String registration_date; // 등록일
	public GanttWorkWriterDto(String profile_pic_url, String name, String registration_date) {
		super();
		this.profile_pic_url = profile_pic_url;
		this.name = name;
		this.registration_date = registration_date;
	}
	public String getProfile_pic_url() {
		return profile_pic_url;
	}
	public void setProfile_pic_url(String profile_pic_url) {
		this.profile_pic_url = profile_pic_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}
	
	public String toString() {
		return "profile_pic_url: " + profile_pic_url + "\n"
				+ "name: " + name + "\n" 
				+ "registration_date: " + registration_date;
	}

}
