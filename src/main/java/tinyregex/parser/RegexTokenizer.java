package tinyregex.parser;

import java.util.*;

public class RegexTokenizer {

    private static final Set<Character> METASYMBOLS = new HashSet<Character>(Arrays.asList('.', '+', '-', '\\'));
    private static final Set<Character> CHARCLASSES = new HashSet<Character>(Arrays.asList('w', 's'));

    private static Token charToken(String type, char c) {
        return new Token(type, Character.toString(c));
    }

    public List<Token> tokenize(String s) {
        List<Token> toks = new ArrayList<Token>();
        char[]  chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i < s.length() - 1 && chars[i] == '\\') {
                if (METASYMBOLS.contains(chars[i + 1])) {
                    toks.add(charToken("char", chars[i]));
                } else if (CHARCLASSES.contains(chars[i + 1])) {
                    toks.add(charToken("charclass", chars[i + 1]));
                } else {
                    throw new IllegalArgumentException("Illegal escape sequence: " + s.substring(i, i + 2));
                }
                i++;
                continue;
            }
            if (METASYMBOLS.contains(chars[i])) {
                toks.add(charToken("meta", chars[i]));
            } else {
                toks.add(charToken("char", chars[i]));
            }
        }
        return toks;
    }

}
