package dto;

public class TopicBoardFileDto {
	private Integer topicBoardIdx;	// 토픽글idx
	private Integer fileIdx;		// 파일idx
	private String fileName;	// 토픽파일이름
	
	
	public TopicBoardFileDto(Integer topicBoardIdx, Integer fileIdx, String fileName) {
		super();
		this.topicBoardIdx = topicBoardIdx;
		this.fileIdx = fileIdx;
		this.fileName = fileName;
	}
	
	public TopicBoardFileDto() {
		// TODO Auto-generated constructor stub
	}

	public Integer getTopicBoardIdx() {
		return topicBoardIdx;
	}

	public void setTopicBoardIdx(Integer topicBoardIdx) {
		this.topicBoardIdx = topicBoardIdx;
	}

	public Integer getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(Integer fileIdx) {
		this.fileIdx = fileIdx;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String toString() {
        return "TopicBoardFileDto { " + "\n" +
                "topicBoardIdx = " + topicBoardIdx + "\n" +
                "fileIdx = " + fileIdx + "\n" +
        		"fileName = " + fileName + "}";
    }
}
