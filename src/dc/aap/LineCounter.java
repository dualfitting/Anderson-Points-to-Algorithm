package dc.aap;

import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LineCounter {
	public static boolean isSingleBracket(String s) {
		if (!s.equals("{") && !s.equals("}") && !s.equals("(") && !s.equals(")")) {
			return false;
		}
		return true;
	}

	public static boolean isKeyword(String s) {
		if (!s.startsWith("do{") && !s.startsWith("while(") && !s.startsWith("for(") && !s.startsWith("if(")
				&& !s.startsWith("elseif(") && !s.startsWith("}while(") && !s.startsWith("}elseif(")
				&& !s.startsWith("try{") && !s.startsWith("}catch(") && !s.startsWith("catch(")
				&& !s.startsWith("public") && !s.startsWith("private")) {
			return false;
		}
		return true;
	}

	public static String chooseFile() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.showOpenDialog(null);

			String path = chooser.getSelectedFile().getAbsolutePath();

			return path;

		} catch (NullPointerException e) {
			System.out.println("Error: No file selected! Operation canceled by user?");
		}

		return "File not selected!";
	}

	public int LOC(String storeFileName) {
		int measuredLOC = 0;
		boolean commentBlock = false;
		try {
			// String storeOutput = "output.txt";
			// PrintWriter output = new PrintWriter(storeOutput);

			// Adding interactive component to allow user to choose a file
			// JOptionPane.showMessageDialog(null,"Locate file.", "LOCATE FILE",
			// JOptionPane.PLAIN_MESSAGE);
			// storeFileName = chooseFile();

			// Opening the source code file.
			File javaFile = new File(storeFileName);
			// File javaFile = new File("test_for_2.txt");
			// File javaFile = new File("LineCounter.java");
			Scanner in = new Scanner(javaFile);

			while (in.hasNextLine()) {
				String line = in.nextLine();
				int numOpenBlkComment = 0;
				int numClosedBlkComment = 0;

				line = line.replaceAll("\\n|\\t|\\s", "");
				// output.println("Current line of code: " + line);
				if (line.startsWith("/*")) {
					commentBlock = true;
					numOpenBlkComment = 1;
				}

				while (commentBlock) {
					line = in.next();
					line = line.replaceAll("\\n|\\t|\\s", "");

					// Keeping count of open and closed multi-lined
					// comments to managed nested comments which are
					// illegal in Java
					if (line.startsWith("/*")) {
						numOpenBlkComment++;
					}
					if (line.endsWith("*/") || line.startsWith("*/")) {
						numClosedBlkComment++;
					}
					if (numClosedBlkComment == numOpenBlkComment) {
						commentBlock = false;
					}
				}

				if (!line.equals("") && !isSingleBracket(line) && !line.startsWith("//") && !line.startsWith("@")) {
					if (line.endsWith(";")) {
						measuredLOC++;
						// output.println("CURRENT LOC COUNT: " + measuredLOC);
					} else if (isKeyword(line)) {
						measuredLOC++;
						// output.println("CURRENT LOC COUNT: " + measuredLOC);
					}
				}
				// output.println();
			}
			in.close();
			// output.close();

			return measuredLOC;

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException occurred.");
		}
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
