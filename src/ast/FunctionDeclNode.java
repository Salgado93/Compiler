
package ast;

import java.util.List;

public class FunctionDeclNode implements ASTNode {
    private final String name;
    private final List<ParamNode> parameters;
    private final String returnType;
    private final ASTNode body;

    public FunctionDeclNode(String name, List<ParamNode> parameters, String returnType, ASTNode body) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    public String toString() {
        return "FunctionDecl(" + name + ", returns " + returnType + ", params=" + parameters + ", body=" + body + ")";
    }

    public String getName() { return name; }
    public List<ParamNode> getParameters() { return parameters; }
    public String getReturnType() { return returnType; }
    public ASTNode getBody() { return body; }
}
