package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.HashMap;
import java.util.List;

public final class MemoizedParser<T> extends Parser<T> {
    private final Parser<T> nested;

    public MemoizedParser(Parser<T> nested) {
        this.nested = nested;
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos, HashMap<Integer, Result<?>> cache) throws NoParseException {
        @SuppressWarnings("unchecked")
        Result<T> result = (Result<T>) cache.get(pos);
        if (result == null) {
            result = nested.parse(toks, pos, cache);
            cache.put(pos, result);
        }
        return result;
    }
}
