package maze;

import javax.swing.*;
import java.awt.*;
import maze.generator.*;
import maze.visual.*;
import maze.solver.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShow);
    }

    private static void createAndShow() {

        Maze maze = new Maze(20, 20);
        maze.grid[0][0].isStart = true;
        maze.grid[maze.rows - 1][maze.cols - 1].isEnd = true;

        MazeGenerator gen = new MazeGenerator(maze);
        gen.generate();

        JFrame frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        MazePanel panel = new MazePanel(maze);

        
        JPanel ctrl = new JPanel();
        ctrl.setLayout(new BoxLayout(ctrl, BoxLayout.Y_AXIS));
        ctrl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton dfsBtn = new JButton("Run DFS");
        JButton bfsBtn = new JButton("Run BFS");
        JButton refreshBtn = new JButton("Refresh Maze");

        ctrl.add(dfsBtn);
        ctrl.add(Box.createVerticalStrut(8));
        ctrl.add(bfsBtn);
        ctrl.add(Box.createVerticalStrut(8));
        ctrl.add(refreshBtn);
        ctrl.add(Box.createVerticalStrut(20));

        JLabel speedLabel = new JLabel("Animation Speed (ms)");
        ctrl.add(speedLabel);

        JSlider speedSlider = new JSlider(5, 200, 20);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        ctrl.add(speedSlider);

        
        speedSlider.addChangeListener(e ->
                panel.setAnimationDelay(speedSlider.getValue())
        );

        frame.add(panel, BorderLayout.CENTER);
        frame.add(ctrl, BorderLayout.EAST);

     

        Runnable clearFlags = () -> {
            for(int r=0; r<maze.rows; r++){
                for(int c=0; c<maze.cols; c++){
                    maze.grid[r][c].isSolutionDFS = false;
                    maze.grid[r][c].isSolutionBFS = false;
                }
            }
            panel.lastDFS = -1;
            panel.lastBFS = -1;
            panel.repaint();
        };

        refreshBtn.addActionListener(e -> {
            clearFlags.run();
            gen.generate();
            panel.repaint();
        });

        dfsBtn.addActionListener(e -> {
            clearFlags.run();
            new Thread(() -> {
                DFSSolver dfs = new DFSSolver();
                dfs.solve(maze, 0, 0, maze.rows - 1, maze.cols - 1, panel);
            }).start();
        });

        bfsBtn.addActionListener(e -> {
            clearFlags.run();
            new Thread(() -> {
                BFSSolver bfs = new BFSSolver();
                bfs.solve(maze, 0, 0, maze.rows - 1, maze.cols - 1, panel);
            }).start();
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
