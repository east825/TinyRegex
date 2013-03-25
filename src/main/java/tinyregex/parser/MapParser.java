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
    public Result<T2> parse(List<Token> toks, int pos) throws NoParseException {
        Result<T1> res = parser.parse(toks, pos);
        return new Result<T2>(fun.map(res.data), res.nextPos);
    }
}
