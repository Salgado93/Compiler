package ast;

public class VariableNode implements ASTNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Var(" + name + ")";
    }
}