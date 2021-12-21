
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
import java.util.TreeSet;

public class Dictionary {

	/**
	 * Read comma-separated values file (.csv), especially dictionary database then
	 * convert to TreeSet of Node by following the constructor.
	 *
	 * @param fileName A string that tells the name or path of the file.
	 * @return TreeSet of each node.
	 */
	public static TreeSet<DictionaryNode> readDictionaryFile(String fileName) {

		TreeSet<DictionaryNode> dictionary = new TreeSet<DictionaryNode>();
		int totalRead = 0;
		int meaning = 0;
		boolean TRY = true;

		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			bufferedReader.read();
			String string;
			while ((string = bufferedReader.readLine()) != null) {
				DictionaryNode inputVocab = new DictionaryNode(string);
				totalRead++;
				if (!dictionary.contains(inputVocab)) {
					dictionary.add(inputVocab);
					meaning++;
				} else {
					TreeSet<DictionaryNode> found = (TreeSet<DictionaryNode>) dictionary.subSet(inputVocab, true,
							inputVocab, true);
					if (!found.first().meaning.contains(inputVocab.meaning.get(0))) {
						found.first().meaning.add(inputVocab.meaning.get(0));
						meaning++;
					}
				}
			}
			bufferedReader.close();
		} catch (IOException exception) {
			TRY = false;
			System.out.println("Error, can't read file.");
		}

		if (TRY) {
			System.out.println("Total Read " + totalRead + " records.");
			System.out.println("Total word size " + dictionary.size() + " words.");
			System.out.println("Total meaning size " + meaning + " words.");
		}
		return dictionary;
	}

	/**
	 * Create a node named keywordNode, search for it and create a tree named found
	 * to find and store results, then locate it using the .subSet command. If the
	 * node is not found in the dictionary (found.isEmpty), it will notify the user.
	 * 
	 * @param keyword    word that user want to search.
	 * @param dictionary find in?
	 * @return founded tree
	 */
	public static TreeSet<DictionaryNode> search(String keyword, TreeSet<DictionaryNode> dictionary) {

		DictionaryNode keywordNode = new DictionaryNode();
		TreeSet<DictionaryNode> found = new TreeSet<DictionaryNode>();

		keywordNode.word = keyword;
		found = (TreeSet<DictionaryNode>) dictionary.subSet(keywordNode, true, keywordNode, true);
		if (found.isEmpty()) {
			System.out.println(keyword + " not found.");
			System.out.println();
		}
		return found;
	}

	/**
	 * The most common default is 1 (must have at least 1) loop for each node in the
	 * dictionary. if the size of the node more than the current maximum. Change
	 * that value to the maximum value and remember that word. When the loop is
	 * over, users will be notified. And take the remembered word to search in the
	 * tree again to get its meaning.
	 * 
	 * @param dictionary TreeSet of each node.
	 * @return search of most found.
	 */
	public static TreeSet<DictionaryNode> mostFoundWord(TreeSet<DictionaryNode> dictionary) {

		int currentMaximum = 1; /* Exist at least 1 node */
		String currentWord = new String("");

		for (DictionaryNode node : dictionary) {
			if (node.meaning.size() > currentMaximum) {
				currentMaximum = node.meaning.size();
				currentWord = node.word;
			}
		}
		System.out.println(currentWord + " have " + currentMaximum + " meaning.");
		return search(currentWord, dictionary);
	}

	/**
	 * If the input tree is not empty. Will loop the print, using search and
	 * mostFoundWord through the render parameter.
	 * 
	 * @param treeSet any treeSet constructed by dictionaryNode.
	 */
	public static void render(TreeSet<DictionaryNode> treeSet) {

		if (!treeSet.isEmpty()) {
			for (int i = 0; i < (treeSet.first().meaning.size()); i++) {
				System.out.println((i + 1) + ") " + treeSet.first().meaning.get(i));
			}
			System.out.println();
		}
	}
}