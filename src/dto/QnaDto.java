package dto;

public class QnaDto {
	private int qnaIdx;  //qna 번호
	private String name; //작성자 이름
	private String phoneNumber; // 폰번호
	private String position; // 직책
	private String email; //이메일
	private int industry; // 산업군
	private int count; // 인원수
	private int question; //문의사항
	private int agreement; // 동의란
	private String questionDate; // 문의일시
 	private int replyCondition; //답변상태
	private String content; // 내용
	public QnaDto(int qnaIdx, String name, String phoneNumber, String position, String email, int industry, int count,
			int question, int agreement, String questionDate, int replyCondition, String content) {
		super();
		this.qnaIdx = qnaIdx;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.position = position;
		this.email = email;
		this.industry = industry;
		this.count = count;
		this.question = question;
		this.agreement = agreement;
		this.questionDate = questionDate;
		this.replyCondition = replyCondition;
		this.content = content;
	}
	public int getQnaIdx() {
		return qnaIdx;
	}
	public void setQnaIdx(int qnaIdx) {
		this.qnaIdx = qnaIdx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIndustry() {
		return industry;
	}
	public void setIndustry(int industry) {
		this.industry = industry;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	public int getAgreement() {
		return agreement;
	}
	public void setAgreement(int agreement) {
		this.agreement = agreement;
	}
	public String getQuestionDate() {
		return questionDate;
	}
	public void setQuestionDate(String questionDate) {
		this.questionDate = questionDate;
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
	
	

	
	
	
	

}
