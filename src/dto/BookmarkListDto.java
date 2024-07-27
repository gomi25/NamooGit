package dto;

public class BookmarkListDto {
    private int bookmarkIdx;
    private String type;
    private int itemIdx;
    private String content;
    private String writeDate;  
    private String locationName; // 채팅방 이름 또는 토픽방 이름
    private String authorName;

    public BookmarkListDto(int bookmarkIdx, String type, int itemIdx, String content, String writeDate,
    		String locationName, String authorName) {
    	this.bookmarkIdx = bookmarkIdx;
    	this.type = type;
    	this.itemIdx = itemIdx;
    	this.content = content;
    	this.writeDate = writeDate;
    	this.locationName = locationName;
    	this.authorName = authorName;
    }

    public BookmarkListDto() {
		// TODO Auto-generated constructor stub
	}

	public int getBookmarkIdx() {
		return bookmarkIdx;
	}


	public void setBookmarkIdx(int bookmarkIdx) {
		this.bookmarkIdx = bookmarkIdx;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getItemIdx() {
		return itemIdx;
	}


	public void setItemIdx(int itemIdx) {
		this.itemIdx = itemIdx;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWriteDate() {
		return writeDate;
	}


	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	@Override
    public String toString() {
        return "BookmarkListDTO{" +
                "bookmarkIdx = " + bookmarkIdx +
                ", type= '" + type + '\'' +
                ", itemIdx = " + itemIdx +
                ", content = '" + content + '\'' +
                ", writeDate = '" + writeDate + '\'' +
                ", locationName = '" + locationName + '\'' +
                ", authorName = '" + authorName +
                '}';
    }

}
