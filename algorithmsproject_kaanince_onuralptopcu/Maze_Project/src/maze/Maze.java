package maze;

public class Maze {
    public int rows, cols;
    public Cell[][] grid;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Cell[rows][cols];

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
    }
}