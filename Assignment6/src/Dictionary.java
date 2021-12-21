
/**
 * A Dictionary class is a combination of DictionaryNode dealt with in
 * various ways both in terms of structure and working with users.
 * 
 * @author Suppakorn Rakna (63070501061)
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {

	/**
	 * Read comma-separated values file (.csv), especially dictionary database then
	 * convert to ArrayList of Node by following the constructor.
	 *
	 * @param fileName A string that tells the name or path of the file.
	 * @return ArrayList of each node.
	 */
	public static ArrayList<DictionaryNode> readDictionaryFile(String fileName) {

		ArrayList<DictionaryNode> dictionaryNode = new ArrayList<DictionaryNode>();

		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			bufferedReader.read();
			String string;
			while ((string = bufferedReader.readLine()) != null) {
				DictionaryNode inputVocab = new DictionaryNode(string);
				dictionaryNode.add(inputVocab);
			}
			bufferedReader.close();
		} catch (IOException exception) {
			System.out.println("Error, can't read file.");
		}
		return dictionaryNode;
	}

	/**
	 * Sorting and find duplicate node (same word, meaning, and type) then remove
	 * and counting simultaneously.
	 * 
	 * @param dictionaryNode ArrayList of each node.
	 * @return A number of duplicate nodes.
	 */
	public static int removeDuplicateNode(ArrayList<DictionaryNode> dictionaryNode) {

		int duplicate = 0;

		sortByWord(dictionaryNode);
		for (int i = 0; i < dictionaryNode.size() - 1; i++) {
			while (dictionaryNode.get(i).compareAll(dictionaryNode.get(i + 1))) {
				dictionaryNode.remove(i + 1);
				duplicate++;
			}
		}
		return duplicate;
	}

	/**
	 * Sort ArrayList of dictionary with using word element.
	 * 
	 * @param dictionaryNode ArrayList of each node.
	 */
	private static void sortByWord(ArrayList<DictionaryNode> dictionaryNode) {

		Collections.sort(dictionaryNode);
	}

	/**
	 * Find the most abundant words in the dictionary.
	 * 
	 * @param dictionaryNode ArrayList of each node.
	 * @return the Object maximum contain [0]"count" is number of the most found
	 *         word, and [1]"word" is the most found word. (in the dictionary).
	 */
	public static Object[] mostFoundWord(ArrayList<DictionaryNode> dictionaryNode) {

		int currentMaximum = 1; /* Exist at least 1 node */
		String currentWord = new String();
		Object[] maximum = new Object[2];

		sortByWord(dictionaryNode);
		currentWord = dictionaryNode.get(0).word;
		maximum[0] = 1;
		maximum[1] = currentWord;
		for (DictionaryNode node : dictionaryNode) {
			if (currentWord.equalsIgnoreCase(node.word)) {
				currentMaximum++;
			} else {
				if (currentMaximum > (int) maximum[0]) {
					maximum[0] = currentMaximum;
					maximum[1] = currentWord;
				}
				currentMaximum = 1;
			}
			currentWord = node.word;
		}
		return maximum;
	}

	/**
	 * Search the interval of the input word in the dictionary.
	 * 
	 * @param inputWord      word from user.
	 * @param dictionaryNode ArrayList of each node.
	 * @return the interval of index that contains the input word which interval[0]
	 *         means start index and interval[1] means stop index. The interval will
	 *         be [-1, -1] if the input word does not exists.
	 */
	public static int[] searchIndex(String inputWord, ArrayList<DictionaryNode> dictionaryNode) {

		int[] interval = new int[2];
		int index = Collections.binarySearch(dictionaryNode, new DictionaryNode(inputWord));

		if (index > -1 && index < dictionaryNode.size()) {/** Set lower and upper bound */
			interval[0] = index;
			interval[1] = index;
			if ((index > 0) && (index < dictionaryNode.size() - 1)) {
				/* iterate to find start index */
				for (interval[0] = index - 1; dictionaryNode.get(interval[0] - 1).word
						.equalsIgnoreCase(inputWord); interval[0]--)
					;
				/* iterate to find stop index */
				for (interval[1] = index + 1; dictionaryNode.get(interval[1] + 1).word
						.equalsIgnoreCase(inputWord); interval[1]++)
					;
			}
		} else { /* the input word does not exists. */
			interval[0] = interval[1] = -1;
		}
		return interval;
	}

	/**
	 * Print the node in range of interval (start, stop).
	 * 
	 * @param start          start index.
	 * @param stop           stop index.
	 * @param dictionaryNode ArrayList of each node.
	 */
	public static void renderBetween(int start, int stop, ArrayList<DictionaryNode> dictionaryNode) {

		for (int i = start, j = 1; i <= stop; i++, j++) {
			System.out.println(j + ")\t" + " " + dictionaryNode.get(i).word + "\t" + dictionaryNode.get(i).meaning + "("
					+ dictionaryNode.get(i).type + ")");
		}
		System.out.println();
	}

	/**
	 * Print the node data in range of interval (start, stop) to system.out.
	 * 
	 * @param inputWord      word from user.
	 * @param dictionaryNode ArrayList of each node.
	 * @return the interval of the index we get from public static int[]
	 *         searchIndex.
	 */
	public static void renderSearch(String inputWord, ArrayList<DictionaryNode> dictionaryNode, int[] interval) {

		if (interval[0] == -1) {
			System.out.println(inputWord + " Not found\n");
		} else {
			System.out.println("found " + dictionaryNode.get(interval[0]).word + " " + (interval[1] - interval[0] + 1)
					+ " token(s) at " + interval[0] + " - " + interval[1]);
			renderBetween(interval[0], interval[1], dictionaryNode);
		}
	}

	/**
	 * Print all nodes in dictionary ArrayList.
	 * 
	 * @param dictionayNode ArrayList of each node that we want to print.
	 */
	public static void renderArray(ArrayList<DictionaryNode> dictionaryNode) {

		for (DictionaryNode node : dictionaryNode) {
			node.render();
		}
		System.out.println();
	}

	/**
	 * Verify required condition and data (test case). Which consists of check read
	 * file complete, handle duplicate node, and check most found node.
	 * 
	 * @param dictionaryNode ArrayList of each node that we want to verify.
	 */
	public static void verify(ArrayList<DictionaryNode> dictionaryNode) {

		System.out.println("Total Read " + dictionaryNode.size() + " records."); /* check read file complete */
		/* handle duplicate node */
		System.out.println("Total duplicate found " + removeDuplicateNode(dictionaryNode) + " records.");
		System.out.println("Total remaining size " + dictionaryNode.size() + " records.");
		/* check most found node */
		Object[] maxmimum = mostFoundWord(dictionaryNode);
		System.out.println("Maximun Meaning token get off have " + maxmimum[0] + " meaning.");
		int[] interval = searchIndex((String) maxmimum[1], dictionaryNode);
		renderBetween(interval[0], interval[1], dictionaryNode);
	}
}