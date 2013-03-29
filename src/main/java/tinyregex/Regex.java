package tinyregex;

import tinyregex.parser.ForwardParser;
import tinyregex.parser.MapFunction;
import tinyregex.parser.NoParseException;
import tinyregex.parser.Parser;
import tinyregex.parser.lexer.RegexTokenizer;
import tinyregex.parser.lexer.Token;
import tinyregex.pattern.*;

import java.util.ArrayList;
import java.util.List;

import static tinyregex.parser.Parsers.*;

public class Regex {


    private static final Parser<Pattern> regexParser = buildParser();

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
    @SuppressWarnings("unchecked")
    private static Parser<Pattern> buildParser() {
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
                oneplus(
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
        return alt;
    }
    public static boolean match(String regex, String text) {
        try {
            Pattern pattern = regexParser.parse(new RegexTokenizer().tokenize(regex), true);
            return pattern.match(text);
        } catch (NoParseException e) {
            throw new IllegalArgumentException("Illegal regular expression");
        }
    }
}
