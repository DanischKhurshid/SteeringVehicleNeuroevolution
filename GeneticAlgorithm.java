import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse GenticAlgorithm.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public abstract class GeneticAlgorithm {

   // Evolutionary algorithm -> Genetic algorithm

    /**
     * Select a fitness.
     */
    public static Vehicle select(Track track, ArrayList<Vehicle> item) {
        int index = 0;
        double r = (double)Math.random();
        while (r > 0) {
            r -= item.get(index).getFitness();
            index++;
        }
        index--;
        // todo: implement copy or cross over
        Vehicle selectedVehicle = item.get(index);
        Vehicle child = new Vehicle(track.getStartPoint(), track, GeneticAlgorithm.mutate(selectedVehicle.getBrain(), 5));
        return child;
    }


    /**
     * Copy any neural network.
     */
    public static NeuralNetwork copy(NeuralNetwork model) {
        return model;
    }
    
    /**
     * Crossing over two neural networks by swipping the weights between eachother.
     */
    public static NeuralNetwork[] crossover(NeuralNetwork a, NeuralNetwork b) {
        // Random layer...
        if (a.getLayers().length == b.getLayers().length) {
            int randomLayer = (int)(Math.random() * a.getLayers().length); 
            System.out.println(randomLayer);
            Matrix aWeights = a.getLayers()[randomLayer].getWeights();
            Matrix bWeights = b.getLayers()[randomLayer].getWeights();
            a.getLayers()[randomLayer].setWeights(bWeights);
            b.getLayers()[randomLayer].setWeights(aWeights);
        }
        return new NeuralNetwork[] {a,b};
    }

    /**
     * Mutate any neural network based on a rate.
     */
    public static NeuralNetwork mutate(NeuralNetwork model, double rate) {
        for (int i = 0; i < model.getLayers().length; i++) {
            if ((float)Math.random() < rate) {
                double mutaion_rate = (double)(Math.random() * (0.1 - (-0.1)) + (-0.1));
                model.getLayers()[i].setWeights(model.getLayers()[i].getWeights().addValue(mutaion_rate));
            }
        }
        return model;
    }
}
