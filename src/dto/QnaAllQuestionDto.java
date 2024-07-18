package dto;

public class QnaAllQuestionDto {
	private int qnaIdx;
	private int replyCondition;
	private String content;
	private String name;
	private String questionDate;
	public QnaAllQuestionDto(int qnaIdx, int replyCondition, String content, String name, String questionDate) {
		super();
		this.qnaIdx = qnaIdx;
		this.replyCondition = replyCondition;
		this.content = content;
		this.name = name;
		this.questionDate = questionDate;
	}
	public int getQnaIdx() {
		return qnaIdx;
	}
	public void setQnaIdx(int qnaIdx) {
		this.qnaIdx = qnaIdx;
	}
	public int getReplyCondition() {
		return replyCondition;
	}
	public void setReplyCondition(int replyCondition) {
		this.replyCondition = replyCondition;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuestionDate() {
		return questionDate;
	}
	public void setQuestionDate(String questionDate) {
		this.questionDate = questionDate;
	}
	
	
	
}
