package dto;

public class HelpPostDto { 			//헬프센터 검색용
	private int postIdx;			//글IDX
	private int helpCategoryIdx;	//카테고리IDX
	private String title;			//글 제목
	private String content;			//글 내용

	public int getPostIdx() {
		return postIdx;
	}
	public void setPostIdx(int postIdx) {
		this.postIdx = postIdx;
	}
	public int getHelpCategoryIdx() {
		return helpCategoryIdx;
	}
	public void setHelpCategoryIdx(int helpCategoryIdx) {
		this.helpCategoryIdx = helpCategoryIdx;
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
	public HelpPostDto(int postIdx, int helpCategoryIdx, String title, String content) {
		super();
		this.postIdx = postIdx;
		this.helpCategoryIdx = helpCategoryIdx;
		this.title = title;
		this.content = content;
	}
	
   @Override //참조변수를 String 타입으로 변하게 하는 거
    public String toString() {
        return "HelpPostDto { " +
                "postIdx = " + postIdx +
                ", helpCategoryIdx = " + helpCategoryIdx +
                ", title = " + title  +
                ", content = " + content +
                '}';
    }

	
}
