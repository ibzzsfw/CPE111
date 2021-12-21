import java.util.ArrayList;
import java.util.Scanner;

public class Assignment6 {

	public static void main(String[] args) {

		int[] interval = new int[2];
		String inputWord = new String();
		final String FILE_PATH = "src/utf8lexitron.csv";
		Scanner scanner = new Scanner(System.in);

		ArrayList<DictionaryNode> dictionaryNode = Dictionary.readDictionaryFile(FILE_PATH);
		Dictionary.verify(dictionaryNode);

		if (!dictionaryNode.isEmpty()) {
			do {
				System.out.print("Enter token: ");
				inputWord = scanner.nextLine().trim().replaceAll("\\s+", " ");
				interval = Dictionary.searchIndex(inputWord, dictionaryNode);
				Dictionary.renderSearch(inputWord, dictionaryNode, interval);
			} while (!inputWord.equalsIgnoreCase("end"));
		}
		scanner.close();
		System.out.println("End Program");
	}
}