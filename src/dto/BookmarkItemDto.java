package dto;

public class BookmarkItemDto {
	int bookmarkIdx;
	int memberIdxFrom;
	int memberIdxTo;
	int chatroomIdx;
	int chatIdx;
	int chatCommentIdx;
	int topicIdx;
	int topicBoardIdx;
	int topicCommentIdx;
	int fileIdx;
	int todoTitleIdx;
	int projectIdx;
	int workIdx;
	int subworkIdx;
	
	public BookmarkItemDto(int bookmarkIdx, int memberIdxFrom, int memberIdxTo, int chatroomIdx, int chatIdx,
			int chatCommentIdx, int topicIdx, int topicBoardIdx, int topicCommentIdx, int fileIdx, int todoTitleIdx,
			int projectIdx, int workIdx, int subworkIdx) {
		this.bookmarkIdx = bookmarkIdx;
		this.memberIdxFrom = memberIdxFrom;
		this.memberIdxTo = memberIdxTo;
		this.chatroomIdx = chatroomIdx;
		this.chatIdx = chatIdx;
		this.chatCommentIdx = chatCommentIdx;
		this.topicIdx = topicIdx;
		this.topicBoardIdx = topicBoardIdx;
		this.topicCommentIdx = topicCommentIdx;
		this.fileIdx = fileIdx;
		this.todoTitleIdx = todoTitleIdx;
		this.projectIdx = projectIdx;
		this.workIdx = workIdx;
		this.subworkIdx = subworkIdx;
	}

	public int getBookmarkIdx() {
		return bookmarkIdx;
	}

	public void setBookmarkIdx(int bookmarkIdx) {
		this.bookmarkIdx = bookmarkIdx;
	}

	public int getMemberIdxFrom() {
		return memberIdxFrom;
	}

	public void setMemberIdxFrom(int memberIdxFrom) {
		this.memberIdxFrom = memberIdxFrom;
	}

	public int getMemberIdxTo() {
		return memberIdxTo;
	}

	public void setMemberIdxTo(int memberIdxTo) {
		this.memberIdxTo = memberIdxTo;
	}

	public int getChatroomIdx() {
		return chatroomIdx;
	}

	public void setChatroomIdx(int chatroomIdx) {
		this.chatroomIdx = chatroomIdx;
	}

	public int getChatIdx() {
		return chatIdx;
	}

	public void setChatIdx(int chatIdx) {
		this.chatIdx = chatIdx;
	}

	public int getChatCommentIdx() {
		return chatCommentIdx;
	}

	public void setChatCommentIdx(int chatCommentIdx) {
		this.chatCommentIdx = chatCommentIdx;
	}

	public int getTopicIdx() {
		return topicIdx;
	}

	public void setTopicIdx(int topicIdx) {
		this.topicIdx = topicIdx;
	}

	public int getTopicBoardIdx() {
		return topicBoardIdx;
	}

	public void setTopicBoardIdx(int topicBoardIdx) {
		this.topicBoardIdx = topicBoardIdx;
	}

	public int getTopicCommentIdx() {
		return topicCommentIdx;
	}

	public void setTopicCommentIdx(int topicCommentIdx) {
		this.topicCommentIdx = topicCommentIdx;
	}

	public int getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}

	public int getTodoTitleIdx() {
		return todoTitleIdx;
	}

	public void setTodoTitleIdx(int todoTitleIdx) {
		this.todoTitleIdx = todoTitleIdx;
	}

	public int getProjectIdx() {
		return projectIdx;
	}

	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
	}

	public int getWorkIdx() {
		return workIdx;
	}

	public void setWorkIdx(int workIdx) {
		this.workIdx = workIdx;
	}

	public int getSubworkIdx() {
		return subworkIdx;
	}

	public void setSubworkIdx(int subworkIdx) {
		this.subworkIdx = subworkIdx;
	}
}
