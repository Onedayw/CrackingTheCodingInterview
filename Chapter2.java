import java.util.HashSet;
import java.util.Stack;

public class Chapter2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ListNode node1 = new ListNode(new int[]{1, 2, 3, 2, 3, 2, 3, 1});
        problem1(node1).print();
        ListNode node1FollowUp = new ListNode(new int[]{1, 2, 3, 2, 3, 2, 3, 1});
        problem1FollowUp(node1FollowUp).print();
        ListNode node2 = new ListNode(new int[]{1, 2, 3, 2, 3, 2, 3, 1});
        problem2(node2, 5).print();
        ListNode node3 = new ListNode(new int[]{1, 2, 3, 2, 3, 2, 3, 1, 5});
        if (problem3(findMiddle(node3))) node3.print();
        ListNode node4 = new ListNode(new int[]{1, 2, 3, 2, 3, 2, 3, 1, 5});
        problem4(node4, 3).print();
        ListNode node5_1 = new ListNode(new int[]{1, 6});
        ListNode node5_2 = new ListNode(new int[]{5, 9, 9, 9, 9});
        problem5_Iterative(node5_1, node5_2).print();
        problem5_Recursive(node5_1, node5_2).print();
        problem5FollowUp(node5_1, node5_2).print();
        ListNode node6 = new ListNode(new int[]{1, 2, 3, 4, 2, 1});
        System.out.println(problem6(node6));
    }
    
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {this.val = val;}
        ListNode(int[] arr) {
            this.val = arr[0];
            int n = arr.length;
            ListNode ptr = this;
            for (int i = 1; i < n; i++) {
                ptr.next = new ListNode(arr[i]);
                ptr = ptr.next;
            }
        }
        void print() {
            ListNode node = this;
            while (node != null) {
                System.out.print(node.val);
                System.out.print(' ');
                node = node.next;
            }
            System.out.println();
        }
    }
    
    public static ListNode problem1(ListNode head) {
        if (head == null || head.next == null) return head;
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(head.val);
        ListNode node = head.next, prev = head;
        while (node != null) {
            if (set.add(node.val)) {
                prev = prev.next;
            }
            else {
                prev.next = node.next;
            }
            node = node.next;
        }
        return head;
    }
    
    public static ListNode problem1FollowUp(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode node = head;
        while (node != null) {
            int val = node.val;
            ListNode ptr = node.next, prev = node;
            while (ptr != null) {
                if (ptr.val == val) {
                    prev.next = ptr.next;
                }
                else {
                    prev = prev.next;
                }
                ptr = ptr.next;
            }
            node = node.next;
        }
        return head;
    }
    
    public static ListNode problem2(ListNode head, int k) {
        ListNode slow = head, fast = head;
        for (int i = 0; i < k - 1; i++) {
            if (fast == null) return null;
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    
    public static ListNode findMiddle(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    public static boolean problem3(ListNode node) {
        if (node == null || node.next == null) return false;
        node.val = node.next.val;
        node.next = node.next.next;
        return true;
    }
    
    public static ListNode problem4(ListNode head, int x) {
        if (head == null || head.next == null) return head;
        ListNode small = new ListNode(0);
        ListNode large = new ListNode(0);
        ListNode node = head, smallNode = small, largeNode = large;
        while (node != null) {
            if (node.val < x) {
                smallNode.next = node;
                smallNode = smallNode.next;
            }
            else {
                largeNode.next = node;
                largeNode = largeNode.next;
            }
            node = node.next;
        }
        largeNode.next = null;
        smallNode.next = large.next;
        return small.next;
    }

    public static ListNode problem5_Iterative(ListNode node1, ListNode node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        boolean carry = false;
        while (node1 != null && node2 != null) {
            int val = node1.val + node2.val + (carry ? 1 : 0);
            if (val > 9) {
                carry = true;
                val -= 10;
            }
            else {
                carry = false;
            }
            node.next = new ListNode(val);
            node = node.next;
            node1 = node1.next;
            node2 = node2.next;
        }
        if (node1 == null) {
            if (carry) {
                node.next = problem5_Iterative(new ListNode(1), node2);
            }
            else {
                node.next = node2;
            }
        }
        else {
            if (carry) {
                node.next = problem5_Iterative(node1, new ListNode(1));
            }
            else {
                node.next = node1;
            }
        }
        return dummy.next;
    }
    
    public static ListNode problem5_Recursive(ListNode node1, ListNode node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;
        ListNode next = problem5_Recursive(node1.next, node2.next);
        int val = node1.val + node2.val;
        if (val > 9) {
            next = problem5_Recursive(next, new ListNode(1));
        }
        ListNode res = new ListNode(val % 10);
        res.next = next;
        return res;
    }
        
    public static boolean problem5FollowUpHelper(ListNode node1, ListNode node2, int n) {
        if (n > 0) {
            if (problem5FollowUpHelper(node1, node2.next, n-1)) {
                node2.val++;
                if (node2.val == 10) {
                    node2.val = 0;
                    return true;
                }
            }
            return false;
        }
        if (node1 == null || node2 == null) return false;
        boolean carry = problem5FollowUpHelper(node1.next, node2.next, 0);
        int val = node1.val + node2.val + (carry ? 1 : 0);
        node2.val = val % 10;
        return val > 9;
    }
    
    public static ListNode problem5FollowUp(ListNode node1, ListNode node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;
        int n1 = 0, n2 = 0;
        ListNode node = node1;
        while (node != null) {
            n1++;
            node = node.next;
        }
        node = node2;
        while (node != null) {
            n2++;
            node = node.next;
        }
        if (n1 > n2) return problem5FollowUp(node2, node1);
        ListNode dummy = new ListNode(1);
        dummy.next = node2;
        node = dummy;
        if (problem5FollowUpHelper(node1, node.next, n2 - n1)) {
            return dummy;
        }
        return dummy.next;
    }
    
    public static boolean problem6(ListNode head) {
        int count = 0;
        ListNode node = head;
        while (node != null) {
            count++;
            node = node.next;
        }
        Stack<Integer> stack = new Stack<Integer>();
        node = head;
        for (int i = 0; i < count / 2; i++) {
            stack.push(node.val);
            node = node.next;
        }
        if (count % 2 == 1) node = node.next;
        while (!stack.isEmpty()) {
            if (node.val != stack.pop()) return false;
            node = node.next;
        }
        return true;
    }
    
    public static ListNode problem7(ListNode node1, ListNode node2) {
        if (node1 == null || node2 == null) return null;
        ListNode node = node1;
        int n1 = 0, n2 = 0;
        while (node != null) {
            n1++;
            node = node.next;
        }
        node = node2;
        while (node != null) {
            n2++;
            node = node.next;
        }
        if (n1 > n2) {
            for (int i = 0; i < n1 - n2; i++) {
                node1 = node1.next;
            }
        }
        if (n2 > n1) {
            for (int i = 0; i < n1 - n2; i++) {
                node2 = node2.next;
            }
        }
        while (node1 != null) {
            if (node1 == node2) return node1;
            node1 = node1.next;
            node2 = node2.next;
        }
        return null;
    }

    public static ListNode problem8(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode fast = head.next.next, slow = head.next;
        while (fast != slow) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
        }
        while (head != slow) {
            head = head.next;
            slow = slow.next;
        }
        return head;
    }
    
}
