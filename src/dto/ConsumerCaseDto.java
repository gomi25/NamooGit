package dto;

public class ConsumerCaseDto {
    private int caseIdx;
    private String companyImgUrl;
    private String logoImgUrl;
    private int industry;
    private int personCnt;
    private String threeTxt;
    private String threeUrl;
    private String mainTitle;
    private String title;
    private String subTitle;
    private String caseTxt;
    private String threeTxtPart1;
    private String threeTxtPart2;
    private String threeTxtPart3;
    
	public ConsumerCaseDto(int caseIdx, String companyImgUrl, String logoImgUrl, int industry, int personCnt,
			String threeTxt, String threeUrl, String mainTitle, String title, String subTitle, String caseTxt,
			String threeTxtPart1, String threeTxtPart2, String threeTxtPart3) {
		super();
		this.caseIdx = caseIdx;
		this.companyImgUrl = companyImgUrl;
		this.logoImgUrl = logoImgUrl;
		this.industry = industry;
		this.personCnt = personCnt;
		this.threeTxt = threeTxt;
		this.threeUrl = threeUrl;
		this.mainTitle = mainTitle;
		this.title = title;
		this.subTitle = subTitle;
		this.caseTxt = caseTxt;
		this.threeTxtPart1 = threeTxtPart1;
		this.threeTxtPart2 = threeTxtPart2;
		this.threeTxtPart3 = threeTxtPart3;
	}
	
	public ConsumerCaseDto() {
		
	}

	public int getCaseIdx() {
		return caseIdx;
	}
	
	public void setCaseIdx(int caseIdx) {
		this.caseIdx = caseIdx;
	}
	
	public String getCompanyImgUrl() {
		return companyImgUrl;
	}
	
	public void setCompanyImgUrl(String companyImgUrl) {
		this.companyImgUrl = companyImgUrl;
	}
	
	public String getLogoImgUrl() {
		return logoImgUrl;
	}
	
	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}
	
	public int getIndustry() {
		return industry;
	}
	
	public void setIndustry(int industry) {
		this.industry = industry;
	}
	
	public int getPersonCnt() {
		return personCnt;
	}
	
	public void setPersonCnt(int personCnt) {
		this.personCnt = personCnt;
	}
	
	public String getThreeTxt() {
		return threeTxt;
	}
	
	public void setThreeTxt(String threeTxt) {
		this.threeTxt = threeTxt;
	}
	
	public String getThreeUrl() {
		return threeUrl;
	}
	
	public void setThreeUrl(String threeUrl) {
		this.threeUrl = threeUrl;
	}
	
	public String getMainTitle() {
		return mainTitle;
	}
	
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getCaseTxt() {
		return caseTxt;
	}
	
	public void setCaseTxt(String caseTxt) {
		this.caseTxt = caseTxt;
	}
	
	public String getThreeTxtPart1() {
		return threeTxtPart1;
	}
	
	public void setThreeTxtPart1(String threeTxtPart1) {
		this.threeTxtPart1 = threeTxtPart1;
	}
	
	public String getThreeTxtPart2() {
		return threeTxtPart2;
	}
	
	public void setThreeTxtPart2(String threeTxtPart2) {
		this.threeTxtPart2 = threeTxtPart2;
	}
	
	public String getThreeTxtPart3() {
		return threeTxtPart3;
	}
	
	public void setThreeTxtPart3(String threeTxtPart3) {
		this.threeTxtPart3 = threeTxtPart3;
	}
	
	
	@Override
	public String toString() {
		return "ConsumerCaseDto [caseIdx=" + caseIdx + ", companyImgUrl=" + companyImgUrl + ", logoImgUrl=" + logoImgUrl
				+ ", industry=" + industry + ", personCnt=" + personCnt + ", threeTxt=" + threeTxt + ", threeUrl="
				+ threeUrl + ", mainTitle=" + mainTitle + ", title=" + title + ", subTitle=" + subTitle + ", caseTxt="
				+ caseTxt + ", threeTxtPart1=" + threeTxtPart1 + ", threeTxtPart2=" + threeTxtPart2 + ", threeTxtPart3="
				+ threeTxtPart3 + "]";
	}
}