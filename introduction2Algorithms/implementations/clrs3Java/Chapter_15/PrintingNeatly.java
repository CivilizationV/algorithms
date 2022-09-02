import static org.apache.commons.math3.util.FastMath.max;
import static org.apache.commons.math3.util.FastMath.min;

import java.util.Arrays;
import java.util.List;

/**
 * @author yangyanlin <yangyanlin@kuaishou.com>
 * Created on 2022-05-23
 */
public class PrintingNeatly {
    //

    /**
     * both the time and space are n^2
     * @param l -- words lengths measured in characters
     * @param n -- words number
     * @param M -- maximum of characters each line can hold
     * @return
     */
    public static List<Object> printNeatly(int[] l, int n, int M) {
        int[][] extras = new int[n+1][n+1];
        int[][] lc = new int[n+1][n+1];
        int[] c = new int[n+1];
        int[] p = new int[n+1];
        // Compute extras[i][j] for 1<=i<=j<=n
        for (int i=1; i<n+1; i++) {
            extras[i][i] = M - l[i-1];
            for (int j=i+1; j<n+1; j++) {
                extras[i][j] = extras[i][j-1] - l[j-1] - 1;
            }
        }
        // Compute lc[i][j] for 1<=i<=j<=n
        for (int i=1; i<n+1; i++) {
            for (int j=i; j<n+1; j++) {
                if (extras[i][j] < 0) {
                    lc[i][j] = Integer.MAX_VALUE;
                } else if (j == n && extras[i][j] >= 0) {
                    lc[i][j] = 0;
                } else {
                    lc[i][j] = (int) Math.pow(extras[i][j], 3);
                }
            }
        }
        // Compute c[j] and p[j] for 1<=j<=n
        c[0] = 0;
        for (int j=1; j<n+1; j++) {
            c[j] = Integer.MAX_VALUE;
            for (int i=1; i<=j; i++) {
                if (c[i-1] + lc[i][j] < c[j]) {
                    c[j] = c[i-1] + lc[i][j];
                    p[j] = i;
                }
            }
        }
        return Arrays.asList(c, p);
    }

    // both the time and space are nM
    public static List<Object> printNeatlyImprovedV1(int[] l, int n, int M) {
        int[][] extras = new int[n+1][n+1];
        int[][] lc = new int[n+1][n+1];
        int[] c = new int[n+1];
        int[] p = new int[n+1];
        int wcl = M % 2 == 0 ? M / 2 : M / 2 + 1;
        // Compute extras[i][j] for 1<=i<=j<=n
        for (int i=1; i<n+1; i++) {
            extras[i][i] = M - l[i-1];
            for (int j=i+1; j<min(i+wcl, n+1); j++) {
                extras[i][j] = extras[i][j-1] - l[j-1] - 1;
            }
        }
        // Compute lc[i][j] for 1<=i<=j<=n
        for (int i=1; i<n+1; i++) {
            for (int j=i; j<min(i+wcl, n+1); j++) {
                if (extras[i][j] < 0) {
                    lc[i][j] = Integer.MAX_VALUE;
                } else if (j == n && extras[i][j] >= 0) {
                    lc[i][j] = 0;
                } else {
                    lc[i][j] = (int) Math.pow(extras[i][j], 3);
                }
            }
        }
        // Compute c[j] and p[j] for 1<=j<=n
        c[0] = 0;
        for (int j=1; j<n+1; j++) {
            c[j] = Integer.MAX_VALUE;
            for (int i=max(j-wcl+1, 1); i<=j; i++) {
                if (c[i-1] + lc[i][j] < c[j]) {
                    c[j] = c[i-1] + lc[i][j];
                    p[j] = i;
                }
            }
        }
        return Arrays.asList(c, p);
    }

    public static int giveLines(int[] p, int j) {
        int i = p[j];
        int k;
        if (i == 1) {
            k = 1;
        } else {
            k = giveLines(p, i-1) + 1;
        }
        System.out.println("line number: " + k + ", start: " + i + ", end: " + j);
        return k;
    }
}
