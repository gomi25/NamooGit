package dto;

public class QnaAnswerDto {
	private int answerIdx;
	private int qnaIdx;
	private String content;
	private String answerDate;
	
	public QnaAnswerDto(int answerIdx, int qnaIdx, String content, String answerDate) {
		this.answerIdx = answerIdx;
		this.qnaIdx = qnaIdx;
		this.content = content;
		this.answerDate = answerDate;
	}
	
	public int getAnswerIdx() {
		return answerIdx;
	}
	public void setAnswerIdx(int answerIdx) {
		this.answerIdx = answerIdx;
	}
	public int getQnaIdx() {
		return qnaIdx;
	}
	public void setQnaIdx(int qnaIdx) {
		this.qnaIdx = qnaIdx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}
	
	

}
