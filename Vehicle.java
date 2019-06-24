import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
/**
 * Beschreiben Sie hier die Klasse Vehicle.
 * 
 * @author (Danisch Khurshid) 
 * @version (1.0)
 */
public class Vehicle {

    private NeuralNetwork brain;

    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private ArrayList<Ray> rays;
    private Track tracknr;
        
    public Boundary goal;
    private boolean dead;
    private boolean finished;

    private int sight;
    public int index;
    private double fitness;

    public Vehicle(Vector2D position, Track tracknr) {
        this.position = new Vector2D(position);
        this.velocity = new Vector2D();
        this.acceleration = new Vector2D();
        this.tracknr = tracknr;
        
        this.dead = false;
        this.finished = false;
        this.sight = 100;

        this.index = 1;
        this.fitness = 0;

       this.rays = new ArrayList<Ray>();
       for (int a = 0; a < 360; a += 45) {  
            this.rays.add(new Ray(this.position, Math.toRadians(a)));
       }

        this.brain = new NeuralNetwork(this.rays.size(), new Layer[] {
            Layer.dense(this.rays.size()),
            Layer.dense(1)
        });
    }

    public Vehicle(Vector2D position, Track tracknr, NeuralNetwork brain) {
        this.position = new Vector2D(position);
        this.velocity = new Vector2D();
        this.acceleration = new Vector2D();
        this.tracknr = tracknr;

        this.dead = false;
        this.finished = false;
        this.sight = 100;

        this.index = 1;
        this.fitness = 0;

        this.rays = new ArrayList<Ray>();
       for (int a = 0; a < 360; a += 45) {  
            this.rays.add(new Ray(this.position, Math.toRadians(a)));
       }

        this.brain = brain;
    }

    public void calculateFitness() {
        this.fitness = Math.pow(2,this.index);
        // if (this.finished == true) {
            // this.fitness = this.index;
        // } else {
            // this.fitness = 1 / this.position.distance(tracknr.getEndingPoint());
        // }
    }

    public NeuralNetwork getBrain() {
        return this.brain;
    }

    public double getFitness() {
        //System.out.println(this.index);
        return this.fitness;
    }
    public void setFitness(double newFitness) {
        this.fitness = newFitness;
    }

    public double pldistance(Vector2D p1, Vector2D p2, double x, double y) {
        double num = Math.abs((p2.y-p1.y) * x - (p2.x-p1.x) * y + p2.x * p1.y - p2.y * p1.x);
        double dist = p1.distance(p2);
        return num / dist;
    }

    public void check(ArrayList<Boundary> checkpoints) {
       if (this.dead != true || this.finished != true) {
           this.goal = checkpoints.get(this.index);

           double distance = this.pldistance(this.goal.getStart(), this.goal.getEnd(), this.position.x, this.position.y);
            if (distance < 5) {
                this.index++;
                if (this.index == checkpoints.size()-1) {
                    this.finished = true;
                }
            }
        }
    }

    public void applyForce(Vector2D force) {
        this.acceleration.add(force);
    }

    public void update() {
       if (this.dead != true || this.finished != true) {
           this.position.add(this.velocity); 
           this.velocity.add(this.acceleration); 
           this.acceleration.set(0,0);
    
           // Set new Position of Rays
           for (int i = 0; i < this.rays.size(); i++) {
               this.rays.get(i).setPosition(this.position);
                this.rays.get(i).rotate(this.velocity.getAngle());
            }
        }
    }

     public double map(double x, double in_min, double in_max, double out_min, double out_max) {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public void detect(Graphics2D g) {
        double[] inputs = new double[this.rays.size()];
        for (int i = 0; i < this.rays.size(); i++) {
            Vector2D closest = null;
            double record = this.sight;
            for (Boundary boundary : this.tracknr.getBoundaries()) {
                Vector2D point = this.rays.get(i).castBoundary(boundary);
                if (point != null) {
                    double distance = this.position.distance(point);
                    if (distance < record && distance < this.sight) {
                        record = distance;
                        closest = point;
                    }
                }
            }
            inputs[i] = map(record, 0, this.sight, 1, 0);

            // Draw the sensor in the sight
            // if (closest != null) {
                // Graphics2D gg = (Graphics2D) g.create();
                // gg.draw(new Line2D.Double(this.position.x,this.position.y,closest.x,closest.y));
            // }
            if (record < 5) {
                this.dead = true;
            }
        }
        ArrayList<Double> outputs = this.brain.predict(inputs);
        double angle = map(outputs.get(0), 0, 1, 0, Math.toRadians(360));
        Vector2D steering = Vector2D.toCartesian(1, angle);
        steering.subtract(this.velocity);
        this.applyForce(steering);
    }

    public ArrayList<Ray> getRays() {
        return this.rays;
    }  

    public boolean getStatusDead() {
        return this.dead;
    }  
    public boolean getStatusFinished() {
        return this.finished;
    }  

    public void draw(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.translate(this.position.x,this.position.y);
        gg.rotate(this.velocity.getAngle());
        Rectangle2D body = new Rectangle2D.Double();
        body.setFrameFromCenter(0,0,5,3);
        gg.draw(body);
        gg.dispose();        
    }
}
