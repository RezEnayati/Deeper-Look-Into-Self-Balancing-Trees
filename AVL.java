/*
Implementation of the AVL Tree using the BalanceTree interface
Plus two additional Methods heightAVL and printAVL
HeightAVL: Prints the height of the node containing E item
printAVL: Print the item and the corresponding balance factor for the item.
 */

import javax.lang.model.type.MirroredTypeException;
import javax.swing.*;
import java.security.PrivateKey;

public class AVL<E extends Comparable<E>> implements BalanceTree<E> {

    //MARK: - Setup ------------------------------------------------------------------

    //root of the AVL
    private Node<E> root;

    //Node Class for AVL
    private static class Node<E>{
        E element;
        Node<E> right;
        Node<E> left;
        int height;

        //Constructor for Node class
        public Node(E element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.height = 0;
        }
    }

    //Constructor for AVL class
    public AVL() {
        root = null;
    }

    //MARK: - Helper Methods ------------------------------------------------------------------

    //Get the height of the Node
    private int height(Node<E> node) {
        //if the node is null return -1
        return (node == null) ? -1 : node.height;
    }

    //Calc the Balance Factor
    private int getBalance(Node<E> node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    //Right Rotation
    private Node<E> rightRotate(Node<E> y) {
        Node<E> x = y.left;
        Node<E> temp = x.right;

        // do the rotation
        // noinspection SuspiciousAssignment
        x.right = y;
        y.left =  temp;

        //Change the heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    //Left Rotation
    private Node<E> leftRotation(Node<E> x) {
        Node<E> y = x.right;
        Node<E> temp = y.left;

        //Perform Rotation
        y.left = x;
        x.right = temp;

        //Update the heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    //Find the Node with the minimum value
    private Node<E> minValueNode(Node<E> node) {
        Node<E> curr = node;
        while(curr.left != null)
            curr = curr.left;
        return curr;
    }

    private Node<E> findNode(Node<E> node, E item) {
        if (node == null)
            return null;

        int cmp = item.compareTo(node.element);
        if (cmp < 0)
            return findNode(node.left, item);
        else if (cmp > 0)
            return findNode(node.right, item);
        else
            return node;
    }

    private void printAVL(Node<E> node,  StringBuilder sb) {
        if(node != null) {
            printAVL(node.left, sb);
            sb.append("(").append(node.element).append(",").append(getBalance(node)).append(")");
            printAVL(node.right, sb);
        }
    }

    //MARK: - Interface Methods ------------------------------------------------------------------
    @Override
    public void insert(E item) {
        root = insert(root, item);
    }

    //Insert Method
    private Node<E> insert(Node<E> node,  E item) {
        // BST Insert
        if (node == null)
            return new Node<>(item);

        int cmp = item.compareTo(node.element);

        if (cmp < 0)
            node.left = insert(node.left, item);
        else if (cmp > 0)
            node.right = insert(node.right, item);
        else
            return node;

        //update height of the curr
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Balance Factor
        int balance = getBalance(node);

        // LL
        if (balance > 1 && item.compareTo(node.left.element) < 0) {
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && item.compareTo(node.right.element) > 0) {
            return leftRotation(node);
        }

        // LR
        if (balance > 1 && item.compareTo(node.left.element) > 0) {
            node.left = leftRotation(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && item.compareTo(node.right.element) < 0) {
            node.right = rightRotate(node.right);
            return leftRotation(node);
        }

        return node;
    }

    @Override
    public boolean find(E item) {
        return find(root, item);
    }

    // Find Method
    private boolean find(Node<E> node,  E item) {
        if (node == null)
            return false;

        int cmp = item.compareTo(node.element);
        if (cmp < 0)
            return find(node.left, item);
        else if (cmp > 0)
            return find(node.right, item);
        else
            return true;
    }

    @Override
    public void delete(E item) {
        root = delete(root, item);
    }

    private Node<E> delete(Node<E> root, E item) {
        // BST Delete
        if (root == null) {
            return root;
        }

        int cmp = item.compareTo(root.element);
        if (cmp < 0)
            root.left = delete(root.left, item);
        if (cmp > 0)
            root.right = delete(root.right, item);
        else {
            // One or no children
            if ((root.left == null) || (root.right == null)) {
                Node<E> temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child
                    root = temp;
            } else {
                // inorder successor
                Node<E> temp = minValueNode(root.right);
                root.element = temp.element;
                root.right = delete(root.right, temp.element);
            }
        }

        //If one node only
        if (root == null)
            return root;

        //update height
        root.height = Math.max(height(root.left), height(root.right))+ 1;

        int balance = getBalance(root);

        //LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0 ) {
            root.left = leftRotation(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotation(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotation(root);
        }
        return root;

    }

    @Override
    public int height() {
        return height(root);
    }

    //MARK: - Additional Methods ------------------------------------------------------------------

    public void heightAVL(E item) {
        Node<E> node = findNode(root, item);
        if (node != null) {
            System.out.println("node height = " + node.height);
        } else {
            System.out.println("Item not found.");
        }
    }

    public void printAVL() {
        StringBuilder sb = new StringBuilder();
        printAVL(root, sb);
        System.out.print(sb.toString());
    }
}
