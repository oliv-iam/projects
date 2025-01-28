public class Tree implements Structure {
    Node root;
    String[] columns;
    int idx;
    int n;

    public Tree(String[][] arr, int pivot) {
        fill(arr, pivot);
    }

    public void fill(String[][] arr, int pivot) {
        columns = arr[0];
        n = arr.length;
        for(int i = 1; i < arr.length; i++) {
            add(new Node(arr[i], pivot, n));
        }
    }

    void add(Node a) {
        if(root == null) {root = a;}
        else {
            Node c = root;
            while(c != null) {
                if(c.compareTo(a) == 0) {
                    c.matches[c.midx++] = a;
                    return;
                } else if(a.compareTo(c) < 0) {
                    if(c.left == null) {
                        c.left = a;
                        return;
                    } else {
                        c = c.left;
                    }
                } else if (a.compareTo(c) > 0) {
                    if(c.right == null) {
                        c.right = a;
                        return;
                    } else {
                        c = c.right;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public String[][] sorted(int pivot) {
        String[][] ret = new String[n][];
        ret[0] = columns;
        idx = 1;
        traversal(root, ret);
        return ret;
    }

    void traversal(Node root, String[][] s) {
        // fill in array by traversing BST
        if(root.left != null) {
            traversal(root.left, s);
        }

        // current value, accounting for possible matches
        s[idx] = root.s;
        idx++;
        if(root.midx > 0) {
            for(int i = 0; i < root.midx; i++) {
                s[idx] = root.matches[i].s;
                idx++;
            }
        }
        
        if(root.right != null) {
            traversal(root.right, s);
        }
    }
}

class Node {
    Node left;
    Node right;
    int pivot;
    String[] s;
    Node[] matches;
    int midx;

    public Node(String[] _s, int _pivot, int n) {
        s = _s;
        pivot = _pivot;
        matches = new Node[n - 2];
        midx = 0;
    }

    public int compareTo(Node other) {
        return this.s[this.pivot].compareTo(other.s[other.pivot]);
    }
}