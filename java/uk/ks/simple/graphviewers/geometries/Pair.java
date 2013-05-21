package uk.ks.simple.graphviewers.geometries;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Comparator;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.utils.RecalculateSystem;

/**
 * Created by root on 5/17/13.
 */
public class Pair implements Comparable<Pair> {

	private int color = Color.BLACK;
	private Point point = new Point(0, 0);
	private Paint paint = new Paint();
	private final int diameter = 6;


	public Pair(int color, Point point) {
		this.color = color;
		this.point = point;
        paint.setColor(color);
	}

	public void draw(Canvas canvas) {
        Point point = RecalculateSystem.recalculateCoordinate(this.point);
        canvas.drawCircle(point.getX(), point.getY(), diameter, paint);
	}

//    private Point recalculateCoordinate() {
//        Point result;
//        CoordinateSystem coordinateSystem = new CoordinateSystem();
//        int x = coordinateSystem.getOriginPoint().getX() + (this.point.getX() * coordinateSystem.getLabelStep());
//        int y = coordinateSystem.getOriginPoint().getY() - (this.point.getY() * coordinateSystem.getLabelStep());
//        result = new Point(x, y);
//        return result;
//    }

    public Point getPoint() {
		return point;
	}

    public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

    @Override
    public int compareTo(Pair pair) {
        if(this.getPoint().getX() > pair.getPoint().getX()) {
            return 1;
        }
        if(this.getPoint().getX() == pair.getPoint().getX()) {
            if(this.getPoint().getY() > pair.getPoint().getY()) {
                return 1;
            } else {
                if(this.getPoint().getY() < pair.getPoint().getY()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Pair that = (Pair) obj;

        if (this.getPoint().getX() != that.getPoint().getX()) return false;
        if (this.getPoint().getY() != that.getPoint().getY()) return false;
        if (this.color != that.color) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pair[x=" + point.getX() + ", y=" + point.getY() + ", color=" + this.color + "]";
    }
}
