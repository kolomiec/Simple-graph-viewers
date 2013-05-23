package uk.ks.simple.graphviewers.utils;

import uk.ks.simple.graphviewers.beans.Point;
import uk.ks.simple.graphviewers.geometries.CoordinateSystem;

/**
 * Created by root on 5/21/13.
 */
public class RecalculateSystem {

    public static Point recalculateCoordinateFromArtificialToOriginal(Point point) {
        Point result;
        CoordinateSystem coordinateSystem = new CoordinateSystem();
        int x = coordinateSystem.getOriginPoint().getX() + (point.getX() * coordinateSystem.getLabelStep());
        int y = coordinateSystem.getOriginPoint().getY() - (point.getY() * coordinateSystem.getLabelStep());
        result = new Point(x, y);
        return result;
    }

    public static Point recalculateCoordinateFromOriginalToArtificial(Point point) {
        Point result;
        CoordinateSystem coordinateSystem = new CoordinateSystem();
        int x = (point.getX() - coordinateSystem.getOriginPoint().getX()) / coordinateSystem.getLabelStep();
        int y = (coordinateSystem.getOriginPoint().getY() - point.getY()) / coordinateSystem.getLabelStep();
        result = new Point(x, y);
        return result;
    }

    public static Point calculateShiftInArtificialCoordinateSystem(Point point) {
        Point result;
        CoordinateSystem coordinateSystem = new CoordinateSystem();
        int x = point.getX() / coordinateSystem.getLabelStep();
        int y = point.getY() / coordinateSystem.getLabelStep();
        result = new Point(x, y);
        return result;
    }
}
