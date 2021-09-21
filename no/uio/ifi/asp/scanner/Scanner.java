package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
    }


    private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
    }


    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
    }


    public void readNextToken() {
		if (! curLineTokens.isEmpty())
			curLineTokens.remove(0);
	}
	
	//To check the next token
	public Token nextToken() {
		Token next = null;
		if (curLineTokens.size() > 1) {
			next = curLineTokens.get(1);
		}
		return next;
	}


    private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// Checking for E-o-f
		if(line == null) {
			curLineTokens.add(new Token(eofToken));
			return;
		}

		// Ignoring blank lines
		if (line.length() == 0) {
			readNextLine();
			return;
		}

		// Checking indent and dedent tokens
		line = expandLeadingTabs(line);
		// line = expandLeadingTabs(line);
		int spaces = findIndent(line);
		if (indents.isEmpty()) {
			indents.add(spaces);
		} else {
			if (spaces > indents.lastElement()) {
				curLineTokens.add(new Token(indentToken, curLineNum()));
				indents.push(spaces);
			} else if (spaces < indents.lastElement()) {
				while(spaces < indents.lastElement()) {

					// Ignoring dedents for commented lines
					if (spaces < line.length() && line.charAt(spaces) == '#') {
						readNextLine();
						return;	
					}
					curLineTokens.add(new Token(dedentToken, curLineNum()));
					indents.pop();
				}
			}
		}

		// Reading line contents
		for (int i = spaces; i < line.length(); i++) {

			// Checks commented lines
			if (line.charAt(i) == '#') {
				if (i == 0) return;
				break;
			}

			else if (line.charAt(i) == ' ') {
				//Do nothing
			}

			// Checking reserved names or variables
			else if (isLetterAZ(line.charAt(i))) {
				int j = i + 1;
				String name = "";
				name += line.charAt(i);

				// Variables with ints in the body are accepted
				while (j <= line.length()-1 && (isLetterAZ(line.charAt(j)) || isDigit(line.charAt(j)))) {
					name += line.charAt(j);
					j++;
				}
				// Creates reserved tokens or nameTokens
				TokenKind kind = isReserved(name);
				Token t = new Token(kind, curLineNum());
				t.name = name;
				curLineTokens.add(t);
				i = j-1;
			}

			// Checking ints and floats
			else if (isDigit(line.charAt(i))) {
				// Checking for illegal 0 position
				if(i < line.length() - 1 && line.charAt(i) == '0' && isDigit(line.charAt(i+1))) {
					reportError(curLineNum(), line.charAt(i), "Illegal integer, 0 cannot be followed by an int.");
					return;
				}
				
				int j = i + 1;
				String num = "";
				num += line.charAt(i);
				boolean isFloat = false;
				while ((j < line.length()) && (isDigit(line.charAt(j)) || (line.charAt(j) == '.'))) {

					// Checking floats and determining illegal floats
					if (line.charAt(j) == '.') {
						isFloat = true;
						if(j >= line.length()-1 || !isDigit(line.charAt(j+1))) {
							reportError(curLineNum(), line.charAt(j), "Illegal float literal.");
						}
					}
					num += line.charAt(j);
					j++;
				}

				// Making tokens
				if (isFloat) {
					Token t = new Token(floatToken, curLineNum());
					t.floatLit = Double.parseDouble(num);
					curLineTokens.add(t);
				} else {
					Token t = new Token(integerToken, curLineNum());
					t.integerLit = Integer.parseInt(num);
					curLineTokens.add(t);
				}
				i = j-1;
			}

			// Checking one and two reserved chars
			else if (isReserved(Character.toString(line.charAt(i))) != nameToken || isReserved(Character.toString(line.charAt(i)) + Character.toString(line.charAt(i+1))) != nameToken) {
				String token = "";
				token += line.charAt(i);
				String possibleToken = "";
				if (i < line.length() - 1) {
					possibleToken = token += line.charAt(i+1);
				}
				TokenKind t;
				if ((i < line.length() - 1) && (isReserved(possibleToken) != nameToken)) {
					t = isReserved(token);
					curLineTokens.add(new Token(t, curLineNum()));
					i++;
				} else {
					t = isReserved(Character.toString(line.charAt(i)));
					curLineTokens.add(new Token(t, curLineNum()));
				}
			}

			// Checking for strings
			else if (line.charAt(i) == '\"' || line.charAt(i) == '\'') {
				
				int j = i + 1;
				String s = "";
				while ((line.charAt(j) != '\"' && line.charAt(i) == '\"') || (line.charAt(j) != '\'' && line.charAt(i) == '\'')) {
					// Checking lines spanning multiple lines
					if(j+1 == line.length()) { 
						reportError(curLineNum(), line.charAt(j), "String is spanning multiple lines.");
					}
					s += line.charAt(j);
					j++;
				}
				Token t = new Token(stringToken, curLineNum());
				t.stringLit = s;
				curLineTokens.add(t);
				i = j;
			}

			// Report error in reading contents of source code
			else {
				reportError(curLineNum(), line.charAt(i), "Error reading symbol.");
			}
		}

		// Terminate line:
		curLineTokens.add(new Token(newLineToken,curLineNum()));
		for (Token t: curLineTokens) Main.log.noteToken(t);
	}



	// Method for reporting errors while reading
	private static void reportError(int line, char what, String message) {
		System.err.println("[Line: " + line + "]: " + message + ": " + what);
		System.exit(1);
	}

	// Method to check for reserved keywords
	private TokenKind isReserved(String s) {
		for (TokenKind t : TokenKind.values()) {
			if (s.equals(t.toString())) {
				return t;
			}
		}
		return nameToken;
	}


	public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
		String newS = "";
		for (int i = 0;  i < s.length();  i++) {
			char c = s.charAt(i);
			if (c == '\t') {
			do {
				newS += " ";
			} while (newS.length()%TABDIST > 0);
			} else if (c == ' ') {
			newS += " ";
			} else {
			newS += s.substring(i);
			break;
			}
		}
		return newS;
    }


    private boolean isLetterAZ(char c) {
		return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
		return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		switch(k) {
			case greaterToken:
				return true;
			case lessToken:
				return true;
			case lessEqualToken:
				return true;
			case greaterEqualToken:
				return true;
			case doubleEqualToken:
				return true;
			case notEqualToken:
				return true;
		}
		return false;
    }


    public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		if(k == plusToken || k == minusToken) {
			return true;
		}
		return false;
    }


    public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
			switch(k) {
				case astToken:
					return true;
				case slashToken:
					return true;
				case percentToken:
					return true;
				case doubleSlashToken:
					return true;
			}
		return false;
    }
	

    public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		if(k == plusToken || k == minusToken) {
			return true;
		}
		return false;
    }


    public boolean anyEqualToken() {
		for (Token t: curLineTokens) {
			if (t.kind == equalToken) return true;
			if (t.kind == semicolonToken) return false;
		}
		return false;
	}
}
