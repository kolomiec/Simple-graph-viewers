package uk.ks.simple.graphviewers.geometries;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.utils.RecalculateSystem;

/**
 * Created by root on 5/17/13.
 */
public class Graph {

	private List<Pair> graphicPairs = new LinkedList<Pair>();
	private int color = Color.BLACK;
	private Pair firstPair = null;
	private int lineWidth = 5;
	private Paint paint = new Paint();

    public Graph(int color, List<Pair> graphicPairs) {
        this.color = color;
        this.graphicPairs = graphicPairs;
        paint.setColor(color);
    }

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
        Point fp = RecalculateSystem.recalculateCoordinate(firstPair.getPoint());
        Point sp = RecalculateSystem.recalculateCoordinate(secondPair.getPoint());
		canvas.drawLine(
				fp.getX(),
				fp.getY(),
				sp.getX(),
				sp.getY(),
				paint);
	}

    public int getColor() {
        return color;
    }

    public List<Pair> getGraphicPairs() {
        return graphicPairs;
    }
}
