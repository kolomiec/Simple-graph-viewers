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
public class GraphicsDAO {
    private SQLiteDatabase database;
    private GraphSQLiteHelper dbHelper;

    public GraphicsDAO(Context context) {
        dbHelper = new GraphSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createGraphic(int color) {
        ContentValues values = new ContentValues();
        values.put(GraphSQLiteHelper.COLOR, color);
        return database.insert(GraphSQLiteHelper.TABLE_GRAPHICS, null, values); // Graphic ID
    }

}
