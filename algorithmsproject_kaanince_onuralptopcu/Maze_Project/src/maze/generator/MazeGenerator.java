package maze.generator;

import maze.*;
import datastructures.Stack;
import java.util.ArrayList;

public class MazeGenerator {

    Maze maze;
    boolean[][] visited;

    public MazeGenerator(Maze maze){
        this.maze = maze;
        this.visited = new boolean[maze.rows][maze.cols];
    }

    public void generate(){

        Stack<int[]> stack = new Stack<>();
        int r = 0, c = 0;

        visited[r][c] = true;
        stack.push(new int[]{r, c});

        while(!stack.isEmpty()){
            int[] current = stack.pop();
            r = current[0];
            c = current[1];

            ArrayList<int[]> neighbors = getUnvisitedNeighbors(r, c);

            if(!neighbors.isEmpty()){
                stack.push(current);

                int[] next = neighbors.get((int)(Math.random() * neighbors.size()));
                int nr = next[0];
                int nc = next[1];

                removeWall(r, c, nr, nc);

                visited[nr][nc] = true;
                stack.push(new int[]{nr, nc});
            }
        }
    }

    private ArrayList<int[]> getUnvisitedNeighbors(int r, int c){
        ArrayList<int[]> list = new ArrayList<>();

        if(r > 0 && !visited[r-1][c]) list.add(new int[]{r-1, c});
        if(r < maze.rows-1 && !visited[r+1][c]) list.add(new int[]{r+1, c});
        if(c > 0 && !visited[r][c-1]) list.add(new int[]{r, c-1});
        if(c < maze.cols-1 && !visited[r][c+1]) list.add(new int[]{r, c+1});

        return list;
    }

    private void removeWall(int r, int c, int nr, int nc){
        if(nr == r - 1){
            maze.grid[r][c].top = false;
            maze.grid[nr][nc].bottom = false;
        }
        else if(nr == r + 1){
            maze.grid[r][c].bottom = false;
            maze.grid[nr][nc].top = false;
        }
        else if(nc == c - 1){
            maze.grid[r][c].left = false;
            maze.grid[nr][nc].right = false;
        }
        else if(nc == c + 1){
            maze.grid[r][c].right = false;
            maze.grid[nr][nc].left = false;
        }
    }
}