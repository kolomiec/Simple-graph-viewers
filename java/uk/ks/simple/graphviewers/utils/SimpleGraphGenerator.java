package uk.ks.simple.graphviewers.utils;

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
}
