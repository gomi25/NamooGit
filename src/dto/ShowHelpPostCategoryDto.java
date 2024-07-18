package dto;

public class ShowHelpPostCategoryDto {
	private String iconImgUrl;
	private String title;
	private String subtitle;
	private int count;
	private int helpIdx;
	
	
	public String getIconImgUrl() {
		return iconImgUrl;
	}


	public void setIconImgUrl(String iconImgUrl) {
		this.iconImgUrl = iconImgUrl;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSubtitle() {
		return subtitle;
	}


	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getHelpIdx() {
		return helpIdx;
	}


	public void setHelpIdx(int helpIdx) {
		this.helpIdx = helpIdx;
	}


	public ShowHelpPostCategoryDto(String iconImgUrl, String title, String subtitle, int count, int helpIdx) {
		super();
		this.iconImgUrl = iconImgUrl;
		this.title = title;
		this.subtitle = subtitle;
		this.count = count;
		this.helpIdx = helpIdx;
	}


	@Override //참조변수를 String 타입으로 변하게 하는 거
    public String toString() {
        return "HelpShowPostCategoryDto { " +
                "iconImgUrl = " + iconImgUrl +
                ", subtitle = " + subtitle +
                ", title = " + title  +
                ", count = " + count +
                '}';
    }
}
