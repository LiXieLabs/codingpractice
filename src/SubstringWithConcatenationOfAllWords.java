import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 30. Substring with Concatenation of All Words
 * (https://leetcode.com/problems/substring-with-concatenation-of-all-words/description/)
 */
public class SubstringWithConcatenationOfAllWords {

    /*************** Solution 1: Brute-Force **********************/
    /**
     * Time: O(N x M x K)
     * where N = s.length(), M = words.length, K = words[0].length()
     *
     * Space: O(M + K) by HashMap + sub = O(M)
     */
    public List<Integer> findSubstring1(String s, String[] words) {
        // Build Dictionary
        Map<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            counter.put(word, counter.getOrDefault(word, 0) + 1);
        }
        // 对每个起始位置，向后遍历 m x k，满足则记入结果
        int m = words.length, k = words[0].length();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < s.length() - m * k + 1; i++) {
            Map<String, Integer> copy = new HashMap<>(counter);
            int r = i, missing = m;
            while (r < i + m * k) {
                String sub = s.substring(r, r + k);
                System.out.println(sub);
                if (copy.getOrDefault(sub, 0) > 0) {
                    copy.put(sub, copy.get(sub) - 1);
                    missing--;
                    r += k;
                } else {
                    break;
                }
            }
            if (missing == 0) {
                res.add(i);
            }
        }
        return res;
    }

    /******************* Solution 2: Sliding Window ***********************/
    /**
     * 对于每个 i = [0,k) 做 sliding window
     *
     * Time: O(N x M x K)
     * where N = s.length(), M = words.length, K = words[0].length()
     *
     * Space: O(M + K) by HashMap + sub = O(M)
     */
    public List<Integer> findSubstring(String s, String[] words) {
        // Build Dictionary
        Map<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            counter.put(word, counter.getOrDefault(word, 0) + 1);
        }
        // 对于每个 i = [0,k) 做 sliding window
        int m = words.length, k = words[0].length();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Map<String, Integer> copy = new HashMap<>(counter);
            int l = i, r = i, missing = m;
            while (r + k <= s.length()) {
                String word = s.substring(r, r + k);
                if (!copy.containsKey(word)) { // 如果当前 word 不在 words 里面，直接 reset
                    l = r + k;
                    r += k;
                    copy = new HashMap<>(counter);
                    missing = m;
                } else {
                    // 先把当前 word 计入 dictionary
                    copy.put(word, copy.get(word) - 1);
                    // 注意！！！只有 count >= 0，才对减少 missing 有贡献，否则就是冗余值！
                    if (copy.get(word) >= 0) missing -= 1;
                    // 更新 sliding window，使得 window size < m x k
                    if (r + k - l > m * k) {
                        String head = s.substring(l, l + k);
                        copy.put(head, copy.get(head) + 1);
                        // 注意！！！只有 count > 0，才会使得 missing 增加，否则就是移除了冗余值！
                        if (copy.get(head) > 0) missing += 1;
                        l += k;
                    }
                    if (missing == 0) {
                        res.add(l);
                    }
                    r += k;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SubstringWithConcatenationOfAllWords solution = new SubstringWithConcatenationOfAllWords();
        System.out.println(solution.findSubstring("barfoothefoobarman", new String[]{"foo","bar"})); // [0, 9]
        System.out.println(solution.findSubstring("wordgoodgoodgoodbestword", new String[]{"foo","bar"})); // []
        System.out.println(solution.findSubstring("barfoofoobarthefoobarman", new String[]{"bar","foo","the"})); // [6, 9, 12]
        System.out.println(solution.findSubstring("aaaaaa", new String[]{"aa"})); // [0, 2, 4, 1, 3]
    }

}
