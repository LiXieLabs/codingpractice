import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 353. Design Snake Game (https://leetcode.com/problems/design-snake-game/description/)
 */
public class DesignSnakeGame {

    /************************** Solution 1: 2D存储 ************************/
//    private static final Map<String, int[]> direc;
//
//    static {
//        direc = new HashMap<>();
//        direc.put("R", new int[]{0, 1});
//        direc.put("L", new int[]{0, -1});
//        direc.put("U", new int[]{-1, 0});
//        direc.put("D", new int[]{1, 0});
//    }
//
//    boolean[][] occupied;
//    Deque<int[]> snake;
//    int i;
//    int[][] foods;
//
//    public DesignSnakeGame(int width, int height, int[][] food) {
//        occupied = new boolean[height][width];
//        occupied[0][0] = true;
//        i = 0;
//
//        snake = new ArrayDeque<>();
//        snake.offer(new int[]{0, 0});
//
//        foods = food;
//    }
//
//    public int move(String direction) {
//        int[] head = snake.peekLast();
//        int[] tail = snake.peek();
//        int[] d = direc.get(direction);
//        int[] newHead = new int[]{head[0] + d[0], head[1] + d[1]};
//        // 检查溢出边界和自身碰撞
//        if (newHead[0] < 0 || newHead[0] >= occupied.length || newHead[1] < 0 || newHead[1] >= occupied[0].length ||
//                (occupied[newHead[0]][newHead[1]] && (tail[0] != newHead[0] || tail[1] != newHead[1]))) {
//            return -1;
//        }
//        if (i < foods.length && foods[i][0] == newHead[0] && foods[i][1] == newHead[1]) {
//            i++;
//        } else {
//            snake.poll();
//            occupied[tail[0]][tail[1]] = false;
//        }
//        snake.offer(newHead);
//        occupied[newHead[0]][newHead[1]] = true;
//        return snake.size() - 1;
//    }

    /**************************** Solution 2: 1D存储 ******************************/
    private static final Map<String, int[]> map;
    static {
        map = new HashMap<>();
        map.put("R", new int[]{0, 1});
        map.put("L", new int[]{0, -1});
        map.put("U", new int[]{-1, 0});
        map.put("D", new int[]{1, 0});
    }

    private Deque<Integer> snake;
    private Set<Integer> body;
    private int r, c;
    private int[] food;
    private int fp; // food pointer

    public DesignSnakeGame(int width, int height, int[][] food) {
        this.r = height;
        this.c = width;
        this.snake = new ArrayDeque<>();
        this.snake.offer(0);
        this.body = new HashSet<>();
        this.food = new int[food.length];
        int i = 0;
        for (int[] f : food) {
            this.food[i++] = f[0] * c + f[1];
        }
        this.fp = 0;
    }

    public int move(String direction) {
        int[] d = map.get(direction);
        int head = snake.peekLast();
        int i = head / c;
        int j = head % c;
        int ni = i + d[0];
        int nj = j + d[1];
        if (ni < 0 || ni >= r || nj < 0 || nj >= c) return -1;
        int next = ni * c + nj;
        if (fp < food.length && next == food[fp]) { // 该 move 吃到 food，得分！
            body.add(next);
            snake.offer(next);
            fp++;
        } else { // 该 move 没吃到 food，不得分。
            body.remove(snake.poll());
            if (!body.add(next)) return -1;
            snake.offer(next);
        }
        return fp;
    }

    public static void main(String[] args) {
        DesignSnakeGame solution = new DesignSnakeGame(3, 2, new int[][]{{1, 2}, {0, 1}});
        System.out.println(solution.move("R")); // 0
        System.out.println(solution.move("D")); // 0
        System.out.println(solution.move("R")); // 1
        System.out.println(solution.move("U")); // 1
        System.out.println(solution.move("L")); // 2
        System.out.println(solution.move("U")); // -1
    }
}
