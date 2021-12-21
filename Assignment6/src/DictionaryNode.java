/**
 * A dictionaryNode class to manage a node of data, Which consists of word,
 * meaning, and type of a vocabulary. with implements Comparable and compareTo
 * method. Can be used in Collections.sort () and Collections.binarySearch ().
 * 
 * @author Suppakorn Rakna (63070501061)
 */

public class DictionaryNode implements Comparable<DictionaryNode> {

	String word;
	String meaning;
	String type;

	public DictionaryNode() {

		word = "";
		meaning = "";
		type = "";
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
			meaning = string[1];
			type = string[2];
		}
	}

	public int compareTo(DictionaryNode dictionaryNode) {

		return (int) this.word.compareToIgnoreCase(dictionaryNode.word);
	}

	/**
	 * Compare all element in a node with other.
	 * 
	 * @param dictionaryNode Analyst this vocabulary whether duplicate to previous
	 *                       or not.
	 * @return boolean that tells about duplication.
	 */
	public boolean compareAll(DictionaryNode dictionaryNode) {

		return ((this.word.equalsIgnoreCase(dictionaryNode.word))
				&& (this.meaning.equalsIgnoreCase(dictionaryNode.meaning))
				&& (this.type.equalsIgnoreCase(dictionaryNode.type)));
	}

	/**
	 * Print all element in a node.
	 */
	public void render() {

		System.out.println(this.word + "\t" + this.meaning + "(" + this.type + ")");
	}
}