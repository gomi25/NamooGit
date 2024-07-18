package dto;

public class ShowCategoryNameAndTitleDto {
	public int helpCategoryIdx;
	public int postIdx;
	public String categoryName;
	public String title;
	
	
	public int getHelpCategoryIdx() {
		return helpCategoryIdx;
	}


	public void setHelpCategoryIdx(int helpCategoryIdx) {
		this.helpCategoryIdx = helpCategoryIdx;
	}


	public int getPostIdx() {
		return postIdx;
	}


	public void setPostIdx(int postIdx) {
		this.postIdx = postIdx;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public ShowCategoryNameAndTitleDto(int helpCategoryIdx, int postIdx, String categoryName, String title) {
		super();
		this.helpCategoryIdx = helpCategoryIdx;
		this.postIdx = postIdx;
		this.categoryName = categoryName;
		this.title = title;
	}


	@Override //참조변수를 String 타입으로 변하게 하는 거
    public String toString() {
        return "ShowCategoryNameAndTitleDto { " +
                "helpCategoryIdx = " + helpCategoryIdx +
                ", postIdx = " + postIdx  +
                ", categoryName = " + categoryName +
                ", title = " + title +
                '}';
    }
	
	
}
