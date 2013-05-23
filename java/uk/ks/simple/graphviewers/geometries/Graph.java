package uk.ks.simple.graphviewers.geometries;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.utils.RecalculateSystem;
import uk.ks.simple.graphviewers.utils.SystemInformation;

/**
 * Created by root on 5/17/13.
 */
public class Graph {

	private List<Pair> graphicPairs = new ArrayList<Pair>();
	private int color = Color.BLACK;
	private Pair firstPair = null;
	private int lineWidth = 5;
	private Paint paint = new Paint();
    private Graph connectedGraph;

    public Graph(int color, List<Pair> graphicPairs) {
        this.color = color;
        this.graphicPairs = graphicPairs;
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        Pair firstPair = graphicPairs.get(0);
        firstPair.draw(canvas);
        for(int i = 1; i <  graphicPairs.size(); i++) {
            graphicPairs.get(i).draw(canvas);
            drawLine(firstPair, graphicPairs.get(i), canvas);
            firstPair = graphicPairs.get(i);
        }
	}

	private void drawLine(Pair firstPair, Pair secondPair, Canvas canvas) {
		paint.setStrokeWidth(lineWidth);
        Point fp = RecalculateSystem.recalculateCoordinateFromArtificialToOriginal(firstPair.getPoint());
        Point sp = RecalculateSystem.recalculateCoordinateFromArtificialToOriginal(secondPair.getPoint());
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

    public boolean isContainPoint(Point point) {
        Point touchPoint = RecalculateSystem.recalculateCoordinateFromOriginalToArtificial(point);
        Point firstPoint = graphicPairs.get(0).getPoint();
        boolean result = false;
        for(int i = 1; i <  graphicPairs.size(); i++) {
            Point nextPoint = graphicPairs.get(i).getPoint();
            if(isBetween(touchPoint, firstPoint, nextPoint)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean move(Point point) {
        Point movePoint = RecalculateSystem.calculateShiftInArtificialCoordinateSystem(point);
        boolean shift = (Math.abs(movePoint.getX()) > 0 || Math.abs(movePoint.getY()) > 0) && isOnCoordinateSystem(movePoint);
//        if(shift){
            shiftGraph(movePoint);
//        }
        return shift;
    }

    private void shiftGraph(Point movePoint) {
        for(Pair pair : graphicPairs ) {
            Point newPoint = new Point(pair.getPoint().getX() + movePoint.getX(), pair.getPoint().getY() - movePoint.getY());
            pair.setPoint(newPoint);
        }
//        for(int i = 0; i < connectedGraph.size(); i++) {
//            for(Pair pair : connectedGraph.get(i).getGraphicPairs() ) {
//                Point newPoint = new Point(pair.getPoint().getX() + movePoint.getX(), pair.getPoint().getY() - movePoint.getY());
////                pair.setPoint(newPoint);
//                connectedGraph.get(i).move(newPoint);
//            }
//        }
    }

    // ToDo try to optimize
    public boolean isOnCoordinateSystem(Point point) {
        Point movePoint = RecalculateSystem.calculateShiftInArtificialCoordinateSystem(point);
        boolean result = true;
        for(Pair pair : graphicPairs ) {
            Point newPoint = new Point(pair.getPoint().getX() + movePoint.getX(), pair.getPoint().getY() - movePoint.getY());
            boolean betweenLeftRight = newPoint.getX() <= SystemInformation.COUNT_LABEL_BY_X_AXIS && newPoint.getX() >= 0;
            boolean betweenTopBottom = newPoint.getY() <= SystemInformation.COUNT_LABEL_BY_Y_AXIS && newPoint.getY() >= 0;
            if (!(betweenLeftRight && betweenTopBottom)) {
                result = betweenLeftRight && betweenTopBottom;
                break;
            }
        }
        return result;
    }

    private boolean isBetween(Point touchPoint, Point firstPoint, Point nextPoint) {
        boolean xIsBetween = (firstPoint.getX() < nextPoint.getX() ? firstPoint.getX() : nextPoint.getX()) <= touchPoint.getX()
                                &&
                            touchPoint.getX() <= (firstPoint.getX() > nextPoint.getX() ? firstPoint.getX() : nextPoint.getX());

        boolean yIsBetween = (firstPoint.getY() < nextPoint.getY() ? firstPoint.getY() : nextPoint.getY()) <= touchPoint.getY()
                                &&
                            touchPoint.getY() <= (firstPoint.getY() > nextPoint.getY() ? firstPoint.getY() : nextPoint.getY());
        return xIsBetween && yIsBetween;
    }

    public void connectGraph(Graph graph) {
        if (connectedGraph == null) {
            this.connectedGraph = graph;
        }
    }

    public boolean isConnected() {
        return connectedGraph != null;
    }

    public Graph getConnectedGraph() {
        return connectedGraph;
    }

    public void clearConnectedGraph() {
        connectedGraph = null;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Graph that = (Graph) obj;

        if (this.color != that.color) return false;
        //if
//        if (this.getPoint().getX() != that.getPoint().getX()) return false;
//        if (this.getPoint().getY() != that.getPoint().getY()) return false;
//        if (this.color != that.color) return false;
        return true;
    }
}
