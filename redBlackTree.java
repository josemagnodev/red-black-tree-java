public class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    class Node {
        int key;
        Node left, right, parent;
        boolean color;

        Node(int key) {
            this.key = key;
            this.color = RED; // New nodes are red by default
        }
    }

    private Node root;

    // Public method to insert a key
    public void insert(int key) {
        Node node = new Node(key);
        root = bstInsert(root, node);
        fixViolation(node);
    }

    // Standard BST insert
    private Node bstInsert(Node root, Node node) {
        if (root == null)
            return node;

        if (node.key < root.key) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else if (node.key > root.key) {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }

        return root;
    }

    // Fix Red-Black Tree violations
    private void fixViolation(Node node) {
        Node parent = null;
        Node grandparent = null;

        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandparent = parent.parent;

            // Case A: Parent is left child of grandparent
            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                // Case 1: Uncle is RED -> Recolor
                if (uncle != null && uncle.color == RED) {
                    grandparent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandparent;
                } else {
                    // Case 2: node is right child -> rotate left
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Case 3: node is left child -> rotate right
                    rotateRight(grandparent);
                    boolean temp = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = temp;
                    node = parent;
                }
            }
            // Case B: Parent is right child of grandparent
            else {
                Node uncle = grandparent.left;

                if (uncle != null && uncle.color == RED) {
                    grandparent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    rotateLeft(grandparent);
                    boolean temp = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = temp;
                    node = parent;
                }
            }
        }

        root.color = BLACK; // Root is always black
    }

    // Left Rotation
    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null)
            rightChild.left.parent = node;

        rightChild.parent = node.parent;

        if (node.parent == null)
            root = rightChild;
        else if (node == node.parent.left)
            node.parent.left = rightChild;
        else
            node.parent.right = rightChild;

        rightChild.left = node;
        node.parent = rightChild;
    }

    // Right Rotation
    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null)
            leftChild.right.parent = node;

        leftChild.parent = node.parent;

        if (node.parent == null)
            root = leftChild;
        else if (node == node.parent.left)
            node.parent.left = leftChild;
        else
            node.parent.right = leftChild;

        leftChild.right = node;
        node.parent = leftChild;
    }

    // Inorder traversal
    public void inorder() {
        inorderHelper(root);
    }

    private void inorderHelper(Node root) {
        if (root != null) {
            inorderHelper(root.left);
            System.out.print(root.key + " ");
            inorderHelper(root.right);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        int[] keys = {10, 20, 30, 15, 25, 5};

        for (int key : keys) {
            tree.insert(key);
        }

        System.out.println("Inorder traversal of constructed Red-Black Tree:");
        tree.inorder(); // should print sorted keys
    }
}
