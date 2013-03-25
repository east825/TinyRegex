package tinyregex.parser;

import java.util.List;

public abstract class Parser<T> {
    protected boolean parsed = false;
    protected T result;


    public final T parse(List<Token> toks) throws NoParseException {
        return parse(toks, false);
    }

    public final T parse(List<Token> toks, boolean matchEnd) throws NoParseException {

        int lastPos = parse(toks, 0);
        if (matchEnd && lastPos != toks.size()) {
            throw new NoParseException("Unmatched tokens left: " + toks.subList(lastPos, toks.size()));
        }
        return result();
    }

    protected abstract int parse(List<Token> toks, int pos) throws NoParseException;

    protected T result() {
        if (!parsed)
            throw new IllegalStateException("result() called before successful call to parse()");
        return result;
    }
}
