package com.example.hustlex.datastructure.linksequence;

public class LinkSequence<T>implements LinkSequenceInterface<T>{

    public class Node{

        T data;
        Node next;

        public T getData() {
            return data;
        }

        Node(T dataNode) {
            this.data = dataNode;
        }
    }

    private Node seqHead;
    int currentSize;

    public LinkSequence() {
        seqHead = null;
        currentSize = 0;
    }

    @Override
    public void add(T dataNode) {
        // TODO Auto-generated method stub
        Node newNode = new Node(dataNode);
        if(this.seqHead == null) {
            this.seqHead = newNode;
            newNode.next = null;
        }else{
            Node temp = this.seqHead;
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = null;
        }
//		seqAdder.next should be seqTail
        currentSize++;

    }

    @Override
    public boolean remove(T dataNode) {
        // TODO Auto-generated method stub
        Node temp = this.seqHead;
        Node prev = null;
        while(temp != null){
            if(temp.data.equals(dataNode)){
                if(prev == null){
                    System.out.println("Removing object first element");
                    this.seqHead = temp.next;
                }else{
                    System.out.println("Removing object any other element in ll");
                    prev.next = temp.next;
                }
                this.currentSize -= 1;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }

    @Override
    public boolean removeLast() {
        // TODO Auto-generated method stub
        if(this.length() == 0){
            return true;
        }
        Node temp = this.seqHead;
        Node prev = null;
        if(this.length() == 1){
            this.seqHead = null;
            this.currentSize -= 1;
            return true;
        }
        while(temp.next != null){
            prev = temp;
            temp = temp.next;
        }
        prev.next = temp.next;
        this.currentSize -= 1;
        return true;

    }

    @Override
    public boolean removeFirst() {
        // TODO Auto-generated method stub
        if(this.seqHead == null){
            return true;
        }
        this.seqHead = this.seqHead.next;
        this.currentSize -= 1;
        return true;

    }

    @Override
    public boolean removeAt(int index) {
        // TODO Auto-generated method stub
        // Index out of bounds
        if (index < 0 || index >= this.currentSize) {
            return false;
        }

        Node temp = this.seqHead;
        Node prev = null;
        int indexCounter = 0;
        while (temp != null) {
            if (indexCounter == index) {
                if (prev == null) {
                    System.out.println("Removing object at index 0");
                    this.seqHead = temp.next;
                } else {
                    System.out.println("Removing object at index " + index);
                    prev.next = temp.next;
                }
                this.currentSize -= 1;
                return true;
            }
            prev = temp;
            temp = temp.next;
            indexCounter++;
        }
        return false;

    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        return currentSize;
    }

    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        if(index >= this.currentSize || this.seqHead == null){
            return null;
        }
        int indexCounter = 0;
        Node temp = this.seqHead;
        while(indexCounter < index){
            temp = temp.next;
            indexCounter++;
        }
        return temp.data;
    }

    @Override
    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("[");
        Node temp = this.seqHead;
        if(temp != null){
            strBuff.append(temp.data);
            temp = temp.next;
            while(temp != null){
                strBuff.append(", ");
                strBuff.append(temp.data);
                temp = temp.next;
            }
        }
        strBuff.append("]");
        return strBuff.toString();
        // return "LinkSequence{" +
        //         "seqHead=" + seqHead +
        //         ", currentSize=" + currentSize +
        //         '}';
    }
}