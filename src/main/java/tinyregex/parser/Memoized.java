package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memoized<T> extends Parser<T> {
    private final Parser<T> nested;
    private final Map<Integer, Result<T>> cache = new HashMap<Integer, Result<T>>();

    public Memoized(Parser<T> nested) {
        this.nested = nested;
    }

    public void reset() {
        cache.clear();
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        if (!cache.containsKey(pos))
            cache.put(pos, nested.parse(toks, pos));
        return cache.get(pos);
    }
}
