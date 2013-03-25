package tinyregex.parser;

import java.util.ArrayList;
import java.util.List;

public final class ManyParser<T> extends Parser<List<T>> {
    private final Parser<T> parser;

    public ManyParser(Parser<T> p) {
        parser = p;
    }

    @Override
    protected Result<List<T>> parse(List<Token> toks, int pos) throws NoParseException {
        List<T> acc = new ArrayList<T>();
        try {
            while (true) {
                Result<T> res = parser.parse(toks, pos);
                pos = res.nextPos;
                acc.add(res.data);
            }
        } catch (NoParseException e) {
            // ignored  - ManyParser terminates successfully even if it can't be matched
        }
        return new Result<List<T>>(acc, pos);
    }
}
