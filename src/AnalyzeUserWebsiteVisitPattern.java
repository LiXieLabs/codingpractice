import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyzeUserWebsiteVisitPattern {

    public List<String> analyzeUserWebsiteVisitPattern(String[] username, int[] timestamp, String[] website) {
        // build user visit history map, key = username, value = list of website in ascendant time order then lexicographically
        int[][] sortTime = new int[timestamp.length][2];
        int idx = 0;
        while (idx < timestamp.length) {
            sortTime[idx] = new int[]{timestamp[idx], idx};
            idx++;
        }
        Arrays.sort(sortTime, Comparator.comparingInt(t -> t[0]));
        Map<String, List<String>> visitHistory = new HashMap<>();
        for (int[] t : sortTime) {
            int i = t[1];
            visitHistory.putIfAbsent(username[i], new ArrayList<>());
            visitHistory.get(username[i]).add(website[i]);
        }


        // maintain pattern count map, key = pattern, value = count
        // iterate over visit history map, generate pattern combination and increment count map
        Map<String, Integer> counter = new HashMap<>();
        for (String user : visitHistory.keySet()) {
            if (visitHistory.get(user).size() >= 3) {
                Set<String> patterns = generatePattern(visitHistory.get(user), 0, 3);
                for(String pattern : patterns) {
                    counter.put(pattern, counter.getOrDefault(pattern, 0) + 1);
                }
            }
        }
        String resPattern = null;
        for (String pattern : counter.keySet()) {
            if (resPattern == null
                    || counter.get(pattern) > counter.get(resPattern)
                    || counter.get(pattern).equals(counter.get(resPattern)) && pattern.compareTo(resPattern) < 0) {
                resPattern = pattern;
            }
        }
        return Arrays.asList(resPattern.split(","));
    }

    public Set<String> generatePattern(List<String> webs, int start, int k) {
        Set<String> res = new HashSet<>();
        if (k == 0) {
            res.add("");
            return res;
        }
        for (int i = start; i < webs.size() - k + 1; i++) {
            String curr = webs.get(i);
            Set<String> nextSet = generatePattern(webs, i + 1, k - 1);
            for (String next : nextSet) {
                res.add(curr + "," + next);
            }
        }
        return res;
    }

    public static void print(List<String> input) {
        System.out.println("[" + String.join(",", input) + "]");
    }

    public static void main(String[] args) {
        AnalyzeUserWebsiteVisitPattern solution = new AnalyzeUserWebsiteVisitPattern();
        print(solution.analyzeUserWebsiteVisitPattern(
                new String[]{"joe","joe","joe","james","james","james","james","mary","mary","mary"},
                new int[]{1,2,3,4,5,6,7,8,9,10},
                new String[]{"home","about","career","home","cart","maps","home","home","about","career"}));
        print(solution.analyzeUserWebsiteVisitPattern(
                new String[]{"ua","ua","ua","ub","ub","ub"},
                new int[]{1,2,3,4,5,6},
                new String[]{"a","b","a","a","b","c"}));
        print(solution.analyzeUserWebsiteVisitPattern(
                new String[]{"zkiikgv","zkiikgv","zkiikgv","zkiikgv"},
                new int[]{436363475,710406388,386655081,797150921},
                new String[]{"b","c","a","d"}));
        print(solution.analyzeUserWebsiteVisitPattern(
                new String[]{"him","mxcmo","jejuvvtye","wphmqzn","uwlblbrkqv","flntc","esdtyvfs","nig","jejuvvtye","nig","mxcmo","flntc","nig","jejuvvtye","odmspeq","jiufvjy","esdtyvfs","mfieoxff","nig","flntc","mxcmo","qxbrmo"},
                new int[]{113355592,304993712,80831183,751306572,34485202,414560488,667775008,951168362,794457022,813255204,922111713,127547164,906590066,685654550,430221607,699844334,358754380,301537469,561750506,612256123,396990840,60109482},
                new String[]{"k","o","o","nxpvmh","dssdnkv","kiuorlwdcw","twwginujc","evenodb","qqlw","mhpzoaiw","jukowcnnaz","m","ep","qn","wxeffbcy","ggwzd","tawp","gxm","pop","xipfkhac","weiujzjcy","x"}));
    }
}