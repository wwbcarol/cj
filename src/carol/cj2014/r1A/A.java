package carol.cj2014.r1A;

import java.io.*;
import java.util.*;

public class A {

    static final String TYPE = "A";
    static String inputFile;
    static String outputFile;
    static String keyFile;

    private static void Name(String s) {
        inputFile = s + ".in";
        outputFile = s + ".out";
        keyFile = s + ".key";
    }

    @SuppressWarnings("boxing")
    public static void main(String[] args) throws FileNotFoundException {

        String root = "data/cj2014/r1A";

        // Test
        Name("A-large-practice");

        Scanner in = new Scanner(new File(root, inputFile));
        PrintWriter out = new PrintWriter(new File(root, outputFile));

        int T = in.nextInt();

        for (int i = 0; i < T; i++) {
            int N = in.nextInt();
            int L = in.nextInt();
            long[] outlets = new long[N];
            long[] electric = new long[N];
            for (int j = 0; j < N; j++) {
                String s = in.next();
                for (int t = 0; t < L; t++) {
                    if (s.charAt(L - t - 1) == '1') {
                        outlets[j] |= 1L << t;
                    }
                }
            }

            for (int j = 0; j < N; j++) {
                String s = in.next();
                for (int t = 0; t < L; t++) {
                    if (s.charAt(L - t - 1) == '1') {
                        electric[j] |= 1L << t;
                    }
                }
            }

            Arrays.sort(electric);

            int num = L + 1;
            for (int j = 0; j < N; j++) {
                long[] newOut = new long[N];
                long flip = outlets[0] ^ electric[j];
                for (int t = 0; t < N; t++) {
                    newOut[t] = outlets[t] ^ flip;
                }
                Arrays.sort(newOut);
                if (Arrays.equals(newOut, electric)) {
                    num = Math.min(num, Long.bitCount(flip));
                }
            }

            out.println("Case #" + (i + 1) + ": " + (num != L + 1 ? num : "NOT POSSIBLE"));
        }

        in.close();
        out.close();

        // check(root);
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