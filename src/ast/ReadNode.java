package ast;

public class ReadNode implements ASTNode {
    private final String variableName;

    public ReadNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "Read(" + variableName + ")";
    }
}

