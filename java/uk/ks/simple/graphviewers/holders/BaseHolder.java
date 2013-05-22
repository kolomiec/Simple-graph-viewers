package uk.ks.simple.graphviewers.holders;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import uk.ks.simple.graphviewers.databases.GraphSQLiteHelper;
import uk.ks.simple.graphviewers.databases.dao.GraphicsDAO;
import uk.ks.simple.graphviewers.databases.dao.PointsDAO;
import uk.ks.simple.graphviewers.databases.dao.PointsToGraphicDAO;
import uk.ks.simple.graphviewers.geometries.CoordinateSystem;
import uk.ks.simple.graphviewers.geometries.Graph;
import uk.ks.simple.graphviewers.geometries.Pair;
import uk.ks.simple.graphviewers.utils.SimpleGraphGenerator;

public class BaseHolder extends View  implements View.OnTouchListener {

	private Paint paint;
	private CoordinateSystem coordinateSystem;
    private List<Graph> graphList;
    private SimpleGraphGenerator simpleGraphGenerator;
    private boolean clear = false;
    private GraphSQLiteHelper graphSQLiteHelper;
    private GraphicsDAO graphicsDAO;
    private PointsDAO pointsDAO;
    private PointsToGraphicDAO pointsToGraphicDAO;

    public BaseHolder(Context context) {
		super(context);
		this.setOnTouchListener(this);
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
//		invalidate();
		return true;
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
}