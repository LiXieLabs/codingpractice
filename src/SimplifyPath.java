import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * 71. Simplify Path (https://leetcode.com/problems/simplify-path/description/)
 */
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
    public String simplifyPath1(String path) {

        String[] folders = path.split("/");

        Deque<String> stack = new ArrayDeque<>();
        for (String folder : folders) {
            if (folder.isEmpty() || ".".equals(folder)) continue;
            if ("..".equals(folder)) {
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

    /****************** Solution 2: Solution 1 的另一种写法 ********************/
    /**
     * 不用 str.split(delimiterStr), 一个一个 char 处理
     *
     * Time: O(N) Space: O(N) where there are N characters in the original path.
     *
     * NOTE:
     * sb.isEmpty() 需要 >= Java 17
     * Deque 作为 stack 和 queue，都是从头出
     */
    public String simplifyPath2(String path) {
        path = path + "/";
        Deque<String> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (char c : path.toCharArray()) {
            if (c == '/') {
                if (sb.length() != 0) {
                    String s = sb.toString();
                    if ("..".equals(s)) {
                        if (!stack.isEmpty()) stack.pop(); // 返回上层
                    } else {
                        if (!".".equals(s)) stack.push(s); // 添加当前 folder/file 到总 path 中
                    }
                    sb = new StringBuilder(); // reset current folder/file name
                }
            } else {
                sb.append(c); // append to current folder/file name
            }
        }
        sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append("/");
            sb.append(stack.pollLast());
        }
        return sb.length() == 0 ? "/" : sb.toString();
    }

    /****************** Solution 3: Solution 1 的又一种写法 ********************/
    /**
     * 更清晰，容易理解
     *
     * Deque<String> deque = new ArrayDeque<>();
     * List<String> list = new ArrayList<>(deque);
     *
     * Time: O(N) Space: O(N) where there are N characters in the original path.
     */
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        int i = 0;
        while (i < path.length()) {
            if (path.charAt(i) == '/') {
                while (i < path.length() && path.charAt(i) == '/') i++;
            } else {
                int start = i;
                while (i < path.length() && path.charAt(i) != '/') i++;
                String curr = path.substring(start, i);
                if (curr.equals(".")) {
                } else if (curr.equals("..")) {
                    if (!stack.isEmpty()) stack.pop();
                } else {
                    stack.push(curr);
                }
            }
        }
        List<String> nodes = new ArrayList<>(stack);
        Collections.reverse(nodes);
        return "/" + String.join("/", nodes);
    }

    public static void main(String[] args) {
        SimplifyPath solution = new SimplifyPath();
        System.out.println(solution.simplifyPath("/home/")); // /home
        System.out.println(solution.simplifyPath("/../")); // /
        System.out.println(solution.simplifyPath("/home//foo/")); // /home/foo
        System.out.println(solution.simplifyPath("/a/./b/../../c/")); // /c
    }

}