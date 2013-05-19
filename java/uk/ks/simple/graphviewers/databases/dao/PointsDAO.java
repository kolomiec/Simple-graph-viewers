package uk.ks.simple.graphviewers.databases.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.databases.GraphSQLiteHelper;

/**
 * Created by root on 5/19/13.
 */
public class PointsDAO {

    private SQLiteDatabase database;
    private GraphSQLiteHelper dbHelper;

    public PointsDAO(Context context) {
        dbHelper = new GraphSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createPoint(Point point) {
        ContentValues values = new ContentValues();
        values.put(GraphSQLiteHelper.X_COORDINATE, point.getX());
        values.put(GraphSQLiteHelper.Y_COORDINATE, point.getY());
        return database.insert(GraphSQLiteHelper.TABLE_POINTS, null, values); // Point ID
    }
}
