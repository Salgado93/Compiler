package ast;

public class UnaryOpNode implements ASTNode {
    private final String operator;
    private final ASTNode operand;

    public UnaryOpNode(String operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public String getOperator() {
        return operator;
    }

    public ASTNode getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return "UnaryOp(" + operator + " " + operand + ")";
    }
}