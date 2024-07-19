package dto;

// 특정 토픽방 내부에 있는 토픽글Dto
public class TopicBoardDto {
	private int topicBoardIdx;	// 토픽글idx
	private int topicIdx;		// 토픽방idx
	private int memberIdx;		// 회원idx
	private String profileUrl;	// 프로필url
	private String name;		// 이름
	private String state;		// 상태
	private String title;		// 제목
	private String content; 	// 내용
	private String writeDate;	// 작성일시
	private int unreadCnt;		// 안 읽은 메시지 수
	
	public TopicBoardDto(int topicBoardIdx, int topicIdx, int memberIdx, String profileUrl, String name, String state,
			String title, String content, String writeDate, int unreadCnt) {
		this.topicBoardIdx = topicBoardIdx;
		this.topicIdx = topicIdx;
		this.memberIdx = memberIdx;
		this.profileUrl = profileUrl;
		this.name = name;
		this.state = state;
		this.title = title;
		this.content = content;
		this.writeDate = writeDate;
		this.unreadCnt = unreadCnt;
	}

	public int getTopicBoardIdx() {
		return topicBoardIdx;
	}


	public void setTopicBoardIdx(int topicBoardIdx) {
		this.topicBoardIdx = topicBoardIdx;
	}


	public int getTopicIdx() {
		return topicIdx;
	}


	public void setTopicIdx(int topicIdx) {
		this.topicIdx = topicIdx;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWriteDate() {
		return writeDate;
	}


	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}


	public int getUnreadCnt() {
		return unreadCnt;
	}


	public void setUnreadCnt(int unreadCnt) {
		this.unreadCnt = unreadCnt;
	}


	@Override
	public String toString() {
		return "topicBoardIdx : " + topicBoardIdx + "\n" +
   			   "topicIdx : " + topicIdx + "\n" +
   			   "memberIdx : " + memberIdx + "\n" +
   			   "profileUrl : " + profileUrl + "\n" +
   			   "name : " + name + "\n" +
   			   "state : " + state + "\n" +
   			   "title : " + title + "\n" +
   			   "content : " + content + "\n" +
   			   "writeDate : " + writeDate + "\n" +
   			   "unreadCnt : " + unreadCnt;
	}
	
}
