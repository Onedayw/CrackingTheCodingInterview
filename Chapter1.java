import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Chapter1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(problem1("abca"));
        System.out.println(problem2("abc", "adb"));
        System.out.println(problem3("Mr John Smith     ", 13));
        System.out.println(problem4("tacT    coA"));
        System.out.println(problem5("pale", "bake"));
        System.out.println(problem6("aabbbccaa"));
        int[][] matrix;
        matrix = problem7(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
        matrix = problem8(new int[][]{{1, 1, 1}, {1, 0, 0}, {1, 1, 1}});
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
        System.out.println(problem9("waterbottle", "erbottlewat"));
    }
       
    
    public static boolean problem1(String s) {
        if (s == null || s.length() == 0) return true;
        int letters = 0, n = s.length(), mask;
        for (int i = 0; i < n; i++) {
            mask = 1 << (s.charAt(i) - 'a');
            if ((letters & mask) != 0) return false;
            letters |= mask;
        }
        return true;
    }

    public static boolean problem2(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null || s1.length() != s2.length()) return false;
        int[] letters = new int[26];
        int n = s1.length();
        for (int i = 0; i < n; i++) {
            letters[s1.charAt(i) - 'a']++;
            letters[s2.charAt(i) - 'a']--;
        }
        for (int letter : letters) {
            if (letter != 0) return false;
        }
        return true;
    }

    public static String problem3(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) return "";
        int numOfSpaces = 0;
        for (int i = 0; i < k; i++) {
            if (s.charAt(i) == ' ') numOfSpaces++;
        }
        char[] res = new char[k + 2 * numOfSpaces];
        int index = res.length - 1;
        for (int i = k - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                res[index--] = '0';
                res[index--] = '2';
                res[index--] = '%';
            }
            else res[index--] = s.charAt(i);
        }
        return new String(res);
    }

    public static boolean problem4(String s) {
        if (s == null || s.length() == 0) return true;
        int letters = 0, n = s.length(), mask;
        char c;
        for (int i = 0; i < n; i++) {
            c = s.charAt(i);
            if (c != ' ') {
                if (c > 'Z') c -= 'a' - 'A';
                mask = 1 << (s.charAt(i) - 'A');
                letters ^= mask;
            }
        }
        boolean flag = false;
        for (int i = 0; i < 32; i++) {
            mask = 1 << i;
            if ((letters & mask) != 0) {
                if (flag) return false;
                flag = true;
            }
        }
        return true;
    }

    public static boolean problem5(String a, String b) {
        if (isInsert(a, b)) return true;
        if (a.length() != b.length()) return false;
        int index = 0;
        boolean flag = false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(index)) {
                if (flag) return false;
                flag = true;
            }
            index++;
        }
        return true;
    }
    
    private static boolean isInsert(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null) return b.length() < 2;
        if (b.length() - a.length() > 1) return false;
        if (b.length() == a.length()) return a.equals(b);
        if (b.length() < a.length()) return isInsert(b, a);
        int index = 0;
        boolean flag = false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(index)) {
                if (flag) return false;
                flag = true;
                i--;
            }
            index++;
        }
        return true;
    }

    public static String problem6(String s) {
        if (s == null || s.length() == 0) return "";
        int count = 1, n = s.length(), i = 0;
        char curr, next;
        StringBuilder sb = new StringBuilder();
        while (i < n) {
            curr = s.charAt(i);
            if (i + 1 == n || s.charAt(i + 1) != curr) {
                sb.append(curr);
                sb.append(count);
                count = 0;
            }
            count++;
            i++;
        }
        return sb.length() < n ? sb.toString() : s;
    }

    public static int[][] problem7(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || 
                matrix[0] == null || matrix[0].length == 0)
            return matrix;
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) rotate(matrix, i, n-2*i);
        return matrix;
    }
    
    private static int[][] rotate(int[][] matrix, int level, int width) {
        if (width < 2) return matrix;
        int temp, n = matrix.length;
        for (int i = 0; i < width - 1; i++) {
            temp = matrix[level][level+i];
            matrix[level][level+i] = matrix[n-1-level-i][level];
            matrix[n-1-level-i][level] = matrix[n-1-level][n-1-level-i];
            matrix[n-1-level][n-1-level-i] = matrix[level+i][n-1-level];
            matrix[level+i][n-1-level] = temp;
        }
        return matrix;
    }

    public static int[][] problem8(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || 
                matrix[0] == null || matrix[0].length == 0)
            return matrix;
        int nrow = matrix.length, ncol = matrix[0].length;
        Set<Integer> set = new HashSet<Integer>();
        boolean flag = false;
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (matrix[i][j] == 0) {
                    flag = true;
                    set.add(j);
                }
            }
            if (flag) {
                for (int j = 0; j < ncol; j++) matrix[i][j] = 0;
            }
            flag = false;
        }
        for (int i = 0; i < nrow; i++) {
            for (int j : set) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    public static boolean problem9(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        String c = a + a;
        return isSubstring(c, b);
    }
    
    private static boolean isSubstring(String c, String b) {
        int clen = c.length(), blen = b.length();
        for (int i = 0; i + blen <= clen; i++) {
            for (int j = 0; j < blen; j++) {
                if (c.charAt(i+j) != b.charAt(j)) break;
                if (j == blen - 1) return true;
            }
        }
        return false;
    }
}
