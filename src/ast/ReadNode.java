package ast;

public class ReadNode implements ASTNode {
    private final ASTNode variable;

    public ReadNode(ASTNode variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "Read(" + variable + ")";
    }

    public ASTNode getVariable() {
        return variable;
    }
}
