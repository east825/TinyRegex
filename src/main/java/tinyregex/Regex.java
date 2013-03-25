package tinyregex;

import tinyregex.parser.*;
import tinyregex.pattern.*;

import java.util.ArrayList;
import java.util.List;

import static tinyregex.parser.Parsers.*;

public class Regex {
    /**
     * Regex grammar:
     * <p/>
     * regex ::= alt
     * alt ::= seq, {'|', seq}
     * seq ::= {repeat | repeatable}
     * repeat ::= star | plus
     * repeatable ::= charclass | group | char | dot
     * star = repeatable, '*'
     * star = repeatable, '+'
     * group ::= '(', alt, ')'
     */
    public static void main(String[] args) throws Exception {
        ForwardParser<Pattern> alt = fwd();
        Parser<Pattern> character = map(
                tok("char"),
                new MapFunction<Token, Pattern>() {
                    @Override
                    public Pattern map(Token arg) {
                        return new CharPattern(arg.value.charAt(0));
                    }
                }
        );
        Parser<Pattern> charclass = map(
                tok("charclass"),
                new MapFunction<Token, Pattern>() {
                    @Override
                    public Pattern map(Token arg) {
                        if (arg.value.equals("w"))
                            return new LetterPattern();
                        return new SpacePattern();
                    }
                }
        );
        Parser<Pattern> group = map(
                seq(
                        skip(tok("meta", "("), Pattern.class),
                        alt,
                        skip(tok("meta", ")"), Pattern.class)

                ),
                new MapFunction<List<Pattern>, Pattern>() {
                    @Override
                    public Pattern map(List<Pattern> arg) {
                        return arg.get(0);
                    }
                }
        );

        Parser<Pattern> dot = map(
                tok("meta", "."),
                new MapFunction<Token, Pattern>() {
                    @Override
                    public Pattern map(Token arg) {
                        return new DotPattern();
                    }
                }
        );

        Parser<Pattern> repeatable = alt(
                group,
                charclass,
                character,
                dot
        );

        Parser<Pattern> star = map(
                seq(
                        repeatable,
                        skip(tok("meta", "*"), Pattern.class)
                ),
                new MapFunction<List<Pattern>, Pattern>() {
                    @Override
                    public Pattern map(List<Pattern> arg) {
                        return new StarPattern(arg.get(0));
                    }
                }
        );

        Parser<Pattern> plus = map(
                seq(
                        repeatable,
                        skip(tok("meta", "+"), Pattern.class)
                ),
                new MapFunction<List<Pattern>, Pattern>() {
                    @Override
                    public Pattern map(List<Pattern> arg) {
                        return new PlusPattern(arg.get(0));
                    }
                }
        );

        Parser<Pattern> seq = map(
                many(
                        alt(
                                star,
                                plus,
                                repeatable
                        )
                ),
                new MapFunction<List<Pattern>, Pattern>() {
                    @Override
                    public Pattern map(List<Pattern> arg) {
                        return new SequencePattern(arg);
                    }
                }
        );

        alt.define(map(
                // List<List<Pattern>>
                seq(
                        // Pattern -> List<Pattern>
                        expanded(seq),
                        // List<Pattern>
                        many(
                                // List<Pattern> -> Pattern
                                collapsed(
                                        seq(
                                                skip(tok("meta", "|"), Pattern.class),
                                                seq
                                        )
                                )
                        )
                ),
                new MapFunction<List<List<Pattern>>, Pattern>() {
                    @Override
                    public Pattern map(List<List<Pattern>> arg) {
                        List<Pattern> ps = new ArrayList<Pattern>();
                        ps.add(arg.get(0).get(0));
                        ps.addAll(arg.get(1));
                        return new AltPattern(ps);
                    }
                }
        ));

        Parser<Pattern> regex = alt;
        System.out.println(regex.parse(new RegexTokenizer().tokenize("(.|.|.)*")));

    }
}
