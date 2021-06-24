package com.noisyapple;

// Models a node.
public class Node<T> {

    private T data;
    private Node<T> nextNode;

    // Class constructor.
    public Node(T data) {
        this.data = data;
        this.nextNode = null;
    }

    // GETTERS +++

    public T getData() {
        return this.data;
    }

    public Node<T> getNextNode() {
        return this.nextNode;
    }

    // GETTERS ---

    // SETTERS +++

    public void setData(T data) {
        this.data = data;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

    // SETTERS ---

}
