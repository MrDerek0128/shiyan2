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
        grammars.add(new Grammar("T","FU"));
        grammars.add(new Grammar("U","*FU"));
        grammars.add(new Grammar("U","$"));
        grammars.add(new Grammar("F","i"));
        grammars.add(new Grammar("F","(E)"));
    }
}
