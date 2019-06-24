import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.awt.geom.GeneralPath;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;


/**
 * Class Frame creates an Window for simulating purpose.
 *
 * @author (Danisch)
 * @version (1.0)
 */
public class Panel extends JPanel {

    private Track track;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Vehicle> savedVehicles;
    private final int population;
    private int generation;
    private Dimension screenSize;

    public Panel() {
        this.setBackground(Color.white);
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.track = new Track(90, 5, 5);
        this.track.generateTrack(this.screenSize.width, this.screenSize.height);
        this.population = 1;

        this.vehicles = new ArrayList<Vehicle>();
        this.savedVehicles = new ArrayList<Vehicle>();
        this.generation = 0;

        for (int i = 0; i < this.population;  i++) {
            this.vehicles.add(new Vehicle(track.getStartPoint(), this.track));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Neroevolution Steering Vehicles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.add(new Panel());
    }

    public void nextGeneration() {
        this.calculateFitness();

        for (int i = 0; i < this.population;  i++) {
            Vehicle mutatedVehicle = GeneticAlgorithm.select(track, this.savedVehicles);
            this.vehicles.add(mutatedVehicle);
        }

        this.generation++;
        //System.out.println(this.generation);
        this.savedVehicles.removeAll(this.savedVehicles);
    }

    public void calculateFitness() {
        double sum = 0;
        for (Vehicle vehicle : this.savedVehicles) {
            vehicle.calculateFitness();
        }
        // Normalize all values

        for (Vehicle vehicle : this.savedVehicles) {
            sum += vehicle.getFitness();
        }

        for (Vehicle vehicle : this.savedVehicles) {
            vehicle.setFitness(vehicle.getFitness() / sum);
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); // important for better rendering

        // Draw the track.
        for (int j = 0; j < this.track.getBoundaries().size(); j++) {
            this.track.getBoundaries().get(j).draw(g2D);
        }

        // Draw start and end point into the track.
        // g2D.setPaint(Color.BLACK);
        // g2D.fill(this.track.drawStartingPoint());
        // g2D.fill(this.track.drawEndingPoint());

        // Draw Checkpoints
        // for (int i = 0; i < track.getOuterTrack().size(); i++) {
           // Line2D point = new Line2D.Double(track.getCheckpoints().get(i).getStart().x, track.getCheckpoints().get(i).getStart().y, track.getCheckpoints().get(i).getEnd().x, track.getCheckpoints().get(i).getEnd().y);
            // g2D.draw(point);
        // }


        for  (int n = 0; n < 10; n++) {
            for (int i = 0; i < this.vehicles.size(); i++) {

                    this.vehicles.get(i).detect(g2D);
                    this.vehicles.get(i).check(track.getCheckpoints());
                    this.vehicles.get(i).update();
            }

            // Delete crashed vehicles
            for (int i = 0; i < this.vehicles.size(); i++) {
                if (this.vehicles.get(i).getStatusDead() == true || this.vehicles.get(i).getStatusFinished() == true) {
                    this.savedVehicles.add(this.vehicles.get(i));
                    this.vehicles.remove(i);
                }
            }

            if (this.vehicles.size() == 0) {
                this.nextGeneration();
            }
          }

          for (int i = 0; i < this.vehicles.size(); i++) {
                    // Draw Rays
                    // for (int j = 0; j < this.vehicles.get(i).getRays().size(); j++) {
                        // this.vehicles.get(i).getRays().get(j).draw(g2D);
                    // }
                    this.vehicles.get(i).draw(g2D);
                    // for (int u = 0; u < this.vehicles.get(i).index; u++) {
                        // if (this.vehicles.get(i).goal != null) {
                        // this.vehicles.get(i).goal.draw(g2D);
                        // }
                    // }
            }

        repaint();
    }
}
