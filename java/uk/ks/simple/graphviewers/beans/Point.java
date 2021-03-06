package uk.ks.simple.graphviewers.beans;

/**
 * Created by root on 5/17/13.
 */
public class Point {

	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

    @Override
    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Point that = (Point) obj;

        if (this.getX() != that.getX()) return false;
        if (this.getY() != that.getY()) return false;
        return true;
    }

}
