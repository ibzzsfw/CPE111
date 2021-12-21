import java.util.Scanner;
import java.util.HashMap;

public class Assignment8 {

	public static void main(String[] args) {

		String inputWord = new String();
		final String FILE_PATH = "src/utf8lexitron.csv";
		Scanner scanner = new Scanner(System.in);

		HashMap<String, DictionaryNode> dictionary = Dictionary.readDictionaryFile(FILE_PATH);

		if (!dictionary.isEmpty()) {
			Dictionary.mostFoundWord(dictionary);
			do {
				System.out.print("Enter token: ");
				inputWord = scanner.nextLine().toLowerCase().trim().replaceAll("\\s+", " ");
				Dictionary.render(inputWord, dictionary);
			} while (!inputWord.equalsIgnoreCase("end"));
		}
		scanner.close();
		System.out.println("End Program");
	}
}