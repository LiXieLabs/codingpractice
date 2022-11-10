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
import java.util.stream.Collectors;

public class FindAllPossibleRecipesFromGivenSupplies {


    /************************ Solution 1: Intuitive DFS 其实已经类似Recursive Topological Sort **************************/
    private Map<String, List<String>> recipeToIngredients;
    private Map<String, Boolean> memo;

    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // transfer supplies to HashSet for O(1) search time => merged into memo
//        Set<String> supplySet = new HashSet<>();
//        for (String supply : supplies) {
//            supplySet.add(supply);
//        }
//        supplySet = new HashSet<>(Arrays.asList(supplies));

        // init recipe to ingredients map
        recipeToIngredients = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            recipeToIngredients.put(recipes[i], ingredients.get(i));
        }

        // init result as memo
        memo = new HashMap<>();
        for (String supply : supplies) {
            memo.put(supply, true);
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < recipes.length; i++) {
            if (find(recipes[i], new HashSet<>())) {
                res.add(recipes[i]);
            }
        }
        return res;
    }

    private boolean find(String curr, Set<String> path) {
        if (memo.containsKey(curr)) return memo.get(curr);
        if (path.contains(curr)) {
            memo.put(curr, false);
            return false;
        }
        if (!recipeToIngredients.containsKey(curr)) return false;
        path.add(curr);
        for (String ingredient : recipeToIngredients.get(curr)) {
            if (!find(ingredient, path)) {
                memo.put(curr, false);
                return false;
            }
        }
        memo.put(curr, true);
        return true;
    }

    /************************ Solution 2: 标准 Recursive Topological Sort by DFS *********************************/
    // Pseudo-code 参见 https://en.wikipedia.org/wiki/Topological_sorting#Depth-first_search

    private final static int NOT_VISITED = 0;
    private final static int VISITING = 1;
    private final static int VISITED = 2;

    public List<String> findAllRecipes1(String[] recipes, List<List<String>> ingredients, String[] supplies) {

        // init recipe to ingredients map
        Map<String, List<String>> recipeToIngred = new HashMap<>();
        Map<String, Integer> status = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            recipeToIngred.put(recipes[i], ingredients.get(i));
            status.put(recipes[i], NOT_VISITED);
        }

        // init result as memo
        for (String supply : supplies) {
            status.put(supply, VISITED);
        }

        List<String> res = new ArrayList<>();
        for (String recipe : recipes) {
            dfs(recipe, recipeToIngred, status, res);
        }
        return res;
    }

    private boolean dfs(String curr, Map<String, List<String>> recipeToIngred, Map<String, Integer> status, List<String> res) {
        if (!status.containsKey(curr)) return false;
        if (status.get(curr) == VISITING) return false;
        if (status.get(curr) == VISITED) return true;
        status.put(curr, VISITING);
        for (String ingred : recipeToIngred.get(curr)) {
            if (!dfs(ingred, recipeToIngred, status, res)) return false;
        }
        status.put(curr, VISITED);
        res.add(curr);
        return true;
    }

    /******************* Solution 3: 标准 Iterative Topological Sort by Kahn's Algo (相当于 BFS) ****************************/
    // Pseudo-code 参见 https://en.wikipedia.org/wiki/Topological_sorting#Kahn's_algorithm

    public List<String> findAllRecipes2(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Map<String, Integer> ins = new HashMap<>();
        Map<String, List<String>> outs = new HashMap<>(); //实际就是 recipeToIngredients Map
        for (int i = 0; i < recipes.length; i++) {
            for (String ingred : ingredients.get(i)) {
                ins.put(recipes[i], ins.getOrDefault(recipes[i], 0) + 1);
                outs.putIfAbsent(ingred, new ArrayList<>());
                outs.get(ingred).add(recipes[i]);
            }
        }
        List<String> res = new ArrayList<>();
        Deque<String> queue = new ArrayDeque<>(Arrays.asList(supplies));
        Set<String> recipeSet = new HashSet<>(Arrays.asList(recipes));
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (recipeSet.contains(curr)) res.add(curr);
            for (String next : outs.getOrDefault(curr, new ArrayList<>())) {
                ins.put(next, ins.get(next) - 1);
                if (ins.get(next) == 0) {
                    queue.add(next);
                }
            }
        }
        return res;
    }


    public void print(List<String> res) {
        System.out.println("[" + String.join(",", res) + "]");
    }

    public static void main(String[] args) {
        FindAllPossibleRecipesFromGivenSupplies solution = new FindAllPossibleRecipesFromGivenSupplies();
        solution.print(solution.
                findAllRecipes2(
                        new String[]{"bread"},
                        Collections.singletonList(Arrays.asList("yeast","flour")),
                        new String[]{"yeast","flour","corn"}));
        solution.print(solution.
                findAllRecipes2(
                        new String[]{"bread","sandwich"},
                        Arrays.asList(Arrays.asList("yeast","flour"), Arrays.asList("bread","meat")),
                        new String[]{"yeast","flour","meat"}));
        solution.print(solution.
                findAllRecipes2(
                        new String[]{"bread","sandwich","burger"},
                        Arrays.asList(Arrays.asList("yeast","flour"), Arrays.asList("bread","meat"), Arrays.asList("sandwich","meat","bread")),
                        new String[]{"yeast","flour","meat"}));
        solution.print(solution.
                findAllRecipes2(
                        new String[]{"bread","sandwich","burger"},
                        Arrays.asList(Arrays.asList("yeast","flour"), Arrays.asList("burger","meat"), Arrays.asList("sandwich","meat","bread")),
                        new String[]{"yeast","flour","meat"}));
    }
}
