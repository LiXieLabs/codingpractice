import java.util.HashMap;
import java.util.Map;

public class DesignFileSystem {

    DesignFileSystemNode root;

    public DesignFileSystem() {
        root = new DesignFileSystemNode("");
    }

    // Time:  O(Longest Path Length)  worst O(Total Path Count)
    // Space: O(Path Length)
    // Returns false if the path already exists or its parent path doesn't exist.
    // Returns true if the path doesn't exist and its parent path exists.
    public boolean createPath(String path, int value) {
        String[] nodes = path.split("/");
        DesignFileSystemNode curr = root;
        // find parent folder
        for (int i = 1; i < nodes.length - 1; i++) {
            String node = nodes[i];
            if (!curr.children.containsKey(node)) return false;
            curr = curr.children.get(node);
        }
        // add last new folder
        String last = nodes[nodes.length - 1];
        if (curr.children.containsKey(last)) return false;
        curr.children.put(last, new DesignFileSystemNode(last));
        curr.children.get(last).val = value;
        return true;
    }

    // Time:  O(Longest Path Length)  worst O(Total Path Count)
    // Space: O(1)
    public int get(String path) {
        String[] nodes = path.split("/");
        DesignFileSystemNode curr = root;
        for (int i = 1; i < nodes.length; i++) {
            String node = nodes[i];
            if (!curr.children.containsKey(node)) return -1;
            curr = curr.children.get(node);
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

class DesignFileSystemNode {

    String key;
    int val;
    Map<String, DesignFileSystemNode> children;

    public DesignFileSystemNode(String key) {
        this.key = key;
        val = -1;
        children = new HashMap<>();
    }
}
