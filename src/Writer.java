import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.*;

public class Writer {
    private final int gridSizeX = 30;
    private final int gridSizeY = 30;
    private final int gridSizeZ = 30;
    private final int radius = 1;
    private final int maxStep = 500;
    private final Rule rule = Rule.CORAL;
    private final GameOfLife.NeighborhoodType neighborhoodType = GameOfLife.NeighborhoodType.MOORE;
    private final double lowerBound = 0.4;
    private final double upperBound = 0.6;
    private double firstAlive;


    private void writeStaticFile(int N) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("static_" + firstAlive + ".txt"));
        writer.write("N " + N + "\n");
        writer.write("RADIUS " + radius + "\n");
        writer.write("RULE " + rule.name() + "\n");
        writer.write("NTYPE " + neighborhoodType.name() + "\n");
        writer.write("MAXSTEP " + maxStep + "\n");
        writer.write("ALIVE " + (int) (firstAlive * 100) + "\n");
        writer.write("SIZEX " + gridSizeX + "\n");
        writer.write("SIZEY " + gridSizeY + "\n");
        writer.write("SIZEZ " + gridSizeZ + "\n");

        writer.close();
    }

    private void writeDynamicFile(Set<Coordinates> particlePositions) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("dynamic_" + firstAlive + ".txt"));
        for (Coordinates coordinates : particlePositions) {
            writer.write(coordinates.x + " " + coordinates.y + " " + coordinates.z + "\n");
        }
        writer.close();
    }


    // Change numbers in this method to have other inputs
    public void write(double firstAlive) {
        this.firstAlive = firstAlive;
        int domainSizeX = (int) (gridSizeX * upperBound - gridSizeX * lowerBound + 1);
        int domainSizeY = (int) (gridSizeY * upperBound - gridSizeY * lowerBound + 1);
        int domainSizeZ = (int) (gridSizeZ * upperBound - gridSizeZ * lowerBound + 1);

        int totalPositions = domainSizeX * domainSizeY * domainSizeZ;
        int desiredParticles = (int) (totalPositions * firstAlive);

        Set<Coordinates> particlePositions = new TreeSet<>();
        Random random = new Random();

        // Position initialization
        while (particlePositions.size() < desiredParticles) {
            int x = (int) (lowerBound*gridSizeX + random.nextInt((int) (upperBound*gridSizeX - lowerBound*gridSizeX + 1)));
            int y = (int) (lowerBound*gridSizeY + random.nextInt((int) (upperBound*gridSizeY - lowerBound*gridSizeY + 1)));
            int z = (int) (lowerBound*gridSizeZ + random.nextInt((int) (upperBound*gridSizeZ - lowerBound*gridSizeZ + 1)));

            particlePositions.add(new Coordinates(x, y, z));
        }
        try {
            writeDynamicFile(particlePositions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writeStaticFile(particlePositions.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}