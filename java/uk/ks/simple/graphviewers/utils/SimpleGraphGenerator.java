package uk.ks.simple.graphviewers.utils;

import android.database.Cursor;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.geometries.Graph;
import uk.ks.simple.graphviewers.geometries.Pair;

import static java.util.Collections.*;

/**
 * Created by root on 5/20/13.
 */
public class SimpleGraphGenerator {

    public List<Graph> generate() {
        List<Graph> result = new ArrayList<Graph>();
        int graphicCount = RandomObjectGenerator.getRandomInt(9)+2;
        int [] graphColors = {Color.BLUE, Color.RED, Color.BLACK, Color.CYAN,
                    Color.DKGRAY, Color.GREEN, Color.MAGENTA, Color.LTGRAY, Color.YELLOW, Color.BLUE};
        Graph currentGraph;
        int graphColor;
        for(int i = 0; i < graphicCount; i++){
            graphColor = graphColors[i];
//            graphColor = Color.argb(255, RandomObjectGenerator.getRandomInt(256), RandomObjectGenerator.getRandomInt(256), RandomObjectGenerator.getRandomInt(256));
            currentGraph = new Graph(graphColor, generatePairs(graphColor));
            result.add(currentGraph);
        }
        return result;
    }

    private List<Pair> generatePairs(int color) {
        List<Pair> result = new ArrayList<Pair>();
        int pairCount = RandomObjectGenerator.getRandomInt(9)+2;
        int i = 0;
        Pair currentPair;
        while(i < pairCount) {
            currentPair = new Pair(color, new Point(RandomObjectGenerator.getRandomInt(9)+2, RandomObjectGenerator.getRandomInt(9)+2));
            if(!result.contains(currentPair)){
                result.add(currentPair);
                i++;
            }
        }
        Collections.sort(result);
        return result;
    }

    public List<Graph> generateFromCursor(Cursor cursor) {
        List<Graph> result = new ArrayList<Graph>();
        List<Pair> graphPairs = new ArrayList<Pair>();
        if (cursor.moveToFirst()){
            int previousGraphId = cursor.getInt(cursor.getColumnIndex("graphicId"));
            int previousColor = cursor.getInt(cursor.getColumnIndex("color"));
            do{
                int graphicId = cursor.getInt(cursor.getColumnIndex("graphicId"));
                int color = cursor.getInt(cursor.getColumnIndex("color"));
                int x = cursor.getInt(cursor.getColumnIndex("x"));
                int y = cursor.getInt(cursor.getColumnIndex("y"));
                if(previousGraphId == graphicId) {
                    graphPairs.add(new Pair(color,new Point(x, y)));
                } else {
                    result.add(new Graph(previousColor, graphPairs));
                    graphPairs = new ArrayList<Pair>();
                    graphPairs.add(new Pair(color,new Point(x, y)));
                    previousGraphId = graphicId;
                    previousColor = color;
                }

            }while(cursor.moveToNext());
            result.add(new Graph(previousColor, graphPairs));
        }
        cursor.close();
        return result;
    }
}
