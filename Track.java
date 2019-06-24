
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.awt.geom.GeneralPath;

/**
 * Responsible for creating the track for the vehicles.
 * 
 * @author (Danisch Khurshid) 
 * @version (1.0)
 */
public class Track {
    private int windowWidth;
    private int windowHeight;
    private int trackWidth;
    private int smoothRate;
    private int angleIncrease;
    private int checkpointsNum;
    private ArrayList<Vector2D> outerTrack;
    private ArrayList<Vector2D> innerTrack;
    private ArrayList<Boundary> checkpoints;

    private ArrayList<Boundary> boundaries;

    public Track(int trackWidth, int smoothRate, int angleIncrease) {
        this.trackWidth = trackWidth;
        this.smoothRate = smoothRate;
        this.angleIncrease = angleIncrease;
        this.checkpointsNum = 30;

        this.outerTrack = new ArrayList<Vector2D>();
        this.innerTrack = new ArrayList<Vector2D>();
        this.checkpoints = new ArrayList<Boundary>();

        this.boundaries = new ArrayList<Boundary>();
    }

    public double map(double x, double in_min, double in_max, double out_min, double out_max) {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public void generateTrack(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        for (int i = 0; i < this.checkpointsNum; i++) {
            double a = map(i, 0, this.checkpointsNum, 0, 360);
            double xoff = this.map(Math.cos(Math.toRadians(a)), -1, 1, 0, this.smoothRate);
            double yoff = this.map(Math.sin(Math.toRadians(a)), -1, 1, 0, this.smoothRate);
            double r = this.map(Noise.noise(xoff,yoff), -1, 1, 100, this.windowWidth/4);
            double xOuter = this.windowWidth/2 + (r + this.trackWidth/2) * Math.cos(Math.toRadians(a));
            double yOuter = this.windowHeight/2 + (r + this.trackWidth/2) * Math.sin(Math.toRadians(a));
            double xInner = this.windowWidth/2 + (r - this.trackWidth/2) * Math.cos(Math.toRadians(a));
            double yInner = this.windowHeight/2 + (r - this.trackWidth/2) * Math.sin(Math.toRadians(a));
            double xCheckpoint = this.windowWidth/2 + r * Math.cos(Math.toRadians(a));
            double yCheckpoint = this.windowHeight/2 + r * Math.sin(Math.toRadians(a));
            
            Vector2D outerCoordinate = new Vector2D(xOuter, yOuter);
            Vector2D innerCoordinate = new Vector2D(xInner, yInner);
            Vector2D checkpoint = new Vector2D(xCheckpoint, yCheckpoint);
            this.checkpoints.add(new Boundary(new Vector2D(xInner, yInner), new Vector2D(xOuter, yOuter)));
            
            this.outerTrack.add(outerCoordinate);
            this.innerTrack.add(innerCoordinate);
        }
        for (int a = 0; a < this.checkpoints.size(); a++) {
            this.boundaries.add(new Boundary(this.outerTrack.get(a), this.outerTrack.get((a+1) % this.checkpoints.size())));
            this.boundaries.add(new Boundary(this.innerTrack.get(a), this.innerTrack.get((a+1) % this.checkpoints.size())));
        }
        // Draw boundary between starting and ending Point
        //this.boundaries.add(new Boundary(this.innerTrack.get(0), this.outerTrack.get(0)));
    }

    public ArrayList<Boundary> getBoundaries() {
        return this.boundaries;
    }
    public ArrayList<Vector2D> getOuterTrack() {
        return this.outerTrack;
    }
    public ArrayList<Vector2D> getInnerTrack() {
        return this.innerTrack;
    }
    public ArrayList<Boundary> getCheckpoints() {
        return this.checkpoints;
    }
    
    public Vector2D getStartPoint() {
        return this.checkpoints.get(1).midpoint();
    }
    public Vector2D getEndingPoint() {
        return this.checkpoints.get(this.checkpoints.size()-1).midpoint();
    }

    public Ellipse2D drawStartingPoint() {
        Ellipse2D stratingPoint = new Ellipse2D.Double();
        stratingPoint.setFrameFromCenter(this.checkpoints.get(1).getStart().x, this.checkpoints.get(1).getStart().y, this.checkpoints.get(1).getStart().x + 2, this.checkpoints.get(1).getStart().y + 2); 
        return stratingPoint;
    }

    public Ellipse2D drawEndingPoint() {
        Ellipse2D endingPoint = new Ellipse2D.Double();
        endingPoint.setFrameFromCenter(this.checkpoints.get(this.checkpoints.size()-1).getEnd().x, this.checkpoints.get(this.checkpoints.size()-1).getEnd().y, this.checkpoints.get(this.checkpoints.size()-1).getEnd().x + 2, this.checkpoints.get(this.checkpoints.size()-1).getEnd().y + 2); 
        return endingPoint;
    }

}
