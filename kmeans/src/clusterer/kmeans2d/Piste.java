package clusterer.kmeans2d;

/**
 *
 * @author timi
 */
public class Piste {
    public double X;
    public double Y;
    public int Group;

    /**
     * Konstruktori
     *
     * @param x
     * @param y
     */
    public Piste(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    public Piste() {
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "(" + this.X + ", " + this.Y + ")";
    }
}
