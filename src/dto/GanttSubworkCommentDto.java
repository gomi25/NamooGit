package dto;

public class GanttSubworkCommentDto {
	private String profile_pic_url;
	private String name;
	private String comment_date;
	private String subwork_comment;

	

	public GanttSubworkCommentDto(String profile_pic_url, String name, String comment_date, String subwork_comment) {
		super();
		this.profile_pic_url = profile_pic_url;
		this.name = name;
		this.comment_date = comment_date;
		this.subwork_comment = subwork_comment;
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






	public String getComment_date() {
		return comment_date;
	}






	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}






	public String getSubwork_comment() {
		return subwork_comment;
	}






	public void setSubwork_comment(String subwork_comment) {
		this.subwork_comment = subwork_comment;
	}






	@Override
	public String toString() {
		return "profile_pic_url: " + profile_pic_url + "\n"
				+ "name: " + name + "\n" 
				+ "comment_date: " + comment_date + "\n"
				+ "subwork_comment: " + subwork_comment;
	}
	
	
}
