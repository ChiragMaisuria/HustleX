package com.example.hustlex.datastructure.tree;


import com.example.hustlex.datastructure.linksequence.LinkSequence;
import com.example.hustlex.datastructure.linksequence.LinkSequenceInterface;

import java.util.UUID;

public class Tree<T> implements TreeInterface<T> {

    // int nodeId; // generate randomly
    // String heading;
    // String type; // heading, subHeading, tasks
    // T kanbanTask;
    // LinkSequenceInterface<Tree<T>> children;

    public class Node {
        UUID nodeId; // generate randomly
        public String heading; // root, notStarted, inProgress, completed, kanbanTask
        public String type; // root[root], status [notStarted, inProgress, completed], tasks
        public T kanbanTask;
        LinkSequenceInterface<Node> children;

        public Node(String heading, String type) {
            this.nodeId = UUID.randomUUID();
            this.heading = heading;
            this.type = type;
            this.kanbanTask = null;
            this.children = new LinkSequence<>();
        }

        public Node(String heading, String type, T kanbanTask) {
            this.nodeId = UUID.randomUUID();
            this.heading = heading;
            this.type = type;
            this.kanbanTask = kanbanTask;
            this.children = new LinkSequence<>();
        }
    }

    private Node treeRoot;
    private int totalNodes = 0;

    public Tree() {
        treeRoot = null;
    }

    @Override
    public Node createNode(String heading, String type, T kanbanTask) {
        Node newNode;
        if (kanbanTask == null) {
            newNode = new Node(heading, type);
            return newNode;
        }
        newNode = new Node(heading, type, kanbanTask);
        return newNode;
    }

    @Override
    public Node findTheParent(Node node, String status){
        Node temp = node;
        if(temp.heading.equals("task")){
            return null;
        }
        System.out.println("temp.heading: " + temp.heading);
        if(temp.heading.equals(status)){
            return temp;
        }

        for(int i=0; i<temp.children.length(); i++){
            Node result = findTheParent(temp.children.get(i), status);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    // Find the status of the node before calling this method and from statusNode
    // call the method with that node to be added.
    @Override
    public boolean addNode(Node newNode, String status) {
        // TODO Auto-generated method stub
        Node temp = this.treeRoot;
        if (temp == null) {
            this.treeRoot = newNode;
            totalNodes++;
            return true;
        }
        if (status.equals("")) {
            temp.children.add(newNode);
            totalNodes++;
            return true;
        }

        Node parent = findTheParent(this.treeRoot, status);
        if(parent == null){
            return false;
        }
        parent.children.add(newNode);
        totalNodes++;
        return true;
    }

    @Override
    public String displayTree() {
        if (this.treeRoot == null) {
            System.out.println("Tree is empty");
            return "";
        }
        StringBuffer resultStr = new StringBuffer();
        return viewTree(treeRoot, 0, resultStr);
    }

    @Override
    public String viewTree(Node node, int level, StringBuffer resultStr) {
        StringBuilder treeView = new StringBuilder();
        for (int i = 0; i < level; i++) {
            treeView.append("  "); // Adjust the number of spaces for indentation
        }
        String addTask = "";
        if(node.kanbanTask == null){
            addTask = "\n" + treeView.toString() + node.heading;
            resultStr.append(addTask);
//            System.out.println(treeView.toString() + node.heading);

        }else{
            addTask = "\n" + treeView.toString() + node.kanbanTask;
            resultStr.append(addTask);
        }
        for ( int i=0; i<node.children.length(); i++) {
            viewTree(node.children.get(i), level + 1, resultStr);
        }
        return resultStr.toString();
    }

    // Find parent using the status in the kanbanTask and then remove from parent.
    @Override
    public boolean removeNode(Node node, String status) {
        // TODO Auto-generated method stub
        // this.children.remove(node);
        Node temp = this.treeRoot;
        if (temp == null) {
            return false;
        }
        if (status.equals("")) {
            temp.children.remove(node);
            totalNodes -= 1;
            return true;
        }

        Node parent = findTheParent(this.treeRoot, status);
        if(parent == null){
            return false;
        }
        parent.children.remove(node);
        totalNodes -= 1;
        return true;
    }

    @Override
    public boolean transferTo(Tree<T>.Node node, String fromStatus, String toStatus){
        return removeNode(node, fromStatus) && addNode(node, toStatus);
    }

    @Override
    public LinkSequence<Tree<T>.Node> getChildrenOf(String status) {
        // TODO Auto-generated method stub
        LinkSequence<Tree<T>.Node> result = new LinkSequence<>();
        Node parent = findTheParent(this.treeRoot, status);
        for(int i=0; i<parent.children.length(); i++){
            result.add(parent.children.get(i));
        }
        return result;
    }

    @Override
    public int getTotalNodes(){
        return totalNodes;
    }

    // @Override
    // public boolean updateNode(Node updatedNode, String status) {
    //     // TODO Auto-generated method stub
    //     // if (this.nodeId == updatedNode.nodeId) {
    //     // this.heading = updatedNode.heading;
    //     // this.type = updatedNode.type;
    //     // this.kanbanTask = updatedNode.kanbanTask;
    //     // this.children = updatedNode.children;
    //     // return true;
    //     // }
    //     return false;
    // }

    // @Override
    // public LinkSequence<Node> getChildrenOf(String status) {
    //     LinkSequence<Node> result = new LinkSequence<>();
    //     // Tree<T> root = this;
    //     // Tree<T> node = root.children.get(0);
    //     // if (status.equals("inProgress")) {
    //     // node = root.children.get(1);
    //     // } else if (status.equals("completed")) {
    //     // node = root.children.get(2);
    //     // }

    //     // if (node.children.length() == 0) {
    //     // return null;
    //     // }

    //     // for (int i = 0; i < node.children.length(); i++) {
    //     // result.add(node.children.get(i));
    //     // }
    //     return result;
    // }

    // // Call this method from the parent statusNodes.
    // @Override
    // public boolean findNode(Tree<T> node) {
    //     // TODO Auto-generated method stub
    //     return false;
    // }

    // @Override
    // public LinkSequence<Tree<T>> inorderTraveral(Tree<T> root) {
    //     // TODO Auto-generated method stub
    //     LinkSequence<Tree<T>> result = new LinkSequence<>();
    //     // if (root.children.length() == 0) {
    //     // result.add(root);
    //     // return result;
    //     // }
    //     // result.add(root);
    //     // for (int i = 0; i < root.children.length(); i++) {
    //     // inorderTraveral(root.children.get(i));
    //     // }
    //     return result;
    // }

    @Override
    public String toString() {
        return "Tree{" +
                // "nodeId=" + nodeId +
                // ", heading='" + heading + '\'' +
                // ", type='" + type + '\'' +
                // ", kanbanTask=" + kanbanTask +
                // ", children=" + children +
                '}';
    }

    // @Override
    // public LinkSequence<Tree<T>> preorderTraversal() {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public LinkSequence<Tree<T>> postorderTraversal() {
    // // TODO Auto-generated method stub
    //
    // }

}