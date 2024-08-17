package dto;

public class GiftReceivedAmountDto {
	private int paymentIdx;
    private String receivedName;
    private String receivedPhone;
    private int qty;
    
    public GiftReceivedAmountDto(int paymentIdx, String receivedName, String receivedPhone, int qty) {
		this.paymentIdx = paymentIdx;
		this.receivedName = receivedName;
		this.receivedPhone = receivedPhone;
		this.qty = qty;
	}

    // 기본 생성자
    public GiftReceivedAmountDto() {}
    
	// Getters and Setters
    public int getPaymentIdx() {
        return paymentIdx;
    }

    public void setPaymentIdx(int paymentIdx) {
        this.paymentIdx = paymentIdx;
    }

    public String getReceivedName() {
        return receivedName;
    }

    public void setReceivedName(String receivedName) {
        this.receivedName = receivedName;
    }

    public String getReceivedPhone() {
        return receivedPhone;
    }

    public void setReceivedPhone(String receivedPhone) {
        this.receivedPhone = receivedPhone;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

	@Override
	public String toString() {
		return "GiftReceivedAmountDto [paymentIdx=" + paymentIdx + ", receivedName=" + receivedName + ", receivedPhone="
				+ receivedPhone + ", qty=" + qty + "]";
	}
}
