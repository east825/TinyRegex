package tinyregex.parser;

import java.util.List;

public final class IgnoredResultParser<T> extends Parser<T> {
    private final Parser<?> parser;

    public IgnoredResultParser(Parser<?> p) {
        parser = p;
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        Result<?> res = parser.parse(toks, pos);
        return new Result<T>(null, res.nextPos);
    }
}
