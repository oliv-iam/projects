public class Heap implements Structure {
    String[][] heap;
    String[] columns;
    int idx;

    public Heap(String[][] arr, int pivot){
        // initialize empty array
        heap = new String[arr.length - 1][];
        columns = arr[0];
        idx = 0;

        // call fill method starting at row 1
        fill(arr, pivot);
    }

    public void fill(String[][] arr, int pivot) {
        for(int i = 1; i < arr.length; i++) {
            add(arr[i], pivot);
        }
    }

    void add(String[] r, int pivot) {
        // add row at end
        int i = idx;
        heap[idx++] = r;

        // bubble up: swap added row with parent until no longer smaller
        int p = (i - 1) / 2;
        while(i > 0 && heap[i][pivot].compareTo(heap[p][pivot]) < 0) {
            String[] t = heap[p];
            heap[p] = heap[i];
            heap[i] = t;
            i = p;
            p = (i - 1) / 2;
        }
    }

    public String[][] sorted(int pivot) {
        String[][] ret = new String[heap.length + 1][];
        ret[0] = columns;
        for(int i = 0; i < heap.length; i++) {
            ret[i + 1] = remove(pivot);
        }
        fill(ret, pivot);
        return ret;
    }

    String[] remove(int pivot) {
        // replace first with last element
        String[] ret = heap[0];
        if(idx == 1) {
            idx--;
            return ret;
        }
        heap[0] = heap[--idx];

        // trickle down: move new root downwards
        int i = 0;
        do {
            int j = -1;
            int r = 2 * i + 2;
            int l = 2 * i + 1;
            if(r < idx && heap[r][pivot].compareTo(heap[i][pivot]) < 0) {
                if(heap[l][pivot].compareTo(heap[r][pivot]) < 0) {j = l;} // case: left is smallest
                else {j = r;} // case: right is smallest
            } else {
                if(l < idx && heap[l][pivot].compareTo(heap[i][pivot]) < 0) {j = l;} // case: left is smallest
            }
            if(j >= 0) {
                String[] t = heap[i];
                heap[i] = heap[j];
                heap[j] = t;
            }
            i = j;
        } while(i >= 0);
        return ret;
    }
}