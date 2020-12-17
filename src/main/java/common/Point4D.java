package common;

import lombok.Data;

import java.util.Comparator;

@Data
public class Point4D implements Comparable<Point4D> {
    int x;
    int y;
    int z;
    int w;
    public final static Point4D ZERO = new Point4D(0, 0, 0, 0);
    public final static Comparator<Point4D> POINT_COMPARATOR = Comparator.comparingInt(Point4D::getZ).thenComparingInt(Point4D::getX).thenComparingInt(Point4D::getY).thenComparingInt(Point4D::getW);

    public Point4D(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public int compareTo(Point4D o) {
        return POINT_COMPARATOR.compare(this, o);
    }
}

