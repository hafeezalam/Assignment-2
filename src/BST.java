/**
 * The same BST class as discussed in the lectures with the comments stripped
 * out Note:(i) some methods are now implemented recursively
 * (size,toString,traversals, min, max etc) 
 * (ii) the BST Node is now specified
 * as an inner class of the BST
 */
public class BST<AnyType extends Comparable<AnyType>> {
	private TreeNode root;

	/**Add Required Methods Here*/

	
	
	/* Note: size is not explicitly tracked */
	// creates an empty binary search tree
	public BST() {
		root = null;
	}

	private TreeNode searchHelper(AnyType key) {
		TreeNode current = root;
		while (current != null) {
			int compResult = key.compareTo(current.element);
			if (compResult == 0) {
				return current;
			} else if (compResult < 0) {
				current = current.left;
			} else if (compResult > 0) {
				current = current.right;
			}
		}
		return null;
	}

	// search renamed to contains
	public boolean contains(AnyType key) {
		if (isEmpty()) {
			return false;
		}
		if (searchHelper(key) == null) {
			return false;
		}
		return true;
	}

	// This insert currently Assumes no duplicates allowed!!
	public boolean insert(AnyType keyToBeInserted) {
		if (isEmpty()) {
			root = new TreeNode(keyToBeInserted);
			return true;
		}
		TreeNode current, previous;
		current = root;
		previous = null;

		while (current != null) {
			int result = keyToBeInserted.compareTo(current.element);
			if (result == 0) {
				System.out.println("Duplicate key!!! Cannot insert duplicates...");
				return false;
			} else if (result < 0) {
				previous = current;
				current = current.left;
			} else {
				previous = current;
				current = current.right;
			}
		}
		TreeNode newest = new TreeNode(keyToBeInserted);
		int result = keyToBeInserted.compareTo(previous.element);
		newest.parent = previous;
		if (result < 0) {
			previous.left = newest;
		} else {
			previous.right = newest;
		}
		return true;
	}

	public boolean remove(AnyType key) {
		TreeNode toBeDeleted = searchHelper(key);
		if (toBeDeleted == null) {
			System.out.println("The key was not found in the BST");
			return false;
		}

		if (hasLeft(toBeDeleted) && hasRight(toBeDeleted)) {
			TreeNode predecessorNode = searchHelper(predecessor(toBeDeleted.element));
			toBeDeleted.element = predecessorNode.element;
			predecessorNode.element = key;
			toBeDeleted = predecessorNode;
		}
		TreeNode immediateChild;
		if (hasLeft(toBeDeleted)) {
			immediateChild = toBeDeleted.left;
		} else {
			immediateChild = toBeDeleted.right;
		}
		if (toBeDeleted == root) {
			root = immediateChild;
			if (immediateChild != null) {
				immediateChild.parent = null;
			}
			return true;
		}
		if (isLeft(toBeDeleted)) {
			toBeDeleted.parent.left = immediateChild;
		} else {
			toBeDeleted.parent.right = immediateChild;
		}
		if (immediateChild != null) {
			immediateChild.parent = toBeDeleted.parent;
		}
		return true;
	}

	// counts the total values stored in the BST.
	public int size() {
		return size(root);
	}

	// helper method for recursively counting the total values rooted at the node rt
	private int size(TreeNode rt) {
		if (rt == null)
			return 0;
		// the current node rt is a root of the subtree, so total items in this subtree
		// include all items in its left + all in the right + the item in the node
		// itself
		return 1 + size(rt.left) + size(rt.right);
	}

	public void preOrdertraversal() {
		preOrdertraversal(root);
	}

	private void preOrdertraversal(TreeNode root) {
		System.out.print(root.element + ", ");
		if (root.left != null) {
			preOrdertraversal(root.left);
		}
		if (root.right != null) {
			preOrdertraversal(root.right);
		}
	}

	public void inOrdertraversal() {
		inOrdertraversal(root);
	}

	private void inOrdertraversal(TreeNode root) {
		if (root.left != null) {
			inOrdertraversal(root.left);
		}
		System.out.print(root.element + ", ");
		if (root.right != null) {
			inOrdertraversal(root.right);
		}
	}

	// returns a String that prints tree top to bottom, right to left in a 90-degree
	// rotated level view
	public String toString() {
		/*
		 * as the string we build up will be updated frequently, it is better to use a
		 * StringBuilder object instead of a simple String object, Remember due to
		 * String objects being immutable, any modification results in a new object
		 * being created (which adds up unnecessary cost).
		 */
		StringBuilder result = new StringBuilder();
		// use the helper method
		return toString(result, -1, root).toString();
	}

	// a helper method to recursively build the string representing the BST contents
	public StringBuilder toString(StringBuilder res, int height, TreeNode rt) {
		if (rt != null) {
			height++;
			res = toString(res, height, rt.right);
			for (int i = 0; i < height; i++)
				res.append("\t");
			res.append(rt.element + "\n");
			res = toString(res, height, rt.left);
		}
		return res;
	}

	/*
	 * Helper for recursively finding the min value within the tree rooted at
	 * current node
	 */
	private AnyType min(TreeNode current) {
		while (current.left != null) {
			current = current.left;
		}
		return current.element;
	}

	public AnyType min() {
		if (isEmpty()) {
			System.out.println("cannot find the min value in an empty tree");
			return null;
		}
		return min(root);
	}

	/*
	 * helper method that searches for the max in the tree rooted at the current
	 * node
	 */
	private AnyType max(TreeNode current) {
		while (current.right != null) {
			current = current.right;
		}
		return current.element;
	}

	public AnyType max() {
		if (isEmpty()) {
			System.out.println("cannot find the max value in an empty tree");
			return null;
		}
		return max(root);
	}

	public AnyType predecessor(AnyType key) {
		TreeNode current = searchHelper(key);
		if (current == null) {
			System.out.println("The given key does not exist!!");
			return null;
		}
		if (current.left != null) {
			return max(current.left);
		}
		current = current.parent;
		while (current != null) {
			if (current.element.compareTo(key) < 0) {
				return current.element;
			}
			current = current.parent;
		}
		System.out.println("No predecessor exists for the given key " + key);
		return null;
	}

	public AnyType successor(AnyType key) {
		TreeNode current = searchHelper(key);
		if (current == null) {
			System.out.println("The given key does not exist!!");
			return null;
		}
		if (current.right != null) {
			return min(current.right);
		}
		current = current.parent;
		while (current != null) {
			if (current.element.compareTo(key) > 0) {
				return current.element;
			}
			current = current.parent;
		}
		System.out.println("No successor exists for the given key " + key);
		return null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean hasLeft(TreeNode current) {
		return current.left != null;
	}

	public boolean hasRight(TreeNode current) {
		return current.right != null;
	}

	public boolean isLeft(TreeNode current) {
		if (current.parent == null) {
			return false;
		}
		return current.parent.left == current;
	}

	public boolean isRight(TreeNode current) {
		if (current.parent == null) {
			return false;
		}
		return current.parent.right == current;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public static void main(String[] args) {
		BST<Integer> treeTest = new BST<Integer>();
		treeTest.insert(7);
		treeTest.insert(5);
		treeTest.insert(4);
		treeTest.insert(10);
		treeTest.insert(6);
		treeTest.insert(8);
		treeTest.inOrdertraversal();
		System.out.println();
		treeTest.preOrdertraversal();
		System.out.println();
		System.out.println(treeTest.size());
		System.out.println(treeTest.contains(6));
		System.out.println(treeTest.contains(112));
		System.out.println(treeTest.contains(7));
		System.out.println(treeTest.contains(10));
		System.out.println();
		System.out.println(treeTest);
	}

	/* NOTE: THE NODE OF THE BST IS DEFINED AS AN INNER CLASS */
	// No need for getters and setters, we can access the attributes in our outer
	// class
	private class TreeNode {

		private AnyType element;
		private TreeNode parent, left, right;

		public TreeNode(AnyType value) {
			element = value;
			parent = left = right = null;
		}
	}

}
