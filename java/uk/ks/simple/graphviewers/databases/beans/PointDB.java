package uk.ks.simple.graphviewers.databases.beans;

/**
 * Created by root on 5/19/13.
 */
public class PointDB {

	private long id;
	private long xCoordinate;
	private long yCoordinate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(long xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public long getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(long yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}
