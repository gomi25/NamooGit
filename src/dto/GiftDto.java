package dto;

public class GiftDto {
    private int giftIdx;
    private String brandName;
    private String giftName;
    private String giftImgUrl;
    private int price;
    private Integer reducedPrice; // 세일 가격은 null일 수 있으므로 Integer로 선언

    public GiftDto() {
    }

    public int getGiftIdx() {
        return giftIdx;
    }

    public void setGiftIdx(int giftIdx) {
        this.giftIdx = giftIdx;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftImgUrl() {
        return giftImgUrl;
    }

    public void setGiftImgUrl(String giftImgUrl) {
        this.giftImgUrl = giftImgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getReducedPrice() {
        return reducedPrice;
    }

    public void setReducedPrice(Integer reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

    @Override
    public String toString() {
        return "GiftDto [giftIdx=" + giftIdx + ", brandName=" + brandName + ", giftName=" + giftName + ", giftImgUrl=" + giftImgUrl + ", price="
                + price + ", reducedPrice=" + reducedPrice + "]";
    }
}
