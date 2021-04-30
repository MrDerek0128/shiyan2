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
        System.out.println();
        for (Character key:follow.keySet()){
            System.out.print(key+"的Follow集为{ ");
            for (Character c:follow.get(key)) {
                System.out.print(c+" ");
            }
            System.out.print("}");
            System.out.println();
        }
        System.out.println();
        HashMap<Grammar, HashSet<Character>> select = utils.getSelect(config.grammars, config.nonTerminal, first, follow);
        for (Grammar key:select.keySet()){
            System.out.print(key+"的Select集为{ ");
            for (Character c:select.get(key)) {
                System.out.print(c+" ");
            }
            System.out.print("}");
            System.out.println();
        }
        System.out.println();
        HashMap<String,String> form = utils.getForm(select);
        if ("1".equals(form.get("error"))) {
            System.out.println("该文法不为LL(1)文法");
            return;
        }
        for (String key:form.keySet()) {
            if ("error".equals(key)) {
                continue;
            }
            System.out.println(key+"对应的产生式为"+form.get(key));
        }
        utils.result("i+i*i",form, config.nonTerminal);
    }
}
