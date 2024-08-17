package dto;

public class GiftPayDto {
	private int paymentIdx;
	private int memberIdx;
	private int giftIdx;
	private String paymentDate;
	private int agree1;
	private int agree2;
	private int agree3;
	private String message;

	public GiftPayDto(int paymentIdx, int memberIdx, int giftIdx, String paymentDate, int agree1, int agree2,
			int agree3, String message) {
		this.paymentIdx = paymentIdx;
		this.memberIdx = memberIdx;
		this.giftIdx = giftIdx;
		this.paymentDate = paymentDate;
		this.agree1 = agree1;
		this.agree2 = agree2;
		this.agree3 = agree3;
		this.message = message;
	}

	public GiftPayDto() {
	} // 기본 생성자

	// Getters and Setters
	public int getPaymentIdx() {
		return paymentIdx;
	}

	public void setPaymentIdx(int paymentIdx) {
		this.paymentIdx = paymentIdx;
	}

	public int getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

	public int getGiftIdx() {
		return giftIdx;
	}

	public void setGiftIdx(int giftIdx) {
		this.giftIdx = giftIdx;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getAgree1() {
		return agree1;
	}

	public void setAgree1(int agree1) {
		this.agree1 = agree1;
	}

	public int getAgree2() {
		return agree2;
	}

	public void setAgree2(int agree2) {
		this.agree2 = agree2;
	}

	public int getAgree3() {
		return agree3;
	}

	public void setAgree3(int agree3) {
		this.agree3 = agree3;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GiftPayDto [paymentIdx=" + paymentIdx + ", memberIdx=" + memberIdx + ", giftIdx=" + giftIdx
				+ ", paymentDate=" + paymentDate + ", agree1=" + agree1 + ", agree2=" + agree2 + ", agree3=" + agree3
				+ ", message=" + message + "]";
	}
}
