package carol.cj2014.r1A;

import java.io.*;
import java.util.*;

public class B {

    static final String TYPE = "B";
    static String inputFile;
    static String outputFile;
    static String keyFile;

    private static void Name(String s) {
        inputFile = s + ".in";
        outputFile = s + ".out";
        keyFile = s + ".key";
    }

    public static void main(String[] args) throws FileNotFoundException {

        String root = "data/CJ2014";

        // Test
        Name(TYPE + "-test");

        Scanner in = new Scanner(new File(root, inputFile));
        PrintWriter out = new PrintWriter(new File(root, outputFile));

        int T = in.nextInt();

        for (int i = 0; i < T; i++) {
            int N = in.nextInt();
            ArrayList<ArrayList<Integer>> links = new ArrayList<ArrayList<Integer>>(N);
            HashMap<String, Boolean> map = new HashMap<String, Boolean>();
            for (int j = 0; j < N - 1; j++) {
                int Xi = in.nextInt();
                int Yi = in.nextInt();
                addLink(links, Xi, Yi);
                addLink(links, Yi, Xi);
                map.put(Xi + "-" + Yi, Boolean.TRUE);
                map.put(Yi + "-" + Xi, Boolean.TRUE);
            }
            out.println("Case #" + (i + 1) + ": ");

        }

        in.close();
        out.close();

        check(root);

    }

    private static void addLink(ArrayList<ArrayList<Integer>> links, int xi, int yi) {
        ArrayList<Integer> link = links.get(xi - 1) == null ? new ArrayList<Integer>() : links.get(xi - 1);
        link.add(Integer.valueOf(yi));
        links.add(link);
    }

    class Node {
        int val;
        Node next;
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