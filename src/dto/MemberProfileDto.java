package dto;

public class MemberProfileDto { //회원가입용?
	private int memberIdx; 			// 멤버idx
	private String email;			// 이메일
	private String pw;				// 비밀번호
	private String name;			// 이름
	private String birth;			// 생년월일
	private String phoneNumber;		// 전화번호
	private String joinDate;		// 가입일자
	public MemberProfileDto(int memberIdx, String email, String pw, String name, String birth, String phoneNumber,
			String joinDate) {
		super();
		this.memberIdx = memberIdx;
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.joinDate = joinDate;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	
}
