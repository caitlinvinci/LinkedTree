/*
 * LinkedTree.java
 *
 * Computer Science E-119
 *
 * Modifications and additions by:
 *     name:Caitlin Vinci
 *     username:caitlinvinci@gmail.com
 * 11/15/2016
 */

import java.util.*;

/**
 * LinkedTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class LinkedTree {
    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private LLList data;     // the data items associated with this key
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree
        private Node parent;     // reference to the parent
        
        private Node(int key, Object data, Node left, Node right, Node parent){
            this.key = key;
            this.data = new LLList();
            this.data.addItem(data, 0);
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
        
        private Node(int key, Object data) {
            this(key, data, null, null, null);
        }
        
        //The constructor below allows for the parent node to be added
        //in public methods such as insert 
        private Node(int key, Object data, Node parent) {
            this(key, data, null, null, parent);
        } 
        
    }
    
    // the root of the tree as a whole
    private Node root;
    
    public LinkedTree() {
        root = null;
    }
    
    /**
     * Prints the keys of the tree in the order given by a preorder traversal.
     * Invokes the recursive preorderPrintTree method to do the work.
     */
    public void preorderPrint() {
        if (root != null)
            preorderPrintTree(root);      
    }
    

    
    /*
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void preorderPrintTree(Node root) {
        System.out.print(root.key + " ");
        if (root.left != null)
            preorderPrintTree(root.left);
        if (root.right != null)
            preorderPrintTree(root.right);
    }
    
    /**
     * Prints the keys of the tree in the order given by a postorder traversal.
     * Invokes the recursive postorderPrintTree method to do the work.
     */
    public void postorderPrint() {
        if (root != null)
            postorderPrintTree(root);      
    }
    
    /*
     * Recursively performs a postorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void postorderPrintTree(Node root) {
        if (root.left != null)
            postorderPrintTree(root.left);
        if (root.right != null)
            postorderPrintTree(root.right);
        System.out.print(root.key + " ");
    }
    
    /**
     * Prints the keys of the tree in the order given by an inorder traversal.
     * Invokes the recursive inorderPrintTree method to do the work.
     */
    public void inorderPrint() {
        if (root != null)
            inorderPrintTree(root);      
    }
    
    /*
     * Recursively performs an inorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void inorderPrintTree(Node root) {
        if (root.left != null)
            inorderPrintTree(root.left);
        System.out.print(root.key + " ");
        if (root.right != null)
            inorderPrintTree(root.right);
    }
    
    /**
     * Searches for the specified key in the tree.
     * Invokes the recursive searchTree method to perform the actual search.
     * Returns the LLList containing the items associated with the key.
     */
    public LLList search(int key) {
        Node n = searchTree(root, key);
        return (n == null ? null : n.data);
    }
    
    /*
     * Recursively searches for the specified key in the tree/subtree
     * whose root is specified. Note that the parameter is *not*
     * necessarily the root of the entire tree.
     */
    private static Node searchTree(Node root, int key) {
        if (root == null)
            return null;
        else if (key == root.key)
            return root;
        else if (key < root.key)
            return searchTree(root.left, key);
        else
            return searchTree(root.right, key);
    }
    
    /**
     * Inserts the specified (key, data) pair in the tree so that the
     * tree remains a binary search tree.
     */
    public void insert(int key, Object data) {
        // Find the parent of the new node.
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                trav.data.addItem(data, 0);
                return;
            }
            parent = trav;
            if (key < trav.key)
                trav = trav.left;
            else
                trav = trav.right;
        }
        
        // Insert the new node.
        Node newNode = new Node(key, data, parent);            //add parent info
        if (parent == null)    // the tree was empty
            root = newNode;
        else if (key < parent.key)
            parent.left = newNode;
        else  
            parent.right = newNode;
    }
    
    /**
     * Deletes the node containing the (key, data) pair with the
     * specified key from the tree and return the associated data item.
     * Returns the LLList containing the items associated with the key.
     */
    public LLList delete(int key) {
        // Find the node to be deleted and its parent.
        Node parent = null;
        Node trav = root;
        while (trav != null && trav.key != key) {
            parent = trav;
            if (key < trav.key)
                trav = trav.left;
            else
                trav = trav.right;
        }
        
        // Delete the node (if any) and return the removed data item.
        if (trav == null)   // no such key    
            return null;
        else {
            LLList removedData = trav.data;
            deleteNode(trav, parent);
            return removedData;
        }
    }
    
    /**
     * Deletes the node specified by the parameter toDelete.  parent
     * specifies the parent of the node to be deleted. 
     */
    private void deleteNode(Node toDelete, Node parent) {
        if (toDelete.left != null && toDelete.right != null) {
            // Case 3: toDelete has two children.
            // Find a replacement for the item we're deleting -- as well as 
            // the replacement's parent.
            // We use the smallest item in toDelete's right subtree as
            // the replacement.
            Node replaceParent = toDelete;
            Node replace = toDelete.right;
            while (replace.left != null) {
                replaceParent = replace;
                replace = replace.left;
            }
            
            // Replace toDelete's key and data with those of the 
            // replacement item.
            toDelete.key = replace.key;
            toDelete.data = replace.data;
            
            // Recursively delete the replacement item's old node.
            // It has at most one child, so we don't have to
            // worry about infinite recursion.
            deleteNode(replace, replaceParent);
        } else {
            // Cases 1 and 2: toDelete has 0 or 1 child
            Node toDeleteChild;
            if (toDelete.left != null)
                toDeleteChild = toDelete.left;
            else
                toDeleteChild = toDelete.right;  // null if it has no children
            
            if (toDelete == root)
                root = toDeleteChild;
            else if (toDelete.key < parent.key)
                parent.left = toDeleteChild;
            else
                parent.right = toDeleteChild;
        }
    }
    
    /**
     * Counts and returns the number of even keys in the tree.
     * It invokes the recursive numEvenKeysTree method to do the work.
     */
    public int numEvenKeys() {
        if (root == null) 
            return -1; 
        return numEvenKeysTree(root);     // begin at root of the entire tree
    }
    
    /*
     * Recursively counts and returns the number of even keys in the
     * tree/subtree whose root is specified.  Note that the parameter root
     * is *not* necessarily the root of the entire tree.
     */
    private static int numEvenKeysTree(Node root) {
        /*** implement this method for PS 4 ***/
        int numEven = 0; 
        if (root == null) {
            return numEven;
        }
        if (root.key % 2 == 0)
            numEven++;
        if (root.left == null && root.right == null) {
            return numEven;
        }
        else numEven += numEvenKeysTree(root.right) + numEvenKeysTree(root.left); 
            
        return numEven;
    }
    
    /**
     * Prints all keys in the tree that are in the range [lower,upper].
     * It invokes the recursive rangeSearchTree method to do the work.
     */
    public void rangeSearch(int lower, int upper) {
        if (lower > upper)
            throw new IllegalArgumentException("lower > upper");
        
        if (root != null)
            rangeSearchTree(root, lower, upper);
    }
    
    /*
     * Recursively searches for keys in the range [lower,upper] in the
     * tree/subtree whose root is specified.  Note that the parameter root
     * is *not* necessarily the root of the entire tree.
     */
    private static void rangeSearchTree(Node root, int lower, int upper) {
        /*** implement this method for PS 4 ***/
        if (root == null)
            return;
        if (root.left != null && (root.left.key + 1) >= lower) {
            rangeSearchTree (root.left, lower, upper);
        }
        //The next if clause is to print the key if its in range
        //this is an inorder traversal. 
        
        if (root.key >= lower && root.key <= upper) {
            System.out.print(root.key + " ");
        }
        
        if (root.left != null && (root.key - 1) <= upper) {
            rangeSearchTree(root.right, lower, upper);
        }
    }
    
    /** Returns a preorder iterator for this tree. */
    public LinkedTreeIterator preorderIterator() {
        return new PreorderIterator();
    }
    
    /** Returns a postorder iterator for this tree. */
    public LinkedTreeIterator postorderIterator() {
        /*** implement this method for PS 4 ***/
        
        return null;
    }
    
    /*** inner class for a preorder iterator ***/
    private class PreorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private PreorderIterator() {
            // The traversal starts with the root node.
            nextNode = root;
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null)
                throw new NoSuchElementException();
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            // Advance nextNode.
            if (nextNode.left != null)
                nextNode = nextNode.left;
            else if (nextNode.right != null)
                nextNode = nextNode.right;
            else {
                // We've just visited a leaf node.
                // Go back up the tree until we find a node
                // with a right child that we haven't seen yet.
                Node parent = nextNode.parent;
                Node child = nextNode;
                while (parent != null &&
                       (parent.right == child || parent.right == null)) {
                    child = parent;
                    parent = parent.parent;
                }
                
                if (parent == null)
                    nextNode = null;  // the traversal is complete
                else
                    nextNode = parent.right;
            }
            
            return key;
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        LinkedTree tree = new LinkedTree();
       tree.insert(7, "root node");
        tree.insert(9, "7's right child");
        tree.insert(5, "7's left child");
        tree.insert(2, "5's left child");
        tree.insert(8, "9's left child");
        tree.insert(6, "5's right child");
        tree.insert(4, "2's right child");
        
        System.out.print("\n preorder: ");
        tree.preorderPrint();
        System.out.println();
        
        System.out.print("postorder: ");
        tree.postorderPrint();
        System.out.println();
        
        System.out.print("  inorder: ");
        tree.inorderPrint();
        System.out.println();
        
        System.out.println("Number of Even keys: " + tree.numEvenKeys());
        System.out.println("Items in range 7-9: ");
        rangeSearchTree(tree.root, 7, 9);
        System.out.print("\nkey to search for: ");
        int key = in.nextInt();
        in.nextLine();
        LLList data = tree.search(key);
        if (data != null)
            System.out.println(key + " = " + data.getItem(0));
        else
            System.out.println("no such key in tree");
        
        System.out.print("\nkey to delete: ");
        key = in.nextInt();
        in.nextLine();
        data = tree.delete(key);
        if (data != null)
            System.out.println("removed " + data);
        else
            System.out.println("no such key in tree");
        
        System.out.print("\n preorder: ");
        tree.preorderPrint();
        System.out.println();
        
        System.out.print("postorder: ");
        tree.postorderPrint();
        System.out.println();
        
        System.out.print("  inorder: ");
        tree.inorderPrint();
        System.out.println();
    }
}
