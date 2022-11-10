import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubdomainVisitCount {

    /************ Solution 1: String split ***************/
    public List<String> subdomainVisits1(String[] cpdomains) {
        Map<String, Integer> counter = new HashMap<>();
        for (String s : cpdomains) {
            String[] sub = s.split(" "); // domain.split("\\s+"); 也可以
            int count = Integer.parseInt(sub[0]); // Integer.valueOf(s) == Integer.parseInt(s, 10)
            String[] domains = sub[1].split("\\."); //是regex，.需要escape
            for (int i = 0; i < domains.length; i++) {
                // String join & Subarray
                String key = String.join(".", Arrays.copyOfRange(domains, i, domains.length));
                counter.put(key, counter.getOrDefault(key, 0) + count);
            }
        }
        List<String> res = new ArrayList<>();
        // 遍历entrySet
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            res.add(entry.getValue() + " " + entry.getKey());
        }
        return res;
    }

    /************ Solution 2: String substring ***************/
    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> counter = new HashMap<>();
        for (String s : cpdomains) {
            int idx = s.indexOf(' ');
            int count = Integer.parseInt(s.substring(0, idx));
            for (int i = idx + 1; i < s.length(); i++) {
                if (s.charAt(i) == '.') {
                    String key = s.substring(idx + 1, i);
                    counter.put(key, counter.getOrDefault(key,0) + count);
                }
            }
            counter.put(s, counter.getOrDefault(s, 0) + count);
        }
        List<String> res = new ArrayList<>();
        // 遍历entrySet
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            res.add(entry.getValue() + " " + entry.getKey());
        }
        return res;
    }

    public static void print(List<String> res) {
        System.out.println("[" + String.join(",", res) + "]");
    }

    public static void main(String[] args) {
        SubdomainVisitCount solution = new SubdomainVisitCount();
        print(solution.subdomainVisits(new String[]{"9001 discuss.leetcode.com"}));
        print(solution.subdomainVisits(new String[]{"900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"}));
    }
}
