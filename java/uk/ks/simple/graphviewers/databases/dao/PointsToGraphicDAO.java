package uk.ks.simple.graphviewers.databases.dao;

import android.content.ContentValues;
import android.content.Context;
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
}
