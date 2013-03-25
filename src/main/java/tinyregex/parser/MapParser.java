package tinyregex.parser;

import java.util.List;

public class MapParser<T1, T2> extends Parser<T2> {
    private final Parser<T1> parser;
    private final MapFunction<? super T1, ? extends T2> fun;

    public MapParser(Parser<T1> p, MapFunction<? super T1, ? extends T2> f) {
        parser = p;
        fun = f;
    }

    @Override
    public int parse(List<Token> toks, int pos) throws NoParseException {
        return parser.parse(toks, pos);
    }

    @Override
    public T2 result() {
        return fun.map(parser.result());
    }
}
