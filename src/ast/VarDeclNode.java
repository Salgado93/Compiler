
package ast;

public class VarDeclNode implements ASTNode {
    private final String name;
    private final String type;

    public VarDeclNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "VarDecl(" + name + ": " + type + ")";
    }

    public String getName() { return name; }
    public String getType() { return type; }
}
