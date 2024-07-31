package dto;

public class DashboardFileBoxDto {
	private String fileName;
	private String saveDate;
	private String volume;
	private int fileTypeIdx;
	private int projectIdx;
	private String image;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public int getFileTypeIdx() {
		return fileTypeIdx;
	}
	public void setFileTypeIdx(int fileTypeIdx) {
		this.fileTypeIdx = fileTypeIdx;
	}
	public int getProjectIdx() {
		return projectIdx;
	}
	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public DashboardFileBoxDto(String fileName, String saveDate, String volume, int fileTypeIdx, int projectIdx,
			String image) {
		super();
		this.fileName = fileName;
		this.saveDate = saveDate;
		this.volume = volume;
		this.fileTypeIdx = fileTypeIdx;
		this.projectIdx = projectIdx;
		this.image = image;
	}
	
	
	
}
