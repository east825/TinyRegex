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
        char[]  chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i < s.length() - 1 && chars[i] == '\\') {
                if (METASYMBOLS.contains(chars[i + 1])) {
                    toks.add(charToken(Token.Type.CHAR, chars[i + 1]));
                } else if (CHARCLASSES.contains(chars[i + 1])) {
                    toks.add(charToken(Token.Type.CHARCLASS, chars[i + 1]));
                } else if (chars[i + 1] == 'n') {
                    toks.add(charToken(Token.Type.CHAR, '\n'));
                } else if (chars[i + 1] == 't') {
                    toks.add(charToken(Token.Type.CHAR, '\t'));
                } else {
                    throw new IllegalArgumentException("Illegal escape sequence: " + s.substring(i, i + 2));
                }
                i++;
                continue;
            }
            if (METASYMBOLS.contains(chars[i])) {
                toks.add(charToken(Token.Type.META, chars[i]));
            } else {
                toks.add(charToken(Token.Type.CHAR, chars[i]));
            }
        }
        return toks;
    }

}
