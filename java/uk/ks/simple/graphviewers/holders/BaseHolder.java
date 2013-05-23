package uk.ks.simple.graphviewers.holders;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.databases.GraphSQLiteHelper;
import uk.ks.simple.graphviewers.databases.dao.GraphicsDAO;
import uk.ks.simple.graphviewers.databases.dao.PointsDAO;
import uk.ks.simple.graphviewers.databases.dao.PointsToGraphicDAO;
import uk.ks.simple.graphviewers.geometries.CoordinateSystem;
import uk.ks.simple.graphviewers.geometries.Graph;
import uk.ks.simple.graphviewers.geometries.Pair;
import uk.ks.simple.graphviewers.utils.RecalculateSystem;
import uk.ks.simple.graphviewers.utils.SimpleGraphGenerator;

public class BaseHolder extends View  implements View.OnTouchListener, View.OnLongClickListener {

	private Paint paint;
	private CoordinateSystem coordinateSystem;
    private List<Graph> graphList;
    private SimpleGraphGenerator simpleGraphGenerator;
    private boolean clear = false;
    private GraphSQLiteHelper graphSQLiteHelper;
    private GraphicsDAO graphicsDAO;
    private PointsDAO pointsDAO;
    private PointsToGraphicDAO pointsToGraphicDAO;
    private Graph movingGraph;
    private Point startDrawPoint;
    private List<Graph> fatGraph = new Vector<Graph>();

    public BaseHolder(Context context) {
		super(context);
		this.setOnTouchListener(this);
        this.setOnLongClickListener(this);
        paint = new Paint();
		coordinateSystem = new CoordinateSystem();
        graphicsDAO = new GraphicsDAO(context);
        pointsDAO = new PointsDAO(context);
        pointsToGraphicDAO = new PointsToGraphicDAO(context);
        simpleGraphGenerator = new SimpleGraphGenerator();
        graphSQLiteHelper = new GraphSQLiteHelper(context);
        getGraphicListFromDB();
	}

    private void getGraphicListFromDB() {
        if(!getGraphicListFromCursor().isEmpty()) {
            graphList = getGraphicListFromCursor();
        } else {
            graphList = fillGraphicListRandom();
        }
    }

    @Override
	public void onDraw(Canvas canvas) {
        if(clear) {
            setBackground(canvas, Color.WHITE);
            coordinateSystem.draw(canvas);
            clear = false;
        } else {
            setBackground(canvas, Color.WHITE);
            coordinateSystem.draw(canvas);
            drawGraphs(canvas);
        }
	}

    private void drawGraphs(Canvas canvas) {
        for(Graph graph: graphList) {
            graph.draw(canvas);
        }
    }

    @Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
        int dx, dy, endX, endY;
        int isTouch = 10;
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startDrawPoint = new Point(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
                movingGraph = findMovingGraph(startDrawPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.w(this.getClass().getName(), "In MOTION MOVE");
                endX = Math.round(motionEvent.getX());
                endY = Math.round(motionEvent.getY());
                dx = endX - startDrawPoint.getX();
                dy = endY - startDrawPoint.getY();
                if(movingGraph != null) {
                    Point movePoint = new Point(dx, dy);
                    if(Math.abs(dx) > isTouch && Math.abs(dy) > isTouch) {
                        move(movePoint);
                        startDrawPoint = new Point(endX, endY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return false;
	}

    private void move(Point movePoint) {
        if (movingGraph.isOnCoordinateSystem(movePoint)){
            if (movingGraph.isConnected()) {
                Graph graph = movingGraph.getConnectedGraph();
                if (graph.isOnCoordinateSystem(movePoint)) {
                    movingGraph.move(movePoint);
                    graph.move(movePoint);
                    invalidate();
                };
            } else {
                movingGraph.move(movePoint);
                invalidate();
                findGraphToConnect(movingGraph);
            }
        }
        ;
    }

    private void findGraphToConnect(Graph movingGraph) {
        if (!movingGraph.isConnected()) {
            for(int i = 0; i < graphList.size(); i++) {
                Graph nextGraph = graphList.get(i);
                if(findSimilarPair(nextGraph, movingGraph) && !nextGraph.isConnected()) {
                    movingGraph.connectGraph(nextGraph);
                    movingGraph.setMergeStyle();
                    nextGraph.connectGraph(movingGraph);
                    nextGraph.setMergeStyle();
                    return;
                }
            }
        }
    }

    private void checkToConnectGraphic(Graph movingGraph) {
        if (fatGraph.isEmpty()) {
            fatGraph.add(movingGraph);
        }
        if (fatGraph.contains(movingGraph)) {
            for(Graph graph: graphList) {
                if(!fatGraph.contains(graph) && findSimilarPair(graph, movingGraph)) {
                    fatGraph.add(graph);
                }
            }
        }
    }

    private boolean findSimilarPair(Graph graph, Graph movingGraph) {
        boolean result = false;
        if (movingGraph.equals(graph)) return false;
        for(Pair currentPair: graph.getGraphicPairs()) {
            for(Pair pair: movingGraph.getGraphicPairs()) {
                if (currentPair.getPoint().equals(pair.getPoint()) ) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private Graph findMovingGraph(Point touchPoint) {
        for(Graph graph: graphList) {
            if(graph.isContainPoint(touchPoint)){
                return graph;
            };
        }
        return null;
    }

    public void reDraw() {
        graphList = fillGraphicListRandom();
        this.invalidate();
    }
    private void setBackground(Canvas canvas, int color) {
        canvas.drawColor(color);
    }

    public void clear() {
        this.clear = true;
        graphSQLiteHelper.clearDB();
        invalidate();
    }

    public List<Graph> getGraphicListFromCursor(){
        List<Graph> result;
        pointsToGraphicDAO.open();
        Cursor cursor = pointsToGraphicDAO.getAllGraphWithPoint();
        result = simpleGraphGenerator.generateFromCursor(cursor);
        pointsToGraphicDAO.close();
        return result;
    }

    public void saveToDB() {
        graphSQLiteHelper.clearDB();
        graphicsDAO.open();
        pointsDAO.open();
        pointsToGraphicDAO.open();
        for(Graph graph: graphList) {
            long graphicId = graphicsDAO.createGraphic(graph.getColor());
            for(Pair pair: graph.getGraphicPairs()) {
                long pointId = pointsDAO.createPoint(pair.getPoint());
                pointsToGraphicDAO.createPointsToGraphic(graphicId, pointId);
            }
        }
    }

    private List<Graph> fillGraphicListRandom() {
        return simpleGraphGenerator.generate();
    }

    @Override
    public boolean onLongClick(View view) {
        for(int i = 0; i < graphList.size(); i++) {
            if (isContainPoint(graphList.get(i), startDrawPoint)) {
                if (graphList.get(i).getConnectedGraph() != null) {
                    graphList.get(i).getConnectedGraph().clearConnectedGraph();
                    graphList.get(i).getConnectedGraph().setSingleStyle();
                }
                graphList.get(i).clearConnectedGraph();
                graphList.get(i).setSingleStyle();
                invalidate();
                return false;
            }
        }
        return false;
    }

    private boolean isContainPoint(Graph graph, Point p) {
        Point point = RecalculateSystem.recalculateCoordinateFromOriginalToArtificial(p);
        for(int i = 0; i < graph.getGraphicPairs().size(); i++) {
            if (graph.getGraphicPairs().get(i).getPoint().equals(point)) {
                return true;
            }
        }
        return false;
    }
}