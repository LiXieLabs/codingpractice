import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DesignInMemoryFileSystem {

    public FileNode root;

    // Trie结构
    public DesignInMemoryFileSystem() {
        root = new FileNode("");
    }

    // Time: HashMap O(M + N + KlogK) TreeMap O(M + NlogK))
    // M path str length, N is node count
    public List<String> ls(String path) {
        FileNode curr = root;
        String[] nodes = path.split("/");
        for (int i = 1; i < nodes.length; i++) {
            curr = curr.children.get(nodes[i]);
        }
        // 如果不用TreeMap，也可以用 Collections.sort(res_files);
        return curr.isDirectory ? new ArrayList<>(curr.children.keySet()) : Collections.singletonList(curr.name);
    }

    // Time: HashMap O(M + N) TreeMap O(M + NlogK)
    public void mkdir(String path) {
        FileNode curr = root;
        String[] nodes = path.split("/");
        for (int i = 1; i < nodes.length; i++) {
            String node = nodes[i];
            if (!curr.children.containsKey(node)) curr.children.put(node, new FileNode(node));
            curr = curr.children.get(node);
        }
    }

    // Time: HashMap O(M + N) TreeMap O(M + NlogK)
    public void addContentToFile(String filePath, String content) {
        FileNode curr = root;
        String[] nodes = filePath.split("/");
        for (int i = 1; i < nodes.length; i++) {
            String node = nodes[i];
            if (!curr.children.containsKey(node)) curr.children.put(node, new FileNode(node));
            curr = curr.children.get(node);
        }
        curr.content.append(content);
        curr.isDirectory = false;
    }

    // Time: HashMap O(M + N) TreeMap O(M + NlogK)
    public String readContentFromFile(String filePath) {
        FileNode curr = root;
        String[] nodes = filePath.split("/");
        for (int i = 1; i < nodes.length; i++) {
            curr = curr.children.get(nodes[i]);
        }
        return curr.content.toString();
    }

    public static void main(String[] args) {
        DesignInMemoryFileSystem solution = new DesignInMemoryFileSystem();
        System.out.println(solution.ls("/"));
        solution.mkdir("/a/b/c");
        solution.addContentToFile("/a/b/c/d", "hello");
        System.out.println(solution.ls("/"));
        System.out.println(solution.readContentFromFile("/a/b/c/d"));
        solution.addContentToFile("/a/b/c/d", " world");
        System.out.println(solution.readContentFromFile("/a/b/c/d"));

    }
}

/**
 * 每个node，有可能是file或者directory
 * 如果是file，则有name和content，isDirectory==false
 * 如果是directory，则有name和children, isDirectory==true
 */
class FileNode {

    public String name;
    public Map<String, FileNode> children;
    public StringBuilder content;
    public boolean isDirectory;

    public FileNode(String name) {
        this.name = name;
        children = new TreeMap<>();
        content = new StringBuilder();
        this.isDirectory = true; // 相当于TrieNode的isEnd
    }
}
