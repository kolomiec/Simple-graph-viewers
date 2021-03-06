package uk.ks.simple.graphviewers.databases.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import uk.ks.simple.graphviewers.databases.GraphSQLiteHelper;

/**
 * Created by root on 5/19/13.
 */
public class PointsToGraphicDAO {

    private SQLiteDatabase database;
    private GraphSQLiteHelper dbHelper;

    public PointsToGraphicDAO(Context context) {
        dbHelper = new GraphSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPointsToGraphic(long graphicId, long pointId) {
        ContentValues values = new ContentValues();
        values.put(GraphSQLiteHelper.GRAPHIC_ID, graphicId);
        values.put(GraphSQLiteHelper.POINT_ID, pointId);
        database.insert(GraphSQLiteHelper.TABLE_POINTS_TO_GRAPHIC, null, values); //
    }

    public Cursor getAllGraphWithPoint(){
        Cursor result;
        String query =
                    "select graphics.id as graphicId, graphics.color as color, points.xCoordinate as x, points.yCoordinate as y "
                    + "from point_to_graphics "
                    + "inner join graphics on point_to_graphics.graphicId = graphics.id "
                    + "inner join points on point_to_graphics.pointId = points.id "
                    + "order by graphics.id";
        result = database.rawQuery(query, null);
        return result;
    }
}
