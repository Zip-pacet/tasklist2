package com.company;

import java.util.Iterator;

public class SimpleLinkedList implements Iterable<Integer> {

    public Integer findCountMax(SimpleLinkedList list) throws SimpleLinkedListException{
        checkEmptyError();
        if(size == 1){
            return 1;
        }

        int count = 0;
        Integer max = getFirst();
        //
        for (int i = 0; i < size(); i++) {
            if(max < list.get(i)){
                max = list.get(i);
            }
        }

        for (int i = 0; i < size(); i++) {
            if(max == list.get(i)){
                count++;
            }
        }

        return count;
    }

    public static class SimpleLinkedListException extends Exception {
        public SimpleLinkedListException(String message) {
            super(message);
        }
    }

    private class SimpleLinkedListNode {
        public Integer value;
        public SimpleLinkedListNode next;

        public SimpleLinkedListNode(Integer value, SimpleLinkedListNode next) {
            this.value = value;
            this.next = next;
        }

        public SimpleLinkedListNode(Integer value) {
            this(value, null);
        }
    }

    private SimpleLinkedListNode head = null;  // first, top
    private SimpleLinkedListNode tail = null;  // last
    private int size = 0;

    // O(1)
    public void addFirst(Integer value) {
        head = new SimpleLinkedListNode(value, head);
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    // O(1)
    public void addLast(Integer value) {
        if (size == 0) {
            head = tail = new SimpleLinkedListNode(value);
        } else {
            tail.next = new SimpleLinkedListNode(value);
            tail = tail.next;
        }
        size++;
    }

    private void checkEmptyError() throws SimpleLinkedListException {
        if (size == 0) {
            throw new SimpleLinkedListException("Empty list");
        }
    }

    // O(n)
    private SimpleLinkedListNode getNode(int index) {
        SimpleLinkedListNode curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    // O(1)
    public void removeFirst() throws SimpleLinkedListException {
        checkEmptyError();
        head = head.next;
        if (size == 1) {
            tail = null;
        }
        size--;
    }

    // O(n)
    public void removeLast() throws SimpleLinkedListException {
        checkEmptyError();
        if (size == 1) {
            head = tail = null;
        } else {
            tail = getNode(size - 2);
            tail.next = null;
        }
        size--;
    }

    // O(n)
    public void remove(int index) throws SimpleLinkedListException {
        checkEmptyError();
        if (index < 0 || index >= size) {
            throw new SimpleLinkedListException("Incorrect index");
        }
        if (index == 0) {
            removeFirst();
        } else {
            SimpleLinkedListNode prev = getNode(index - 1);
            prev.next = prev.next.next;
            if (prev.next == null) {
                tail = prev;
            }
            size--;
        }
    }

    // O(1)
    public int size() {
        return size;
    }

    // O(n)
    public Integer get(int index) throws SimpleLinkedListException {
        checkEmptyError();
        return getNode(index).value;
    }

    // O(1)
    public Integer getFirst() throws SimpleLinkedListException {
        checkEmptyError();
        return head.value;
    }

    // O(1)
    public Integer getLast() throws SimpleLinkedListException {
        checkEmptyError();
        return tail.value;
    }


    @Override
    public Iterator<Integer> iterator() {
        class SimpleLinkedListIterator implements Iterator<Integer> {
            SimpleLinkedListNode curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Integer next() {
                Integer value = curr.value;
                curr = curr.next;
                return value;
            }
        }

        return new SimpleLinkedListIterator();
    }

}
