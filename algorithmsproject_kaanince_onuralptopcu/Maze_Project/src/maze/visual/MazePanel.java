package maze.visual;

import javax.swing.*;
import java.awt.*;
import maze.*;

public class MazePanel extends JPanel {

    public double lastDFS = -1;
    public double lastBFS = -1;

    private final Maze maze;
    private final int cellSize = 28;
    private final int infoArea = 50;

    
    private int animationDelay = 20;

    // Colors (modern/material-like)
    private final Color wallColor = new Color(44, 62, 80);
    private final Color floorColor = new Color(236, 240, 241);
    private final Color startColor = new Color(39, 174, 96);
    private final Color endColor = new Color(231, 76, 60);
    private final Color dfsColor = new Color(142, 68, 173, 200);
    private final Color bfsColor = new Color(231, 76, 60, 200);
    private final Color pathColor = new Color(241, 196, 15, 220);

    public MazePanel(Maze maze){
        this.maze = maze;
        setPreferredSize(new Dimension(
                maze.cols * cellSize,
                maze.rows * cellSize + infoArea
        ));
        setBackground(new Color(250, 250, 250));
    }

    
    public void setAnimationDelay(int delay){
        this.animationDelay = delay;
    }

    
    public void refresh(){
        repaint();
        try {
            Thread.sleep(animationDelay);
        } catch (InterruptedException ignored) {}
    }

    @Override
    protected void paintComponent(Graphics gg){
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);

        for(int r=0; r<maze.rows; r++){
            for(int c=0; c<maze.cols; c++){
                Cell cell = maze.grid[r][c];

                int x = c * cellSize;
                int y = r * cellSize;

                g.setColor(floorColor);
                g.fillRoundRect(x+1, y+1, cellSize-2, cellSize-2, 8, 8);

                if(cell.isSolutionDFS){
                    g.setColor(dfsColor);
                    g.fillRoundRect(x+3, y+3, cellSize-6, cellSize-6, 6, 6);
                }

                if(cell.isSolutionBFS){
                    g.setColor(bfsColor);
                    g.fillOval(x+6, y+6, cellSize-12, cellSize-12);
                }

                if(cell.isStart){
                    g.setColor(startColor);
                    g.fillRoundRect(x+4, y+4, cellSize-8, cellSize-8, 6, 6);
                } else if(cell.isEnd){
                    g.setColor(endColor);
                    g.fillRoundRect(x+4, y+4, cellSize-8, cellSize-8, 6, 6);
                }

                g.setColor(wallColor);
                g.setStroke(new BasicStroke(3f));
                if(cell.top) g.drawLine(x, y, x+cellSize, y);
                if(cell.bottom) g.drawLine(x, y+cellSize, x+cellSize, y+cellSize);
                if(cell.left) g.drawLine(x, y, x, y+cellSize);
                if(cell.right) g.drawLine(x+cellSize, y, x+cellSize, y+cellSize);
            }
        }

        int infoY = maze.rows * cellSize + 20;
        g.setColor(new Color(50, 50, 50));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        String dfsText = (lastDFS >= 0) ? String.format("DFS: %.3f s", lastDFS) : "DFS: -";
        String bfsText = (lastBFS >= 0) ? String.format("BFS: %.3f s", lastBFS) : "BFS: -";

        g.drawString(dfsText, 12, infoY);
        g.drawString(bfsText, 180, infoY);
    }
}
