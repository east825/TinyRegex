package tinyregex.parser;

import java.util.ArrayList;
import java.util.List;

public final class SeqParser<T> extends Parser<List<T>> {
    private final List<Parser<T>> parsers = new ArrayList<Parser<T>>();

    protected SeqParser(List<? extends Parser<T>> ps) {
        parsers.addAll(ps);
        result = new ArrayList<T>();
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        for (Parser<T> p : parsers) {
            pos = p.parse(toks, pos);
            if (!(p instanceof IgnoredResultParser))
                result.add(p.result());
        }
        parsed = true;
        return pos;
    }
}


