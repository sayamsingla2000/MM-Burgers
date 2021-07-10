import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

class customer {
    int customer_id;
    int entry_time;
    int counter;
    int burgers;
    //List<Burger> list ;
    // Burger[] list;
    int departTime;
    Order order;


    public customer(int id, int t, int numb) {
        this.customer_id = id;
        this.entry_time = t;
        this.burgers = numb;
        this.counter = 0;
        //this.list = new Burger[numb];
        this.departTime = 0;
        this.order = new Order(numb);

    }

}

class Order {
    int timeOfCompletion;
    Burger[] list;

    public Order(int numb) {
        this.timeOfCompletion = 0;
        this.list = new Burger[numb];

    }
}


class Burger {
    int totalBurgers;
    int timeOfCooking;
    int timeOfOrder;
    int counterNo;


    public Burger(int chefTime) {
        this.timeOfOrder = chefTime;
        this.timeOfCooking = 0;
    }


    public int compareTo(Burger h) {
        if (timeOfOrder < h.timeOfOrder || (timeOfOrder == h.timeOfOrder && counterNo > h.counterNo)) {
            return -1;
        }
        return 1;
    }
}


class Node_counter {
    int queue_length;
    int counterNo;
    Queue<customer> queue = new LinkedList<>();

    public Node_counter(int k) {
        this.counterNo = k;
        this.queue_length = 0;
        this.queue = new LinkedList<>();
    }

    public int compareTo(Node_counter h) {
        if (queue_length < h.queue_length || (queue_length == h.queue_length && counterNo < h.counterNo)) {
            return -1;
        }
        return 1;
    }
}


class Heap {
    private Node_counter[] heap;
    private int size = 0;
    private int capacity = 1;

    public Heap() {
        heap = new Node_counter[1];
    }

    private void doubleCapacity() {

        capacity = (capacity * 2);
        Node_counter[] new_array = new Node_counter[capacity];

        for (int i = 0; i < size; ++i) {
            new_array[i] = heap[i];
        }
        this.heap = new_array;
        this.capacity = capacity;
    }

    public void insert(Node_counter node) {
        if (isFull()) {
            //throw new IndexOutOfBoundsException("Heap is full");
            doubleCapacity();
        }

        heap[size] = node;

        fixHeapAbove(size);
        size++;
    }


    public Node_counter delete(int index) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        int parent = getParent(index);
        Node_counter deletedValue = heap[index];

        heap[index] = heap[size - 1];
// comparison of size
        if (index == 0 || heap[index].compareTo(heap[parent]) < 0) {
            fixHeapBelow(index, size - 1);
        } else {
            fixHeapAbove(index);
        }
        size--;
        return deletedValue;
    }

    public Node_counter peek2() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        return heap[2];
    }

    public Node_counter peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        return heap[0];
    }

    public void fixHeapAbove(int index) {
        Node_counter newValue = heap[index];
        // camparison of queue length
        while (index > 0 && newValue.compareTo(heap[getParent(index)]) < 0) {
            heap[index] = heap[getParent(index)];
            index = getParent(index);
        }

        heap[index] = newValue;
    }

    public void fixHeapBelow(int index, int lastHeapIndex) {
        int childToSwap;

        while (index <= lastHeapIndex) {
            int leftChild = getChild(index, true);
            int rightChild = getChild(index, false);
            if (leftChild <= lastHeapIndex) {
                if (rightChild > lastHeapIndex) {
                    childToSwap = leftChild;
                } else {
                    // comparison taking place
                    childToSwap = heap[leftChild].compareTo(heap[rightChild]) < 0 ? leftChild : rightChild;
                }

                if (heap[index].compareTo(heap[childToSwap]) > 0) {
                    Node_counter tmp = heap[index];
                    heap[index] = heap[childToSwap];
                    heap[childToSwap] = tmp;
                } else {
                    break;
                }

                index = childToSwap;
            } else {
                break;
            }
        }
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i].queue_length);
            System.out.print(", ");
        }
        System.out.println();
    }

    //    public void getElement(){
//        for (int i = 0; i < size; i++) {
//            return
//        }
//    }
    public void updateQueue(int t) {
        int k = 0;
        for (int i = 0; i < Size(); i++)

            while (!heap[i].queue.isEmpty() && heap[i].queue.peek().entry_time <= t) {
                heap[i].queue.remove();
                heap[i].queue_length--;
                k++;
            }
        fixHeapAbove(Size() - 1);

    }

    public boolean isFull() {
        return size == heap.length;
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int Size() {
        return size;
    }

    public int getChild(int index, boolean left) {
        return 2 * index + (left ? 1 : 2);
    }
}

public class MMBurgers implements MMBurgersInterface {

    Heap heap = new Heap();
    HeapBurger chefHeap = new HeapBurger();

    int counters;
    int griddleSize;
    Node_counter nodeQueues;
    customer customerNode;
    Node_counter[] queue_array = new Node_counter[counters];
    Vector<Node_counter> array = new Vector<>();


    public void adding_queue() {
        int j = 1;
        for (int i = 0; i < counters; i++) {

            nodeQueues = new Node_counter(j);
            //System.out.println(nodeQueues.counterNo);
            array.add(nodeQueues);

            j++;
        }
        for (int i = 0; i < counters; i++) {
            heap.insert(array.get(i));
            // System.out.println(array.get(i));
        }
    }


    public Node_counter findQueue() {
        heap.fixHeapAbove(heap.Size() - 1);

        return heap.peek();
    }


    public boolean isEmpty() {
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setK(int k) throws IllegalNumberException {

        if (k <= 0) throw new IllegalNumberException(" cannot accept this number");
        counters = k;
        adding_queue();
    }

    public void setM(int m) throws IllegalNumberException {
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if (m <= 0) throw new IllegalNumberException(" cannot accept this number");
        griddleSize = m;
    }

    public void advanceTime(int t) throws IllegalNumberException {
        //your implementation
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");

    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException {
        //your implementation
//        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        heap.updateQueue(t);
        Node_counter a = findQueue();
        a.queue_length = a.queue_length + 1;
        customerNode = new customer(id, t + a.counterNo * a.queue_length, numb);

        //System.out.println(a.counterNo);

        customerNode.counter = a.counterNo;
        a.queue.add(customerNode);

        //System.out.println(a.queue.toString() + a.counterNo);

        Burger burgerNode = new Burger(t + a.counterNo * a.queue_length);
        burgerNode.counterNo = a.counterNo;

        for (int i = 0; i < numb; i++) {
            customerNode.order.list[i] = burgerNode;
            //            doubt
            chefHeap.insert(burgerNode);
            //  chefHeap.printHeap();
        }

        heap.fixHeapBelow(0, heap.Size() - 1);

        System.out.println(customerNode.customer_id);
        System.out.println(customerNode.counter);

    }

    public int customerState(int id, int t) throws IllegalNumberException {
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int griddleState(int t) throws IllegalNumberException {
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int griddleWait(int t) throws IllegalNumberException {
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int customerWaitTime(int id) throws IllegalNumberException {
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public float avgWaitTime() {
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }


}

class AVLTree {

    private Node root;

    private class Node {
        private int key;
        private int balance;
        private int height;
        private Node left, right, parent;

        Node(int k, Node p) {
            key = k;
            parent = p;
        }
    }

    public boolean insert(int key) {
        if (root == null) root = new Node(key, null);
        else {
            Node n = root;
            Node parent;
            while (true) {
                if (n.key == key) return false;

                parent = n;

                boolean goLeft = n.key > key;
                n = goLeft ? n.left : n.right;

                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node(key, parent);
                    } else {
                        parent.right = new Node(key, parent);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }

    private void delete(Node node) {
        if (node.left == null && node.right == null) {
            if (node.parent == null) root = null;
            else {
                Node parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else parent.right = null;
                rebalance(parent);
            }
            return;
        }
        if (node.left != null) {
            Node child = node.left;
            while (child.right != null) child = child.right;
            node.key = child.key;
            delete(child);
        } else {
            Node child = node.right;
            while (child.left != null) child = child.left;
            node.key = child.key;
            delete(child);
        }
    }

    public void delete(int delKey) {
        if (root == null) return;
        Node node = root;
        Node child = root;

        while (child != null) {
            node = child;
            child = delKey >= node.key ? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }

    private void rebalance(Node n) {
        setBalance(n);

        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right)) n = rotateRight(n);
            else n = rotateLeftThenRight(n);

        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left)) n = rotateLeft(n);
            else n = rotateRightThenLeft(n);
        }

        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }

    private Node rotateLeft(Node a) {

        Node b = a.right;
        b.parent = a.parent;

        a.right = b.left;

        if (a.right != null) a.right.parent = a;

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private Node rotateRight(Node a) {

        Node b = a.left;
        b.parent = a.parent;

        a.left = b.right;

        if (a.left != null) a.left.parent = a;

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }

    private int height(Node n) {
        if (n == null) return -1;
        return n.height;
    }

    private void setBalance(Node... nodes) {
        for (Node n : nodes) {
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }

    public void printBalance() {
        printBalance(root);
    }

    private void printBalance(Node n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }

    private void reheight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    public boolean search(int key) {
        Node result = searchHelper(this.root, key);
        if (result != null) return true;

        return false;
    }

    private Node searchHelper(Node root, int key) {
        // root is null or key is present at root
        if (root == null || root.key == key) return root;

        // key is greater than root's key
        if (root.key > key)
            return searchHelper(root.left, key); // call the function on the node's left child

        // key is less than root's key then
        // call the function on the node's right child as it is greater
        return searchHelper(root.right, key);
    }

//    public static void main(String[] args) {
//        AVLTree tree = new AVLTree();
//
//        System.out.println("Inserting values 1 to 10");
//        for (int i = 1; i < 10; i++) tree.insert(i);
//
//        System.out.print("Printing balance: ");
//        tree.printBalance();
//    }
}


class HeapBurger {
    private Burger[] heapBurger;
    private int size = 0;
    private int capacity = 1;

    public HeapBurger() {
        heapBurger = new Burger[1];
    }

    private void doubleCapacity() {

        capacity = (capacity * 2);
        Burger[] new_array = new Burger[capacity];

        for (int i = 0; i < size; ++i) {
            new_array[i] = heapBurger[i];
        }
        this.heapBurger = new_array;
        this.capacity = capacity;
    }

    public void insert(Burger node) {
        if (isFull()) {
            //throw new IndexOutOfBoundsException("Heap is full");
            doubleCapacity();
        }

        heapBurger[size] = node;

        fixHeapAbove(size);
        size++;
    }


    public Burger delete(int index) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        int parent = getParent(index);
        Burger deletedValue = heapBurger[index];

        heapBurger[index] = heapBurger[size - 1];
// comparison of size
        if (index == 0 || heapBurger[index].compareTo(heapBurger[parent]) < 0) {
            fixHeapBelow(index, size - 1);
        } else {
            fixHeapAbove(index);
        }
        size--;
        return deletedValue;
    }


    public Burger peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        return heapBurger[0];
    }

    public void fixHeapAbove(int index) {
        Burger newValue = heapBurger[index];
        // camparison of queue length
        while (index > 0 && newValue.compareTo(heapBurger[getParent(index)]) < 0) {
            heapBurger[index] = heapBurger[getParent(index)];
            index = getParent(index);
        }

        heapBurger[index] = newValue;
    }

    public void fixHeapBelow(int index, int lastHeapIndex) {
        int childToSwap;

        while (index <= lastHeapIndex) {
            int leftChild = getChild(index, true);
            int rightChild = getChild(index, false);
            if (leftChild <= lastHeapIndex) {
                if (rightChild > lastHeapIndex) {
                    childToSwap = leftChild;
                } else {
                    // comparison taking place
                    childToSwap = heapBurger[leftChild].compareTo(heapBurger[rightChild]) < 0 ? leftChild : rightChild;
                }

                if (heapBurger[index].compareTo(heapBurger[childToSwap]) > 0) {
                    Burger tmp = heapBurger[index];
                    heapBurger[index] = heapBurger[childToSwap];
                    heapBurger[childToSwap] = tmp;
                } else {
                    break;
                }

                index = childToSwap;
            } else {
                break;
            }
        }
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heapBurger[i].timeOfOrder);
            System.out.print(", ");
        }
        System.out.println();
    }

    public boolean isFull() {
        return size == heapBurger.length;
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int Size() {
        return size;
    }

    public int getChild(int index, boolean left) {
        return 2 * index + (left ? 1 : 2);
    }
}

