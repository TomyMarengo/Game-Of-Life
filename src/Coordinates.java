import java.text.DecimalFormat;
import java.util.Objects;

public class Coordinates implements Comparable<Coordinates> {
    int x;
    int y;
    int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        return "{ " + x + ", " + y + ", " + z + " }";
    }

    @Override
    public int compareTo(Coordinates otherCoordinates) {
        int xComparison = Integer.compare(this.x, otherCoordinates.x);
        if (xComparison != 0) {
            return xComparison;
        }
        int yComparison = Integer.compare(this.y, otherCoordinates.y);
        if (yComparison != 0) {
            return yComparison;
        }
        return Integer.compare(this.z, otherCoordinates.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates coordinates = (Coordinates) obj;
        return Objects.equals(x, coordinates.x) && Objects.equals(y, coordinates.y) && Objects.equals(z, coordinates.z);
    }
}
