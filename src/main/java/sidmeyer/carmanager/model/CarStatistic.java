package sidmeyer.carmanager.model;

/**
 * Created by Stas on 23.03.2017.
 */
public class CarStatistic {

	//private boolean onWaitingLine;
	private int waitingLineCount = 0;
	private int garageCount = 0;
	private int yardCount = 0;
	private int cancelCount = 0;
	//private boolean cancelled;

	// general constructor
	public CarStatistic() {
	}

	// for tests
	public CarStatistic(int waitingLineCount, int garageCount, int yardCount, int cancelCount) {
		this.waitingLineCount = waitingLineCount;
		this.garageCount = garageCount;
		this.yardCount = yardCount;
		this.cancelCount = cancelCount;
	}

	public int getWaitingLineCount() {
		return waitingLineCount;
	}

	public void incWaitingLineCount() {
		waitingLineCount++;
	}

	public int getGarageCount() {
		return garageCount;
	}

	public void incGarageCount() {
		garageCount++;
	}

	public int getYardCount() {
		return yardCount;
	}

	public void incYardCount() {
		yardCount++;
	}

	public int getCancelCount() {
		return cancelCount;
	}

	public void incCancelCount() {
		cancelCount++;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CarStatistic that = (CarStatistic) o;

		if (waitingLineCount != that.waitingLineCount) return false;
		if (garageCount != that.garageCount) return false;
		if (yardCount != that.yardCount) return false;
		return cancelCount == that.cancelCount;
	}

	@Override
	public int hashCode() {
		int result = waitingLineCount;
		result = 31 * result + garageCount;
		result = 31 * result + yardCount;
		result = 31 * result + cancelCount;
		return result;
	}

//	public String toString(){
//		return waitingLineCount + "\t" + garageCount + "\t" + yardCount + "\t" + cancelCount;
//	}


	@Override
	public String toString() {
		return "CarStatistic{" +
				"WL=" + waitingLineCount +
				", G=" + garageCount +
				", Y=" + yardCount +
				", C=" + cancelCount +
				'}';
	}
}
