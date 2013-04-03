package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.WeakHashMap;

public final class MemoizedParser<T> extends Parser<T> {
    private static final IdentityHashMap<MemoizedParser<?>, WeakHashMap<List<Token>, HashMap<Integer, Result<?>>>> cachesRegistry =
            new IdentityHashMap<MemoizedParser<?>, WeakHashMap<List<Token>, HashMap<Integer, Result<?>>>>();

    private final Parser<T> nested;
    private final WeakHashMap<List<Token>, HashMap<Integer, Result<?>>> cache;

    public MemoizedParser(Parser<T> nested) {
        this.nested = nested;
        cachesRegistry.put(this, new WeakHashMap<List<Token>, HashMap<Integer, Result<?>>>());
        cache = cachesRegistry.get(this);
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        // Lazy instantiate cache for processed token sequence
        if (!cache.containsKey(toks)) {
            // thread safety through double-synchronized check
            // otherwise attempt to parse the same sequence if other thread with
            // this parser may result in erasure of all results obtained so far
            synchronized (cache) {
                if (!cache.containsKey(toks))
                    cache.put(toks, new HashMap<Integer, Result<?>>());
            }
        }
        // on the other hand parsing input for one position once again seems not so devastating
        HashMap<Integer, Result<?>> tokensCache = cache.get(toks);
        if (!tokensCache.containsKey(pos))
            tokensCache.put(pos, nested.parse(toks, pos));
        @SuppressWarnings("unchecked")
        Result<T> result = (Result<T>) tokensCache.get(pos);
        return result;
    }
}
