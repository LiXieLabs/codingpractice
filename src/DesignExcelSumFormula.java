import java.util.HashMap;
import java.util.Map;

public class DesignExcelSumFormula {

    Cell[][] matrix;

    public DesignExcelSumFormula(int height, char width) {
        matrix = new Cell[height][width - 'A' + 1];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 'A' + 1; j++) {
                matrix[i][j] = new Cell(0);
            }
        }
    }

    public void set(int row, char column, int val) {
        matrix[row - 1][column - 'A'].setValue(val);
    }

    public int get(int row, char column) {
        return matrix[row - 1][column - 'A'].getValue();
    }

    public int sum(int row, char column, String[] numbers) {
        matrix[row - 1][column - 'A'].setFormula(numbers);
        return matrix[row - 1][column - 'A'].getValue();
    }

    private class Cell {

        int v;
        Map<Cell, Integer> formula;

        public Cell(int v) {
            setValue(v);
        }

        public Cell(String[] formulaStr) {
            setFormula(formulaStr);
        }

        public void setValue(int v) {
            formula = null;
            this.v = v;
        }

        public void setFormula(String[] formulaStr) {
            formula = new HashMap<>();
            for (String f : formulaStr) {
                parseFormula(f);
            }
        }

        private void parseFormula(String formula) {
            String[] pairs = formula.split(":");
            if (pairs.length == 1) {
                addCell(parsePair(pairs[0]));
            } else {
                int[] s = parsePair(pairs[0]);
                int[] e = parsePair(pairs[1]);
                for (int r = s[0]; r <= e[0]; r++) {
                    for (int c = s[1]; c <= e[1]; c++) {
                        addCell(new int[]{r, c});
                    }
                }
            }
        }

        private int[] parsePair(String pair) {
            int r = Integer.valueOf(pair.substring(1)) - 1;
            int c = pair.charAt(0) - 'A';
            return new int[]{r, c};
        }

        private void addCell(int[] cellPos) {
            int r = cellPos[0], c = cellPos[1];
            if (matrix[r][c] == null) matrix[r][c] = new Cell(0);
            formula.put(matrix[r][c], formula.getOrDefault(matrix[r][c], 0) + 1);
        }

        public int getValue() {
            if (formula == null) return v;
            int res = 0;
            for (Cell c : formula.keySet()) {
                res += c.getValue() * formula.get(c);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        DesignExcelSumFormula solution = new DesignExcelSumFormula(3, 'C');
        solution.set(1, 'A', 2);
        System.out.println(solution.sum(3, 'C', new String[]{"A1", "A1:B2"}));
        solution.set(2, 'B', 2);
        System.out.println(solution.get(3, 'C'));
        solution.set(1, 'A', 10);
        System.out.println(solution.get(3, 'C'));
    }
}
