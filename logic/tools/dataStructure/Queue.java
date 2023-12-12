package dataStructure;

import java.util.LinkedList;

public class Queue<T> {
    private LinkedList<T> elements;

    public Queue() {
        this.elements = new LinkedList<>();
    }

    public void enqueue(T item) {
        elements.addLast(item);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return elements.removeFirst();
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return elements.getFirst();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public LinkedList<T> getElements() {
        return new LinkedList<>(elements);
    }
}
