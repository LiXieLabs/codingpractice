import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountsMerge {

    /****************** Solution 1: DFS 遍历多源无向图 ********************、
     *
     * Time: O(Sum(NiKlogNiK)) Ni is length of accounts[i], K is avg length of an account
     * worst case ends up with all accounts belong to 1 user, O(NKlogNK)
     * each node is visited once, sort each group takes dominance
     *
     * Space: O(Sum(Ni)) by maps and visited set
     */
    public List<List<String>> accountsMerge1(List<List<String>> accounts) {
        // build adj list
        Map<String, String> emailToName = new HashMap<>();
        Map<String, Set<String>> adjList = new HashMap<>();
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.putIfAbsent(email, name);
                adjList.putIfAbsent(email, new HashSet<>());
                if (i == 1) continue;
                adjList.get(email).add(account.get(1));
                adjList.get(account.get(1)).add(email);
            }
        }

        // dfs each connected group of accounts
        List<List<String>> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String email : emailToName.keySet()) {
            if (visited.contains(email)) continue;
            List<String> groupedEmail = new ArrayList<>();
            Deque<String> stack = new ArrayDeque<>();
            stack.push(email);
            visited.add(email); // 小心！！! 必须跟最内层if里面一致！！！
            while (!stack.isEmpty()) {
                String curEmail = stack.pop();
                groupedEmail.add(curEmail);
                for (String nexEmail : adjList.get(curEmail)) {
                    if (!visited.contains(nexEmail)) {
                        stack.push(nexEmail);
                        visited.add(nexEmail);
                    }
                }
            }
            Collections.sort(groupedEmail);
            res.add(new ArrayList<>());
            res.get(res.size() - 1).add(emailToName.get(email));
            res.get(res.size() - 1).addAll(groupedEmail);
        }
        return res;
    }

    /*********************** Solution 2: Similar but BFS *********************/
    public List<List<String>> accountsMerge2(List<List<String>> accounts) {
        Map<String, String> emailToName = new HashMap<>();
        Map<String, Set<String>> adjList = new HashMap<>();
        for (List<String> account : accounts) {
            for (int i = 1; i < account.size(); i++) {
                emailToName.put(account.get(i), account.get(0));
                adjList.putIfAbsent(account.get(i), new HashSet<>());
                if (i == 1) continue;
                adjList.get(account.get(i)).add(account.get(1));
                adjList.get(account.get(1)).add(account.get(i));
            }
        }
        List<List<String>> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String email : emailToName.keySet()) {
            // ⚠️注意⚠️ 必加！！！
            if (!visited.add(email)) continue;
            res.add(new ArrayList<>());
            List<String> groupedEmail = new ArrayList<>();
            List<String> currLevel = new ArrayList<>();
            currLevel.add(email);
            while (!currLevel.isEmpty()) {
                List<String> nextLevel = new ArrayList<>();
                for (String curr : currLevel) {
                    groupedEmail.add(curr);
                    for (String next : adjList.get(curr)) {
                        // ⚠️注意⚠️ 必加！！！
                        if (!visited.add(next)) continue;
                        nextLevel.add(next);
                    }
                }
                currLevel = nextLevel;
            }
            res.get(res.size() - 1).add(emailToName.get(email));
            Collections.sort(groupedEmail);
            res.get(res.size() - 1).addAll(groupedEmail);
        }
        return res;

    }

    /*********************** Solution 3: Union Find ***********************/
    /**
     * Time: O(Sort + Union&Find w/ path compression) = O(Sum(NiKlogNiK) + N X alpha(N))
     * alpha(N) is inverse-ackermann function, Ni is length of accounts[i], K is avg length of an account
     * ~ O(Sum(NiKlogNiK) + N)
     * worst case ends up with all accounts belong to 1 user, O(NKlogNK)
     *
     * Space: O(NK) by maps
     *
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // init union find
        UnionFind721 uf = new UnionFind721();
        Map<String, String> emailToName = new HashMap<>();
        for (List<String> account : accounts) {
            String name = account.get(0);
            String firstEmail = account.get(1);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.put(email, name);
                uf.add(email);
                // 把每一个跟第一个 union 起来！不同 account 里面的，会自动通过 root union 起来！！！
                uf.union(email, firstEmail);
            }
        }

        Map<String, List<String>> groupedEmail = new HashMap<>();
        for (String email : emailToName.keySet()) {
            // 注意！如果不是 full compression，就需要在压缩一次！！！
            String root = uf.find(email);
            groupedEmail.putIfAbsent(root, new ArrayList<>());
            groupedEmail.get(root).add(email);
        }

        List<List<String>> res = new ArrayList<>();
        for (String key : groupedEmail.keySet()) {
            List<String> merged = new ArrayList<>();
            merged.add(emailToName.get(key));

            List<String> emails = groupedEmail.get(key);
            Collections.sort(emails);
            merged.addAll(emails);

            res.add(merged);
        }
        return res;
    }

    public static void main(String[] args) {
        AccountsMerge solution = new AccountsMerge();

        System.out.println(solution.accountsMerge(Arrays.asList(
                Arrays.asList("John","johnsmith@mail.com","john_newyork@mail.com"),
                Arrays.asList("John","johnsmith@mail.com","john00@mail.com"),
                Arrays.asList("Mary","mary@mail.com"),
                Arrays.asList("John","johnnybravo@mail.com")
        )));
        // [[John, johnnybravo@mail.com],
        // [John, john00@mail.com, john_newyork@mail.com, johnsmith@mail.com],
        // [Mary, mary@mail.com]]

        System.out.println(solution.accountsMerge(Arrays.asList(
                Arrays.asList("Alex","Alex5@m.co","Alex4@m.co","Alex0@m.co"),
                Arrays.asList("Ethan","Ethan3@m.co","Ethan3@m.co","Ethan0@m.co"),
                Arrays.asList("Kevin","Kevin4@m.co","Kevin2@m.co","Kevin2@m.co"),
                Arrays.asList("Gabe","Gabe0@m.co","Gabe3@m.co","Gabe2@m.co"),
                Arrays.asList("Gabe","Gabe3@m.co","Gabe4@m.co","Gabe2@m.co")
        )));
        // [[Alex, Alex0@m.co, Alex4@m.co, Alex5@m.co],
        // [Ethan, Ethan0@m.co, Ethan3@m.co],
        // [Kevin, Kevin2@m.co, Kevin4@m.co],
        // [Gabe, Gabe0@m.co, Gabe2@m.co, Gabe3@m.co, Gabe4@m.co]]

        System.out.println(solution.accountsMerge(Arrays.asList(
                Arrays.asList("David","David0@m.co","David1@m.co"),
                Arrays.asList("David","David3@m.co","David4@m.co"),
                Arrays.asList("David","David4@m.co","David5@m.co"),
                Arrays.asList("David","David2@m.co","David3@m.co"),
                Arrays.asList("David","David1@m.co","David2@m.co")
        )));
        // [[Alex, Alex0@m.co, Alex4@m.co, Alex5@m.co],
        // [Ethan, Ethan0@m.co, Ethan3@m.co],
        // [Kevin, Kevin2@m.co, Kevin4@m.co],
        // [Gabe, Gabe0@m.co, Gabe2@m.co, Gabe3@m.co, Gabe4@m.co]]
    }
}

class UnionFind721 {

    Map<String, String> roots;

    public UnionFind721() {
        roots = new HashMap<>();
    }

    public void add(String s) {
        // ⚠️注意⚠️ 有可能添加过了！！！
        roots.putIfAbsent(s, s);
    }

    public String find(String s) {
        if (!s.equals(roots.get(s))) {
            roots.put(s, find(roots.get(s)));
        }
        return roots.get(s);
    }

    public void union(String s1, String s2) {
        String s1Root = find(s1), s2Root = find(s2);
        if (s1Root.equals(s2Root)) return;
        roots.put(s1Root, s2Root);
    }

}
