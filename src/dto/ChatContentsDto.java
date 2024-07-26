package dto;

import java.util.ArrayList;

// 채팅글
public class ChatContentsDto {

	private int chatIdx;		 // 채팅글idx
	private int chatroomIdx;	 // 채팅방idx
	private Integer memberIdx;	 // 작성자idx
	private String profileUrl;	 // 프로필url
	private String name;		 // 이름
	private String state;		 // 상태
	private String content;		 // 내용 
	private Integer fileIdx; 	 // 파일idx
	private String fileName;	 // 파일명
	private String writeDate;	 // 작성일시
	private int unreadCnt;		 // 안 읽은 메시지 수 
	private int modified;		 // 수정 여부
	
	
	
    public ChatContentsDto(int chatIdx, int chatroomIdx, Integer memberIdx, String profileUrl, String name, String state,
			String content, Integer fileIdx, String fileName, String writeDate, int unreadCnt, int modified) {
		super();
		this.chatIdx = chatIdx;
		this.chatroomIdx = chatroomIdx;
		this.memberIdx = memberIdx;
		this.profileUrl = profileUrl;
		this.name = name;
		this.state = state;
		this.content = content;
		this.fileIdx = fileIdx;
		this.fileName = fileName;
		this.writeDate = writeDate;
		this.unreadCnt = unreadCnt;
		this.modified = modified;
	}


	public int getChatIdx() {
		return chatIdx;
	}

	public void setChatIdx(int chatIdx) {
		this.chatIdx = chatIdx;
	}

	public int getChatroomIdx() {
		return chatroomIdx;
	}

	public void setChatroomIdx(int chatroomIdx) {
		this.chatroomIdx = chatroomIdx;
	}

	public Integer getMemberIdx() {
		return memberIdx;
	}



	public void setMemberIdx(Integer memberIdx) {
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



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Integer getFileIdx() {
		return fileIdx;
	}



	public void setFileIdx(Integer fileIdx) {
		this.fileIdx = fileIdx;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
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



	public int getModified() {
		return modified;
	}



	public void setModified(int modified) {
		this.modified = modified;
	}



	public String toString() {
        return "ChatContentsDto{" + "\n" +
                "chatIdx = " + chatIdx + "\n" +
                "chatroomIdx = " + chatroomIdx + "\n" +
                "memberIdx = " + memberIdx + "\n" +
                "profileUrl = " + profileUrl + "\n" +
                "name = " + name + "\n" +
                "state = " + state + "\n" +
                "content = " + content + "\n" +
                "fileIdx = " + fileIdx + "\n" +
                "fileName = " + fileName + "\n" + 
                "writeDate = " + writeDate + "\n" +
                "unreadCnt = " + unreadCnt + "\n" +
                "modified = " + modified + '}';
    }
}
