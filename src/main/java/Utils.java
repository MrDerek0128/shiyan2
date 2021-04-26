import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Utils {
    public HashMap<String, HashSet<Character>> getFirst(ArrayList<Grammar> list, HashSet<Character> nonTerminal) {
        HashMap<String, HashSet<Character>> res = new HashMap<>();
        HashMap<String, HashSet<Character>> cache = new HashMap<>();
        while (res.size() == nonTerminal.size()) {
            for (Grammar grammar : list) {
                if (!nonTerminal.contains(grammar.right.charAt(0)) && !cache.get(grammar.left).contains(grammar.right.charAt(0))) {
                    if (!cache.containsKey(grammar.left)) {
                        cache.put(grammar.left, new HashSet<>());
                    }
                    cache.get(grammar.left).add(grammar.right.charAt(0));
                }
            }
            //遍历Cache，如果某一个Cache没有非终结符，加入Res
            for (Grammar grammar : list) {
                int flag = 0;
                for (Character c : cache.get(grammar.left)) {
                    if (nonTerminal.contains(c)) {
                        flag--;
                        if (!res.containsKey(grammar.left)) {
                            break;
                        } else {
                            cache.get(grammar.left).remove(c);
                            cache.get(grammar.left).addAll(res.get(grammar.left));
                        }

                    }
                    flag++;
                    if (flag==cache.get(grammar.left).size()) {
                        res.put(grammar.left,cache.get(grammar.left));
                    }
                }
            }
        }
        return res;
    }
}
