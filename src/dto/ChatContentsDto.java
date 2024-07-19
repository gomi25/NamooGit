package dto;

import java.util.ArrayList;

// 채팅글
public class ChatContentsDto {

	private int chatIdx;
	private ArrayList<ProfileUrlImgDto> profileUrlColor;
	private String name;
	private Integer state;
	private String content;
	private Integer fileIdx;
	private Integer emoticonIdx;
	private String writeDate;
	private int unreadCnt;
	private int modified;
	
	public ChatContentsDto(int chatIdx, ArrayList<ProfileUrlImgDto> profileUrlColor, String name, Integer state,
			String content, Integer fileIdx, Integer emoticonIdx, String writeDate, int unreadCnt, int modified) {
		super();
		this.chatIdx = chatIdx;
		this.profileUrlColor = profileUrlColor;
		this.name = name;
		this.state = state;
		this.content = content;
		this.fileIdx = fileIdx;
		this.emoticonIdx = emoticonIdx;
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


	public ArrayList<ProfileUrlImgDto> getProfileUrlColor() {
		return profileUrlColor;
	}


	public void setProfileUrlColor(ArrayList<ProfileUrlImgDto> profileUrlColor) {
		this.profileUrlColor = profileUrlColor;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
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


	public Integer getEmoticonIdx() {
		return emoticonIdx;
	}


	public void setEmoticonIdx(Integer emoticonIdx) {
		this.emoticonIdx = emoticonIdx;
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


	@Override
    public String toString() {
        return "ChatContentsDto{" +
                "chatIdx=" + chatIdx +
                ", profileUrlColor=" + profileUrlColor +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", content='" + content + '\'' +
                ", fileIdx=" + fileIdx +
                ", emoticonIdx=" + emoticonIdx +
                ", writeDate='" + writeDate + '\'' +
                ", unreadCnt=" + unreadCnt +
                ", modified=" + modified +
                '}';
    }
}
