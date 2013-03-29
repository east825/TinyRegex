package tinyregex.parser;

import tinyregex.parser.lexer.Token;

import java.util.List;

public final class MaybeParser<T>  extends Parser<T> {
    private final Parser<T> parser;

    public MaybeParser(Parser<T> p) {
        parser = p;
    }

    @Override
    protected Result<T> parse(List<Token> toks, int pos) throws NoParseException {
        try {
            return parser.parse(toks, pos);
        } catch (NoParseException e) {
            // ignored
        }
        // return null by default
        return new Result<T>(null, pos);
    }

}
