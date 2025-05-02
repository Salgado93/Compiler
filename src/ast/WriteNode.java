package ast;

public class WriteNode implements ASTNode {
    private final String message;
    private final ASTNode variable;

    public WriteNode(String message, ASTNode variable) {
        this.message = message;
        this.variable = variable;
    }

    @Override
    public String toString() {
        if (variable != null) {
            return "Write(\"" + message + "\", " + variable + ")";
        } else {
            return "Write(\"" + message + "\")";
        }
    }
}
