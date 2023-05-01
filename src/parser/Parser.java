package parser;

import java.util.regex.Pattern;

public class Parser implements IParser {
    private String input;
    private int lookahead;

    public Parser() {
        this.input = "";
        this.lookahead = 0;
    }

    @Override
    public char lookahead() {
        if (lookahead >= input.length()) {
            return EOF;
        }
        return input.charAt(lookahead);
    }

    @Override
    public char next() {
        while (lookahead < input.length() && Character.isWhitespace(input.charAt(lookahead))) {
            lookahead++;
        }
        if (lookahead >= input.length()) {
            return EOF;
        }

        char nextChar = input.charAt(lookahead);
        
        lookahead++;
        return nextChar;
    }

    @Override
    public void Match(char c) {

        if (next() == c) {
            next();
        } else {
            error("Erro de sintaxe: esperava '" + c + "' mas encontrou '" + lookahead() + "'");

        }
    }

    @Override
    public void error(String msg) {
        int col = (lookahead == 0) ? 1 : lookahead;
        throw new RuntimeException("Syntax error: " + msg + " na coluna " + col);
    }

    @Override
    public boolean parse(String string) {
        this.input = string;
        lookahead = 0;
        try{
            
            Bool();
            return true;
        } catch (RuntimeException e) {
            System.err.println(e);
            return false;
        }
    }
    

    public void Bool(){
        Join();
        Bool_();
    }

    public void Bool_(){
        if(next() == '|'){
            Match('|');
            next() ;
            Join();
            Bool_();
            System.out.println("x");
        }
    }

    public void Join(){
        Join_();
        Equal();
    }

    public void Join_(){
        if(next() == '&'){
            Match('&');
            next() ;
            Equal();
            Join_();
        }
    }

    public void Equal(){
        Equal_();
        Rel();

    }

    public void Equal_(){
        if(next() == '='){
            Match('=');
            next() ;
            Rel();
            Equal_();
        }else if(next() == '~'){
            Match('~');
            next() ;
            Rel();
            Equal_();
        }
    }

    public void Rel(){
        Expr();
        Rel_();
    }

    public void Rel_(){
        if(next() == '<'){
            Match('<');
            next() ;
            Expr();
            Rel_();
        }else if(next() == '>'){
            Match('>');
            next() ;
            Expr();
            Rel_();
        }
    }

    public void Expr(){
        Term();
        Expr_();
    }

    public void Expr_(){
        if(next() == '+'){
            Match('+');
            System.out.println("a");
            next() ;
            Term();
            Expr_();
        }else if (next() == '-'){
            Match('-');
            next() ;
            Term();
            Expr_();
        }
    }

    public void Term(){
        Unary();
        Term_();
    }

    public void Term_(){
        if(next() == '*'){
            Match('*');
            next() ;
            Unary();
            Term_();
        }else if(next() == '/'){
            Match('/');
            next() ;
            Unary();
            Term_();
        }
    }

    public void Unary(){
        switch(lookahead()){
            case '!':
                Match('!');
                next() ;
                Unary();
                break;
            case '-':
                Match('-');
                next() ;
                Unary();
                break;
            case '+':
                Match('+');
                next() ;
                Unary();
                break;
            default:
                next() ;
                // Factor();
                break;
            
        }
    }

    public void Factor(){
        if(next() == '('){
            Match('(');
            Bool();
            Match(')');
        }else if(Pattern.matches("[a - z]", Character.toString(lookahead()))){
            Id();
        }else if(Pattern.matches("[0 - 9]",Character.toString(lookahead()))){
            Digi();
        }else if(next() == 't'  || next() == 'f'){
            Lit_Bool();
        }else{
            error(" Erro de sintaxe 1");
        }
    }

    public void Id(){
        Match(lookahead());
    }

    public void Digi(){
        Match(lookahead());
    }

    public void Lit_Bool(){
        Match(lookahead());
    }


}
