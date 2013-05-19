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
	private Point startPointXAxis = new Point(50, SystemInformation.DISPLAY_HEIGHT-200);
	private Point endPointXAxis = new Point(SystemInformation.DISPLAY_WIDTH-10, SystemInformation.DISPLAY_HEIGHT-200);
	private Point startPointYAxis = new Point(50,SystemInformation.DISPLAY_HEIGHT - 200);
	private Point endPointYAxis = new Point(50,10);
	private final int labelStep = 40;
	private final int labelHeight = 15;
	private final int lineWidth = 5;
	private final int labelWidth = 2;
    private final int textWidth = 30;
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
        int magicNumber = 7; // :) ToDo Rename
		Point cursorPos = new Point(startPointYAxis.getX(), startPointXAxis.getY() - labelStep);
		paint.setStrokeWidth(labelWidth);
        paint.setTextSize(textWidth);
		for(int i = 0; i < labelsCount - 1; i++) {
			canvas.drawLine(cursorPos.getX() - labelHeight, cursorPos.getY(), cursorPos.getX() + labelHeight, cursorPos.getY(), paint);
            canvas.drawText(Integer.toString(i+1), cursorPos.getX() -labelHeight - textWidth, cursorPos.getY() + magicNumber, paint);
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
        int magicNumber = 7; // :) ToDo Rename
		Point cursorPos = new Point(startPointXAxis.getX() + labelStep, startPointXAxis.getY());
		paint.setStrokeWidth(labelWidth);
        paint.setTextSize(textWidth);
		for(int i = 0; i < labelsCount - 1; i++) {
			canvas.drawLine(cursorPos.getX(), cursorPos.getY()-labelHeight, cursorPos.getX(), cursorPos.getY()+labelHeight,paint);
            canvas.drawText(Integer.toString(i+1), cursorPos.getX() - magicNumber, cursorPos.getY()+labelHeight + textWidth, paint);
			cursorPos.setX(cursorPos.getX() + labelStep);
		}
	}
}
