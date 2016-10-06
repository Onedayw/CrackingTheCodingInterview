import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Chapter3 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int[] arr = new int[]{1, 4, 3, 2};
        Stack<Integer> stack = new Stack<Integer>();
        for (int num : arr) stack.add(num);
        sortStack(stack);
        System.out.println(stack.toString());
    }
    
    public static class My3Stack<T> {
        private T[] arr;
        private int index1, index2, index3;
        private final int capacity;
        public My3Stack(int capacity) {
            arr = (T[])new Object[capacity * 3];
            index1 = 0;
            index2 = capacity;
            index3 = capacity * 2;
            this.capacity = capacity;
        }
        
        public int getSize1() {return index1;}
        public int getSize2() {return index2 - capacity;}
        public int getSize3() {return index3 - 2 * capacity;}
        public boolean push1(T element) {
            if (index1 < capacity) {
                arr[index1++] = element;
                return true;
            }
            return false;
        }
        public boolean push2(T element) {
            if (index2 < 2 * capacity) {
                arr[index2++] = element;
                return true;
            }
            return false;
        }public boolean push3(T element) {
            if (index3 < 3 * capacity) {
                arr[index3++] = element;
                return true;
            }
            return false;
        }
        public T pop1() {
            if (index1 <= 0) {
                index1 = 0;
                return null;
            }
            return arr[index1--];
        }
        public T pop2() {
            if (index2 <= capacity) {
                index2 = 0;
                return null;
            }
            return arr[index2--];
        }
        public T pop3() {
            if (index3 <= 2 * capacity) {
                index3 = 0;
                return null;
            }
            return arr[index3--];
        }
        public T peek1() {return arr[index1];}
        public T peek2() {return arr[index2];}
        public T peek3() {return arr[index3];}
    }

    public static class MinStack1 {
        private Stack<Integer> stack;
        private Stack<Integer> min;
        public MinStack1() {
            stack = new Stack<Integer>();
            min = new Stack<Integer>();
        }
        
        public void push(int num) {
            stack.push(num);
            min.push(min.peek() < num ? min.peek() : num);
        }
        
        public int pop() {
            min.pop();
            return stack.pop();
        }
        
        public int min() {return min.peek();}
    }
    
    public static class MinStack2 {
        private Stack<Integer> stack;
        private Stack<Integer> min;
        public MinStack2() {
            stack = new Stack<Integer>();
            min = new Stack<Integer>();
        }
        
        public void push(int num) {
            stack.push(num);
            if (num <= min.peek()) min.push(num);
        }
        
        public int pop() {
            int res = stack.pop();
            if (res == min.peek()) min.pop();
            return res;
        }
        
        public int min() {return min.peek();}           
    }

    public static class SetOfStacks<T> {
        List<Stack<T>> list;
        int index, capacity;
        public SetOfStacks(int capacity) {
            list = new ArrayList<Stack<T>>();
            index = 0;
            this.capacity = capacity;
            list.add(new Stack<T>());
        }
        
        public void push(T element) {
            if (list.get(index).size() == capacity) {
                list.add(new Stack<T>());
                index++;
            }
            list.get(index).add(element);
        }
        
        public T pop() {
            while (list.get(index).size() == 0) {
                list.remove(list.size() - 1);
                index--;
            }
            return list.get(index).pop();
        }
        
        public T popAt(int index) {return list.get(index).pop();}
    }

    public static class MyQueue<T> {
        Stack<T> s1, s2;
        public MyQueue() {
            s1 = new Stack<T>();
            s2 = new Stack<T>();
        }
        
        public void offer(T element) {s1.push(element);}
        public int size() {return s1.size() + s2.size();}
        public T poll() {
            if (!s2.isEmpty()) 
                while (!s1.isEmpty()) s2.push(s1.pop());
            return s2.pop();
        }
        
    }
    
    public static void sortStack(Stack<Integer> s) {
        Stack<Integer> temp = new Stack<Integer>();
        int n = s.size(), num;
        for (int i = 0; i < n; i++) {
            num = s.pop();
            int j;
            for (j = 0; j < i; j++) {
                if (num >= temp.peek()) {
                    temp.push(num);
                    break;
                }
                s.push(temp.pop());
            }
            if (temp.isEmpty()) temp.push(num);
            for (; j > 0; j--) {temp.push(s.pop());}
        }
        while (!temp.isEmpty()) s.push(temp.pop());
    }
    
    public static class Animal {
        String name;
        boolean isCat;
        int timestamp;
        public Animal(String name, boolean isCat, int timestamp) {
            this.name = name;
            this.isCat = isCat;
            this.timestamp = timestamp;
        }
    }
    
    public static class Shelter {
        private Queue<Animal> dogs;
        private Queue<Animal> cats;
        public Shelter() {
            dogs = new LinkedList<Animal>();
            cats = new LinkedList<Animal>();
        }
        
        public void enqueue(Animal a) {
            if (a.isCat) cats.offer(a);
            else dogs.offer(a);
        }
        
        public Animal dequeueAny() {
            if (dogs.isEmpty()) return cats.poll();
            if (cats.isEmpty() || dogs.peek().timestamp < cats.peek().timestamp) 
                return dogs.poll();
            return cats.poll();
        }
        
        public Animal dequeueCat() {return cats.poll();}
        public Animal dequeueDog() {return dogs.poll();}
    }
    
}
