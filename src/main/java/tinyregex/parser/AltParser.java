package tinyregex.parser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class AltParser<T>  extends Parser<T> {
    private List<Parser<T>> parsers = new ArrayList<Parser<T>>();

    public AltParser(List<? extends Parser<T>> ps) {
        if (ps.isEmpty())
            throw new IllegalArgumentException("No nested parsers inside AltParser");
        parsers.addAll(ps);
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        NoParseException lastException = null;
        for (Parser<T> p: parsers) {
            try {
                int nextPos = p.parse(toks, pos);
                parsed = true;
                result = p.result();
                return nextPos;
            } catch (NoParseException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

}
