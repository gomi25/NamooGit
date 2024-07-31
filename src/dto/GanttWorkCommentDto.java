package dto;

public class GanttWorkCommentDto {
	private String profile_pic_url;
	private String name;
	private String comment_date;
	private String work_comment;
	private int work_idx;
	public GanttWorkCommentDto(String profile_pic_url, String name, String comment_date, String work_comment,
			int work_idx) {
		super();
		this.profile_pic_url = profile_pic_url;
		this.name = name;
		this.comment_date = comment_date;
		this.work_comment = work_comment;
		this.work_idx = work_idx;
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
	public String getWork_comment() {
		return work_comment;
	}
	public void setWork_comment(String work_comment) {
		this.work_comment = work_comment;
	}
	public int getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(int work_idx) {
		this.work_idx = work_idx;
	}
	
	@Override
	public String toString() {
		return "profile_pic_url: " + profile_pic_url + "\n"
				+ "name: " + name + "\n" 
				+ "comment_date: " + comment_date + "\n"
				+ "work_comment: " + work_comment;
	}
	
	
}
