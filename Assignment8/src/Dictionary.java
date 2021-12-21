
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
import java.util.HashMap;

public class Dictionary {

	/**
	 * Read comma-separated values file (.csv), especially dictionary database then
	 * convert to Hash table of Node by following the constructor.
	 *
	 * @param fileName A string that tells the name or path of the file.
	 * @return Hash table of each node.
	 */
	public static HashMap<String, DictionaryNode> readDictionaryFile(String fileName) {

		HashMap<String, DictionaryNode> dictionary = new HashMap<String, DictionaryNode>();
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
				String key = inputVocab.word.toLowerCase();
				totalRead++;
				if (!dictionary.containsKey(key)) {
					dictionary.put(key, inputVocab);
					meaning++;
				} else {
					if (!dictionary.get(key).meaning.contains(inputVocab.meaning.get(0))) {
						dictionary.get(key).meaning.add(inputVocab.meaning.get(0));
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
	 * The most common default is 1 (must have at least 1) loop for each key in the
	 * HashMap of dictionary. if the size of the node more than the current maximum.
	 * Change that value to the maximum value and remember that word. When the loop
	 * is over, users will be notified. And take the remembered word to search in
	 * the tree again to get its meaning.
	 * 
	 * @param dictionary Hash table of each node.
	 */
	public static void mostFoundWord(HashMap<String, DictionaryNode> dictionary) {

		int currentMaximum = 1; /* Exist at least 1 node */
		String currentWord = new String("");

		for (String key : dictionary.keySet()) {
			if (dictionary.get(key).meaning.size() > currentMaximum) {
				currentMaximum = dictionary.get(key).meaning.size();
				currentWord = dictionary.get(key).word;
			}
		}
		System.out.println(currentWord + " have " + currentMaximum + " meaning.");
		render(currentWord, dictionary);
	}

	/**
	 * If the input "key" is in the hashMap. Will loop the print, using search and
	 * mostFoundWord through the render method.
	 * 
	 * @param key
	 * @param hashMap any Hash table constructed by dictionaryNode.
	 */
	public static void render(String key, HashMap<String, DictionaryNode> hashMap) {

		if (hashMap.containsKey(key)) {
			for (int i = 0; i < hashMap.get(key).meaning.size(); i++) {
				System.out.println((i + 1) + ") " + hashMap.get(key).meaning.get(i));
			}
		} else {
			System.out.println(key + " not found.");
		}
		System.out.println();
	}
}