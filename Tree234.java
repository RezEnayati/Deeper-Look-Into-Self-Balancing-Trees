/*
Implementation of the 2-3-4 Tree using the BalanceTree interface
Plus two additional Methods status234 and print234
status234: Prints the item contained in the root
print234: Prints the item and the node height in the tree
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree234<E extends Comparable<E>> implements BalanceTree<E> {

    //MARK: - Setup ------------------------------------------------------------------
    private Node<E> root;
    private int twoNodeCount = 0;
    private int threeNodeCount = 0;
    private int fourNodeCount = 0;

    //Node class for 231
    private static class Node<E> {
        List<E> keys;
        List<Node<E>> children;

        public Node() {
            keys = new ArrayList<>();
            children = new ArrayList<>();
        }

        public boolean isLeaf() {
            return children.isEmpty();
        }

        public int getType() {
            return keys.size() + 1;
        }
    }

    // Constructor
    public Tree234() {
        root = new Node<>();
    }

    //MARK: - Helper Methods ------------------------------------------------------------------
    // For insertion into the node
    public void instertNode(Node<E> node, E item) {
        int i = 0;
        while (i < node.keys.size() && item.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.keys.size() && item.compareTo(node.keys.get(i)) == 0) {
            return;
        }

        if (node.isLeaf()) {
            node.keys.add(i, item);
            return;
        }

        Node<E> childNode = node.children.get(i);
        if (childNode.keys.size() == 3) {
            splitChild(node, i);

            if (item.compareTo(node.keys.get(i)) > 0) {
                childNode = node.children.get(i + 1);
            } else {
                childNode = node.children.get(i);
            }
        }

        // Continue insertion process
        instertNode(childNode, item);
    }

    private void splitChild(Node<E> parent, int childIndex) {
        Node<E> child = parent.children.get(childIndex);
        Node<E> newChild = new Node<>();

        E middleKey = child.keys.get(1);
        parent.keys.add(childIndex, middleKey);

        newChild.keys.add(child.keys.get(2));

        if (!child.isLeaf()) {
            newChild.children.add(child.children.get(2));
            newChild.children.add(child.children.get(3));

            child.children.remove(3);
            child.children.remove(2);
        }

        child.keys.remove(2);
        child.keys.remove(1);

        parent.children.add(childIndex + 1, newChild);
    }

    private boolean find(Node<E> node, E item) {
        int i = 0;

        while (i < node.keys.size() && item.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.keys.size() && item.compareTo(node.keys.get(i)) == 0) {
            return true;
        }

        if (node.isLeaf()) {
            return false;
        }

        return find(node.children.get(i), item);
    }

    private void delete(Node<E> node, E item) {
        int i = 0;

        while (i < node.keys.size() && item.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        // Case 1: Item found in current node
        if (i < node.keys.size() && item.compareTo(node.keys.get(i)) == 0) {
            if (node.isLeaf()) {
                node.keys.remove(i);
                return;
            }
            else {
                Node<E> predecessor = node.children.get(i);
                while (!predecessor.isLeaf()) {
                    minKey(predecessor);
                    predecessor = predecessor.children.get(predecessor.children.size() - 1);
                }

                if (!predecessor.keys.isEmpty()) {
                    node.keys.set(i, predecessor.keys.get(predecessor.keys.size() - 1));
                    delete(predecessor, predecessor.keys.get(predecessor.keys.size() - 1));
                }
            }
        }
        // Case 2: Item not found in current node
        else {
            if (node.isLeaf()) {
                return;
            }
            minKey(node.children.get(i));
            delete(node.children.get(i), item);
        }
    }

    private void minKey(Node<E> node) {
        // Fine in these two situations : 1
        if (node.keys.size() >= 2) {
            return;
        }
        // 2
        if (node == root) {
            return;
        }

        // find the node's index in its parent
        Node<E> parent = findParent(root, node);
        int nodeIndex = parent.children.indexOf(node);

        // Try to borrow from left sibling
        if (nodeIndex > 0) {
            Node<E> leftSibling = parent.children.get(nodeIndex - 1);

            if (leftSibling.keys.size() >= 2) {
                node.keys.add(0, parent.keys.get(nodeIndex - 1));

                parent.keys.set(nodeIndex - 1, leftSibling.keys.get(leftSibling.keys.size() - 1));
                leftSibling.keys.remove(leftSibling.keys.size() - 1);

                if (!leftSibling.isLeaf()) {
                    node.children.add(0, leftSibling.children.get(leftSibling.children.size() - 1));
                    leftSibling.children.remove(leftSibling.children.size() - 1);
                }

                return;
            }
        }

        // Try to borrow from right sibling
        if (nodeIndex < parent.children.size() - 1) {
            Node<E> rightSibling = parent.children.get(nodeIndex + 1);

            if (rightSibling.keys.size() >= 2) {

                node.keys.add(parent.keys.get(nodeIndex));

                parent.keys.set(nodeIndex, rightSibling.keys.get(0));
                rightSibling.keys.remove(0);

                if (!rightSibling.isLeaf()) {
                    node.children.add(rightSibling.children.get(0));
                    rightSibling.children.remove(0);
                }

                return;
            }
        }

        if (nodeIndex > 0) {

            mergeNodes(parent, nodeIndex - 1);
        } else {
            // Merge with right sibling
            mergeNodes(parent, nodeIndex);
        }
    }

    private Node<E> findParent(Node<E> current, Node<E> target) {
        if (current.children.contains(target)) {
            return current;
        }

        for (Node<E> child : current.children) {
            Node<E> result = findParent(child, target);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    private void mergeNodes(Node<E> parent, int keyIndex) {
        Node<E> leftChild = parent.children.get(keyIndex);
        Node<E> rightChild = parent.children.get(keyIndex + 1);

        leftChild.keys.add(parent.keys.get(keyIndex));
        parent.keys.remove(keyIndex);

        // Move all keys from right child to left child
        leftChild.keys.addAll(rightChild.keys);

        // Move all children from right child to left child
        if (!rightChild.isLeaf()) {
            leftChild.children.addAll(rightChild.children);
        }
        parent.children.remove(keyIndex + 1);
    }

    private void countNodes(Node<E> node) {
        if (node == null) {
            return;
        }

        int type = node.getType();
        if (type == 2) {
            twoNodeCount++;
        } else if (type == 3) {
            threeNodeCount++;
        } else if (type == 4) {
            fourNodeCount++;
        }

        for (Node<E> child : node.children) {
            countNodes(child);
        }
    }

    private void updateNodeCount() {
        twoNodeCount = 0;
        threeNodeCount = 0;
        fourNodeCount = 0;
        countNodes(root);
    }

    private Node<E> findNodeWith(Node<E> node, E item) {
        int i = 0;
        while (i < node.keys.size() && item.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.keys.size() && item.compareTo(node.keys.get(i)) == 0) {
            return node;
        }

        if (node.isLeaf()) {
            return null;
        }

        return findNodeWith(node.children.get(i), item);
    }

    private void collectElements(Node<E> node, List<E> elements) {
        if (node == null) {
            return;
        }

        elements.addAll(node.keys);

        for (Node<E> child : node.children) {
            collectElements(child, elements);
        }
    }

    private int height(Node<E> node) {
        if (node.isLeaf()) {
            return 0;
        }
        return 1 + height(node.children.get(0));
    }

    //MARK: - Interface Methods ------------------------------------------------------------------


    @Override
    public void insert(E item) {
        if (root.keys.isEmpty()) {
            root.keys.add(item);
            updateNodeCount();
            return;
        }


        if (root.keys.size() == 3) {
            Node<E> newRoot = new Node<>();
            Node<E> newRight = new Node<>();

            // Middle key moves up
            newRoot.keys.add(root.keys.get(1));

            newRight.keys.add(root.keys.get(2));


            if (!root.isLeaf()) {
                newRight.children.add(root.children.get(2));
                newRight.children.add(root.children.get(3));

                root.children.remove(3);
                root.children.remove(2);
            }

            // Remove keys that moved to new nodes
            root.keys.remove(2);
            root.keys.remove(1);

            newRoot.children.add(root);
            newRoot.children.add(newRight);

            root = newRoot;
        }

        instertNode(root, item);
        updateNodeCount();
    }

    @Override
    public boolean find(E item) {
        return find(root, item);
    }

    @Override
    public void delete(E item) {
        delete(root, item);

        if (root.keys.isEmpty() && !root.children.isEmpty()) {
            root = root.children.get(0);
        }

        updateNodeCount();
    }

    @Override
    public int height() {
        return height(root);
    }

    //MARK: - Additional Methods ------------------------------------------------------------------
    public void status234() {
        System.out.println("two = " + twoNodeCount + " three = " + threeNodeCount + " four = " + fourNodeCount);
    }
    public void print234() {
        List<E> allElements = new ArrayList<>();
        collectElements(root, allElements);
        Collections.sort(allElements);

        StringBuilder sb = new StringBuilder();
        for (E element : allElements) {
            Node<E> node = findNodeWith(root, element);
            String nodeType = "";
            switch (node.getType()) {
                case 2: nodeType = "two"; break;
                case 3: nodeType = "three"; break;
                case 4: nodeType = "four"; break;
            }
            sb.append("(").append(element).append(", ").append(nodeType).append(") ");
        }
        System.out.print(sb.toString());
    }
}
