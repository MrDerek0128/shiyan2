import java.util.ArrayList;
import java.util.HashSet;

public class Config {
    public HashSet<Character> nonTerminal = new HashSet<>();
    public ArrayList<Grammar> grammars = new ArrayList<>();

    Config() {
        nonTerminal.add('E');
        nonTerminal.add('G');
        nonTerminal.add('T');
        nonTerminal.add('U');
        nonTerminal.add('F');

        grammars.add(new Grammar("E","TG"));
        grammars.add(new Grammar("G","+TG"));
        grammars.add(new Grammar("G","$"));
        grammars.add(new Grammar("T","FU"));
        grammars.add(new Grammar("U","*FU"));
        grammars.add(new Grammar("U","$"));
        grammars.add(new Grammar("F","i"));
        grammars.add(new Grammar("F","(E)"));

//        nonTerminal.add('S');
//        nonTerminal.add('A');
//        nonTerminal.add('B');
//        nonTerminal.add('C');
//        nonTerminal.add('D');
//
//        grammars.add(new Grammar("S","AB"));
//        grammars.add(new Grammar("S","bC"));
//        grammars.add(new Grammar("A","$"));
//        grammars.add(new Grammar("A","b"));
//        grammars.add(new Grammar("B","$"));
//        grammars.add(new Grammar("B","aD"));
//        grammars.add(new Grammar("C","AD"));
//        grammars.add(new Grammar("C","b"));
//        grammars.add(new Grammar("D","aS"));
//        grammars.add(new Grammar("D","c"));
    }
}
