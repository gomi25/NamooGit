package dto;

public class TeamListDto {
	private String teamName;
	private String domain;
	private String teamImage;
	
	public TeamListDto(String teamName, String domain, String teamImage) {
		this.teamName = teamName;
		this.domain = domain;
		this.teamImage = teamImage;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTeamImage() {
		return teamImage;
	}

	public void setTeamImage(String teamImage) {
		this.teamImage = teamImage;
	}

	@Override
	public String toString() {
		return "TeamListDto [teamName=" + teamName + ", domain=" + domain + ", teamImage=" + teamImage + "]";
	}
}
