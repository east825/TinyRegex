package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SeqParser<T> extends Parser<List<T>> {
    private final List<Parser<T>> parsers = new ArrayList<Parser<T>>();

    public SeqParser(List<? extends Parser<T>> ps) {
        parsers.addAll(ps);
    }

    @Override
    protected Result<List<T>> parse(List<Token> toks, int pos, HashMap<Integer, Result<?>> cache) throws NoParseException {
        List<T> acc = new ArrayList<T>();
        for (Parser<T> p : parsers) {
            Result<T> res = p.parse(toks, pos, cache);
            pos = res.nextPos;
            if (!(p instanceof IgnoredResultParser))
                acc.add(res.data);
        }
        return new Result<List<T>>(acc, pos);
    }
}


