package dto;

public class GanttWorkReadMemberDto {
	private String profile_pic_url; //프로필사진
	private String member_name; //멤버명
	private String team_name; // 팀명
	private String read_date; //읽음일시  
	public GanttWorkReadMemberDto(String profile_pic_url, String member_name, String team_name, String read_date) {
		super();
		this.profile_pic_url = profile_pic_url;
		this.member_name = member_name;
		this.team_name = team_name;
		this.read_date = read_date;
	}
	public String getProfile_pic_url() {
		return profile_pic_url;
	}
	public void setProfile_pic_url(String profile_pic_url) {
		this.profile_pic_url = profile_pic_url;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getRead_date() {
		return read_date;
	}
	public void setRead_date(String read_date) {
		this.read_date = read_date;
	}
	
	
    @Override
	public String toString() {
		return "profile_pic_url: " + profile_pic_url + "\n"
				+ "member_name: " + member_name + "\n" 
				+ "team_name: " + team_name + "\n" 
				+ "read_date: " + read_date; 
	}

}
