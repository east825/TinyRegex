package tinyregex.parser;

import java.util.List;

public final class TokenParser extends Parser<Token> {
    private final String type;
    private final String value;

    public TokenParser(String type) {
        this(type, null);
    }

    public TokenParser(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        if (pos >= toks.size()) {
            throw new NoParseException("Unexpected end of token sequence");
        }
        Token t = toks.get(pos);
        if (type.equals(t.type) && (value == null || value.equals(t.value))) {
            parsed = true;
            result = t;
            return pos + 1;
        }
        throw new NoParseException("Unexpected token " + t + " at " + pos);
    }
}
