package uk.ks.simple.graphviewers.holders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.geometries.CoordinateSystem;
import uk.ks.simple.graphviewers.geometries.Graph;
import uk.ks.simple.graphviewers.geometries.Pair;
import uk.ks.simple.graphviewers.utils.SimpleGraphGenerator;

public class BaseHolder extends View  implements View.OnTouchListener {

	private Paint paint;
	private CoordinateSystem coordinateSystem;
    private List<Graph> graphList;
    private SimpleGraphGenerator simpleGraphGenerator;

    {
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}

	public BaseHolder(Context context) {
		super(context);
		this.setOnTouchListener(this);
		coordinateSystem = new CoordinateSystem();
        simpleGraphGenerator = new SimpleGraphGenerator();
        graphList = simpleGraphGenerator.generate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		coordinateSystem.draw(canvas);
        drawGraphs(canvas);
//        Pair pair = new Pair(Color.RED, new Point(6, 3));
//        pair.draw(canvas);
	}

    private void drawGraphs(Canvas canvas) {
        for(Graph graph: graphList) {
            graph.draw(canvas);
        }
    }

    @Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		invalidate();
		return true;
	}

	private void refresh(Canvas canvas, Paint p) {
		canvas.drawColor(Color.BLACK);
	}
}