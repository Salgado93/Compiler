
package ast;

import java.util.Optional;

public class IfNode implements ASTNode {
    private final ASTNode condition;
    private final ASTNode thenBranch;
    private final Optional<ASTNode> elseBranch;

    public IfNode(ASTNode condition, ASTNode thenBranch, ASTNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = Optional.ofNullable(elseBranch);
    }

    @Override
    public String toString() {
        return "If(" + condition + ", " + thenBranch +
               (elseBranch.isPresent() ? ", " + elseBranch.get() : "") + ")";
    }

    public ASTNode getCondition() { return condition; }
    public ASTNode getThenBranch() { return thenBranch; }
    public Optional<ASTNode> getElseBranch() { return elseBranch; }
}
