import java.util.HashMap;
import java.util.Map;

public class DesignFileSystem {

    TrieNode1166 root;

    public DesignFileSystem() { root = new TrieNode1166(-1); }

    // Time:  O(Longest Path Length)  worst O(Total Path Count)
    // Space: O(Path Length)
    // Returns false if the path already exists or its parent path doesn't exist.
    // Returns true if the path doesn't exist and its parent path exists.
    public boolean createPath(String path, int value) {
        String[] steps = path.split("/");
        TrieNode1166 curr = root;
        // find parent folder
        // ⚠️注意⚠️ 必须从 1 开始找！因为 "/a" split 完是 ["", "a"]
        for (int i = 1; i < steps.length - 1; i++) {
            if (!curr.children.containsKey(steps[i])) return false;
            curr = curr.children.get(steps[i]);
        }
        // add new folder
        if (curr.children.containsKey(steps[steps.length - 1])) return false;
        curr.children.put(steps[steps.length - 1], new TrieNode1166(value));
        return true;
    }

    // Time:  O(Longest Path Length)  worst O(Total Path Count)
    // Space: O(1)
    public int get(String path) {
        String[] steps = path.split("/");
        TrieNode1166 curr = root;
        for (int i = 1; i < steps.length; i++) {
            if (!curr.children.containsKey(steps[i])) return -1;
            curr = curr.children.get(steps[i]);
        }
        return curr.val;
    }

    public static void main(String[] args) {

        System.out.println("---------- Test Case 1 --------------");
        DesignFileSystem solution1 = new DesignFileSystem();
        System.out.println(solution1.createPath("/a", 1));
        System.out.println(solution1.get("/a"));
        System.out.println(solution1.createPath("/a", 2));
        System.out.println(solution1.get("/a"));
        System.out.println(solution1.createPath("/a/b/c", 3));
        System.out.println(solution1.get("/a/b"));
        System.out.println(solution1.get("/a/b/c"));

        System.out.println("---------- Test Case 2 --------------");
        DesignFileSystem solution2 = new DesignFileSystem();
        System.out.println(solution2.createPath("/zt/x",710263947));
        System.out.println(solution2.get("/zt/ob"));
        System.out.println(solution2.get("/zt/u"));
        System.out.println(solution2.get("/zt"));
        System.out.println(solution2.createPath("/zt/u",99415876));
        System.out.println(solution2.createPath("/zt",912783775));
        System.out.println(solution2.createPath("/zt/ob",885899287));
        System.out.println(solution2.get("/zt/x"));
    }
}

class TrieNode1166 {

    Map<String, TrieNode1166> children;
    int val;

    public TrieNode1166(int val) {
        children = new HashMap<>();
        this.val = val;
    }
}
