package tinyregex.parser;

import java.util.ArrayList;
import java.util.List;

public final class ManyParser<T> extends Parser<List<T>> {
    private final Parser<T> parser;

    public ManyParser(Parser<T> p) {
        parser = p;
        result = new ArrayList<T>();
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        try {
            while (true) {
                pos = parser.parse(toks, pos);
                result.add(parser.result());
            }
        } catch (NoParseException e) {
            // ignored  - ManyParser terminates successfully even if it can't be matched
        }
        parsed = true;
        return pos;
    }
}
