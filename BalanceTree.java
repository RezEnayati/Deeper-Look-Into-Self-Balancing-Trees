
/*
Interface Containing:
    1. Insert: Adds an Item to the tree
    2. Find: Returns True if the item is found
    3. Delete: Deletes an Item
    4. Height: Returns the Height of the tree
 */

public interface BalanceTree<E extends Comparable<E>> {
    public void insert(E item);
    public boolean find(E item);
    public void delete(E item);
    public int height();
}
