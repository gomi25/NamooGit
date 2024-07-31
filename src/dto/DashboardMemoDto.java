package dto;

public class DashboardMemoDto {
	private int memoIdx;
	private int widgetIdx;
	private int memberIdx;
	private String memo;
	public int getMemoIdx() {
		return memoIdx;
	}
	public void setMemoIdx(int memoIdx) {
		this.memoIdx = memoIdx;
	}
	public int getWidgetIdx() {
		return widgetIdx;
	}
	public void setWidgetIdx(int widgetIdx) {
		this.widgetIdx = widgetIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public DashboardMemoDto(int memoIdx, int widgetIdx, int memberIdx, String memo) {
		super();
		this.memoIdx = memoIdx;
		this.widgetIdx = widgetIdx;
		this.memberIdx = memberIdx;
		this.memo = memo;
	}
	
	
	
}
