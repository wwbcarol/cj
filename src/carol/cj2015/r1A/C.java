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
        Name(TYPE + "-test");

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
            for (int j = 0; j < N; j++) {
                Point p = ins.get(j);
                res[j] = solve(ins, p);
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

    private static int solve(ArrayList<Point> ins, Point p1, Point p2) {

        ArrayList<Point> leftSet = new ArrayList<Point>();
        ArrayList<Point> rightSet = new ArrayList<Point>();

        for (int i = 0; i < ins.size(); i++) {
            Point p = ins.get(i);
            if (pointLocation(A, B, p) == -1) {
                leftSet.add(p);
            } else if (pointLocation(A, B, p) == 1) {
                rightSet.add(p);
            }
        }
    }

    public int pointLocation(Point A, Point B, Point P) {
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0) {
            return 1;
        } else if (cp1 == 0) {
            return 0;
        } else {
            return -1;
        }
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