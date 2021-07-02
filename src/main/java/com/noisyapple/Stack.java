package com.noisyapple;

// Models a stack based on a linked list.
public class Stack<T> {

    LinkedList<T> list;

    // Class constructor.
    public Stack() {
        list = new LinkedList<T>();
    }

    // Pushes the given value into the stack
    public void push(T value) {
        list.add(value);
    }

    // Pops the last element from the stack.
    public T pop() {
        if (size() > 0) {
            T value = list.get(list.getSize() - 1);
            list.removeLast();

            return value;
        } else {
            throw new Error("Unable to retrieve a value from an empty stack.");
        }
    }

    // Returns the size of the stack.
    public int size() {
        return list.getSize();
    }

    // Returns the last value in the stack without removing it.
    public T peek() {
        return list.getLast();
    }

    // Tells whether the stack is empty or not.
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Returns a string representation of the stack.
    @Override
    public String toString() {
        return list.toString();
    }

}
