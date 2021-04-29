/******************************************************************************
 *  Name:              Juan D Hernandez G
 *  Coursera User ID:  123456
 *  Last modified:     April 28, 2021
 *****************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fraccionOpenSites;

    // perform independent trials on and n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) throw new IllegalArgumentException();
        fraccionOpenSites = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            fraccionOpenSites[i] = (double) p.numberOfOpenSites() / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraccionOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraccionOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(fraccionOpenSites.length));
    }

    // high endpoint of 95% confifence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(fraccionOpenSites.length));
    }

    // test client
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("Std dev = " + stats.stddev());
        StdOut.println("95% confidence interval = " + "[" +
                               +stats.confidenceLo() + ", "
                               + stats.confidenceHi() + "]");

    }
}
