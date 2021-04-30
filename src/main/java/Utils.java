import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

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
                if (c == '#') {
                    continue;
                }
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
        while (res.size() != nonTerminal.size()){
            for (Character c:nonTerminal) {
                for (Grammar grammar:list) {
                    int position = grammar.right.indexOf(c);
                    if (position != -1) {
                        cache.computeIfAbsent(c, k -> new HashSet<>());
                        while (position < grammar.right.length()) {
                            if (position == grammar.right.length()-1) {
                                if (c != grammar.left.charAt(0)) {
                                    if (res.containsKey(grammar.left.charAt(0))) {
                                        cache.get(c).addAll(res.get(grammar.left.charAt(0)));
                                        break;
                                    }
                                    cache.get(c).add(grammar.left.charAt(0));
                                }
                                break;
                            }
                            if (!nonTerminal.contains(grammar.right.charAt(position+1))) {
                                cache.get(c).add(grammar.right.charAt(position+1));
                                break;
                            } else {
                                HashSet<Character> first = firstSet.get(grammar.right.charAt(position+1));
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
            cache.get('E').add('#');
            check(nonTerminal, res, cache);
        }

        return res;
    }

    public HashMap<Grammar, HashSet<Character>> getSelect(ArrayList<Grammar> list, HashSet<Character> nonTerminal, HashMap<Character, HashSet<Character>> firstSet, HashMap<Character, HashSet<Character>> followSet) {
        HashMap<Grammar, HashSet<Character>> res = new HashMap<>();
        for (Grammar grammar:list) {
            res.put(grammar,new HashSet<>());
            for (int i = 0; i < grammar.right.length(); i++) {
                if (!nonTerminal.contains(grammar.right.charAt(i)) && grammar.right.charAt(i)!='$') {
                    res.get(grammar).add(grammar.right.charAt(i));
                    break;
                } else {
                    if (grammar.right.charAt(i) == '$') {
                        res.get(grammar).addAll(followSet.get(grammar.left.charAt(0)));
                        break;
                    }
                    res.get(grammar).addAll(firstSet.get(grammar.right.charAt(i)));
                    if (!firstSet.get(grammar.right.charAt(i)).contains('$')) {
                        break;
                    }
                    if (i == grammar.right.length() - 1) {
                        res.get(grammar).addAll(followSet.get(grammar.left.charAt(0)));
                    }
                }
            }
        }
        return res;
    }

    public HashMap<String,String> getForm(HashMap<Grammar, HashSet<Character>> selectSet) {
        HashMap<String,String> res = new HashMap<>();
        res.put("error","0");
        for (Grammar grammar:selectSet.keySet()) {
            for (Character character:selectSet.get(grammar)) {
                String key = grammar.left.charAt(0)+""+character;
                if (res.containsKey(key)) {
                    res.replace("error","1");
                    return res;
                }
                res.put(key,grammar.right);
            }
        }
        return res;
    }

    public void result(String s, HashMap<String,String> form, HashSet<Character> nonTerminal) {
        Stack<Character> analyse = new Stack<>();
        Stack<Character> input = new Stack<>();
        analyse.push('#');
        analyse.push('E');
        input.push('#');
        StringBuilder aStack = new StringBuilder("#E");
        StringBuilder iStack = new StringBuilder("#");
        for (int i = s.length()-1; i >= 0; i--) {
            iStack.append(s.charAt(i));
            input.push(s.charAt(i));
        }
        System.out.println("分析式"+"   "+"剩余输入串"+"   "+"所用产生式");
        while (true) {
            if (analyse.peek().equals('#') && input.peek().equals('#')) {
                System.out.println(aStack+"   "+iStack+"   "+"接受");
                break;
            }
            if (nonTerminal.contains(analyse.peek())) {
                String key = analyse.peek()+""+input.peek();
                String in = form.get(key);
                System.out.println(aStack+"   "+iStack+"   "+analyse.peek()+"->"+form.get(key));
                aStack.delete(aStack.length()-1,aStack.length());
                analyse.pop();
                if ("$".equals(form.get(key))){
                    continue;
                }
                for (int i = in.length()-1; i >= 0; i--) {
                    aStack.append(in.charAt(i));
                    analyse.push(in.charAt(i));
                }
            } else {
                if (analyse.peek().equals(input.peek()) ) {
                    System.out.println(aStack+"   "+iStack+"   "+input.peek()+"匹配");
                    aStack.delete(aStack.length()-1,aStack.length());
                    iStack.delete(iStack.length()-1,iStack.length());
                    analyse.pop();
                    input.pop();
                }
            }
        }
    }

}
