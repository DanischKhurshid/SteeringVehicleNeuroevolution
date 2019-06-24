
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Matrix class with basic math elements.
 * 
 * @author (Danisch Khurshid) 
 * @version (1.0)
 */
public class Matrix {

    private int rows;
    private int cols;
    private double[][] data;

    public Matrix(int rows, int cols) { 
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    /**
     * Print this matrix.
     */
    public void printMatrix() {
        System.out.println("\n");
        for (int i = 0; i < data.length; i++) {
            System.out.println(Arrays.toString(data[i]));
        }
    }

    /**
     * Get the rows of this matrix.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Get the colums of this matrix.
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Create a matrix object from an array.
     */
    public static Matrix fromArray(double[] arr) {
        Matrix convertedMatrix = new Matrix(arr.length, 1);
        for (int i = 0; i < convertedMatrix.data.length; i++) {
            for (int j = 0; j < convertedMatrix.data[i].length; j++) {
                convertedMatrix.data[i][j] = arr[i];
            }
        }
        return convertedMatrix;
    }
    
    /**
     * Create a list from any matrix object.
     */
    public static ArrayList<Double> toList(Matrix n) {
        ArrayList<Double> lis = new ArrayList<>();
        for (int i = 0; i < n.data.length; i++) {
            for (int j = 0; j < n.data[i].length; j++) {
                lis.add(n.data[i][j]);
            }
        }
        return lis;
    }

    /**
     * Randomize every value in this matrix.
     */
    public void randomize() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (((double)Math.random() * (1 - (-1)))-1);
            }
        }
    }

    /**
     * Basic sigmoid function.
     */
    public double sigmoid(double x) {
        return 1/( 1 + Math.pow(Math.E,(-1*x)));
    }

    /**
     * Execute sigmoid function to every value in this matrix.
     */
    public void sigmoidMatrix() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = sigmoid(data[i][j]);
            }
        }
    }

    /**
     * Execute derivative sigmoid function to every value in this matrix.
     */
    public void desigmoidMatrix() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = sigmoid(data[i][j]) * (1 - sigmoid(data[i][j]));
            }
        }
    }

    /**
     * Adding a number to this matrix.
     */
    public Matrix addValue(double n) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] += n;
            }
        }
        return this;
    }

    /**
     * Multiply a number to this matrix.
     */
    public Matrix multiplyValue(double n) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] *= n;
            }
        }
        return this;
    }

    /**
     * Adding a other matrix to this matrix.
     */
    public void addMatrix(Matrix n) {
        if (this.rows == n.rows && this.cols == n.cols) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    this.data[i][j] += n.data[i][j];
                }
            }
        }
    }

    /**
     * Multiply a other matrix to this matrix.
     */
    public void multiplyMatrix(Matrix n) {
        if (this.rows == n.rows && this.cols == n.cols) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    this.data[i][j] *= n.data[i][j];
                }
            }
        }
    }

    /**
     * Copy any matrix.
     */
    public static Matrix copy(Matrix n) {
        Matrix copiedMatrix = new Matrix(n.rows,n.cols);
        for (int i = 0; i < copiedMatrix.data.length; i++) {
            for (int j = 0; j < copiedMatrix.data[i].length; j++) {
                copiedMatrix.data[i][j] = n.data[i][j];
            }
        }
        return copiedMatrix;
    }

    /**
     * Transpose any matrix.
     */
    public static Matrix transpose(Matrix n) {
        Matrix transposedMatrix = new Matrix(n.cols,n.rows);
        for (int i = 0; i < transposedMatrix.data.length; i++) {
            for (int j = 0; j < transposedMatrix.data[i].length; j++) {
                transposedMatrix.data[i][j] = n.data[j][i];
            }
        }
        return transposedMatrix;
    }

    /**
     * Multiply two matrices (Dot product).
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix multipliedMatrix = new Matrix(a.rows,b.cols);
        if (a.cols == b.rows) {
            for (int i = 0; i < multipliedMatrix.data.length; i++) {
                for (int j = 0; j < multipliedMatrix.data[i].length; j++) {
                    for (int k = 0; k < a.data[i].length; k++) {
                        multipliedMatrix.data[i][j] += a.data[i][k] * b.data[k][j];
                    }
                }
            }
        }
        return multipliedMatrix;
    }

    /**
     * Subtract two matrices.
     */
    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix subtractMatrix = new Matrix(a.rows,b.cols);
        if (a.rows == b.rows && b.rows == b.cols) {
            for (int i = 0; i < subtractMatrix.data.length; i++) {
                for (int j = 0; j < subtractMatrix.data[i].length; j++) {
                    subtractMatrix.data[i][j] = a.data[i][j] - b.data[i][j];
                }
            }
        }
        return subtractMatrix;
    }

}
