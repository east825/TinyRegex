package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.List;

public abstract class Parser<T> {

    public static class Result<T> {
        public final T data;
        public final int nextPos;

        public Result(T data, int nextPos) {
            this.data = data;
            this.nextPos = nextPos;
        }
    }

    public final T parse(List<Token> toks) throws NoParseException {
        return parse(toks, false);
    }

    public final T parse(List<Token> toks, boolean matchEnd) throws NoParseException {
        Result<T> result = parse(toks, 0);
        if (result.nextPos > toks.size())
            throw new AssertionError("More tokens parsed than exist: consumed=" + result.nextPos + ", total=" + toks.size());

        if (matchEnd && result.nextPos < toks.size()) {
            throw new NoParseException("Unmatched tokens left: " + toks.subList(result.nextPos, toks.size()));
        }
        return result.data;
    }

    protected abstract Result<T> parse(List<Token> toks, int pos) throws NoParseException;
}
