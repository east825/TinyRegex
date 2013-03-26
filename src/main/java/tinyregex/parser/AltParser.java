package tinyregex.parser;

import java.util.ArrayList;
import java.util.List;

public final class AltParser<T>  extends Parser<T> {
    private final List<Parser<T>> parsers = new ArrayList<Parser<T>>();

    public AltParser(List<? extends Parser<T>> ps) {
        if (ps.isEmpty())
            throw new IllegalArgumentException("No nested parsers inside AltParser");
        parsers.addAll(ps);
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        NoParseException lastException = null;
        for (Parser<T> p: parsers) {
            try {
                return p.parse(toks, pos);
            } catch (NoParseException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

}
