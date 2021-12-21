import java.util.ArrayList;

/**
 * A dictionaryNode class to manage a node of data, Which consists of word,
 * meaning, and type of a vocabulary. with implements Comparable and compareTo
 * method.
 * 
 * @author Suppakorn Rakna (63070501061)
 */

public class DictionaryNode {

	String word;
	ArrayList<String> meaning;

	public DictionaryNode() {

		word = "";
		meaning = new ArrayList<String>();
	}

	/**
	 * After read, trim and split by "," then analyst the string whether the key
	 * input or data from file.
	 * 
	 * @param buff A line in dictionary database file read by scanner.
	 */
	public DictionaryNode(String buff) {

		String[] string = buff.trim().replaceAll("\\s+", " ").split(",");

		if (string.length == 1) { /* key from user. */
			word = string[0];
		} else { /* A line from file. */
			word = string[0];
			meaning = new ArrayList<String>();
			meaning.add(string[1] + " (" + string[2] + ")");
		}
	}
}