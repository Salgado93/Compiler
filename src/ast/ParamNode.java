
package ast;

public class ParamNode implements ASTNode {
    private final String name;
    private final String type;
    private final boolean isByReference;

    public ParamNode(String name, String type, boolean isByReference) {
        this.name = name;
        this.type = type;
        this.isByReference = isByReference;
    }

    @Override
    public String toString() {
        return (isByReference ? "var " : "") + name + ": " + type;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public boolean isByReference() { return isByReference; }
}
