
package ast;

import java.util.List;

public class FunctionCallNode implements ASTNode {
    private final String name;
    private final List<ASTNode> arguments;

    public FunctionCallNode(String name, List<ASTNode> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "Call(" + name + ", " + arguments + ")";
    }

    public String getName() { return name; }
    public List<ASTNode> getArguments() { return arguments; }
}
