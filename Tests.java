public class Tests {

    // Test Cases for AVL
    static void testAVL() {
        System.out.println("===== Testing AVL Tree =====");
        AVL<Integer> avl = new AVL<>();

        System.out.println("Test 1: Inserting elements 10, 5, 15, 3, 7, 12, 20");
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.insert(3);
        avl.insert(7);
        avl.insert(12);
        avl.insert(20);

        System.out.println("\nTest 2: Print the AVL tree structure");
        System.out.print("AVL Tree (element, balance factor): ");
        avl.printAVL();
        System.out.println();

        System.out.println("\nTest 3: Find operations");
        System.out.println("Find 7: " + avl.find(7));
        System.out.println("Find 9: " + avl.find(9));

        System.out.println("\nTest 4: Get height of nodes");
        System.out.print("Height of node 10: ");
        avl.heightAVL(10);
        System.out.print("Height of node 5: ");
        avl.heightAVL(5);
        System.out.print("Height of node 15: ");
        avl.heightAVL(15);

        System.out.println("\nTest 5: Tree height");
        System.out.println("Tree height: " + avl.height());

        System.out.println("\nTest 6: Delete operations");
        System.out.println("Deleting 5...");
        avl.delete(5);
        System.out.print("AVL Tree after deletion: ");
        avl.printAVL();
        System.out.println();
        System.out.println("Find 5: " + avl.find(5));

        System.out.println("\nTest 7: Testing rotations");

        // Create a new tree for rotation testing
        AVL<Integer> rotationTest = new AVL<>();

        System.out.println("Inserting elements to test rotations: 30, 20, 40, 10, 25, 35, 50, 5, 15");
        rotationTest.insert(30);
        rotationTest.insert(20);
        rotationTest.insert(40);
        rotationTest.insert(10);
        rotationTest.insert(25);
        rotationTest.insert(35);
        rotationTest.insert(50);
        rotationTest.insert(5);
        rotationTest.insert(15);

        System.out.print("Final AVL tree: ");
        rotationTest.printAVL();
        System.out.println();
        System.out.println("Tree height: " + rotationTest.height());

        System.out.println("\nTest 8: Delete root");
        System.out.println("Deleting root node from original tree");
        avl.delete(10);
        System.out.print("AVL Tree after deleting root: ");
        avl.printAVL();
        System.out.println();
        System.out.println("Tree height after deletion: " + avl.height());
    }

    static void testSplay() {
        System.out.println("===== Testing Splay Tree =====");
        Splay<Integer> splay = new Splay<>();

        System.out.println("Test 1: Inserting elements 10, 20, 5, 30, 15, 25");
        splay.insert(10);
        System.out.print("Root after inserting 10: ");
        splay.printRoot();

        splay.insert(20);
        System.out.print("Root after inserting 20: ");
        splay.printRoot();

        splay.insert(5);
        System.out.print("Root after inserting 5: ");
        splay.printRoot();

        splay.insert(30);
        System.out.print("Root after inserting 30: ");
        splay.printRoot();

        splay.insert(15);
        System.out.print("Root after inserting 15: ");
        splay.printRoot();

        splay.insert(25);
        System.out.print("Root after inserting 25: ");
        splay.printRoot();

        System.out.println("\nTest 2: Print the Splay tree structure");
        System.out.print("Splay Tree (element, height): ");
        splay.printSplay();
        System.out.println();

        System.out.println("\nTest 3: Find operations");
        System.out.println("Find 15: " + splay.find(15));
        System.out.print("Root after finding 15: ");
        splay.printRoot();

        System.out.println("Find 40: " + splay.find(40));  // Should be false
        System.out.print("Root after finding 40 (should be closest value): ");
        splay.printRoot();

        System.out.println("\nTest 4: Tree height");
        System.out.println("Tree height: " + splay.height());

        System.out.println("\nTest 5: Delete operations");
        System.out.println("Deleting 15...");
        splay.delete(15);
        System.out.print("Splay Tree after deletion: ");
        splay.printSplay();
        System.out.println();
        System.out.print("Root after deleting 15: ");
        splay.printRoot();

        System.out.println("Find 15 after deletion: " + splay.find(15));

        System.out.println("\nTest 6: Delete root");
        System.out.print("Current root: ");
        splay.printRoot();

        System.out.println("Deleting root...");
        splay.delete(10);
        System.out.print("Root after deletion: ");
        splay.printRoot();

        System.out.println("\nTest 7: Final tree");
        System.out.print("Final Splay Tree: ");
        splay.printSplay();
        System.out.println();
        System.out.println("Final tree height: " + splay.height());
    }

    static void testRedBlack() {
        System.out.println("===== Testing Red-Black Tree =====");
        RedBlack<Integer> rb = new RedBlack<>();

        System.out.println("Test 1: Inserting elements 10, 20, 30, 15, 25, 5, 1, 7");
        rb.insert(10);
        rb.insert(20);
        rb.insert(30);
        rb.insert(15);
        rb.insert(25);
        rb.insert(5);
        rb.insert(1);
        rb.insert(7);

        System.out.println("\nTest 2: Print the Red-Black tree structure");
        System.out.print("Red-Black Tree (element, color): ");
        rb.printRedBlack();
        System.out.println();

        System.out.println("\nTest 3: Get status of Red-Black tree");
        rb.statusRB();

        System.out.println("\nTest 4: Find operations");
        System.out.println("Find 15: " + rb.find(15));  // Should be true
        System.out.println("Find 40: " + rb.find(40));  // Should be false

        System.out.println("\nTest 5: Tree height");
        System.out.println("Tree height: " + rb.height());

        System.out.println("\nTest 6: Delete operations");
        System.out.println("Deleting 15...");
        rb.delete(15);
        System.out.print("Red-Black Tree after deletion: ");
        rb.printRedBlack();
        System.out.println();
        System.out.println("Find 15 after deletion: " + rb.find(15));  // Should be false

        System.out.println("\nTest 7: Updated status after deletion");
        rb.statusRB();

        System.out.println("\nTest 8: Delete important node");
        System.out.println("Deleting 10...");
        rb.delete(10);
        System.out.print("Red-Black Tree after deleting 10: ");
        rb.printRedBlack();
        System.out.println();
        rb.statusRB();

        System.out.println("\nTest 9: Final tree height");
        System.out.println("Final tree height: " + rb.height());
    }

    static void testTree234() {
        System.out.println("===== Testing 2-3-4 Tree =====");
        Tree234<Integer> tree = new Tree234<>();

        System.out.println("Test 1: Inserting elements 10, 20, 30, 5, 15, 25, 35");
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(5);
        tree.insert(15);
        tree.insert(25);
        tree.insert(35);

        System.out.println("\nTest 2: Print the 2-3-4 tree structure");
        System.out.print("2-3-4 Tree (element, node type): ");
        tree.print234();
        System.out.println();

        System.out.println("\nTest 3: Get status of 2-3-4 tree");
        tree.status234();

        System.out.println("\nTest 4: Find operations");
        System.out.println("Find 15: " + tree.find(15));
        System.out.println("Find 40: " + tree.find(40));

        System.out.println("\nTest 5: Tree height");
        System.out.println("Tree height: " + tree.height());

        System.out.println("\nTest 6: Delete operations");
        System.out.println("Deleting 15...");
        tree.delete(15);
        System.out.print("2-3-4 Tree after deletion: ");
        tree.print234();
        System.out.println();
        System.out.println("Find 15 after deletion: " + tree.find(15));

        System.out.println("\nTest 7: Updated status after deletion");
        tree.status234();

        System.out.println("\nTest 8: Insert more elements to test node splitting");
        System.out.println("Inserting elements 40, 45, 50, 55");
        tree.insert(40);
        tree.insert(45);
        tree.insert(50);
        tree.insert(55);
        System.out.print("2-3-4 Tree after additional insertions: ");
        tree.print234();
        System.out.println();
        tree.status234();

        System.out.println("\nTest 9: Delete multiple elements to test node merging");
        System.out.println("Deleting elements 10, 20, 30");
        tree.delete(10);
        tree.delete(20);
        tree.delete(30);
        System.out.print("2-3-4 Tree after multiple deletions: ");
        tree.print234();
        System.out.println();
        tree.status234();

        System.out.println("\nTest 10: Final tree height");
        System.out.println("Final tree height: " + tree.height());
    }
}
