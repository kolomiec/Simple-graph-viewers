package uk.ks.simple.graphviewers.holders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import uk.ks.simple.graphviewers.geometries.CoordinateSystem;

public class BaseHolder extends View  implements View.OnTouchListener {

	private Paint paint;
	private CoordinateSystem coordinateSystem;

	{
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}

	public BaseHolder(Context context) {
		super(context);
		this.setOnTouchListener(this);
		coordinateSystem = new CoordinateSystem();
	}

	@Override
	public void onDraw(Canvas canvas) {
		coordinateSystem.draw(canvas);
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