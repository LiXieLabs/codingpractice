import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
     Set<Integer> occupied;
     Deque<Integer> snake;
     int i;
     int r;
     int c;
     int[][] foods;

     public DesignSnakeGame(int width, int height, int[][] food) {
        occupied = new HashSet<>();
        occupied.add(0);

        snake = new ArrayDeque<>();
        snake.offer(0);

        r = height;
        c = width;

        foods = food;
        i = 0;
     }

     public int move(String direction) {
         int headr = snake.peekLast() / c;
         int headc = snake.peekLast() % c;
         switch (direction) {
             case "U": headr--; break;
             case "D": headr++; break;
             case "L": headc--; break;
             default: headc++;
         }
         int newHead = headr * c + headc;
         int tail = snake.peek();
         if (headr < 0 || headr == r || headc < 0 || headc == c || occupied.contains(newHead) && tail != newHead) {
             return -1;
         }
         if (i < foods.length && foods[i][0] == headr && foods[i][1] == headc) {
             i++;
         } else {
             snake.poll();
             occupied.remove(tail);
         }
         snake.offer(newHead);
         occupied.add(newHead);
         return snake.size() - 1;
     }

    public static void main(String[] args) {
        DesignSnakeGame solution = new DesignSnakeGame(3, 2, new int[][]{{1, 2}, {0, 1}});
        System.out.println(solution.move("R"));
        System.out.println(solution.move("D"));
        System.out.println(solution.move("R"));
        System.out.println(solution.move("U"));
        System.out.println(solution.move("L"));
        System.out.println(solution.move("U"));
    }
}
