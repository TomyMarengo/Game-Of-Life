import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WriteFiles {

    private static final int N = 100; // Total number of particles
    private static final int gridSizeX = 100;
    private static final int gridSizeY = 100;
    private static final int gridSizeZ = 1;
    private static final int radius = 1;
    private static final int maxStep = 180;
    private static final double lowerBound = 0.45;
    private static final double upperBound = 0.55;

    public static void writeStaticFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("static.txt"));
        writer.write("N " + N + "\n");
        writer.write("RADIUS " + radius + "\n");
        writer.write("MAXSTEP " + maxStep + "\n");
        writer.write("SIZEX " + gridSizeX + "\n");
        writer.write("SIZEY " + gridSizeY + "\n");
        writer.write("SIZEZ " + gridSizeZ + "\n");

        writer.close();
    }

    public static void writeDynamicFile(int[][] positions) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("dynamic.txt"));
        for (int[] position : positions) {
            writer.write(position[0] + " " + position[1] + " " + position[2] + "\n");
        }
        writer.close();
    }


    // Change numbers in this method to have other inputs
    public static void main(String[] args) {
        try {
            writeStaticFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] positions = new int[N][3];
        Random random = new Random();

        // Position initialization
        for (int i = 0; i < N; i++) {
            positions[i][0] = (int) (gridSizeX * (lowerBound + random.nextDouble() * (upperBound - lowerBound))); // X position
            positions[i][1] = (int) (gridSizeY * (lowerBound + random.nextDouble() * (upperBound - lowerBound))); // Y position
            positions[i][2] = (int) (gridSizeZ * (lowerBound + random.nextDouble() * (upperBound - lowerBound))); // Z position
        }
        try {
            writeDynamicFile(positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}