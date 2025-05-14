
package ast;

import java.util.List;

public class RepeatNode implements ASTNode {
    private final List<ASTNode> body;
    private final ASTNode condition;

    public RepeatNode(List<ASTNode> body, ASTNode condition) {
        this.body = body;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Repeat(" + body + ", Until " + condition + ")";
    }

    public List<ASTNode> getBody() { return body; }
    public ASTNode getCondition() { return condition; }
}
