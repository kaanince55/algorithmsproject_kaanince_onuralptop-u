package maze;

public class Cell {
    public boolean top = true;
    public boolean bottom = true;
    public boolean left = true;
    public boolean right = true;

    // Solution flags for visualization
    public boolean isSolutionDFS = false;
    public boolean isSolutionBFS = false;

    // Start / End
    public boolean isStart = false;
    public boolean isEnd = false;

    public Cell() {}
}