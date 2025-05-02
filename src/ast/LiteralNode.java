package ast;

public class LiteralNode implements ASTNode {
    private final Object value;

    public LiteralNode(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Literal(" + value + ")";
    }
}