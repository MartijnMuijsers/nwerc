
// A trie is a data structure that recursively stores a set of strings
// This is done by keeping an array of 26 potential subtries, 1 for each character, that contain the rest of the strings starting with that character
// For instance, if this trie contains the words 'bee', 'better' and 'super', then the subtrie at index 1 (describing the letter 'b') contains the words 'ee' and 'etter'
// This data structure can be augmented to efficiently store information about the set of strings and all their prefixes
// This brief implementation only stores the number of strings in the set that are in this trie
public class Trie {
	
	// The 26 potential subtries
	Trie[] sub = new Trie[26];
	// The number of strings that go through this trie
	int amount = 0;
	
	// Add a string to this trie (to add a string to the top-level trie, call add(string.toCharArray(), 0))
	void add(char[] l, int i) {
		amount++;
		if (i == l.length) {
			return;
		}
		int j = l[i] - 'a';
		if (sub[j] == null) {
			sub[j] = new Trie();
		}
		sub[j].add(l, i + 1);
	}
	
}
