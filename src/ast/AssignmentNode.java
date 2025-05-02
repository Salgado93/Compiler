package ast;

public class AssignmentNode implements ASTNode {
    private final String variable;
    private final ASTNode expression;

    public AssignmentNode(String variable, ASTNode expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "Assign(" + variable + " := " + expression + ")";
    }
}