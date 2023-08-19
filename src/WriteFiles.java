import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WriteFiles {

    private static final int N = 3; // Total number of particles
    private static final int gridSizeX = 100;
    private static final int gridSizeY = 100;
    private static final int gridSizeZ = 0;
    private static final int radius = 1;
    private static final int maxStep = 10;

    public static void writeStaticFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("static.txt"));
        writer.write(N + "\n");
        writer.write(gridSizeX+1 + "\n");
        writer.write(gridSizeY+1 + "\n");
        writer.write(gridSizeZ+1 + "\n");
        writer.write(radius + "\n");
        writer.write(maxStep + "\n");
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
            positions[i][0] = random.nextInt(gridSizeX+1); // X position
            positions[i][1] = random.nextInt(gridSizeY+1); // Y position
            positions[i][2] = random.nextInt(gridSizeZ+1);; // Z position
        }
        try {
            writeDynamicFile(positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}