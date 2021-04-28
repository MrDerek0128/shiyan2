import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        Config config = new Config();
        HashMap<Character, HashSet<Character>> res = new Utils().getFirst(config.grammars,config.nonTerminal);
        for (Character key:res.keySet()){
            System.out.print(key+"的First集为{ ");
            for (Character c:res.get(key)) {
                System.out.print(c+" ");
            }
            System.out.print("}");
            System.out.println();
        }

    }
}
