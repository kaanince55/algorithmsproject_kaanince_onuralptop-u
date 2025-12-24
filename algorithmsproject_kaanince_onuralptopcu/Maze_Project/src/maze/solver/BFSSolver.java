package maze.solver;

import maze.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import maze.visual.MazePanel;
import javax.swing.SwingUtilities;

public class BFSSolver {

    private final int stepDelay;

    public BFSSolver() { this.stepDelay = 20; }
    public BFSSolver(int stepDelay) { this.stepDelay = stepDelay; }

    public ArrayList<int[]> solve(Maze maze, int sr, int sc, int er, int ec, MazePanel panel) {

        long start = System.currentTimeMillis();

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[maze.rows][maze.cols];
        int[][][] parent = new int[maze.rows][maze.cols][2];

        queue.add(new int[]{sr, sc});
        visited[sr][sc] = true;
        parent[sr][sc][0] = -1;
        parent[sr][sc][1] = -1;

        ArrayList<int[]> resultPath = null;

        while(!queue.isEmpty()){
            int[] cur = queue.remove();
            int r = cur[0], c = cur[1];

            
            maze.grid[r][c].isSolutionBFS = true;
            maze.grid[r][c].isSolutionDFS = false;
            SwingUtilities.invokeLater(panel::repaint);
            sleep(stepDelay);

            if(r == er && c == ec){
                resultPath = buildPath(parent, er, ec);
                for(int[] p : resultPath){
                    maze.grid[p[0]][p[1]].isSolutionBFS = true;
                }
                SwingUtilities.invokeLater(panel::repaint);
                break;
            }

            for(int[] nb : getNeighbors(maze, r, c)){
                int nr = nb[0], nc = nb[1];
                if(!visited[nr][nc]){
                    visited[nr][nc] = true;
                    parent[nr][nc][0] = r;
                    parent[nr][nc][1] = c;

                    queue.add(nb);
                }
            }
        }

        long end = System.currentTimeMillis();
        panel.lastBFS = (end - start) / 1000.0;
        SwingUtilities.invokeLater(panel::repaint);

        return resultPath;
    }

    private ArrayList<int[]> buildPath(int[][][] parent, int r, int c){
        ArrayList<int[]> path = new ArrayList<>();
        while(r != -1){
            path.add(new int[]{r, c});
            int nr = parent[r][c][0];
            int nc = parent[r][c][1];
            r = nr;
            c = nc;
        }
        return path;
    }

    private ArrayList<int[]> getNeighbors(Maze maze, int r, int c){
        ArrayList<int[]> list = new ArrayList<>();
        Cell cell = maze.grid[r][c];

        if(!cell.top) list.add(new int[]{r-1, c});
        if(!cell.bottom) list.add(new int[]{r+1, c});
        if(!cell.left) list.add(new int[]{r, c-1});
        if(!cell.right) list.add(new int[]{r, c+1});

        return list;
    }

    private void sleep(int ms){
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
