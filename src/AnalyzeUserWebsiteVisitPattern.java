import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1152. Analyze User Website Visit Pattern (https://leetcode.com/problems/analyze-user-website-visit-pattern/description/)
 */
public class AnalyzeUserWebsiteVisitPattern {

    /**************** Solution 1: HashMap + Backtracking + Sorting ****************/
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        int n = timestamp.length;
        // ⚠️注意⚠️ 必须 Integer[] 而非 int[]
        // Arrays.sort(int[]) ❌ does NOT accept a Comparator
        // Arrays.sort(T[], Comparator) ✅ only works for object arrays
        Integer[] sortTime = new Integer[n];
        for (int i = 0; i < n; i++) sortTime[i] = i;
        Arrays.sort(sortTime, Comparator.comparingInt(i -> timestamp[i]));

        Map<String, List<String>> nameToWeb = new HashMap<>();
        for (int i : sortTime) {
            nameToWeb.putIfAbsent(username[i], new ArrayList<>());
            nameToWeb.get(username[i]).add(website[i]);
        }

        Map<String, Integer> counter = new HashMap<>();
        for (List<String> webs : nameToWeb.values()) {
            if (webs.size() >= 3) {
                for (String pattern : generatePatterns(webs, 0, 3)) {
                    counter.put(pattern, counter.getOrDefault(pattern, 0) + 1);
                }
            }
        }

        String resPattern = null;
        for (String pattern : counter.keySet()) {
            // ⚠️注意⚠️ 起始 resPattern = null，所以必须 counter.getOrDefault(resPattern, 0)
            if (counter.get(pattern) > counter.getOrDefault(resPattern, 0)
                    || counter.get(pattern) == counter.get(resPattern)
                    && pattern.compareTo(resPattern) < 0)
                resPattern = pattern;
        }
        // ⚠️注意⚠️ String[] -> List<String>
        return Arrays.asList(resPattern.split(","));
    }

    private Set<String> generatePatterns(List<String> webs, int start, int remain) {
        Set<String> res = new HashSet<>();
        if (remain == 0) {
            res.add("");
            return res;
        }
        for (int i = start; i < webs.size(); i++) {
            String cur = webs.get(i);
            Set<String> nexSet = generatePatterns(webs, i + 1, remain - 1);
            for (String nex : nexSet) {
                res.add(cur + "," + nex);
            }
        }
        return res;
    }

    public static void print(List<String> input) {
        System.out.println("[" + String.join(",", input) + "]");
    }

    public static void main(String[] args) {
        AnalyzeUserWebsiteVisitPattern solution = new AnalyzeUserWebsiteVisitPattern();
        print(solution.mostVisitedPattern(
                new String[]{"joe","joe","joe","james","james","james","james","mary","mary","mary"},
                new int[]{1,2,3,4,5,6,7,8,9,10},
                new String[]{"home","about","career","home","cart","maps","home","home","about","career"}));
        print(solution.mostVisitedPattern(
                new String[]{"ua","ua","ua","ub","ub","ub"},
                new int[]{1,2,3,4,5,6},
                new String[]{"a","b","a","a","b","c"}));
        print(solution.mostVisitedPattern(
                new String[]{"zkiikgv","zkiikgv","zkiikgv","zkiikgv"},
                new int[]{436363475,710406388,386655081,797150921},
                new String[]{"b","c","a","d"}));
        print(solution.mostVisitedPattern(
                new String[]{"him","mxcmo","jejuvvtye","wphmqzn","uwlblbrkqv","flntc","esdtyvfs","nig","jejuvvtye","nig","mxcmo","flntc","nig","jejuvvtye","odmspeq","jiufvjy","esdtyvfs","mfieoxff","nig","flntc","mxcmo","qxbrmo"},
                new int[]{113355592,304993712,80831183,751306572,34485202,414560488,667775008,951168362,794457022,813255204,922111713,127547164,906590066,685654550,430221607,699844334,358754380,301537469,561750506,612256123,396990840,60109482},
                new String[]{"k","o","o","nxpvmh","dssdnkv","kiuorlwdcw","twwginujc","evenodb","qqlw","mhpzoaiw","jukowcnnaz","m","ep","qn","wxeffbcy","ggwzd","tawp","gxm","pop","xipfkhac","weiujzjcy","x"}));
    }
}