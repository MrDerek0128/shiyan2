import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Utils {
    public HashMap<Character, HashSet<Character>> getFirst(ArrayList<Grammar> list, HashSet<Character> nonTerminal) {
        HashMap<Character, HashSet<Character>> res = new HashMap<>();
        HashMap<Character, HashSet<Character>> cache = new HashMap<>();
        for (Grammar grammar : list) {
            cache.computeIfAbsent(grammar.left.charAt(0), k -> new HashSet<>());
            cache.get(grammar.left.charAt(0)).add(grammar.right.charAt(0));
        }
        //先找到所有在第一步能找出来First集的元素
        check(nonTerminal, res, cache);
        while (res.size() != nonTerminal.size()) {
            HashSet<Character> cacheList = new HashSet<>();
            for (Grammar grammar : list) {
                if (!res.containsKey(grammar.left.charAt(0))) {
                    if (cache.get(grammar.left.charAt(0)) == null) {
                        cache.put(grammar.left.charAt(0),new HashSet<>());
                        cacheList.add(grammar.left.charAt(0));
                    }
                    for (int i = 0; i < grammar.right.length(); i++) {
                        if (!nonTerminal.contains(grammar.right.charAt(i))) {
                            cache.get(grammar.left.charAt(0)).add(grammar.right.charAt(i));
                            break;
                        }
                        else {
                            if (!res.containsKey(grammar.right.charAt(i))) {
                                 break;
                            } else {
                                cache.get(grammar.left.charAt(0)).addAll(res.get(grammar.right.charAt(i)));
                                cache.get(grammar.left.charAt(0)).remove('$');
                                if (!res.get(grammar.right.charAt(i)).contains('$')) {
                                    break;
                                }
                                if (i == grammar.right.length() - 1) {
                                    cache.get(grammar.left.charAt(0)).add('$');
                                }
                            }
                        }
                    }
                }
            }
            check(cacheList, res, cache);
            cacheList.clear();
        }
        return res;
    }

    private void check(HashSet<Character> cacheList, HashMap<Character, HashSet<Character>> res, HashMap<Character, HashSet<Character>> cache) {
        for (Character character : cacheList) {
            int flag = 0;
            if (cache.get(character).size()==0) {
                break;
            }
            for (Character c : cache.get(character)) {
                if (cacheList.contains(c)) {
                    flag++;
                }
            }
            if (flag == 0) {
                res.put(character,cache.get(character));
            }
        }
        cache.clear();
    }

    public HashMap<Character, HashSet<Character>> getFollow(ArrayList<Grammar> list, HashSet<Character> nonTerminal, HashMap<Character, HashSet<Character>> firstSet) {
        HashMap<Character, HashSet<Character>> res = new HashMap<>();
        HashMap<Character, HashSet<Character>> cache = new HashMap<>();
        HashSet<Character> cacheList = new HashSet<>();
        while (res.size() != nonTerminal.size()){
            for (Character c:nonTerminal) {
                for (Grammar grammar:list) {
                    int position = grammar.left.indexOf(c);
                    if (position != -1) {
                        cache.computeIfAbsent(c, k -> new HashSet<>());
                        while (position < grammar.left.length()) {
                            if (position == grammar.left.length()-1) {
                                cache.get(c).add(grammar.left.charAt(0));
                                break;
                            }
                            if (!nonTerminal.contains(grammar.left.charAt(position+1))) {
                                cache.get(c).add(grammar.left.charAt(position+1));
                                break;
                            } else {
                                HashSet<Character> first = firstSet.get(grammar.left.charAt(position));
                                cache.get(c).addAll(first);
                                if (first.contains('$')) {
                                    cache.get(c).remove('$');
                                    position++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            check();
        }

        return res;
    }

}
