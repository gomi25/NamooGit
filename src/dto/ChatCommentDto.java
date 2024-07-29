package dto;

public class ChatCommentDto {
	
	private int chatCommentIdx;		// 채팅댓글idx
	private int chatIdx;			// 채팅글idx
	private int memberIdx;			// 댓글작성자idx
	private String profileUrl;		// 프로필url
	private String name;			// 댓글작성자 이름
	private String state;			// 댓글작성자 상태
	private String comments;		// 댓글내용
	private String writeDate;		// 작성일시
	private Integer fileIdx;		// 파일idx
	
	public ChatCommentDto(int chatCommentIdx, int chatIdx, int memberIdx, String profileUrl, String name, String state,
			String comments, String writeDate, Integer fileIdx) {
		super();
		this.chatCommentIdx = chatCommentIdx;
		this.chatIdx = chatIdx;
		this.memberIdx = memberIdx;
		this.profileUrl = profileUrl;
		this.name = name;
		this.state = state;
		this.comments = comments;
		this.writeDate = writeDate;
		this.fileIdx = fileIdx;
	}

	public int getChatCommentIdx() {
		return chatCommentIdx;
	}

	public void setChatCommentIdx(int chatCommentIdx) {
		this.chatCommentIdx = chatCommentIdx;
	}

	public int getChatIdx() {
		return chatIdx;
	}

	public void setChatIdx(int chatIdx) {
		this.chatIdx = chatIdx;
	}

	public int getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public Integer getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(Integer fileIdx) {
		this.fileIdx = fileIdx;
	}

	@Override
	public String toString() {
		return "chatCommentIdx : " + chatCommentIdx + "\n" +
			   "chatIdx : " + chatIdx + "\n" +
   			   "memberIdx : " + memberIdx + "\n" +
   			   "profileUrl : " + profileUrl + "\n" +
   			   "name : " + name + "\n" +
   			   "state : " + state + "\n" +
   			   "comments : " + comments + "\n" +
   			   "writeDate : " + writeDate + "\n" +
				"fileIdx : " + fileIdx + "\n";
	}

}
