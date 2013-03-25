package tinyregex.parser;

import java.util.List;

public final class ForwardParser<T> extends Parser<T> {
    private Parser<T> parser;

    public void define(Parser<T> p) {
        parser = p;
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        if (parser == null) {
            throw new IllegalStateException("Forward parser was not defined");
        }
        return parser.parse(toks, pos);
    }

    @Override
    protected T result() {
        return parser.result();
    }
}
