import java.util.Arrays;

/**
 * 238. Product of Array Except Self (https://leetcode.com/problems/product-of-array-except-self/description/)
 */
public class ProductOfArrayExceptSelf {

    /************* Solution 1: Left & Right Product Prefix ***************/
    /**
     * left[i] 表示 nums[0:i] inclusive 的乘积
     * right[i] 表示 nums[i:] inclusive 的乘积
     * answer[i] = left[i-1] * right[i+1]
     *
     * Time: O(N)  Space: O(N)
     */
    public int[] productExceptSelf1(int[] nums) {
        int l = nums.length;
        int[] left = new int[l], right = new int[l];
        for (int i = 0; i < l; i++) {
            left[i] = (i == 0 ? 1 : left[i - 1]) * nums[i];
            right[l - i - 1] = (i == 0 ? 1 : right[l - i]) * nums[l - i - 1];
        }
        int[] answer = new int[l];
        for (int i = 0; i < l; i++) {
            answer[i] = (i == 0 ? 1 : left[i - 1]) * (i == l - 1 ? 1 : right[i + 1]);
        }
        return answer;
    }

    /************* Solution 2: calculate prefix product as array X carry suffix product as integer ***************/
    /**
     * 第一遍从左到右，answer[i] 表示 nums[0:i] exclusive 乘积, = answer[i-1] x nums[i-1]
     * 第二遍从右到左，answer[i] 为最终解，= answer[i] * suffixProduct
     * suffixProduct[i] 表示 nums[i:] exclusive 乘积，suffixProduct[i] = suffixProduct[i-1] x nums[i-1]
     *
     * suffixProduct 没必要 array 表示了，carry 着走，并直接计算进结果即可！！！
     *
     * Time: O(N)  Space: O(1)
     */
    public int[] productExceptSelf(int[] nums) {
        int l = nums.length;
        int[] answer = new int[l];
        answer[0] = 1;
        for (int i = 1; i < l; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        int suffixProduct = 1;
        for (int i = l - 1; i >= 0; i--) {
            answer[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }
        return answer;
    }

    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();

        // [24, 12, 8, 6]
        System.out.println(Arrays.toString(solution.productExceptSelf(new int[]{1,2,3,4})));

        // [0, 0, 9, 0, 0]
        System.out.println(Arrays.toString(solution.productExceptSelf(new int[]{-1,1,0,-3,3})));
    }
}
