import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		readfileText("output.txt");
		// System.out.println(words);
	}

	public static void convertCode(String words) {
		if (words.startsWith("int")) {
			System.out.println("\tlw $s0, ");
		} else if (words.startsWith("do {")) {
			System.out.print("loop: ");
			if (words.matches("(?i).*=.*")) {
				System.out.println("\t=");
			}
		} else if (words.startsWith("} while")) {
			if (words.matches("(?i).*<.*")) {
				System.out.print("\tblt");
			}
			if (words.matches("(?i).*>.*")) {
				System.out.print("\tbne");
			}
			System.out.println(", loop");
		}
	}

	public static void readfileText(String fileName) {
		// get user name
		String userNamePath = System.getProperty("user.name");
		// read file form desktop
		String path = "C:\\Users\\" + userNamePath + "\\Desktop\\" + fileName;
		File file = new File(path);

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println(line);
				convertCode(line);
			}
			bufferedReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
