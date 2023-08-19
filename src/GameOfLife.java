import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;

public class GameOfLife {
    private boolean[][][] grid;
    private final int maxStep;
    private final int radius;
    private final NeighborhoodType neighborhoodType;
    private final boolean is3d;
    private int currentStep;
    private final int gridSizeX;
    private final int gridSizeY;
    private final int gridSizeZ;
    private final BiFunction<Coordinates, List<Coordinates>, Boolean> customRule;

    public enum NeighborhoodType {
        VON_NEUMANN, MOORE
    }

    public GameOfLife (Set<Coordinates> coordinates, int sizeX, int sizeY, int sizeZ, int radius, int maxStep,
                       NeighborhoodType neighborhoodType, boolean is3d, BiFunction<Coordinates, List<Coordinates>, Boolean> customRule) {
        grid = new boolean[sizeX][sizeY][sizeZ];
        this.gridSizeX = sizeX;
        this.gridSizeY = sizeY;
        this.gridSizeZ = sizeZ;

        for (Coordinates coords : coordinates) {
            grid[coords.x][coords.y][coords.z] = true;
        }

        this.maxStep = maxStep;
        this.radius = radius;
        this.neighborhoodType = neighborhoodType;
        this.is3d = is3d;
        this.currentStep = 0;
        this.customRule = customRule;
    }

    private boolean isCoordinateValid(int coordinate, int size) {
        return coordinate >= 0 && coordinate < size;
    }

    private List<Coordinates> getNeighbors(Coordinates coordinates, boolean[][][] grid, int radius, NeighborhoodType neighborhoodType) {
        List<Coordinates> neighborsOffsetCoordinates = new ArrayList<>();

        for (int dz = -radius; dz <= radius; dz++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dx = -radius; dx <= radius; dx++) {
                    if(!(dx==0 && dy==0 && dz== 0)) {
                        if (neighborhoodType == NeighborhoodType.VON_NEUMANN && Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > radius) {
                            continue;
                        }
                        if (isCoordinateValid(coordinates.x + dx, grid.length) &&
                                isCoordinateValid(coordinates.y + dy, grid[0].length) &&
                                isCoordinateValid(coordinates.z + dz, grid[0][0].length)) {
                            if (grid[coordinates.x + dx][coordinates.y + dy][coordinates.z + dz]) {
                                neighborsOffsetCoordinates.add(new Coordinates(dx, dy, dz));
                            }
                        }
                    }

                }
            }
        }
        return neighborsOffsetCoordinates;
    }

    private boolean[][][] doAStep() {

        boolean[][][] newGrid = new boolean[gridSizeX][gridSizeY][gridSizeZ];

        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                for (int z = 0; z < gridSizeZ; z++) {
                    Coordinates coordinates = new Coordinates(x,y,z);
                    // Returns Offsets like (+1, +1, +1), not the absolute coordinates
                    List<Coordinates> neighborsOffsetCoordinates = getNeighbors(coordinates, grid, radius, neighborhoodType);
                    newGrid[x][y][z] = customRule.apply(coordinates, neighborsOffsetCoordinates);
                }
            }
        }
        return newGrid;
    }

    private void writeOutput(int step, boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", append));
            writer.write("TIEMPO " + step + "\n");

            for (int z = 0; z < gridSizeZ; z++) {
                for (int y = 0; y < gridSizeY; y++) {
                    for (int x = 0; x < gridSizeX; x++) {
                        writer.write(grid[x][y][z] ? "1" : "0");
                    }
                }
                writer.write("\n");
            }

            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        writeOutput(0, false);
        for (int i = 1; i <= maxStep; i++) {
            grid = doAStep();
            writeOutput(i, true);
        }
    }

    public static void main(String[] args) {
        int N, gridSizeX, gridSizeY, gridSizeZ, radius, maxStep;
        boolean is3d;
        Set<Coordinates> coordinates = new TreeSet<>();

        try {
            // Read static file
            BufferedReader staticReader = new BufferedReader(new FileReader("static.txt"));
            N = Integer.parseInt(staticReader.readLine());
            gridSizeX = Integer.parseInt(staticReader.readLine());
            gridSizeY = Integer.parseInt(staticReader.readLine());
            gridSizeZ = Integer.parseInt(staticReader.readLine());
            radius = Integer.parseInt(staticReader.readLine());
            maxStep = Integer.parseInt(staticReader.readLine());
            is3d = gridSizeZ > 1;

            staticReader.close();

            // Read dynamic file
            BufferedReader dynamicReader = new BufferedReader(new FileReader("dynamic.txt"));
            String line;

            while ((line = dynamicReader.readLine()) != null) {
                    String[] position = line.split(" ");
                    int x = Integer.parseInt(position[0]);
                    int y = Integer.parseInt(position[1]);
                    int z = Integer.parseInt(position[2]);
                    coordinates.add(new Coordinates(x, y, z));
            }
            dynamicReader.close();

            /* -------------- */

            //Change this rule
            BiFunction<Coordinates, List<Coordinates>, Boolean> customRule = (cords, neighbors) -> neighbors.isEmpty();

            //Change this NeighborhoodType
            NeighborhoodType neighborhoodType = NeighborhoodType.MOORE;

            /* -------------- */

            GameOfLife game = new GameOfLife(coordinates, gridSizeX, gridSizeY, gridSizeZ, radius, maxStep, neighborhoodType, is3d, customRule);
            game.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
