package uk.ks.simple.graphviewers.utils;

import java.util.Random;

import uk.ks.simple.graphviewers.beans.Point;

/**
 * Created by root on 5/17/13.
 */
public class RandomObjectGenerator {

	public static Point getNewRandomPoint(int maxX, int maxY){
		return new Point(getRandomInt(maxX), getRandomInt(maxY));
	}

	public static int getRandomInt(int r) {
		return new Random().nextInt(r);
	}
}
