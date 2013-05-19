package uk.ks.simple.graphviewers.geometries;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import uk.ks.simple.graphviewers.beans.Point;

/**
 * Created by root on 5/17/13.
 */
public class Pair {

	private int color = Color.BLACK;
	private final Point point;
	private Paint paint = new Paint();
	private final int diameter = 5;

	{
		paint.setStrokeWidth(diameter);
		paint.setColor(this.color);
	}

	public Pair(Point point) {
		this.point = point;
	}

	public Pair(int color, Point point) {
		this.color = color;
		this.point = point;
	}

	public void draw(Canvas canvas) {
		canvas.drawPoint(point.getX(), point.getY(), paint);
	}

	public Point getPoint() {
		return point;
	}

    public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
