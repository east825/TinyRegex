package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

public final class MemoizedParser<T> extends Parser<T> {
    private static final ThreadLocal<IdentityHashMap<MemoizedParser<?>, HashMap<Integer, Result<?>>>> cachesRegistry =
            new ThreadLocal<IdentityHashMap<MemoizedParser<?>, HashMap<Integer, Result<?>>>>() {
        @Override
        protected IdentityHashMap<MemoizedParser<?>, HashMap<Integer, Result<?>>> initialValue() {
            return new IdentityHashMap<MemoizedParser<?>, HashMap<Integer, Result<?>>>();
        }
    };

    static void resetCache() {
        cachesRegistry.get().clear();
    }

    private final Parser<T> nested;

    public MemoizedParser(Parser<T> nested) {
        this.nested = nested;
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        // get thread local cache for all memoized parsers
        IdentityHashMap<MemoizedParser<?>, HashMap<Integer, Result<?>>> globalCache = cachesRegistry.get();
        // lazy instantiate parser-specific cache
        if (!globalCache.containsKey(this))
            globalCache.put(this, new HashMap<Integer, Result<?>>());
        HashMap<Integer, Result<?>> cache = globalCache.get(this);
        if (!cache.containsKey(pos))
            cache.put(pos, nested.parse(toks, pos));
        @SuppressWarnings("unchecked")
        Result<T> result = (Result<T>) cache.get(pos);
        return result;
    }
}
