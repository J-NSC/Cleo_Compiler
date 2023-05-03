import parser.*;

public class GustinhoCompile {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        String input = "! ( a = b ) &(c<d|e>f)";
        
        boolean syntax = parser.parse(input);

        if(syntax){
            System.out.println("sintaxe correta");
        }else{
            System.out.println("erro de sintaxe");
        }
    }
}
