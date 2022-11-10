import java.util.HashMap;
import java.util.Map;

public class DotProductOfTwoSparseVectors {

    static class SparseVector {

        Map<Integer, Integer> map;

        SparseVector(int[] nums) {
            map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    map.put(i, nums[i]);
                }
            }
        }

        // Return the dotProduct of two sparse vectors
        public int dotProduct(SparseVector vec) {
            int res = 0;
            Map<Integer, Integer> smaller = vec.map.size() < this.map.size() ? vec.map : this.map;
            Map<Integer, Integer> larger = vec.map.size() >= this.map.size() ? vec.map : this.map;
            for (Map.Entry<Integer, Integer> e : smaller.entrySet()) {
                res += larger.getOrDefault(e.getKey(), 0) * e.getValue();
            }
            return res;
        }
    }

    public static void main(String[] args) {
        SparseVector sv1 = new SparseVector(new int[]{1, 0, 0, 2, 3});
        SparseVector sv2 = new SparseVector(new int[]{0, 3, 0, 4, 0});
        System.out.println(sv1.dotProduct(sv2));

        SparseVector sv3 = new SparseVector(new int[]{0,1,0,0,0});
        SparseVector sv4 = new SparseVector(new int[]{0,0,0,0,2});
        System.out.println(sv3.dotProduct(sv4));

        SparseVector sv5 = new SparseVector(new int[]{0,1,0,0,2,0,0});
        SparseVector sv6 = new SparseVector(new int[]{1,0,0,0,3,0,4});
        System.out.println(sv5.dotProduct(sv6));
    }

}
