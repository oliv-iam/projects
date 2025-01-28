import java.util.Scanner;
import java.io.*;

public class Main {
    // uncomment chosen data structure
    static String DATA_STRUCTURE = "priority-queue"; // sorting time result: 4848728 ns
    // static String DATA_STRUCTURE = "binary-search-tree"; // sorting time result: 8139900 ns

    Scanner scanner;

    String inpath;
    String outpath;

    Dataset dataset;

    long sortstart;
    long sortend;

    public Main(String _inpath, String _outpath) {
        // fill in information with basic user input
        inpath = _inpath;
        outpath = _outpath;

        // initialize dataset given chosen data structure
        dataset = new Dataset(inpath, outpath, Main.DATA_STRUCTURE);
    }

    void workshop() throws IOException, InterruptedException {
        System.out.println("entering workshop");
        dataset.visualize();
        boolean done = false;

        // enter loop for user viewing and input
        scanner = new Scanner(System.in);
        while(!done) {
            System.out.println("next action? ('viewer' / 'sort' / 'filter' / 'done')");
            String action = scanner.nextLine();
            if(action.equals("done")) {
                done = true;
            } else if(action.equals("viewer")) {
                viewer();
            } else if(action.equals("sort")) {
                System.out.println("sort by values of which column?");
                String pivot = scanner.nextLine();
                sortstart = System.nanoTime();
                dataset.sort(pivot);
                sortend = System.nanoTime();
                dataset.visualize();
            } else if(action.equals("filter")) {  
                // filtering rows
                System.out.println("rows to include? ('all' / 'same' / <comma-separated list>)");
                String respa = scanner.nextLine();
                if(respa.equals("all")) {dataset.filterrows(null);}
                else if (!respa.equals("same")) {
                    int[] inclrows = toint(respa.split(","));
                    dataset.filterrows(inclrows);
                }

                // filtering columns
                System.out.println("columns to include? ('all' / 'same' / <comma-separated list>)");
                String respb = scanner.nextLine();
                if(respb.equals("all")) {dataset.filtercols(null);}
                else if(!respb.equals("same")) {
                    String[] inclcols = respb.split(",");
                    dataset.filtercols(inclcols);
                }

                dataset.visualize();
            } else {
                System.err.println("action not recognized");
            }
        }

        // generate output
        dataset.gencsv();
    }

    int[] toint(String[] a) {
        int[] b = new int[a.length];
        for(int i = 0; i < b.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }
        return b;
    }

    void viewer() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("vim", "-M", "visual.txt");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        Process proc = pb.start();
        int exitcode = proc.waitFor();
        if(exitcode != 0){
            System.err.println("viewer failed");
            System.exit(1);
        } 
    }

    static void writer() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("testing.txt"));
            writer.write("hello");
            writer.close();
        } catch(IOException e) {
            System.err.println("writing to file failed");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // two arguments: input name, output name
        if(args.length != 2) {
            System.err.println("USAGE: java Main <input filename> <output filename>");
            System.exit(1);
        }

        // setup instance and csv
        Main main = new Main("input/" + args[0], "output/" + args[1]);
        System.out.println("--------------------------------------");
        main.workshop();
        System.out.println("--------------------------------------");
        System.out.println("output written to: " + main.outpath);
        main.scanner.close();
        System.out.println("removing auxiliary files");
        File vis = new File("visual.txt");
        if(vis.exists()) {vis.delete();}
        long sorttime = main.sortend - main.sortstart;
        System.out.println("time for last sort: " +  sorttime + " ns");
    }
}
