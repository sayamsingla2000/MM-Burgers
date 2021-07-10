

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

class customer {
    int customer_id;
    int entry_timeToChef;
    int counter;
    int burgers;
    int departTime;
    Order order;
    int extra;


    public customer(int id, int t, int numb, int p) {
        this.customer_id = id;
        this.entry_timeToChef = t;
        this.burgers = numb;
        this.counter = 0;
        this.departTime = 0;
        this.order = new Order(numb);
        this.extra = p;
    }

}

class Order {
    int timeOfCompletion;
    Queue<Burger> queue;
    int timeOfOrderEntry;
    int orderCounterNo;
    int maxCooking;
    int overallTime;

    public Order(int numb) {
        this.timeOfCompletion = 0;
        this.queue = new LinkedList<>();
        this.timeOfOrderEntry = 0;
        this.orderCounterNo = 0;
        this.maxCooking = 0;
        this.overallTime = this.timeOfOrderEntry + this.maxCooking + 1;
    }

    public int compareTo(Order h) {
        if (timeOfOrderEntry < h.timeOfOrderEntry || (timeOfOrderEntry == h.timeOfOrderEntry && orderCounterNo > h.orderCounterNo)) {
            return -1;
        }
        return 1;
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
            doubleCapacity();
        }

        heap[size] = node;

        fixHeapAbove(size);
        size++;
    }


    public Node_counter delete(int index) {

        int parent = getParent(index);
        Node_counter deletedValue = heap[index];

        heap[index] = heap[size - 1];

        if (index == 0 || heap[index].compareTo(heap[parent]) < 0) {
            fixHeapBelow(index, size - 1);
        } else {
            fixHeapAbove(index);
        }
        size--;
        return deletedValue;
    }


    public Node_counter peek() {
        return heap[0];
    }

    public void fixHeapAbove(int index) {
        Node_counter newValue = heap[index];

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


    public void updateQueue(int t) {

        for (int i = 0; i < Size(); i++)

            while (!heap[i].queue.isEmpty() && heap[i].queue.peek().entry_timeToChef <= t) {
                heap[i].queue.remove();
                heap[i].queue_length--;
            }

        for (int i = Size() / 2 - 1; i >= 0; i--)
            fixHeapBelow(i, size - 1);

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

    storeAVL avl = new storeAVL();

    Heap heap = new Heap();
    HeapBurger chefHeap = new HeapBurger();

    Queue<Order> queue = new LinkedList<>();
    int totalBurgersInKitchen = 0;

    int counters;
    int griddleSize;

    Node_counter nodeQueues;
    customer customerNode;
    Vector<customer> avgTimeVec = new Vector<customer>();
    Vector<Node_counter> array = new Vector<>();
    griddleHeap gridHeap;


    int globalTime = 0;

    public void adding_queue() {
        int j = 1;
        for (int i = 0; i < counters; i++) {

            nodeQueues = new Node_counter(j);
            array.add(nodeQueues);

            j++;
        }
        for (int i = 0; i < counters; i++) {
            heap.insert(array.get(i));

        }
    }


    public Node_counter findQueue() {
        heap.fixHeapAbove(heap.Size() - 1);

        return heap.peek();
    }

    public void addToGrid(Order n, int t) {
        int k;

        while (!n.queue.isEmpty()) {

            if (!gridHeap.isFull()) {
                Burger b = n.queue.remove();
                b.timeOfCooking = b.timeOfOrder + 10;
                gridHeap.insert(b.timeOfCooking);
                k = b.timeOfCooking;

            } else if (gridHeap.peek() <= t) {
                int popTime = gridHeap.peek();
                Burger b = n.queue.remove();
                b.timeOfCooking = 10 + popTime;
                gridHeap.delete(0);
                totalBurgersInKitchen--;
                gridHeap.insert(b.timeOfCooking);
                k = b.timeOfCooking;


            } else {
                break;

            }

            if (n.queue.isEmpty()) {
                n.timeOfCompletion = k;
            }
        }
    }


    public void processing(int t) {
        int k = 0;

        while (!chefHeap.isEmpty() && chefHeap.peek().timeOfOrderEntry <= t) {
            Order a = chefHeap.peek();
            chefHeap.delete(0);
            queue.add(a);
            totalBurgersInKitchen += a.queue.size();

        }

        while (!queue.isEmpty()) {
            Order p = queue.peek();

            addToGrid(p, t);
            if (p.queue.isEmpty()) {
                queue.remove();
            } else
                break;

        }
        while (!gridHeap.isEmpty() && gridHeap.peek() <= t) {
            gridHeap.delete(0);
            totalBurgersInKitchen--;
        }
    }


    public boolean isEmpty() {

        if (chefHeap.isEmpty() && gridHeap.isEmpty() && queue.isEmpty())
            return true;
        return false;
    }

    public void setK(int k) throws IllegalNumberException {

        if (k <= 0) throw new IllegalNumberException(" cannot accept this number");
        counters = k;
        adding_queue();
    }

    public void setM(int m) throws IllegalNumberException {

        if (m <= 0) throw new IllegalNumberException(" cannot accept this number");
        griddleSize = m;
        gridHeap = new griddleHeap(griddleSize);
    }

    public void advanceTime(int t) throws IllegalNumberException {
        if (t >= globalTime) {
            globalTime = t;
        } else throw new IllegalNumberException("time is not correct ");

        processing(t);
    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException {

        if (id <= 0 || t < 0 || numb < 0) throw new IllegalNumberException(" cannot accept this number");
        if (avl.search(id) != null) throw new IllegalNumberException(" same id exist");

        globalTime = t;
        heap.updateQueue(t);
        Node_counter a = findQueue();
        a.queue_length = a.queue_length + 1;
        customerNode = new customer(id, t + a.counterNo * a.queue_length, numb, t);

        customerNode.counter = a.counterNo;
        a.queue.add(customerNode);
        avl.insert_avl(id, customerNode);
        avgTimeVec.add(customerNode);

        Burger burgerNode = new Burger(t + a.counterNo * a.queue_length);

        burgerNode.counterNo = a.counterNo;
        customerNode.order.orderCounterNo = a.counterNo;
        customerNode.order.timeOfOrderEntry = t + a.counterNo * a.queue_length;


        for (int i = 0; i < numb; i++) {
            customerNode.order.queue.add(burgerNode);

        }

        chefHeap.insert(customerNode.order);

        heap.fixHeapBelow(0, heap.Size() - 1);

    }

    public int customerState(int id, int t) throws IllegalNumberException {

        if (t >= globalTime) {
            globalTime = t;
        } else throw new IllegalNumberException("time is not correct ");

        if (id <= 0 || t < 0) throw new IllegalNumberException(" cannot accept this number");
        if (avl.search(id) == null) {
            return 0;
        }

        if (t < avl.search(id).entry_timeToChef) {

            return avl.search(id).counter;
        }

        advanceTime(t);

        customer b = avl.search(id);

        if (b.order.timeOfCompletion == 0 || b.order.timeOfCompletion + 1 > t) {
            return counters + 1;
        }

        if (b.order.timeOfCompletion + 1 <= t) {
            return counters + 2;
        }

        return -1;
    }

    public int griddleState(int t) throws IllegalNumberException {

        if (t >= globalTime) {
            globalTime = t;
        } else throw new IllegalNumberException("time is not correct ");

        advanceTime(t);

        return gridHeap.Size();
    }

    public int griddleWait(int t) throws IllegalNumberException {

        if (t >= globalTime) {
            globalTime = t;
        } else throw new IllegalNumberException(" time is not correct ");
        advanceTime(t);
        int k = totalBurgersInKitchen - gridHeap.Size();

        return k;
    }

    public int customerWaitTime(int id) throws IllegalNumberException {

        if (avl.search(id) == null) throw new IllegalNumberException("ID doesnt exist");

        customer a = avl.search(id);
        int k = a.order.timeOfCompletion + 1 - a.extra;

        return k;
    }

    public float avgWaitTime() {

        float k = 0;
        for (int i = 0; i < avgTimeVec.size(); i++) {
            k += (avgTimeVec.get(i).order.timeOfCompletion + 1 - avgTimeVec.get(i).extra);

        }

        return (k / avgTimeVec.size());
    }


}

class Node_avl {
    Node_avl left, right;
    int data;
    int height;
    customer node;

    public Node_avl() {
        left = null;
        right = null;
        data = 0;
        height = 0;
        customer node;
    }

    public Node_avl(int n, customer node) {
        this.left = null;
        this.right = null;
        this.data = n;
        this.height = 0;
        this.node = node;
    }
}


class storeAVL {
    Node_avl root;
    int size;

    public storeAVL() {
        root = null;
    }

    public void newHeight(Node_avl n) {
        int a;
        if (height(n.left) >= height(n.right)) {
            a = height(n.left);
        } else {
            a = height(n.right);
        }
        n.height = 1 + a;
    }

    public int height(Node_avl n) {
        if (n == null) {
            return -1;
        } else {
            return n.height;
        }
    }

    public int getBalance(Node_avl n) {
        if (n == null) {
            return 0;
        } else {
            return height(n.right) - height(n.left);
        }
    }

    public Node_avl rotateRight(Node_avl y) {
        Node_avl x = y.left;
        Node_avl z = x.right;
        x.right = y;
        y.left = z;
        newHeight(y);
        newHeight(x);
        return x;
    }

    public Node_avl rotateLeft(Node_avl y) {
        Node_avl x = y.right;
        Node_avl z = x.left;
        x.left = y;
        y.right = z;
        newHeight(y);
        newHeight(x);
        return x;
    }

    public Node_avl rebalance(Node_avl z) {
        newHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);
            else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    public void insert_avl(int data, customer n) {
        root = insert(root, data, n);
        size++;

    }

    public Node_avl insert(Node_avl node, int key, customer n) {
        if (node == null) {
            return new Node_avl(key, n);
        } else if (node.data > key) {
            node.left = insert(node.left, key, n);
        } else if (node.data < key) {
            node.right = insert(node.right, key, n);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    private Node_avl mostLeftChild(Node_avl node) {
        Node_avl current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void delete_avl(int data) {
        root = delete(root, data);
    }

    public Node_avl delete(Node_avl node, int key) {
        if (node == null) {
            return node;
        } else if (node.data > key) {
            node.left = delete(node.left, key);
        } else if (node.data < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node_avl mostLeftChild = mostLeftChild(node.right);
                node.data = mostLeftChild.data;
                //node.node = mostLeftChild.node;
                node.right = delete(node.right, node.data);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    public customer search(int val) {
        return search(root, val);
    }

    private customer search(Node_avl r, int value) {

        if (r == null) {
            return null;
        }

        if (value == r.data) {
            return r.node;
        }
        if (value < r.data) {
            if (r.left != null) {
                return search(r.left, value);
            }
        } else {
            if (r.right != null) {
                return search(r.right, value);
            }
        }
        return null;
    }

    public int totalSumAvg_avl() {
        return totalSum(root);
    }

    private int totalSum(Node_avl n) {
        int k = 0;

        if (n != null) {
            totalSum(n.left);
            k += (search(n.data).order.timeOfCompletion + 1);

            totalSum(n.right);
        }
        return k / size;
    }
}


class HeapBurger {
    private Order[] heapBurgerOrder;
    private int size = 0;
    private int capacity = 1;

    public HeapBurger() {
        heapBurgerOrder = new Order[1];
    }

    private void doubleCapacity() {

        capacity = (capacity * 2);
        Order[] new_array = new Order[capacity];

        for (int i = 0; i < size; ++i) {
            new_array[i] = heapBurgerOrder[i];
        }
        this.heapBurgerOrder = new_array;
        this.capacity = capacity;
    }

    public void insert(Order node) {
        if (isFull()) {
            doubleCapacity();
        }

        heapBurgerOrder[size] = node;

        fixHeapAbove(size);
        size++;
    }


    public Order delete(int index) {

        int parent = getParent(index);
        Order deletedValue = heapBurgerOrder[index];

        heapBurgerOrder[index] = heapBurgerOrder[size - 1];

        if (index == 0 || heapBurgerOrder[index].compareTo(heapBurgerOrder[parent]) < 0) {
            fixHeapBelow(index, size - 1);
        } else {
            fixHeapAbove(index);
        }
        size--;
        return deletedValue;
    }


    public Order peek() {

        return heapBurgerOrder[0];
    }

    public void fixHeapAbove(int index) {
        Order newValue = heapBurgerOrder[index];

        while (index > 0 && newValue.compareTo(heapBurgerOrder[getParent(index)]) < 0) {
            heapBurgerOrder[index] = heapBurgerOrder[getParent(index)];
            index = getParent(index);
        }

        heapBurgerOrder[index] = newValue;
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

                    childToSwap = heapBurgerOrder[leftChild].compareTo(heapBurgerOrder[rightChild]) < 0 ? leftChild : rightChild;
                }

                if (heapBurgerOrder[index].compareTo(heapBurgerOrder[childToSwap]) > 0) {
                    Order tmp = heapBurgerOrder[index];
                    heapBurgerOrder[index] = heapBurgerOrder[childToSwap];
                    heapBurgerOrder[childToSwap] = tmp;
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
            System.out.print(heapBurgerOrder[i].queue.size());
            System.out.print(", ");
        }
        System.out.println();
    }

    public boolean isFull() {
        return size == heapBurgerOrder.length;
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

class griddleHeap {
    private int[] heap;
    public int size;

    public griddleHeap(int capacity) {
        heap = new int[capacity];
    }

    public void insert(int value) {

        heap[size] = value;

        fixHeapAbove(size);
        size++;
    }

    public int delete(int index) {

        int parent = getParent(index);
        int deletedValue = heap[index];

        heap[index] = heap[size - 1];

        if (index == 0 || heap[index] > heap[parent]) {
            fixHeapBelow(index, size - 1);
        } else {
            fixHeapAbove(index);
        }
        size--;
        return deletedValue;
    }

    public int peek() {

        return heap[0];
    }

    private void fixHeapAbove(int index) {
        int newValue = heap[index];
        while (index > 0 && newValue < heap[getParent(index)]) {
            heap[index] = heap[getParent(index)];
            index = getParent(index);
        }

        heap[index] = newValue;
    }

    private void fixHeapBelow(int index, int lastHeapIndex) {
        int childToSwap;

        while (index <= lastHeapIndex) {
            int leftChild = getChild(index, true);
            int rightChild = getChild(index, false);
            if (leftChild <= lastHeapIndex) {
                if (rightChild > lastHeapIndex) {
                    childToSwap = leftChild;
                } else {
                    childToSwap = (heap[leftChild] < heap[rightChild] ? leftChild : rightChild);
                }

                if (heap[index] > heap[childToSwap]) {
                    int tmp = heap[index];
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
            System.out.print(heap[i]);
            System.out.print(", ");
        }
        System.out.println();
    }

    public boolean isFull() {
        return size == heap.length;
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public int Size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getChild(int index, boolean left) {
        return 2 * index + (left ? 1 : 2);
    }
}

