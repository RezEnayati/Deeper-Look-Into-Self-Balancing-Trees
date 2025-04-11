# Understanding Self-Balancing Trees (Implementation + Explanation)

In this repository, my main goal is to explain self-balancing trees, specifically AVL Trees, Splay Trees, Red-Black Trees, and 2-3-4 Trees. My approach will be to explain these concepts in a more grounded way, starting from the fundamentals and building up. The reason I mention this is because I recently came across a [tutorial](https://davetang.org/file/Singular_Value_Decomposition_Tutorial.pdf) on Singular Value Decomposition in Linear Algebra by Kirk Baker, and I absolutely loved his explanation style. I recommend this guide for students who already have some understanding of data structures, as it's focused more on **Self-Balancing Trees** and not as much on other tree structures, but I will explain the fundamentals to some extent. I should mention, though, that this explanation might not be the most complete or in-depth, but I’ll do my best to help you grasp a high-level understanding of these concepts, so use this at your own risk. But let’s get right to it.

## Tree
The Tree data structure is a way for us computer scientists to organize data in a hierarchical, non-linear way. When you think of trees in computer science, I want you to think of upside-down real-life trees. Unlike arrays and hash maps (or dictionaries), this data structure has depth, which lets us do some really cool stuff in a speedy way. Trees power some of the most important things we use almost daily, like file systems, sorting, searching, autocomplete, pathfinding, and optimization. Trees have specific components to them that you have to know in order to really understand this guide.
**Node:** An enitity containing the data which may have links to other nodes. 
**Edge:** A connection between two nodes.
**Root:** The top most node in the tree with no parents.
**Parent:** A node that has child nodes.
**Child:** A node that has a parent node.
**Leaf:** A node with no children.
**Subtree:** A tree formed by a node and its children. 
**Level:** The distance of a node from the root.
**Height:** The length of the longest path from a node to a leaf.
**Depth:** The lenght of the path from the root to a node. 

## Binary Tree 
The interesting thing here is that the name of this structure actually gives us two important pieces of information about the structure. First, it tells us that this is a tree, and second, the word "binary" indicates that it has something to do with "two" things. Binary Trees are a type of data structure where each node has at most **two children**, commonly referred to as the left and right node. Binary Trees can take on five different forms, which I’ll explain now:

**1. Full Binary Tree:** Every node has either 0 or 2 children.  
**2. Perfect Binary Tree:** All interior nodes have two children, and all leaves are at the same level.  
**3. Complete Binary Tree:** All levels are filled except possibly the last, which is filled from left to right.  
**4. Balanced Binary Tree:** A tree where the height is kept as small as possible.  
**5. Degenerate (Skewed) Tree:** Every parent has only one child, and the tree is more similar to a linked list. (This is where things start to get problematic.)

### Binary Search Trees
Binary Search Trees, or BSTs for short, are a type of binary tree with one important rule: for every node in the tree, all elements in the left subtree are less than the node, and all elements in the right subtree are greater than the node. This makes searching very efficient — O(log n). So what's the problem? Imagine a scenario where you're only inserting elements greater or smaller than the root. For example, let's say you insert 10, then 11, then 12, then 15. This will make the tree behave more like a linked list, and we don’t want that, since the whole point of trees is to make searching efficient. 
To solve this, we need to find a way to keep these trees as balanced as possible, in order to maintain that O(log n) search time.
The solution? **Self-Balancing Trees.**




