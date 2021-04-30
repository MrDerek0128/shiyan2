import java.util.Objects;

public class Grammar {
    public String left;
    public String right;

    public Grammar(String left, String right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Grammar)){
            return false;
        }
        Grammar grammar = (Grammar) o;
        return Objects.equals(left, grammar.left) && Objects.equals(right, grammar.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return left + "->" + right;
    }
}
