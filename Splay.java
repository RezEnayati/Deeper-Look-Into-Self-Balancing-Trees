/*
Implementation of the Splay Tree using the BalanceTree interface
Plus two additional Methods printRoot and printSplay
printRoot: Prints the item contained in the root
printSplay: Prints the item and the node height in the tree
 */

public class Splay<E extends Comparable<E>> implements BalanceTree<E> {

    //MARK: - Setup ------------------------------------------------------------------

    //root of the Splay
    private Node<E> root;

    //Node class for the Splay
    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;

        public Node(E element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }

    //constructor for the SPlay
    public Splay() {
        root = null;
    }

    //MARK: - Helper Methods ------------------------------------------------------------------

    // Right Rotation
    private Node<E> rightRotation(Node<E> y) {
        Node<E> x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }

    //Left Rotation
    private Node<E> leftRotation(Node<E> x) {
        Node<E> y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    //splay operation
    private Node<E> splay(Node<E> root, E key) {

        if (root == null || key.compareTo(root.element) == 0) {
            return root;
        }
        if (key.compareTo(root.element) < 0) {

            if (root.left == null) {
                return root;
            }
            // Zig-Zig (Left Left)
            if (key.compareTo(root.left.element) < 0) {
                root.left.left = splay(root.left.left, key);
                root = rightRotation(root);
            }
            // Zig-Zag (Left Right)
            else if (key.compareTo(root.left.element) > 0) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null) {
                    root.left = leftRotation(root.left);
                }
            }
            return (root.left == null) ? root : rightRotation(root);
        }
        else {
            if (root.right == null) {
                return root;
            }
            // Zag-Zig (Right Left)
            if (key.compareTo(root.right.element) < 0) {
                // Recursively bring the key as root of right-left
                root.right.left = splay(root.right.left, key);
                // Do first rotation for root.right
                if (root.right.left != null) {
                    root.right = rightRotation(root.right);
                }
            }
            // Zag-Zag (Right Right)
            else if (key.compareTo(root.right.element) > 0) {

                root.right.right = splay(root.right.right, key);

                root = leftRotation(root);
            }
            return (root.right == null) ? root : leftRotation(root);
        }
    }

    private int height(Node<E> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private int nodeHeight(Node<E> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));
    }

    private void printSplay(Node<E> node,  StringBuilder sb) {
        if(node != null) {
            printSplay(node.left, sb);
            sb.append("(").append(node.element).append(",").append(nodeHeight(node)).append(")");
            printSplay(node.right, sb);
        }
    }

    //MARK: - Interface Methods ------------------------------------------------------------------

    @Override
    public void insert(E item) {
        // Tree is empty
        if (root == null) {
            root = new Node<>(item);
            return;
        }

        root = splay(root, item);

        if (item.compareTo(root.element) == 0) {
            return;
        }

        Node<E> newNode = new Node<>(item);

        if (item.compareTo(root.element) < 0) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        }

        else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }

        root = newNode;
    }

    @Override
    public boolean find(E item) {
        if (root == null) {
            return false;
        }
        root = splay(root, item);

        return item.compareTo(root.element) == 0;
    }

    @Override
    public void delete(E item) {
        if (root == null) {
            return;
        }

        root = splay(root, item);

        if (item.compareTo(root.element) != 0) {
            return;
        }

        if (root.left == null) {
            root = root.right;
        }
        else {
            Node<E> temp = root.right;
            root = root.left;
            root = splay(root, item);
            root.right = temp;
        }
    }

    @Override
    public int height() {
        return height(root);
    }

    //MARK: - Additional Methods ------------------------------------------------------------------

    public void printRoot() {
        if (root != null) {
            System.out.println("the root contains: " + root.element);
        } else {
            System.out.println("tree is empty");
        }
    }
    public void printSplay() {
        StringBuilder sb = new StringBuilder();
        printSplay(root, sb);
        System.out.print(sb.toString());
    }
}
