import java.util.ArrayList;

/**
 * Basic NeuralNetwork class for training and prediction.
 * 
 * @author (Danisch Khurshid) 
 * @version (1.0)
 */

public class NeuralNetwork {
    private int input_nodes;
    private double learning_rate;
    private Layer[] layers;

    public NeuralNetwork(int input_nodes, Layer[] layers) {
        this.input_nodes = input_nodes;
        this.learning_rate = 0.1;
        this.layers = layers;

        this.layers[0].initWeights(this.input_nodes);
        for (int i = 1; i < this.layers.length; i++) {
            this.layers[i].initWeights(this.layers[i-1].getNodes());
        }
    }

    public Layer[] getLayers() {
        return layers;
    }

    /**
     * Train the Neural Network. (Supervised Learning)
     */
    public void train(double[] input_array, double[] target_array) {
        if (this.input_nodes == input_array.length) {
            Matrix inputs = Matrix.fromArray(input_array);
            Matrix targets = Matrix.fromArray(target_array);
            this.predict(input_array);

            // TO-DO: Backpropogation...
            Matrix output = this.layers[this.layers.length-1].getLayer();

        } else {
            System.out.println("Die Eingabe muss den Nodes entsprechen!");
        }
    }

    /**
     * Predict the output of an input.
     */
    public ArrayList<Double> predict(double[] input_array) {
        if (this.input_nodes == input_array.length) {
            Matrix inputs = Matrix.fromArray(input_array);

            this.layers[0].feedforward(inputs);
            for (int i = 1; i < this.layers.length; i++) {
                this.layers[i].feedforward(this.layers[i-1].getLayer());
            }
    
            return Matrix.toList(this.layers[this.layers.length-1].getLayer());
        } else {
            System.out.println("Die Eingabe muss den Nodes entsprechen!");
        }
        return null;
    }
  
}
