package dto;

public class AlarmGanttNoticeDto {
	private String profilePicUrl;
	private String txtName;
	private String memberName;
	private String alarmTxt;
	private String alarmDate;
	private int teamIdx;
	public AlarmGanttNoticeDto(String profilePicUrl, String txtName, String memberName, String alarmTxt,
			String alarmDate, int teamIdx) {
		super();
		this.profilePicUrl = profilePicUrl;
		this.txtName = txtName;
		this.memberName = memberName;
		this.alarmTxt = alarmTxt;
		this.alarmDate = alarmDate;
		this.teamIdx = teamIdx;
	}
	public String getProfilePicUrl() {
		return profilePicUrl;
	}
	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}
	public String getTxtName() {
		return txtName;
	}
	public void setTxtName(String txtName) {
		this.txtName = txtName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getAlarmTxt() {
		return alarmTxt;
	}
	public void setAlarmTxt(String alarmTxt) {
		this.alarmTxt = alarmTxt;
	}
	public String getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(String alarmDate) {
		this.alarmDate = alarmDate;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	
	
}
