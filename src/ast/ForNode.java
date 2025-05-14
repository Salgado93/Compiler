
package ast;

public class ForNode implements ASTNode {
    private final String variable;
    private final ASTNode start;
    private final ASTNode end;
    private final boolean isTo;
    private final ASTNode body;

    public ForNode(String variable, ASTNode start, ASTNode end, boolean isTo, ASTNode body) {
        this.variable = variable;
        this.start = start;
        this.end = end;
        this.isTo = isTo;
        this.body = body;
    }

    @Override
    public String toString() {
        return "For(" + variable + " := " + start + 
               (isTo ? " to " : " downto ") + end + ", " + body + ")";
    }

    public String getVariable() { return variable; }
    public ASTNode getStart() { return start; }
    public ASTNode getEnd() { return end; }
    public boolean isTo() { return isTo; }
    public ASTNode getBody() { return body; }
}
