package uk.ks.simple.graphviewers.geometries;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 5/17/13.
 */
public class Graph {

	private List<Pair> graphicPairs = new LinkedList<Pair>();
	private int color = Color.BLACK;
	private Pair firstPair = null;
	private int lineWidth = 3;
	private Paint paint = new Paint();

	public void draw(Canvas canvas) {
		for(Pair pair : graphicPairs ) {
			if(firstPair == null) {
				firstPair = pair;
				firstPair.draw(canvas);
			} else {
				pair.draw(canvas);
				drawLine(firstPair, pair, canvas);
				firstPair = pair;
			}

		}
	}

	private void drawLine(Pair firstPair, Pair secondPair, Canvas canvas) {
		paint.setStrokeWidth(lineWidth);
		canvas.drawLine(
				firstPair.getPoint().getX(),
				firstPair.getPoint().getY(),
				secondPair.getPoint().getX(),
				secondPair.getPoint().getY(),
				paint);
	}
}
