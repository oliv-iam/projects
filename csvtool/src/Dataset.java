import java.io.*;

public class Dataset {
    private String inpath;
    private String outpath;

    private String[][] arr;
    private String ds;
    private Structure struct;
    private int pivot;

    private boolean[] rows;
    private boolean[] cols;

    public Dataset(String in, String out, String _ds) {
        inpath = in; 
        outpath = out;

        pivot = -1;
        ds = _ds;
        arr = readcsv();
    }

    public String[][] readcsv() {
        // read csv and create 2D array
        int n = numLines(inpath);
        String[][] arr = new String[n][];
        System.out.println("reading from CSV file " + inpath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(inpath));
            String line = br.readLine();
            for(int i = 0; i < n; i++) {
                arr[i] = line.split(",");
                line = br.readLine();
            }
            br.close();
        } catch(IOException e) {
            System.err.println("error reading from CSV");
        }
        return arr;
    }

    int numLines(String s) {
        // return number of lines in file
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(inpath));
            String line = br.readLine();
            while((line != null)) {
                count++;
                line = br.readLine();
            }
            br.close();
        } catch(IOException e) {
            System.err.println("error reading file");
        }
        return count;
    }

    public void visualize() { // FIXME: accommodate for row number spacing
        // print 2D array to text file in table format
        System.out.println("generating table visualization");

        // one space after 
        int space = (arr.length + " ").length();

        // division line string
        String divline = spacer("", space) + "| "; // FIXME: modify
        int len = arr[0].length;
        if (cols != null) {
            len = 0;
            for(int i = 0; i < cols.length; i++) {
                if(cols[i]) {len++;}
            }
        }
        int cidx = 0;
        for(int i = 0; i < arr[0].length; i++) {
            if(cols != null && cols[i] == false) {continue;}
            for(int j = 0; j < arr[0][i].length(); j++) {
                divline += "-";
            }
            cidx++;
            if(cidx >= len) {divline += cols == null ? " |" : "-|";}
            else {divline += "-|-";}
        }

        // write visualization to file
        try{
            BufferedWriter bf = new BufferedWriter(new FileWriter("visual.txt"));
            for(int r = 0; r < arr.length; r++) {
                if(rows != null && rows[r] == false) {continue;}
                String row;
                if(r == 0) {row = spacer("", space) + "| ";}
                else {row = spacer(r + "", space) + "| ";}
                for(int c = 0; c < arr[r].length; c++) {
                    if(cols != null && cols[c] == false) {continue;}
                    if(r == 0) {row += arr[r][c];}
                    else if(arr[0][c].length() < arr[r][c].length()) {row += arr[r][c].substring(0, arr[0][c].length());}
                    else {
                        int diff = arr[0][c].length() - arr[r][c].length();
                        for(int i = 0; i <= diff; i++) {
                            if(i == diff / 2) {row += arr[r][c];}
                            else {row += " ";}
                        }
                    }
                    if(c < arr[r].length - 1) {row += " | ";}
                    else {row += " |";}
                }
                bf.write(row + "\n");
                bf.write(divline + "\n");
            }
            bf.close();
        } catch (IOException e) {
            System.err.println("generating visualization failed");
        }
    }

    String spacer(String s, int tot) {
        String ret = "";
        for(int i = 0; i < tot - s.length() - 1; i++) {
            ret += " ";
        }
        ret += s + " ";
        return ret;
    }

    public void gencsv() {
        // generate CSV from 2D array
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(outpath));
            for(int r = 0; r < arr.length; r++) {
                if(rows != null && !rows[r]) {continue;}
                for(int c = 0; c < arr[r].length - 1; c++) {
                    if(cols != null && !cols[c]) {continue;}
                    bf.write(arr[r][c] + ",");
                }
                if(cols == null || cols[arr[r].length - 1]) {bf.write(arr[r][arr[r].length - 1]);} // FIXME: neaten csv output for filtered columns
                bf.write("\n");
            }
            bf.close();
        } catch (IOException e) {
            System.err.println("generating CSV failed");
        }
    }

    public void sort(String col) {
        System.out.println("sorting table based on column " + col);
        // set pivot column
        int opivot = pivot;
        for(int i = 0; i < arr[0].length; i++) {
            if(arr[0][i].equals(col)) {pivot = i;}
        }
        if(opivot == pivot) {
            System.err.println("specified column not found, try again");
            return;
        }
        // load to data structure, retrieve sorted array
        dsinit();
        arr = struct.sorted(pivot);
    }

    void dsinit() {
        if(pivot == -1) {return;}
        if(ds.equals("priority-queue")) {
            struct = new Heap(arr, pivot);
        } else if(ds.equals("binary-search-tree")) {
            struct = new Tree(arr, pivot);
        } else {
            System.err.println("data structure not recognized");
            System.exit(1);
        }
    }

    public void filterrows(int[] r) {
        if(r == null) {
            rows = null;
            return;
        }
        if(r.length >= arr.length) {
            System.err.println("filtering should include fewer rows than original array");
            return;
        }
        System.out.print("included rows: ");
        boolean[] torows = new boolean[arr.length];
        torows[0] = true;
        int ridx = 0;
        for(int i = 1; i < arr.length; i++) {
            if(ridx >= r.length) {break;}
            if(r[ridx] == i ) {
                System.out.print(i + " ");
                torows[i] = true;
                ridx++;
            }
        }
        System.out.println();
        rows = torows;
    }

    public void filtercols(String[] c) {
        if(c == null) {
            cols = null;
            return;
        }
        if(c.length >= arr[0].length) {
            System.err.println("filtering should include fewer columns than original array");
            return;
        }

        System.out.print("included columns: ");

        boolean[] tocols = new boolean[arr[0].length];
        int cidx = 0;
        for(int i = 0; i < arr[0].length; i++) {
            if(cidx >= c.length) {break;}
            if(c[cidx].equals(arr[0][i])) {
                System.out.print("'" + c[cidx] + "' ");
                tocols[i] = true;
                cidx++;
            }
        }
        System.out.println();
        cols = tocols;
    }
}