import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOfLifeVisualization extends JPanel {
    private JFrame frame;
    private final int width;
    private final int height;
    private final int cellSize = 1;
    private Particle[][][] grid;

    public GameOfLifeVisualization(Particle[][][] grid, int sizeX, int sizeY, int sizeZ) {
        this.grid = grid;

        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.width = sizeX;
        this.height = sizeY;

        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < width; i+=cellSize) {
            for (int j = 0; j < height; j+=cellSize) {
                if(grid[i][j][0] != null) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i, j, cellSize, cellSize);
                }
                else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(i, j, cellSize, cellSize);
                }
            }
        }
    }

    public void updateGrid(Particle[][][] newGrid) {
        this.grid = newGrid;
        repaint();
    }

}