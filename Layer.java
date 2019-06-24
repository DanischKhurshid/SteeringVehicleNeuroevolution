
/**
 * Beschreiben Sie hier die Klasse Layer.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Layer
{
    private int nodes;
    private Matrix bias;
    private Matrix weights;
    private Matrix layer;

    public Layer(int nodes) {
        this.nodes = nodes;
        this.bias = new Matrix(this.nodes,1);
        this.bias.randomize();
    }

    public void initWeights(int before_nodes) {
       this.weights = new Matrix(this.nodes, before_nodes);
       this.weights.randomize();
    }
    
    public Matrix getLayer() {
        return this.layer;
    }

    public int getNodes() {
        return this.nodes;
    }
    
    public Matrix getWeights() {
        return this.weights;
    }
    
    public void setWeights(Matrix newWeights) {
       this.weights = newWeights;
    }

    public void feedforward(Matrix beforeDense) {
        layer = Matrix.multiply(this.weights, beforeDense);
        layer.addMatrix(this.bias);
        layer.sigmoidMatrix();
    }

    public static Layer dense(int nodes) {
        return new Layer(nodes);
    }


}
