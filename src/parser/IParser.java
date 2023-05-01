package parser;

public interface IParser {
    public static final char EOF = (char) -1;

    public char lookahead();

    public char next();

    public void Match(char c);

    public void error(String msg);

    public boolean parse(String s);

}
