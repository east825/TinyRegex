package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.HashMap;
import java.util.List;

public final class IgnoredResultParser<T> extends Parser<T> {
    private final Parser<?> parser;

    public IgnoredResultParser(Parser<?> p) {
        parser = p;
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos, HashMap<Integer, Result<?>> cache) throws NoParseException {
        Result<?> res = parser.parse(toks, pos, cache);
        return new Result<T>(null, res.nextPos);
    }
}
