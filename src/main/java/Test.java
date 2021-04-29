import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        Config config = new Config();
        Utils utils = new Utils();
        HashMap<Character, HashSet<Character>> first = utils.getFirst(config.grammars,config.nonTerminal);
        for (Character key:first.keySet()){
            System.out.print(key+"的First集为{ ");
            for (Character c:first.get(key)) {
                System.out.print(c+" ");
            }
            System.out.print("}");
            System.out.println();
        }
        HashMap<Character, HashSet<Character>> follow = utils.getFollow(config.grammars,config.nonTerminal,first);
        System.out.println(follow.size());
        for (Character key:follow.keySet()){
            System.out.print(key+"的Follow集为{ ");
            for (Character c:follow.get(key)) {
                System.out.print(c+" ");
            }
            System.out.print("}");
            System.out.println();
        }
    }
}
