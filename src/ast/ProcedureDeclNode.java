
package ast;

import java.util.List;

public class ProcedureDeclNode implements ASTNode {
    private final String name;
    private final List<ParamNode> parameters;
    private final ASTNode body;

    public ProcedureDeclNode(String name, List<ParamNode> parameters, ASTNode body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public String toString() {
        return "ProcedureDecl(" + name + ", params=" + parameters + ", body=" + body + ")";
    }

    public String getName() { return name; }
    public List<ParamNode> getParameters() { return parameters; }
    public ASTNode getBody() { return body; }
}
