import java.util.ArrayList;
import java.util.List;

public class ProductOfTheLastKNumbers {

    /************ Solution 1: Prefix Products ****************/
    /**
     * 1. products[i] 记录由开始到 nums[i] 的 prefix products
     *    则 getProduct(k) = products[count-1] / products[count-k-1]
     * 2. 一旦 x 点出现 0，则包含和越过 x 位置的 k 的 product都是 0
     *    可以将 prefix products 重置，舍弃记录前面的值
     * 3. prefix products 需要一个（1）可扩展 && （2）可以index索引查找 的结构 => ArrayList
     */

    int count;
    List<Integer> products;

    public ProductOfTheLastKNumbers() {
        count = 0;
        products = new ArrayList<>();
        products.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            products = new ArrayList<>();
            products.add(1);
        } else {
            products.add(products.get(products.size() - 1) * num);
        }
        count++;
    }

    public int getProduct(int k) {
        int n = products.size();
        // n 正好是从末尾由1开始数 lastZero 的位置，故 k == n 就刚好会把 0 包含进去
        return k < n ? products.get(n - 1) / products.get(n - k - 1) : 0;

    }

    public static void main(String[] args) {
        ProductOfTheLastKNumbers solution = new ProductOfTheLastKNumbers();
        solution.add(3);
        solution.add(0);
        solution.add(2);
        solution.add(5);
        solution.add(4);
        System.out.println(solution.getProduct(2));
        System.out.println(solution.getProduct(3));
        System.out.println(solution.getProduct(4));
        solution.add(8);
        System.out.println(solution.getProduct(2));

    }
}
