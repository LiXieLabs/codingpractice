import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SimplifyPath {

    /****************** Solution 1: Stack by Deque ********************/
    /**
     * Time: O(N) Space: O(N) where there are N characters in the original path.
     *
     * NOTE:
     * String比较用equals
     * str.isEmpty()
     * "/a//b/" str.split("/") 之后是 {"","a","","b}
     */
    public String simplifyPath(String path) {

        String[] folders = path.split("/");

        Deque<String> stack = new ArrayDeque<>();
        for (String folder : folders) {
            if (folder.isEmpty() || ".".equals(folder)) {
                continue;
            } else if ("..".equals(folder)) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(folder);
            }
        }
        List<String> res = new ArrayList<>();
        while (!stack.isEmpty()) {
            res.add(stack.pollLast());
        }
        return "/" + String.join("/", res);
    }

    public static void main(String[] args) {
        SimplifyPath solution = new SimplifyPath();
        System.out.println(solution.simplifyPath("/home/"));
        System.out.println(solution.simplifyPath("/../"));
        System.out.println(solution.simplifyPath("/home//foo/"));
        System.out.println(solution.simplifyPath("/a/./b/../../c/"));
    }

}