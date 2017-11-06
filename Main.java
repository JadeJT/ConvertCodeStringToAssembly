import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	private static int x;
	private static String[] s, t;
	private static int[] ss;

	public static void main(String[] args) {

		for (int numberFile = 0; numberFile < 5; numberFile++) {
			x = 0;
			s = new String[2];
			t = new String[2];
			ss = new int[2];
			readfileText("input" + numberFile + ".txt");
			System.out.println();
		}
	}

	public static void convertCode(String words) {
		// check value
		if (words.startsWith("int")) {
			System.out.println("\tlw $s" + x + ", " + words.substring(4, 5));
			s[x] = words.substring(4, 5);
			t[x] = "$s" + x;
			x++;
		} else if (words.startsWith("do {")) {
			System.out.print("loop:");

		} else if (words.contains("+") || words.contains("-")) {
			String sign;
			if (words.contains("+")) {
				sign = "+";
			} else {
				sign = "-";
			}

			// 1 value
			if (x == 1) {
				if (words.contains(s[0] + sign + sign + ";")) {
					if (sign.contains("+")) {
						System.out.println("\taddi " + t[0] + ", " + t[0] + ", 1");
					} else {
						System.out.println("\tsubi " + t[0] + ", " + t[0] + ", 1");
					}
					// example a = a + a, a = a - a
				} else if (words.contains(s[0] + " = " + s[0] + " " + sign + " " + s[0])) {
					if (sign.contains("+")) {
						System.out.println("\tadd " + t[0] + ", " + t[0] + ", " + t[0]);
					} else {
						System.out.println("\tsub " + t[0] + ", " + t[0] + ", " + t[0]);
					}
				} else if (words.contains(s[0] + " = " + s[0] + " " + sign + " " + words.replaceAll("\\D+", "") + ";")
						|| words.contains(
								s[0] + " = " + words.replaceAll("\\D+", "") + " " + sign + " " + s[0] + ";")) {
					if (sign.contains("+")) {
						System.out.println("\taddi " + t[0] + ", " + t[0] + ", " + words.replaceAll("\\D+", ""));
					} else {
						System.out.println("\tsubi " + t[0] + ", " + t[0] + ", " + words.replaceAll("\\D+", ""));
					}
				}
				// 2 value
			} else if (x == 2) {
				if (words.contains(s[0]) && !words.contains(s[1]) || words.contains(s[1]) && !words.contains(s[0])) {
					for (int i = 0; i < x; i++) {
						// example a++, a--
						if (words.contains(s[i] + sign + sign + ";")) {
							if (sign.contains("+")) {
								System.out.println("\taddi " + t[i] + ", " + t[i] + ", 1");
							} else {
								System.out.println("\tsubi " + t[i] + ", " + t[i] + ", 1");
							}
							// example a = a + a, a = a - a
						} else if (words.contains(s[i] + " = " + s[i] + " " + sign + " " + s[i])) {
							if (sign.contains("+")) {
								System.out.println("\tadd " + t[i] + ", " + t[i] + ", " + t[i]);
							} else {
								System.out.println("\tsub " + t[i] + ", " + t[i] + ", " + t[i]);
							}
						} else if (words
								.contains(s[i] + " = " + s[i] + " " + sign + " " + words.replaceAll("\\D+", "") + ";")
								|| words.contains(
										s[i] + " = " + words.replaceAll("\\D+", "") + " " + sign + " " + s[i] + ";")) {
							if (sign.contains("+")) {
								System.out
										.println("\taddi " + t[i] + ", " + t[i] + ", " + words.replaceAll("\\D+", ""));
							} else {
								System.out
										.println("\tsubi " + t[i] + ", " + t[i] + ", " + words.replaceAll("\\D+", ""));
							}
						}
					}
					// a = b + 10
				} else if (words.contains(s[0]) && words.contains(s[1]) && words.matches(".*\\d+.*")) {
					if (sign.contains("+")) {
						System.out.print("\taddi ");
					} else {
						System.out.print("\tsubi ");
					}
					
					for (int i = 0; i < x; i++) {
						// find all occurrences forward
						for (int j = -1; (j = words.indexOf(s[i], j + 1)) != -1; j++) {
							ss[i] = j;
						}
					}

					if (ss[0] < ss[1]) {
						System.out.print("$s0, $s1");
					} else {
						System.out.print("$s1, $s0");
					}

					System.out.println(", " + words.replaceAll("\\D+", ""));

					// a = b + a
				} else {

					if (sign.contains("+")) {
						System.out.print("\tadd ");
					} else {
						System.out.print("\tsub ");
					}

					for (int i = 0; i < x; i++) {
						// find all occurrences forward
						for (int j = -1; (j = words.indexOf(s[i], j + 1)) != -1; j++) {
							ss[i] = j;
						}
					}

					if (words.indexOf(s[0]) == 3) {
						System.out.print("$s0, ");

						if (ss[0] < ss[1]) {
							System.out.println("$s0, $s1");
						} else {
							System.out.println("$s1, $s0");
						}
					} else {
						System.out.print("$s1, ");

						if (ss[0] < ss[1]) {
							System.out.println("$s0, $s1");
						} else {
							System.out.println("$s1, $s0");
						}
					}
				}
			}

		} else if (words.startsWith("} while")) {
			for (int i = 0; i < x; i++) {
				// find all occurrences forward
				for (int j = -1; (j = words.indexOf(s[i], j + 1)) != -1; j++) {
					ss[i] = j;
				}
			}
			// condition 1 value
			if (x == 1) {
				// < sign
				if (words.matches("(?i).*<.*")) {
					System.out.print("\tblti ");

					if (ss[0] < 10) {
						System.out.print("$s0, " + words.replaceAll("\\D+", ""));
					} else {
						System.out.print(words.replaceAll("\\D+", "") + ", $s0");
					}
				}
				// > sign
				if (words.matches("(?i).*>.*")) {
					System.out.print("\tbgti ");

					if (ss[0] < 10) {
						System.out.print("$s0, " + words.replaceAll("\\D+", ""));
					} else {
						System.out.print(words.replaceAll("\\D+", "") + ", $s0");
					}
				}
				// != sign
				if (words.matches("(?i).*!=.*")) {
					System.out.print("\tbne ");

					if (ss[0] < 10) {
						System.out.print("$s0, " + words.replaceAll("\\D+", ""));
					} else {
						System.out.print(words.replaceAll("\\D+", "") + ", $s0");
					}
				}
				// == sign
				if (words.matches("(?i).*==.*")) {
					System.out.print("\tbeq ");

					if (ss[0] < 10) {
						System.out.print("$s0, " + words.replaceAll("\\D+", ""));
					} else {
						System.out.print(words.replaceAll("\\D+", "") + ", $s0");
					}
				}

				System.out.println(", loop");

				// condition 2 value
			} else {

				for (int i = 0; i < x; i++) {
					// find all occurrences forward
					for (int j = -1; (j = words.indexOf(s[i], j + 1)) != -1; j++) {
						ss[i] = j;
					}
				}

				// < sign
				if (words.matches("(?i).*<.*")) {
					System.out.print("\tblt ");

					if (words.contains(s[0]) && words.contains(s[1])) {
						if (ss[0] < ss[1]) {
							System.out.print("$s0, $s1");
						} else {
							System.out.print("$s1, $s0");
						}
					} else {
						if (words.contains(s[0])) {
							if (ss[0] < 10) {
								System.out.print("$s0, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s0");
							}
						} else {
							if (ss[1] < 10) {
								System.out.print("$s1, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s1");
							}
						}
					}

				}

				// > sign
				if (words.matches("(?i).*>.*")) {
					System.out.print("\tbgt ");

					if (words.contains(s[0]) && words.contains(s[1])) {
						if (ss[0] < ss[1]) {
							System.out.print("$s0, $s1");
						} else {
							System.out.print("$s1, $s0");
						}
					} else {
						if (words.contains(s[0])) {
							if (ss[0] < 10) {
								System.out.print("$s0, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s0");
							}
						} else {
							if (ss[1] < 10) {
								System.out.print("$s1, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s1");
							}
						}
					}
				}

				// != sign
				if (words.matches("(?i).*!=.*")) {
					System.out.print("\tbne ");

					if (words.contains(s[0]) && words.contains(s[1])) {
						if (ss[0] < ss[1]) {
							System.out.print("$s0, $s1");
						} else {
							System.out.print("$s1, $s0");
						}
					} else {
						if (words.contains(s[0])) {
							if (ss[0] < 10) {
								System.out.print("$s0, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s0");
							}
						} else {
							if (ss[1] < 10) {
								System.out.print("$s1, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s1");
							}
						}
					}
				}

				// == sign
				if (words.matches("(?i).*==.*")) {
					System.out.print("\tbeq ");

					if (words.contains(s[0]) && words.contains(s[1])) {
						if (ss[0] < ss[1]) {
							System.out.print("$s0, $s1");
						} else {
							System.out.print("$s1, $s0");
						}
					} else {
						if (words.contains(s[0])) {
							if (ss[0] < 10) {
								System.out.print("$s0, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s0");
							}
						} else {
							if (ss[1] < 10) {
								System.out.print("$s1, " + words.replaceAll("\\D+", ""));
							} else {
								System.out.print(words.replaceAll("\\D+", "") + ", $s1");
							}
						}
					}
				}
				System.out.println(", loop");
			}
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
//				System.out.println("\t" + line);
				convertCode(line);
			}
			bufferedReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
