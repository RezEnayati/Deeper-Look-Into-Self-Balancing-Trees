/*
Implementation of the Red-Black Tree using the BalanceTree interface
Plus two additional Methods statusRB and printRedBlack
statusRB: print the number of red nodes, black nodes and the black height.
printRedBlack: print the items and the node color in ascending order.
 */

public class RedBlack<E extends Comparable<E>> implements BalanceTree<E> {

    //MARK: - Setup ------------------------------------------------------------------
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node<E> root;
    private int blackHeight;

    //Node class for the RB tree
    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        boolean color;

        public Node(E element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.parent = null;
            this.color = RED; //new nodes are always red
        }
    }

    //Constructor:
    public RedBlack() {
        root = null;
        blackHeight = 0;
    }

    //MARK: - Helper Methods ------------------------------------------------------------------

    // Right rotation
    private void rightRotation(Node<E> y) {
        Node<E> x = y.left;
        y.left = x.right;

        if (x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        x.right = y;
        y.parent = x;
    }

    private void leftRotation(Node<E> x) {
        Node<E> y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private boolean getColor(Node<E> node) {
        return (node == null) ? BLACK : node.color;
    }

    private void setColor(Node<E> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    private Node<E> minValueNode(Node<E> node) {
        Node<E> curr = node;
        while(curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    private int height(Node<E> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private void calcBlackHeight() {
        blackHeight = getBlackHeight(root);
    }

    private int getBlackHeight(Node<E> node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getBlackHeight(node.left);
        int rightHeight = getBlackHeight(node.right);

        int add = (getColor(node) == BLACK) ? 1 : 0;

        return add + Math.max(leftHeight, rightHeight);
    }

    private void fixInsert(Node<E> node) {
        Node<E> parent = null;
        Node<E> grandParent = null;

        while (node != root && getColor(node) == RED && getColor(node.parent) == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node<E> uncle = grandParent.right;

                if (getColor(uncle) == RED) {
                    setColor(grandParent, RED);
                    setColor(parent, BLACK);
                    setColor(uncle, BLACK);
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        leftRotation(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    rightRotation(grandParent);
                    boolean tempColor = getColor(parent);
                    setColor(parent, getColor(grandParent));
                    setColor(grandParent, tempColor);
                    node = parent;
                }
            } else { // Parent is right child of granparent
                Node<E> uncle = grandParent.left;

                if (getColor(uncle) == RED) {
                    setColor(grandParent, RED);
                    setColor(parent, BLACK);
                    setColor(uncle, BLACK);
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        rightRotation(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    leftRotation(grandParent);
                    boolean tempColor = getColor(parent);
                    setColor(parent, getColor(grandParent));
                    setColor(grandParent, tempColor);
                    node = parent;
                }
            }
        }
        // root is always black
        setColor(root, BLACK);
    }

    private boolean find(Node<E> node, E item) {
        if (node == null) {
            return false;
        }

        int cmp = item.compareTo(node.element);

        if (cmp < 0) {
            return find(node.left, item);
        } else if (cmp > 0) {
            return find(node.right, item);
        } else {
            return true;
        }
    }

    private int countBlackNodes(Node<E> node) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (getColor(node) == BLACK) {
            count = 1;
        }

        return count + countBlackNodes(node.left) + countBlackNodes(node.right);
    }

    private int countRedNodes(Node<E> node) {
        if(node == null) {
            return 0;
        }
        int c = (getColor(node) == RED) ? 1 : 0;

        return c + countRedNodes(node.left) + countRedNodes(node.right);
    }

    private void printRedBlack(Node<E> node, StringBuilder sb) {
        if(node != null) {
            printRedBlack(node.left, sb);
            sb.append("(").append(node.element).append(",").append(getColor(node) ? "R" : "B").append(")");
            printRedBlack(node.right, sb);
        }
    }

    //MARK: - Interface Methods ------------------------------------------------------------------

    @Override
    public void insert(E item) {
        Node<E> newNode = new Node<>(item);

        if (root == null) {
            root = newNode;
        } else {
            Node<E> current = root;
            Node<E> parent = null;

            while (current != null) {
                parent = current;
                int cmp = item.compareTo(current.element);

                if (cmp < 0) {
                    current = current.left;
                } else if (cmp > 0) {
                    current = current.right;
                } else {
                    return;
                }
            }
            newNode.parent = parent;
            int cmp = item.compareTo(parent.element);

            if (cmp < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }

        // Fix Red-Black properties
        fixInsert(newNode);
        calcBlackHeight();
    }

    @Override
    public boolean find(E item) {
        return find(root, item);
    }

    // Transplants one subtree for another
    private void transplant(Node<E> u, Node<E> v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    @Override
    public void delete(E item) {
        deleteNode(item);
        calcBlackHeight();
    }

    private void deleteNode(E item) {
        // Find the node to delete
        Node<E> z = root;
        while (z != null) {
            int cmp = item.compareTo(z.element);
            if (cmp < 0) {
                z = z.left;
            } else if (cmp > 0) {
                z = z.right;
            } else {
                break; // Found the node
            }
        }

        if (z == null) {
            return; // Item not found
        }

        Node<E> y = z; // y is the node that will be removed or movedd
        boolean originalColor = y.color;
        Node<E> x; // x is the node that will replace y

        if (z.left == null) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minValueNode(z.right);
            originalColor = y.color;
            x = y.right;

            if (y.parent == z) {
                if (x != null) {
                    x.parent = y;
                }
            } else {
                transplant(y, y.right);
                y.right = z.right;
                if (y.right != null) {
                    y.right.parent = y;
                }
            }

            transplant(z, y);
            y.left = z.left;
            if (y.left != null) {
                y.left.parent = y;
            }
            y.color = z.color;
        }

        // If the original coloer was BLACK, we need to fix the tree
        if (originalColor == BLACK) {
            fixDelete(x);
        }
    }

    private void fixDelete(Node<E> x) {
        while (x != null && x != root && getColor(x) == BLACK) {
            if (x.parent != null) { // Check if parent exists
                if (x == x.parent.left) {
                    Node<E> w = x.parent.right;

                    // Case 1: x's sibling is red
                    if (getColor(w) == RED) {
                        setColor(w, BLACK);
                        setColor(x.parent, RED);
                        leftRotation(x.parent);
                        w = x.parent.right;
                    }


                    if (getColor(w.left) == BLACK && getColor(w.right) == BLACK) {
                        setColor(w, RED);
                        x = x.parent;
                    } else {
                        // Case 3: x's sibling has a red left child and black right child
                        if (getColor(w.right) == BLACK) {
                            setColor(w.left, BLACK);
                            setColor(w, RED);
                            rightRotation(w);
                            w = x.parent.right;
                        }

                        // Case 4: x's sibling has a red right child
                        setColor(w, getColor(x.parent));
                        setColor(x.parent, BLACK);
                        setColor(w.right, BLACK);
                        leftRotation(x.parent);
                        x = root; // End the loop
                    }
                } else {
                    Node<E> w = x.parent.left;

                    // Case 1: x's sibling is red
                    if (getColor(w) == RED) {
                        setColor(w, BLACK);
                        setColor(x.parent, RED);
                        rightRotation(x.parent);
                        w = x.parent.left;
                    }

                    // Case 2: x's sibling has two black children
                    if (getColor(w.right) == BLACK && getColor(w.left) == BLACK) {
                        setColor(w, RED);
                        x = x.parent;
                    } else {
                        // Case 3: x's sibling has a red right child and black left child
                        if (getColor(w.left) == BLACK) {
                            setColor(w.right, BLACK);
                            setColor(w, RED);
                            leftRotation(w);
                            w = x.parent.left;
                        }


                        setColor(w, getColor(x.parent));
                        setColor(x.parent, BLACK);
                        setColor(w.left, BLACK);
                        rightRotation(x.parent);
                        x = root; // End the loop
                    }
                }
            } else {
                break; // Parent is null, can't continue
            }
        }

        if (x != null) {
            setColor(x, BLACK);
        }
    }

    @Override
    public int height() {
        return height(root);
    }

    //MARK: - Additional Methods ------------------------------------------------------------------

    public void statusRB() {
        int redCount = countRedNodes(root);
        int blackCount = countBlackNodes(root);
        System.out.println("R = " + redCount + " B = " + blackCount + " BH = " + blackHeight);
    }

    public void printRedBlack() {
        StringBuilder sb = new StringBuilder();
        printRedBlack(root, sb);
        System.out.print(sb.toString());
    }
}