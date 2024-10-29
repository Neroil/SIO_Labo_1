package ch.heig.sio.lab1.groupD.Utilities;

public class OptimizedLinkedList<T>{

    public static class Node<T>{
        private T value;
        private Node<T> next;

        Node(T value) {
            this.value = value;
            this.next = null;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    private int size;

    public Node<T> insertAfter(Node<T> n, T value){
        Node<T> newNode = new Node<>(value);
        Node<T> afterN = n.next;
        n.next = newNode;
        newNode.next = afterN;

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
        }

        tail.next = newNode;
        tail = newNode;

        ++size;

        return newNode;
    }

    public boolean isEmpty(){
        return tail == null && head == null;
    }

    public int size(){
        return size;
    }
}