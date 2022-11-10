import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SnapshotArray {

    /************************* Solution 1 **************************/

//    private List<List<Integer>> array;

//    public SnapshotArray(int length) {
//        this.array = new ArrayList<>();
//        this.array.add(new ArrayList<>());
//        for (int i = 0; i < length; i++) {
//            this.array.get(0).add(0);
//        }
//        print();
//    }

//    public void set(int index, int val) {
//        this.array.get(this.array.size() - 1).set(index, val);
//        print();
//    }
//
//    // NOTE: O(N) copy => TLE
//    public int snap(){
//        int size = this.array.size();
//        this.array.add(new ArrayList<>());
//        for (int i = 0; i < this.array.get(0).size(); i++) {
//            this.array.get(size).add(this.array.get(size-1).get(i));
//        }
//        print();
//        return size - 1;
//    }
//
//    public int get(int index, int snap_id) {
//        return this.array.get(snap_id).get(index);
//    }

//    public void print() {
//        Arrays.stream(this.array.toArray()).forEach(System.out::println);
//        System.out.println();
//    }

    /************************* Solution 2 **************************/

    // NOTE: 可以用 List<int[]>[] 代替 List<List<Node>
    // this.array = new List[length]
    // this.array[i] = new ArrayList<>();
    // this.array[i].add(new int[]{snapId, val});
    private List<List<Node>> array;
    private int snapId;

    private class Node {
        int snapId;
        int val;

        public Node(int snapId, int val) {
            this.snapId = snapId;
            this.val = val;
        }

        @Override
        public String toString() {
            return "snapId: " + this.snapId + ", val: " + this.val;
        }
    }

    public SnapshotArray(int length) {
        this.snapId = 0;
        this.array = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            this.array.add(new ArrayList<>());
            this.array.get(i).add(new Node(this.snapId, 0));
        }
        print();
    }

    // NOTE: O(1)
    public void set(int index, int val) {
        Node currNode = this.array.get(index).get(this.array.get(index).size()-1);
        if (currNode.snapId != this.snapId) {
            currNode = new Node(this.snapId, val);
            this.array.get(index).add(currNode);
        } else {
            currNode.val = val;
        }
        print();
    }

    // NOTE: O(1)
    public int snap(){
        return this.snapId++;
    }

    // NOTE: O(log(SnapId)) binary search
    public int get(int index, int snap_id) {
        List<Node> snapshots = this.array.get(index);
        int l = 0, r = snapshots.size() - 1;
        while (l < r) {
            int mid = (l + r + 1) / 2; // NOTE!!! +1 to avoid TLE!!!
            if (snapshots.get(mid).snapId == snap_id) {
                return snapshots.get(mid).val;
            } else if (snapshots.get(mid).snapId > snap_id) {
                r = mid - 1;
            } else {
                l = mid;
            }
        }
        return snapshots.get(l).val;
    }

    public void print() {
        Arrays.stream(this.array.toArray()).forEach(System.out::println);
        System.out.println();
    }

    /************************* Solution 3 **************************/

//    private TreeMap<Integer, Integer>[] array;
//    private int snapId;
//
//    public SnapshotArray(int length) {
//        this.snapId = 0;
//        this.array = new TreeMap[length];
//        for (int i = 0; i < length; i++) {
//            this.array[i] = new TreeMap<>();
//            this.array[i].put(0, 0);
//        }
//    }
//
//    // NOTE: O(log(snapId))
//    public void set(int index, int val) {
//        this.array[index].put(this.snapId, val);
//    }
//
//    // NOTE: O(1)
//    public int snap(){
//        return this.snapId++;
//    }
//
//    // NOTE: O(log(snapId))
//    public int get(int index, int snap_id) {
//        return this.array[index].floorEntry(snap_id).getValue();
//    }

    public static void main(String[] args) {
        SnapshotArray solution1 = new SnapshotArray(3);
        solution1.set(0, 5);
        System.out.println(solution1.snap());
        solution1.set(0, 6);
        System.out.println(solution1.get(0, 0));

        SnapshotArray solution2 = new SnapshotArray(3);
        solution2.set(1, 6);
        System.out.println(solution2.snap());
        System.out.println(solution2.snap());
        solution2.set(1, 19);
        solution2.set(0, 4);
        System.out.println(solution2.get(2, 1));
        System.out.println(solution2.get(2, 0));
        System.out.println(solution2.get(0, 1));
    }
}
