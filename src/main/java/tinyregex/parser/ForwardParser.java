package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.List;

public final class ForwardParser<T> extends Parser<T> {
    private Parser<T> parser;

    public void define(Parser<T> p) {
        parser = p;
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        if (parser == null) {
            throw new IllegalStateException("Forward parser was not defined");
        }
        return parser.parse(toks, pos);
    }
}
