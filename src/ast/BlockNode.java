package ast;

import java.util.List;

public class BlockNode implements ASTNode {
    private final List<ASTNode> statements;

    public BlockNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "Block(" + statements + ")";
    }
}