package ch.heig.sio.lab1.groupD.Utilities;

public class OptimizedLinkedList<T>{

    public static class Node<T>{
        private T value;
        private Node<T> next;
        private Node<T> previous;

        Node(T value) {
            this.value = value;
            this.next = null;
            this.previous = null;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrevious(){
            return previous;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    private int size;

    public Node<T> insertAfter(Node<T> n, T value){
        Node<T> newNode = new Node<>(value);
        Node<T> afterN = n.next;
        if (afterN != null) {
            afterN.previous = newNode;
        }
        n.next = newNode;
        newNode.previous = n;
        newNode.next = afterN;

        if (n == tail) {
            tail = newNode;
        }

        ++size;

        return newNode;
    }

    public Node<T> getFirst(){
        return head;
    }

    public Node<T> add(T value){
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }

        ++size;

        return newNode;
    }

    public void remove(Node<T> node){
        if(isEmpty()){
            return;
        }

        if (node == head) {
            head = node.next;
        }

        if (node == tail) {
            tail = node.previous;
        }

        if (node.previous != null) {
            node.previous.next = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        }

        --size;
    }

    public boolean isEmpty(){
        return tail == null && head == null;
    }

    public int size(){
        return size;
    }

}