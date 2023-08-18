import java.text.DecimalFormat;
import java.util.Objects;

public class Particle implements Comparable<Particle> {
    int x;
    int y;
    int z;

    public Particle(int x, int y, int z) {
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
    public int compareTo(Particle otherParticle) {
        int xComparison = Integer.compare(this.x, otherParticle.x);
        if (xComparison != 0) {
            return xComparison;
        }
        int yComparison = Integer.compare(this.y, otherParticle.y);
        if (yComparison != 0) {
            return yComparison;
        }
        return Integer.compare(this.z, otherParticle.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Particle particle = (Particle) obj;
        return Objects.equals(x, particle.x) && Objects.equals(y, particle.y) && Objects.equals(z, particle.z);
    }
}
