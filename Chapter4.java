import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Chapter4 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String[] projects = new String[] {"a", "b", "c", "d", "e", "f"};
        String[][] dependencies = new String[][] {{"d", "a"}, {"b", "f"}, {"d", "b"}, {"a", "f"}, {"c", "d"}};
        System.out.println(Arrays.toString(problem7(projects, dependencies)));
        TreeNode tn1 = new TreeNode(1);
        TreeNode tn2 = new TreeNode(2);
        TreeNode tn3 = new TreeNode(3);
        tn2.left = tn1;
        tn2.right = tn3;
        List<LinkedList<Integer>> list = problem9(tn2);
        System.out.println(list.toString());
    }
    
    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {this.val = val;}
    }
    
    public static class Node {
        int val;
        List<Node> next;
        Node(int val) {
            this.val = val;
            next = new ArrayList<Node>();
        }
    }
    
    public static class Project {
        String s;
        int d;
        Set<Project> next;
        Project(String s) {
            this.s = s;
            next = new HashSet<Project>();
            d = 0;
        }
    }

    public static boolean problem1DFS(Node a, Node b) {
        if (a == null || b == null) return false;
        if (a == b) return true;
        else {
            for (Node nextNode : a.next) {
                if (problem1DFS(nextNode, b)) return true;
            }
        }
        return false;
    }

    public static boolean problem1BFS(Node a, Node b) {
        if (a == null || b == null) return false;
        Queue<Node> queue = new LinkedList<Node>();
        Set<Node> visited = new HashSet<Node>();
        queue.offer(a);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            if (temp == b) return true;
            for (Node nextNode : a.next) {
                if (visited.add(nextNode)) {
                    queue.offer(nextNode);
                }
            }
        }
        return false;
    }

    public static TreeNode problem2(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        return problem2Helper(arr, 0, arr.length - 1);
    }
    
    private static TreeNode problem2Helper(int[] arr, int left, int right) {
        if (right < left) return null;
        else {
            int mid = left + (right - left) / 2;
            TreeNode res = new TreeNode(arr[mid]);
            res.left = problem2Helper(arr, left, mid - 1);
            res.right = problem2Helper(arr, mid + 1, right);
            return res;
        }
    }

    public static List<LinkedList<TreeNode>> problem3DFS(TreeNode root) {
        List<LinkedList<TreeNode>> res = new ArrayList<LinkedList<TreeNode>>();
        if (root == null) return res;
        problem3DFSHelper(root, 0, res);
        return res;
    }
    
    public static void problem3DFSHelper(TreeNode root, int level, List<LinkedList<TreeNode>> res) {
        if (root == null) return;
        else {
            if (level >= res.size()) {
                LinkedList<TreeNode> ll = new LinkedList<TreeNode>();
                res.add(ll);
            }
            res.get(level).add(root);
            problem3DFSHelper(root.left, level + 1, res);
            problem3DFSHelper(root.right, level + 1, res);
        }
    }

    public static List<LinkedList<TreeNode>> problem3BFS(TreeNode root) {
        List<LinkedList<TreeNode>> res = new ArrayList<LinkedList<TreeNode>>();
        if (root == null) return res;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        TreeNode node = null;
        while (!queue.isEmpty()) {
            LinkedList<TreeNode> ll = new LinkedList<TreeNode>();
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(ll);
        }
        return res;
    }

    public static boolean problem4(TreeNode root) {
        return getHeight(root) != -1;
    }
    
    private static int getHeight(TreeNode root) {
        if (root == null) return 0;
        int left = getHeight(root.left);
        int right = getHeight(root.right);
        if (left == -1 || right == -1) return -1;
        if (Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }
    
    public static boolean problem5(TreeNode root) {
        return problem5(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private static boolean problem5(TreeNode root, int min, int max) {
        if (root == null) return true;
        if (root.val < min || root.val > max) return false;
        return problem5(root.left, min, root.val) && problem5(root.right, root.val, max);
    }
    
    public static TreeNode problem6(TreeNode root, TreeNode target) {
        if (root == null || target == null) return null;
        if (target.right != null) return target.right;
        TreeNode node = root;
        while (node.val < target.val) node = node.right;
        TreeNode res = null;
        while (node != target) {
            res = node;
            while (node.val > target.val) node = node.left;
            while (node.val < target.val) node = node.right;
        }
        return res;
    }

    public static String[] problem7(String[] projects, String[][] dependencies) {
        if (projects == null || projects.length == 0) return new String[0];
        if (dependencies == null || dependencies.length == 0 
                || dependencies[0] == null || dependencies[0].length == 0) return projects;
        Map<String, Project> nodes = new HashMap<String, Project>();
        for (String p : projects) {
            Project project = new Project(p);
            nodes.put(p, project);
        }
        for (String[] dep : dependencies) {
            nodes.get(dep[1]).next.add(nodes.get(dep[0]));
            nodes.get(dep[0]).d++;
        }
        Queue<Project> queue = new LinkedList<Project>();
        String[] res = new String[projects.length];
        int index = 0;
        for (String p : nodes.keySet()) {
            if (nodes.get(p).d == 0) {
                queue.offer(nodes.get(p));
            }
        }
        while (!queue.isEmpty()) {
            Project temp = queue.poll();
            res[index++] = temp.s;
            for (Project next : temp.next) {
                if (--next.d == 0) queue.offer(next);
            }
        }
        return index == res.length ? res : null;
    }
    
    public static TreeNode problem8BST(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) return null;
        if (root.val <= b.val && root.val >= a.val) return root;
        if (root.val > b.val) return problem8BST(root.left, a, b);
        else return problem8BST(root.right, a, b);
    }
    
    public static TreeNode problem8BT(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null || root == a || root == b) return root;
        TreeNode left = problem8BT(root.left, a, b);
        TreeNode right = problem8BT(root.right, a, b);
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

    public static List<LinkedList<Integer>> problem9(TreeNode root) {
        List<LinkedList<Integer>> res = new ArrayList<LinkedList<Integer>>();
        if (root == null) {
            res.add(new LinkedList<Integer>());
            return res;
        }
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(root.val);
        List<LinkedList<Integer>> leftSeq = problem9(root.left);
        List<LinkedList<Integer>> rightSeq = problem9(root.right);
        for (LinkedList<Integer> left : leftSeq) {
            for (LinkedList<Integer> right : rightSeq) {
                List<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
                problem9DFSHelper(left, right, weaved, list);
                res.addAll(weaved);
            }
        }
        return res;
    }
    
    private static void problem9DFSHelper(LinkedList<Integer> left, LinkedList<Integer> right, 
            List<LinkedList<Integer>> res, LinkedList<Integer> list) {
        if (left.size() == 0 || right.size() == 0) {
            LinkedList<Integer> result = (LinkedList<Integer>) list.clone();
            result.addAll(left);
            result.addAll(right);
            res.add(result);
            return;
        }
        else {
            int headFirst = left.removeFirst();
            list.addLast(headFirst);
            problem9DFSHelper(left, right, res, list);
            list.removeLast();
            left.addFirst(headFirst);
            
            int headSecond = right.removeFirst();
            list.addLast(headSecond);
            problem9DFSHelper(left, right, res, list);
            list.removeLast();
            right.addFirst(headSecond);
        }
    }
    
    public static boolean problem10(TreeNode t1, TreeNode t2) {
        if (t2 == null) return true;
        if (t1 == null) return false;
        if (checkIdentical(t1, t2)) return true;
        return problem10(t1.left, t2) || problem10(t1.right, t2);
    }
    
    private static boolean checkIdentical(TreeNode t1, TreeNode t2) {
        if (t2 == null && t1 == null) return true;
        if (t2 == null || t1 == null || t1.val != t2.val) return false;
        return checkIdentical(t1.left, t2.left) && checkIdentical(t1.right, t2.right);
    }

    private static class RandomTreeNode {
        int val, size;
        RandomTreeNode left, right;
        public RandomTreeNode(int val) {
            this.val = val;
            size = 1;
        }
        
        public RandomTreeNode getIthNode(int i) {
            int leftSize = left == null ? 0 : left.size;
            if (i == leftSize) return this;
            if (i < leftSize) return left.getIthNode(i);
            return right.getIthNode(i - leftSize - 1);
        }
    }
    
    public static class RandomTree {
        private final Random random;
        private RandomTreeNode root;
        public RandomTree() {
            random = new Random();
            root = null;
        }
                
        public RandomTreeNode find(int val) {
            RandomTreeNode node = root;
            while (node != null) {
                if (node.val == val) return node;
                if (node.val < val) node = node.right;
                else node = node.left;
            }
            return node;
        }
        
        public void insert(int val) {
            if (root == null) {
                root = new RandomTreeNode(val);
            }
            else {
                RandomTreeNode prev = null, node = root;
                while (node != null) {
                    prev = node;
                    node.size++;
                    node = val > node.val ? node.right : node.left;
                }
                if (val > prev.val) prev.right = new RandomTreeNode(val);
                else prev.left = new RandomTreeNode(val);
            }
        }
        
        public RandomTreeNode getRandomNode() {
            if (root == null) return null;
            int index = random.nextInt(root.size);
            return root.getIthNode(index);
        }
    }

    private static int res = 0;
    public static int problem12(TreeNode root, int target) {
        problem12DFSHelper(root, target, new HashMap<Integer, Integer>(), 0);     
        return res;
    }
    
    private static void problem12DFSHelper(TreeNode root, int target, Map<Integer, Integer> map, int prev) {
        if (root == null) return;
        else {
            int sum = prev + root.val;
            Integer count = map.get(sum - target);
            if (count != null) res += count;
            Integer temp = map.get(sum);
            if (temp == null) map.put(sum,  1);
            else map.put(sum, temp + 1);
            problem12DFSHelper(root.left, target, map, sum);
            problem12DFSHelper(root.right, target, map, sum);
        }
    }
}
