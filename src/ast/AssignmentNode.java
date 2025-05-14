package ast;

public class AssignmentNode implements ASTNode {
    private final ASTNode variable;
    private final ASTNode value;

    public AssignmentNode(ASTNode variable, ASTNode value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Assign(" + variable + " := " + value + ")";
    }

    public ASTNode getVariable() {
        return variable;
    }

    public ASTNode getValue() {
        return value;
    }
}
