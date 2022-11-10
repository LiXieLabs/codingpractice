public class MaximumPointsYouCanObtainFromCards {

    /******************** Solution 1: Recursive Brute-Force O(2^k) **********************/

    private int result = 0;
    private int[] cardPoints;

    public int maxScore(int[] cardPoints, int k) {
        this.result = 0;
        this.cardPoints = cardPoints;
        recur(0, cardPoints.length - 1, k, 0);
        return this.result;
    }

    public void recur(int l, int r, int k, int total) {
        if (k == 0) {
            this.result = Math.max(this.result, total);
            return;
        }
        recur(l + 1, r, k - 1, total + this.cardPoints[l]);
        recur(l, r - 1, k - 1, total + this.cardPoints[r]);
    }

    /******************** Solution 2: Sliding Window 求 sub-array min sum ***********************/
    public int maxScore2(int[] cardPoints, int k) {
        int total = 0, minWindowSum = Integer.MAX_VALUE, curWindowSum = 0, window = cardPoints.length - k;
        for (int i = 0; i < cardPoints.length; i++) {
            total += cardPoints[i];
            curWindowSum += cardPoints[i];
            if (i - window >= 0) curWindowSum -= cardPoints[i - window];
            if (i >= window - 1) minWindowSum = Math.min(minWindowSum, curWindowSum);
        }
        return total - minWindowSum;
    }

    public static void main(String[] args) {
        MaximumPointsYouCanObtainFromCards solution = new MaximumPointsYouCanObtainFromCards();
        System.out.println(solution.maxScore2(new int[]{1,2,3,4,5,6,1}, 3));
        System.out.println(solution.maxScore2(new int[]{2,2,2}, 2));
        System.out.println(solution.maxScore2(new int[]{9,7,7,9,7,7,9}, 7));
    }

}
