import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameOfLife {
    private Particle[][][] grid;
    private final int maxStep;
    private final int radius;
    private final NeighborhoodType neighborhoodType;
    private final boolean is3d;
    private int currentStep;
    private final int gridSizeX;
    private final int gridSizeY;
    private final int gridSizeZ;
    private final BiFunction<Particle, List<Particle>, Boolean> customRule;

    public enum NeighborhoodType {
        VON_NEUMANN, MOORE
    }

    public GameOfLife (Set<Particle> particles, int sizeX, int sizeY, int sizeZ, int radius, int maxStep,
                       NeighborhoodType neighborhoodType, boolean is3d, BiFunction<Particle, List<Particle>, Boolean> customRule) {
        grid = new Particle[sizeX][sizeY][sizeZ];

        for (Particle particle : particles) {
            grid[particle.x][particle.y][particle.z] = particle;
        }

        this.maxStep = maxStep;
        this.radius = radius;
        this.neighborhoodType = neighborhoodType;
        this.is3d = is3d;
        this.currentStep = 0;

        this.gridSizeX = grid.length;
        this.gridSizeY = grid[0].length;
        this.gridSizeZ = grid[0][0].length;

        this.customRule = customRule;
    }

    public Particle[][][] getGrid() {
        return grid;
    }

    private boolean isCoordinateValid(int coordinate, int size) {
        return coordinate >= 0 && coordinate < size;
    }

    private List<Particle> getNeighbors(Particle particle, Particle[][][] grid, int radius, NeighborhoodType neighborhoodType) {
        List<Particle> neighbors = new ArrayList<>();

        for (int dz = -radius; dz <= radius; dz++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dx = -radius; dx <= radius; dx++) {
                    if(!(dx==0 && dy==0 && dz== 0)) {
                        if (neighborhoodType == NeighborhoodType.VON_NEUMANN && Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > radius) {
                            continue;
                        }
                        if (isCoordinateValid(particle.x + dx, grid.length) &&
                                isCoordinateValid(particle.y + dy, grid[0].length) &&
                                isCoordinateValid(particle.z + dz, grid[0][0].length)) {
                            if (grid[particle.x + dx][particle.y + dy][particle.z + dz] != null) {
                                neighbors.add(grid[particle.x + dx][particle.y + dy][particle.z + dz]);
                            }
                        }
                    }

                }
            }
        }

        return neighbors;
    }

    private Particle[][][] doAStep() {

        Particle[][][] newGrid = new Particle[gridSizeX][gridSizeY][gridSizeZ];

        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                for (int z = 0; z < gridSizeZ; z++) {
                    if (grid[x][y][z] != null) {
                        Particle particle = grid[x][y][z];
                        List<Particle> neighbors = getNeighbors(particle, grid, radius, neighborhoodType);
                        if (customRule.apply(particle, neighbors)) {
                            newGrid[x][y][z] = new Particle(x, y, z);
                        } else {
                            newGrid[x][y][z] = null;
                        }
                    }
                    else {
                        newGrid[x][y][z] = null;
                    }
                }
            }
        }
        return newGrid;
    }

    public void start(GameOfLifeVisualization visualization) {
        Timer timer = new Timer(2000, e -> {
            if (currentStep == 0) {
                visualization.updateGrid(grid);
                currentStep++;
            }
            else if (currentStep < maxStep) {
                grid = doAStep();
                visualization.updateGrid(grid);
                currentStep++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.setInitialDelay(0); // Comenzar el Timer inmediatamente
        timer.start();
    }

    public static void main(String[] args) {
        int N, gridSizeX, gridSizeY, gridSizeZ, radius, maxStep;
        boolean is3d;
        Set<Particle> particles = new TreeSet<>();

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
                    particles.add(new Particle(x, y, z));
            }
            dynamicReader.close();

            /* -------------- */

            //Change this rule
            BiFunction<Particle, List<Particle>, Boolean> customRule = (particle, neighbors) -> neighbors.size() >= 1;

            //Change this NeighborhoodType
            NeighborhoodType neighborhoodType = NeighborhoodType.MOORE;

            /* -------------- */

            GameOfLife game = new GameOfLife(particles, gridSizeX, gridSizeY, gridSizeZ, radius, maxStep, neighborhoodType, is3d, customRule);

            GameOfLifeVisualization visualization = new GameOfLifeVisualization(game.getGrid(), gridSizeX, gridSizeY, gridSizeZ);

            game.start(visualization);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
