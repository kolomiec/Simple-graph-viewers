package uk.ks.simple.graphviewers.geometries;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.utils.SystemInformation;

/**
 * Created by root on 5/18/13.
 */
public class CoordinateSystem {
	private Point startPointXAxis = new Point(35, SystemInformation.DISPLAY_HEIGHT-200);
	private Point endPointXAxis = new Point(SystemInformation.DISPLAY_WIDTH-10, SystemInformation.DISPLAY_HEIGHT-200);
	private Point startPointYAxis = new Point(35,SystemInformation.DISPLAY_HEIGHT - 200);
	private Point endPointYAxis = new Point(35,10);
	private final int labelStep = 40;
	private final int labelHeight = 15;
	private final int lineWidth = 5;
	private final int labelWidth = 2;
	private Paint paint = new Paint();

	public CoordinateSystem() {
		paint.setColor(Color.BLACK);
	}

	public void draw(Canvas canvas) {
		drawXAxis(canvas);
		drawYAxis(canvas);
	}

	private void drawYAxis(Canvas canvas) {
		paint.setStrokeWidth(lineWidth);
		canvas.drawLine(startPointYAxis.getX(), startPointYAxis.getY(), endPointYAxis.getX(), endPointYAxis.getY(), paint);
		drawLabelOnYAxis(canvas);
	}

	private void drawLabelOnYAxis(Canvas canvas) {
		int labelsCount = (startPointYAxis.getY()) / labelStep;
		Point cursorPos = new Point(startPointYAxis.getX(), startPointXAxis.getY() - labelStep);
		paint.setStrokeWidth(labelWidth);
		for(int i = 0; i < labelsCount - 1; i++) {
			canvas.drawLine(cursorPos.getX() - labelHeight, cursorPos.getY(), cursorPos.getX() + labelHeight, cursorPos.getY(), paint);
			cursorPos.setY(cursorPos.getY() - labelStep);
		}
	}

	private void drawXAxis(Canvas canvas){
		paint.setStrokeWidth(lineWidth);
		canvas.drawLine(startPointXAxis.getX(), startPointXAxis.getY(), endPointXAxis.getX(), endPointXAxis.getY(), paint);
		drawLabelOnXAxis(canvas);
	}

	private void drawLabelOnXAxis(Canvas canvas) {
		int labelsCount = (SystemInformation.DISPLAY_WIDTH - startPointXAxis.getX()) / labelStep;
		Point cursorPos = new Point(startPointXAxis.getX() + labelStep, startPointXAxis.getY());
		paint.setStrokeWidth(labelWidth);
		for(int i = 0; i < labelsCount - 1; i++) {
			canvas.drawLine(cursorPos.getX(), cursorPos.getY()-labelHeight, cursorPos.getX(), cursorPos.getY()+labelHeight,paint);
			cursorPos.setX(cursorPos.getX() + labelStep);
		}
	}
}
