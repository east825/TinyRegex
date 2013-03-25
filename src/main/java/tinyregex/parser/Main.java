package tinyregex.parser;

import tinyregex.pattern.CharPattern;
import tinyregex.pattern.DotPattern;
import tinyregex.pattern.Pattern;
import tinyregex.pattern.SequencePattern;

import java.util.List;

import static tinyregex.parser.Parsers.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Parser<Pattern> charSequence = map(
                oneplus(
                        map(
                                alt(
                                        tok("char"),
                                        tok("meta", ".")
                                ),
                                new MapFunction<Token, Pattern>() {
                                    @Override
                                    public Pattern map(Token arg) {
                                        if (arg.type.equals("char"))
                                            return new CharPattern(arg.value.charAt(0));
                                        else
                                            return new DotPattern();
                                    }
                                })
                ),
                new MapFunction<List<Pattern>, Pattern>() {
                    @Override
                    public Pattern map(List<Pattern> arg) {
                        return new SequencePattern(arg);
                    }
                }
        );
        RegexTokenizer tokenizer = new RegexTokenizer();
        System.out.println(charSequence.parse(tokenizer.tokenize("abbb"), true));
    }
}
