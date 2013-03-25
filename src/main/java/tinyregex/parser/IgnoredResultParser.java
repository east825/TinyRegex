package tinyregex.parser;

import java.util.List;

public final class IgnoredResultParser<T> extends Parser<T> {
    private final Parser<?> parser;

    public IgnoredResultParser(Parser<?> p) {
        parser = p;
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        return parser.parse(toks, pos);
    }
}
