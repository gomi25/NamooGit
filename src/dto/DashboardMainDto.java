package dto;

public class DashboardMainDto {
	private int wOrder;
	private int widgetIdx;
	private int wSize;
	
	 public DashboardMainDto(int wOrder, int widgetIdx, int wSize) {
		super();
		this.wOrder = wOrder;
		this.widgetIdx = widgetIdx;
		this.wSize = wSize;
	}

	public int getwOrder() {
		return wOrder;
	}

	public void setwOrder(int wOrder) {
		this.wOrder = wOrder;
	}

	public int getWidgetIdx() {
		return widgetIdx;
	}

	public void setWidgetIdx(int widgetIdx) {
		this.widgetIdx = widgetIdx;
	}

	public int getwSize() {
		return wSize;
	}

	public void setwSize(int wSize) {
		this.wSize = wSize;
	}

	@Override //참조변수를 String 타입으로 변하게 하는 거
	    public String toString() {
	        return "DashboardMainDto { " +
	                "wOrder = " + wOrder +
	                ", widgetIdx = " + widgetIdx  +
	                ", wSize = " + wSize +
	                '}';
	    }

	
}
