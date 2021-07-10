import java.util.LinkedList;
import java.util.Queue;

class Node_entry{
    int customer_id;
    int entry_time;
    int counter;
    int burgers;

    public Node_entry(int id, int t, int  numb){
        this.customer_id = id;
        this.entry_time = t;
        this.burgers = numb;
        this.counter = 0;
    }

}


class Node_counter{
    int queue_length;
    int counterNo;
    Queue<Node_entry> queue = new LinkedList<>();

    public Node_counter(int k){
        this.counterNo = k;
        this.queue_length = 0;
        this.queue = new LinkedList<>();
        }

    }





class Heap {
    private int[] heap;
    private int size = 0;
    private int capacity = 1;

    public Heap() {
        heap = new int[1];
    }
    private void doubleCapacity(){

        capacity = (capacity * 2);
        int[] new_array = new int[capacity];

        for(int i = 0 ; i<size; ++i) {
            new_array[i] = heap[i];
        }
        this.heap = new_array;
        this.capacity = capacity;
    }

    public void insert(int value) {
        if (isFull()) {
            //throw new IndexOutOfBoundsException("Heap is full");
            doubleCapacity();
        }

        heap[size] = value;

        fixHeapAbove(size);
        size++;
    }

    public int delete(int index) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

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
    public int peek2() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        return heap[2];
    }
    public int peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

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

                if (heap[index] >  heap[childToSwap]) {
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

    public boolean isEmpty() {
        return size == 0;
    }

    public int getChild(int index, boolean left) {
        return 2 * index + (left ? 1 : 2);
    }
}

public class MMBurgers implements MMBurgersInterface {

    Heap heap = new Heap();
    int counters;
    Node_counter nodeQueues;
    Node_entry customerNode;
    Node_counter[] queue_array = new Node_counter[counters];

    public void adding_queue(){
        for(int i = 0; i< counters; i++){
            int j= 1;
            nodeQueues = new Node_counter(j);
            queue_array[i] = nodeQueues;
            j++;
        }
    }


    public Node_counter findQueue(){
        for(int i = 0; i< counters; i++){
            heap.insert(queue_array[i].queue_length);
        }
        if(heap.peek() == heap.peek2()){
            if (queue_array[0].counterNo > queue_array[2].counterNo);
            {return queue_array[2];}


        }
        return queue_array[0];
    }


    public boolean isEmpty(){
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setK(int k) throws IllegalNumberException{
//        //your implementation
//        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(k<= 0) throw new IllegalNumberException(" cannot accept this number");
        counters = k;
        adding_queue();
    }

    public void setM(int m) throws IllegalNumberException{
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void advanceTime(int t) throws IllegalNumberException{
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
//        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        customerNode = new Node_entry(id, t, numb) ;
        Node_counter a = findQueue();
        a.queue_length = a.queue_length+1;
        a.queue.add(customerNode);

    }

    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public float avgWaitTime(){
        //your implementation
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }


}
