package common;

import lombok.Data;

import java.util.Comparator;

@Data
public class Point3D implements Comparable<Point3D> {
    int x;
    int y;
    int z;
    public final static Point3D ZERO = new Point3D(0, 0, 0);
    public final static Comparator<Point3D> POINT_COMPARATOR = Comparator.comparingInt(Point3D::getZ).thenComparingInt(Point3D::getX).thenComparingInt(Point3D::getY);

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "[" + getX() + ":" + getY() + ":" + getZ() + "]";
    }

    @Override
    public int compareTo(Point3D o) {
        return POINT_COMPARATOR.compare(this, o);
    }

    public Point3D add(Point3D other) {
        return new Point3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }
}

