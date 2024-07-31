package dto;

public class GanttWorkLikeDto {
	private int title_like;
	private String name;
	public GanttWorkLikeDto(int title_like, String name) {
		super();
		this.title_like = title_like;
		this.name = name;
	}
	public int getTitle_like() {
		return title_like;
	}
	public void setTitle_like(int title_like) {
		this.title_like = title_like;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
    @Override
    public String toString() {
        return "title_like: " + title_like + ", name: " + name;
    }
}
