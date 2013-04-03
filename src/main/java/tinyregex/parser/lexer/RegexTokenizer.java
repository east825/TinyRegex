package tinyregex.parser.lexer;

import java.util.*;

public class RegexTokenizer {

    private static final Set<Character> METASYMBOLS = new HashSet<Character>(Arrays.asList('.', '+', '*', '\\', '|', '(', ')'));
    private static final Set<Character> CHARCLASSES = new HashSet<Character>(Arrays.asList('w', 's'));

    private static Token charToken(Token.Type type, char c) {
        return new Token(type, Character.toString(c));
    }

    public List<Token> tokenize(String s) {
        List<Token> toks = new ArrayList<Token>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                if (i < s.length() - 1) {
                    char c2 = s.charAt(i + 1);
                    if (METASYMBOLS.contains(c2)) {
                        toks.add(charToken(Token.Type.CHAR, c2));
                    } else if (CHARCLASSES.contains(c2)) {
                        toks.add(charToken(Token.Type.CHARCLASS, c2));
                    } else if (c2 == 'n') {
                        toks.add(charToken(Token.Type.CHAR, '\n'));
                    } else if (c2 == 't') {
                        toks.add(charToken(Token.Type.CHAR, '\t'));
                    } else {
                        throw new IllegalArgumentException("Illegal escape sequence at " + i + ": " + s.substring(i, i + 2));
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Trailing backslash at " + i);
                }
            } else if (METASYMBOLS.contains(c)) {
                toks.add(charToken(Token.Type.META, c));
            } else {
                toks.add(charToken(Token.Type.CHAR, c));
            }
        }
        return toks;
    }
}
