
// A binary tree is a simple rooted tree that is recursively defined by its left and right subtree
// Additionally, there is some value stored for each node
// This is a persistent implementation, where one of the subtrees can be replaced, and a new binary tree is returned that implements this replacement (but the original tree remains the same)
// This mostly serves to demonstrate the key technique in creating persistent trees, and can be similarly applied to tries, segment trees and more
public class PersistentBinaryTree {
	
	// The left and right subtrees
	PersistentBinaryTree left = null, right = null;
	// The value of the root of this tree
	int value;
	
	PersistentBinaryTree(int value) {
		this.value = value;
	}
	
	// Replace one subtree by a replacement
	// Returns a binary tree where this replacement has been applied
	// This tree itself will remain the same
	PersistentBinaryTree replaceSide(boolean onLeft, PersistentBinaryTree replacement) {
		PersistentBinaryTree newTree = new PersistentBinaryTree(this.value);
		if (onLeft) {
			newTree.right = right;
			newTree.left = replacement;
		} else {
			newTree.left = left;
			newTree.right = replacement;
		}
		return newTree;
	}
	
}
