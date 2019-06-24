import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Sensors for the vehicles.
 * 
 * @author (Danisch Khurshid) 
 * @version (1.0)
 */
public class Ray {

    private Vector2D position;
    private Vector2D direction;
    private Line2D line;

    public Ray(Vector2D position, double angle) {
        this.position = position;
        this.direction = new Vector2D();
        this.direction = Vector2D.toCartesian(15, angle);
        this.getLine();
    }

    
    
    public void setPosition(Vector2D newPosition) {
        this.position = newPosition;
        
    }
    public Line2D getLine() {
        this.line = new Line2D.Double(this.position.x,this.position.y, this.position.x-this.direction.x, this.position.y-this.direction.y);
        return this.line;
    }

    public void draw(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.draw(this.getLine());
        gg.dispose(); 
    }
    
    public Vector2D castBoundary(Boundary wall) {
        double x1 = wall.getStart().x;
        double y1 = wall.getStart().y;
        double x2 = wall.getEnd().x;
        double y2 = wall.getEnd().y;
    
        double x3 = this.position.x;
        double y3 = this.position.y;
        double x4 = this.position.x + this.direction.x;
        double y4 = this.position.y + this.direction.y;
    
        double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (den == 0) {
          return null;
        }
    
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
        if (t > 0 && t < 1 && u > 0) {
          Vector2D point = new Vector2D(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
          return point;
        } else {
          return null;
        }
    }
}
