package ast;

public class ProgramNode implements ASTNode {
    private final String name;
    private final ASTNode block;

    public ProgramNode(String name, ASTNode block) {
        this.name = name;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public ASTNode getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "Program(" + name + ", " + block + ")";
    }
}