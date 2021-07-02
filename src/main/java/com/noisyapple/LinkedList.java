package com.noisyapple;

// Models a singly linked list.
public class LinkedList<T> {

    private Node<T> head;
    private int size;

    // Class constructor.
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Tells whether the linked list is empty or not.
    public boolean isEmpty() {
        return this.head == null;
    }

    // Adds a node with the given value at the head of the list.
    public void add(T value) {

        Node<T> node = new Node<T>(value);

        if (head == null) {
            head = node;
        } else {
            node.setNextNode(head);
            head = node;
        }

        this.size++;
    }

    // Returns the node found with the given index.
    public Node<T> getNode(int index) {
        Node<T> foundNode = head;
        int innerIndex = size - 1;

        if (index >= 0 && index < size) {
            while (innerIndex != index) {
                foundNode = foundNode.getNextNode();

                innerIndex--;
            }
        } else {
            throw new Error("Index out of bounds");
        }

        return foundNode;
    }

    // Returns the size of the list.
    public int getSize() {
        return size;
    }

    // Returns the value found in the node with the given index.
    public T get(int index) {
        return getNode(index).getData();
    }

    // Returns the last value of the list.
    public T getLast() {
        return head.getData();
    }

    // Returns the first value of the list.
    public T getFirst() {
        return getNode(0).getData();
    }

    // Clears the list.
    public void clear() {
        this.head = null;
    }

    // Removes the node found in the given index.
    public void remove(int index) {
        if (size > 0) {
            Node<T> foundNode = head;
            int innerIndex = size - 1;

            if (index >= 0 && index < size) {
                while (innerIndex != index) {
                    foundNode = foundNode.getNextNode();

                    innerIndex--;
                }
            } else {
                throw new Error("Index out of bounds");
            }

            if (foundNode == head) {
                removeLast();
                return;
            }

            Node<T> previousNode = getNode(index + 1);

            if (foundNode.getNextNode() != null) {
                previousNode.setNextNode(foundNode.getNextNode());
            } else {
                previousNode.setNextNode(null);
            }

            size--;
        }
    }

    // Removes the last node.
    public void removeLast() {
        if (size > 0) {
            head = head.getNextNode();
            size--;
        }
    }

    // Removes the first node.
    public void removeFirst() {
        if (size > 0) {
            remove(0);
        }
    }


    // Returns the string representation of the list.
    @Override
    public String toString() {
        String data = "";

        for (int i = size - 1; i >= 0; i--) {
            data = ((i != 0) ? "," : "") + get(i) + data;
        }

        return "[" + data + "]";

    }

    // Inner node class.
    class Node<E> {

        private E data;
        private Node<E> nextNode;

        // Class constructor.
        public Node(E data) {
            this.data = data;
            this.nextNode = null;
        }

        // GETTERS +++

        public E getData() {
            return this.data;
        }

        public Node<E> getNextNode() {
            return this.nextNode;
        }

        // GETTERS ---

        // SETTERS +++

        public void setData(E data) {
            this.data = data;
        }

        public void setNextNode(Node<E> nextNode) {
            this.nextNode = nextNode;
        }

        // SETTERS ---

    }

}
