package ast;

public class BinaryOpNode implements ASTNode {
    private final String operator;
    private final ASTNode left;
    private final ASTNode right;

    public BinaryOpNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BinOp(" + left + " " + operator + " " + right + ")";
    }
}