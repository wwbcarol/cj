package carol.cj2015.r1A;

import java.awt.*;
import java.io.*;
import java.util.*;

public class C {

    static final String TYPE = "C";
    static String inputFile;
    static String outputFile;
    static String keyFile;

    private static void Name(String s) {
        inputFile = s + ".in";
        outputFile = s + ".out";
        keyFile = s + ".key";
    }

    public static void main(String[] args) throws FileNotFoundException {

        String root = "data/CJ2015/r1A";

        // Test
        // Name(TYPE + "-test");
        Name("C-small-practice");

        Scanner in = new Scanner(new File(root, inputFile));
        PrintWriter out = new PrintWriter(new File(root, outputFile));

        int T = in.nextInt();

        for (int i = 0; i < T; i++) {

            int N = in.nextInt();
            ArrayList<Point> ins = new ArrayList<Point>();
            for (int j = 0; j < N; j++) {
                int x = in.nextInt();
                int y = in.nextInt();
                ins.add(new Point(x, y));
            }

            int[] res = new int[N];
            Arrays.fill(res, N - 1);
            for (int t = 0; t < N; t++) {
                for (int j = 0; j < N; j++) {
                    if (j != t) {
                        int min = solve(t, j, ins);
                        if (res[t] > min) {
                            res[t] = min;
                        }
                    }
                }
            }

            out.println("Case #" + (i + 1) + ":");
            for (int t = 0; t < N; t++) {
                out.println(res[t]);
            }

        }

        in.close();
        out.close();

        check(root);

    }

    private static int solve(int t, int j, ArrayList<Point> ins) {
        Point p = ins.get(t);
        Point q = ins.get(j);
        int left = 0;
        int right = 0;
        for (int i = 0, len = ins.size(); i < len; i++) {
            if (i != t && i != j) {
                Point p0 = ins.get(i);
                long val = (long)(p0.x - p.x) * (q.y - p.y) - (long)(p0.y - p.y) * (q.x - p.x);
                if (val < 0) {
                    left++;
                } else if (val > 0) {
                    right++;
                }
            }
        }
        return Math.min(left, right);
    }

    public static ArrayList<String> check(String root) {

        Scanner key = null;
        Scanner out = null;
        ArrayList<String> diffs = new ArrayList<String>();
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