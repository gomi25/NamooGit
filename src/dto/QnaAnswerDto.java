package dto;

public class QnaAnswerDto {
	private String content;
	private String answer_date;
	public QnaAnswerDto(String content, String answer_date) {
		super();
		this.content = content;
		this.answer_date = answer_date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer_date() {
		return answer_date;
	}
	public void setAnswer_date(String answer_date) {
		this.answer_date = answer_date;
	}
	@Override
	public String toString() {
		return "content: " + content + "\n"
				+ "answer_date: " + answer_date;
	}
	

}
