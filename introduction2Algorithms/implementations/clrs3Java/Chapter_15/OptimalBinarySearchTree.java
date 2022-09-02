import java.util.Arrays;
import java.util.List;

/**
 * @author yangyanlin <yangyanlin@kuaishou.com>
 * Created on 2022-05-23
 */
public class OptimalBinarySearchTree {
	/**
	 * Construct a binary search tree whose expected search cost is smallest.
	 * <p>
	 * Arguments:
	 * p -- array of probabilities of succesful searches. p[i] is the
	 * probability that a search will be for k[i], where k is the sequence
	 * of n distinct keys in sorted order. Entries used are p[1:n+1], and
	 * p[0] = 0.
	 * q -- array of probabilities of unsuccessful searches. q[i] is the
	 * probability that a search falls between keys k[i] and k[i+1]. q[0]
	 * is the probability that a search falls to the left of k[1] and q[n]
	 * is the probability that a search falls to the right of k[n]. Entries
	 * used are q[0:n+1].
	 * n -- number of distinct keys
	 * Returns:
	 * e -- the cost of an optimal solution for keys k[i] to k[j]. Entries used
	 * are e[1:n+2][0:n+1].
	 * root -- the root of an optimal binary search tree for each subproblem. root[i, j]
	 * is the root of an optimal binary search tree containing the keys k[i]
	 * to k[j]. The table uses only entries for which 1 <= i <= j <= n.
	 */
	public static List<Object> optimalBST(double[] p, double[] q, int n) {
		// w[i][j] is the sum of probabilities p[i] through p[j] and
		// q[i - 1] through q[j]. The entries used are w[1:n+2][0:n+1]
		// where j >= i -1.
		double[][] e = new double[n + 2][n + 1];
		double[][] w = new double[n + 2][n + 1];
		int[][] root = new int[n + 1][n + 1];
		// Initialize base cases of e and w.
		for (int i = 1; i < n + 2; i++) {
			e[i][i - 1] = q[i - 1];
			w[i][i - 1] = q[i - 1];
		}
		// Use recurrences to compute e[i, j] and w[i, j].
		for (int l = 1; l < n + 1; l++) {
			for (int i = 1; i < n - l + 2; i++) {
				int j = i + l - 1;
				e[i][j] = Double.MAX_VALUE;
				w[i][j] = w[i][j - 1] + p[j] + q[j];
				for (int r = i; r < j + 1; r++) {
					double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
					if (t < e[i][j]) {
						e[i][j] = t;
						root[i][j] = r;
					}
				}
			}
		}
		return Arrays.asList(e, root);
	}

	// Testing
	public static void main(String[] args) {
		// Example from textbook.
		double[] p = new double[] {0, 0.15, 0.10, 0.05, 0.10, 0.20};
		double[] q = new double[] {0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
		List<Object> result = optimalBST(p, q, p.length - 1);
		double[][] e = (double[][]) result.get(0);
		int[][] root = (int[][]) result.get(1);
	}
}
