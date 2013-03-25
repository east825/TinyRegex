package tinyregex.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Parsers {
    public static Parser<Token> tok(String name, String value) {
        return new TokenParser(name, value);
    }

    public static Parser<Token> tok(String name) {
        return new TokenParser(name);
    }

    public static <T> SeqParser<T> seq(Parser<T>... ps) {
        return new SeqParser<T>(Arrays.asList(ps));
    }

    public static <T> AltParser<T> alt(Parser<T>... ps) {
        return new AltParser<T>(Arrays.asList(ps));
    }

    public static <T> ManyParser<T> many(Parser<T> p) {
        return new ManyParser<T>(p);
    }

    public static <T> Parser<List<T>> oneplus(Parser<T> p) {
        return map(
                seq(
                        map(
                                p,
                                // wrap single element of type T to List<T> containing it
                                new MapFunction<T, List<T>>() {
                                    @Override
                                    public List<T> map(T arg) {
                                        return Collections.singletonList(arg);
                                    }
                                }),
                        many(p)
                ),
                new MapFunction<List<List<T>>, List<T>>() {
                    @Override
                    public List<T> map(List<List<T>> arg) {
                        List<T> result = new ArrayList<T>();
                        result.addAll(arg.get(0)); // should be one element list
                        result.addAll(arg.get(1)); // remaining args
                        return result;
                    }
                }
        );
    }

    public static <T> MaybeParser<T> maybe(Parser<T> p) {
        return new MaybeParser<T>(p);
    }

    public static <T> ForwardParser<T> fwd() {
        return new ForwardParser<T>();
    }

    public static <T1, T2> MapParser<T1, T2> map(Parser<T1> p, MapFunction<? super T1, ? extends T2> f) {
        return new MapParser<T1, T2>(p, f);
    }


}
