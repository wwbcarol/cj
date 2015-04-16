package carol.cj2014.r1A;

import java.io.*;
import java.util.*;

public class B {

    static final String TYPE = "B";
    static String inputFile;
    static String outputFile;
    static String keyFile;
    @SuppressWarnings("rawtypes")
    static ArrayList[] links;

    private static void Name(String s) {
        inputFile = s + ".in";
        outputFile = s + ".out";
        keyFile = s + ".key";
    }

    @SuppressWarnings({ "unchecked", "boxing" })
    public static void main(String[] args) throws FileNotFoundException {

        String root = "data/CJ2014/r1A";

        // Test
        // Name("B-large-practice");
        Name("B-test");

        Scanner in = new Scanner(new File(root, inputFile));
        PrintWriter out = new PrintWriter(new File(root, outputFile));

        int T = in.nextInt();

        for (int i = 0; i < T; i++) {
            int N = in.nextInt();
            links = new ArrayList[N];
            for (int j = 0; j < N; j++) {
                links[j] = new ArrayList<Integer>();
            }
            for (int j = 0; j < N - 1; j++) {
                int Xi = in.nextInt() - 1;
                int Yi = in.nextInt() - 1;
                links[Xi].add(Yi);
                links[Yi].add(Xi);
            }
            int[][] dp = new int[N][N + 1];
            for (int[] ar : dp) {
                Arrays.fill(ar, -1);
            }

            int ans = N - 1;
            for (int j = 0; j < N; j++) {
                System.out.print(solve(dp, j, N) + " ");
                ans = Math.min(ans, N - solve(dp, j, N));
                System.out.println("--");
            }

            out.println("Case #" + (i + 1) + ": " + ans);

        }

        in.close();
        out.close();

        check(root);

    }

    @SuppressWarnings("unchecked")
    private static int solve(int[][] dp, int j, int t) {
        if (dp[j][t] == -1) {
            int max1 = -1, max2 = -1;
            for (int node : (ArrayList<Integer>)links[j]) {
                if (node == t) {
                    continue;
                }

                int res = solve(dp, node, j);
                if (res > max1) {
                    max2 = max1;
                    max1 = res;
                } else if (res > max2) {
                    max2 = res;
                }

            }

            dp[j][t] = max2 == -1 ? 1 : max1 + max2 + 1;
        }

        return dp[j][t];
    }

    public static List<String> check(String root) {

        Scanner key = null;
        Scanner out = null;
        List<String> diffs = new ArrayList<String>();
        try {
            key = new Scanner(new File(root, keyFile));
            out = new Scanner(new File(root, outputFile));
            String lineKey = "";
            String lineOut = "";
            int lineNum = 0;
            while (key.hasNext() && out.hasNext()) {
                lineKey = key.nextLine();
                lineOut = out.nextLine();
                lineNum++;
                if (!lineKey.equals(lineOut)) {
                    diffs.add("Differ at line " + lineNum + ": \n\tOutput : " + lineOut + "\n\tKey    : " + lineKey);
                }
            }

            while (!key.hasNext() && out.hasNext()) {
                lineOut = out.nextLine();
                lineNum++;
                diffs.add("Output has extra lines at line " + lineNum + ": \n\tOutput : " + lineOut);
            }

            while (key.hasNext() && !out.hasNext()) {
                lineKey = key.nextLine();
                lineNum++;
                diffs.add("Key has extra lines at line " + lineNum + ": \n\tKey    : " + lineKey);
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {
            if (key != null) {
                key.close();
            }

            if (out != null) {
                out.close();
            }
        }

        for (String line : diffs) {
            System.out.println(line);
        }

        return diffs;
    }

}