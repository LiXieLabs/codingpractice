import java.util.*;

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

    /*********************** Solution 2: Union Find ***********************/
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
        // init Union Find
        Map<String, String> emailToName = new HashMap<>();
        UnionFind721 uf = new UnionFind721();
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.put(email, name);
                // if 1st time encountered, init root of email to itself
                uf.roots.putIfAbsent(email, email);
                // union email with the first email in the list
                // the 1st email also needs to be union-ed => TC2
                uf.union(email, account.get(1));
            }
        }

        // group all connected email into 1 list
        Map<String, List<String>> group = new HashMap<>();
        for (String email : uf.roots.keySet()) {
            // 注意！！！必须再压缩一次，不然同一个group可能有多个root，如TC3
            String root = uf.find(uf.roots.get(email));
            group.putIfAbsent(root, new ArrayList<>());
            group.get(root).add(email);
        }
        List<List<String>> res = new ArrayList<>();
        for (String email : group.keySet()) {
            Collections.sort(group.get(email));
            res.add(new ArrayList<>());
            res.get(res.size() - 1).add(emailToName.get(email));
            res.get(res.size() - 1).addAll(group.get(email));
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

    public String find(String p) {
        while (!p.equals(roots.get(p))) {
            roots.put(p, roots.get(roots.get(p)));
            p = roots.get(p);
        }
        return p;
    }

    public void union(String p1, String p2) {
        roots.put(find(p1), find(p2));
    }
}
