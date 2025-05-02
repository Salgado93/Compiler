package ast;

import java.util.List;

public class ArrayAccessNode implements ASTNode {
    private final String arrayName;
    private final List<ASTNode> indices;

    public ArrayAccessNode(String arrayName, List<ASTNode> indices) {
        this.arrayName = arrayName;
        this.indices = indices;
    }

    public String getArrayName() {
        return arrayName;
    }

    public List<ASTNode> getIndices() {
        return indices;
    }

    @Override
    public String toString() {
        return "ArrayAccess(" + arrayName + ", " + indices + ")";
    }
}