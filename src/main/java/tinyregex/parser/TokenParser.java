package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.HashMap;
import java.util.List;

public final class TokenParser extends Parser<Token> {
    private final Token.Type type;
    private final String value;

    public TokenParser(Token.Type type) {
        this(type, null);
    }

    public TokenParser(Token.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    protected Result<Token> parse(List<Token> toks, int pos, HashMap<Integer, Result<?>> cache) throws NoParseException {
        if (pos >= toks.size()) {
            throw new NoParseException("Unexpected end of token sequence");
        }
        Token t = toks.get(pos);
        if (type == t.type && (value == null || value.equals(t.value))) {
            return new Result<Token>(t, pos + 1);
        }
        throw new NoParseException("Unexpected token " + t + " at " + pos);
    }
}
