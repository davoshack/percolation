/******************************************************************************
 *  Name:              Juan D Hernandez G
 *  Coursera User ID:  123456
 *  Last modified:     April 28, 2021
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] grid;
    private int side;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    // 0 -> blocked, 1 -> open
    public Percolation(int n) {
        side = n;
        grid = new int[(side * side) + 2];

        // initialize grid
        for (int i = 0; i < (side * side); i++) {
            grid[i] = 0;
        }
        // crate virtual top site
        grid[side * side] = 1;

        // create virtual button site
        grid[(side * side) + 1] = 1;

        // create object WQUF
        uf = new WeightedQuickUnionUF((side * side) + 2);
    }

    private void checkGridLimits(int row, int col) {
        if (row <= 0 || col <= 0 || row > side || col > side) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int getIndex(int row, int col) {
        int index = (side * (row - 1)) + col - 1;
        return index;
    }

    private void union(int p, int q) {
        if (uf.find(p) != uf.find(q)) uf.union(p, q);
    }

    private void unionTopRow(int row, int col, int index) {
        if (row != 1 && isOpen(row - 1, col)) {
            union(getIndex(row - 1, col), index);
        }
        else if (row == 1) {
            union(index, side * side);
        }
    }

    private void unionButtRow(int row, int col, int index) {
        if (row != side && isOpen(row + 1, col)) {
            union(getIndex(row + 1, col), index);
        }
        else if (row == side) {
            union(index, (side * side) + 1);
        }
    }

    private void unionLeftBorder(int row, int col, int index) {
        if (col != 1 && isOpen(row, col - 1)) {
            union(getIndex(row, col - 1), index);
        }
    }

    private void unionRightBorder(int row, int col, int index) {
        if (col != side && isOpen(row, col + 1)) {
            union(getIndex(row, col + 1), index);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkGridLimits(row, col);
        if (isOpen(row, col)) return;
        int index = getIndex(row, col);
        grid[index] = 1;

        unionTopRow(row, col, index);
        unionButtRow(row, col, index);
        unionLeftBorder(row, col, index);
        unionRightBorder(row, col, index);

    }

    //is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkGridLimits(row, col);
        return grid[getIndex(row, col)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkGridLimits(row, col);
        if (uf.find(side * side) == uf.find(getIndex(row, col))) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < (side * side) + 1; i++) {
            if (grid[i] == 1) ++count;
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(side * side) == uf.find((side * side) + 1)) return true;
        return false;
    }

    // test client
    public static void main(String[] args) {
    }

}
