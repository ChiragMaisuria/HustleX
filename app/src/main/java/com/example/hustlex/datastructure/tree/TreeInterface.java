package com.example.hustlex.datastructure.tree;

import com.example.hustlex.datastructure.linksequence.LinkSequence;

public interface TreeInterface<T> {

    /**
     * Find the parent of the certain node based on the status value.
     */
    public Tree<T>.Node findTheParent(Tree<T>.Node node, String status);

    /**
     * Create the node for the tree.
     */
    public Tree<T>.Node createNode(String heading, String type, T kanbanTask);

    /**
     * Add a new node in the tree.
     */
    boolean addNode(Tree<T>.Node newNode, String status);

    /**
     * Deletes a node from the tree.
     */
    boolean removeNode(Tree<T>.Node node, String status);

    /**
     * Transfers a node from the tree under one status to another.
     */
    boolean transferTo(Tree<T>.Node node, String fromStatus, String toStatus);

    /**
     * Display the tree.
     */
    String displayTree();

    /**
     * Helper method: Display the tree.
     */
    String viewTree(Tree<T>.Node node, int level, StringBuffer strBuff);

    /**
     * Finds the node and returns all the children of that node.
     *
     * */
    LinkSequence<Tree<T>.Node> getChildrenOf(String status);

    /**
     * Returns the total number of nodes in the tree.
     */
    int getTotalNodes();


    // /**
    // * Updates a node from the tree.
    // * */
    // boolean updateNode(Tree<T>.Node updateNode, String status);

    // /**
    // * Find the node in the tree.
    // * */
    // boolean findNode(Tree<T> node);

    // /**
    // * Performs the inorder Traveral of the tree.
    // * */
    // LinkSequence<Tree<T>> inorderTraveral(Tree<T> root);

    // /**
    // * Performs the preorder Traveral of the tree.
    // * */
    // LinkSequence<Tree<T>> preorderTraversal();
    //
    // /**
    // * Performs the postorder Traversal of the tree.
    // * */
    // LinkSequence<Tree<T>> postorderTraversal();

}
