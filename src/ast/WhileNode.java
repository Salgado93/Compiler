
package ast;

public class WhileNode implements ASTNode {
    private final ASTNode condition;
    private final ASTNode body;

    public WhileNode(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "While(" + condition + ", " + body + ")";
    }

    public ASTNode getCondition() { return condition; }
    public ASTNode getBody() { return body; }
}
