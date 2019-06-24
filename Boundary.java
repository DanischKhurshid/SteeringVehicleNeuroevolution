import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Create walls for the track.
 * 
 * @author (Danisch Khurshid) 
 * @version (1.0)
 */

public class Boundary {
 
    private Vector2D a;
    private Vector2D b;
    private Line2D line;

    public Boundary(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
        this.line = new Line2D.Double(this.a.x, this.a.y, this.b.x, this.b.y);
    }

    public Vector2D midpoint() {
        return new Vector2D((this.a.x + this.b.x) * 0.5, (this.a.y + this.b.y) * 0.5);
    }

    public Line2D getLine() {
        return this.line;
    }
    
    public Vector2D getStart() {
        return a;
    }

    public Vector2D getEnd() {
        return b;
    }

    public void draw(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.draw(this.line);
        gg.dispose(); 
    }
}
