package ast;

import parser.MiniPascalBaseVisitor;
import parser.MiniPascalParser;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends MiniPascalBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(MiniPascalParser.ProgramContext ctx) {
        String name = ctx.ID().getText();
        ASTNode block = visit(ctx.block());
        return new ProgramNode(name, block);
    }

    @Override
    public ASTNode visitBlock(MiniPascalParser.BlockContext ctx) {
        List<ASTNode> statements = new ArrayList<>();

        if (ctx.compoundStatement() != null) {
            MiniPascalParser.CompoundStatementContext comp = ctx.compoundStatement();
            if (comp.statementList() != null) {
                for (var stmtCtx : comp.statementList().statement()) {
                    ASTNode stmt = visit(stmtCtx);
                    if (stmt != null) statements.add(stmt);
                }
            }
        }

        return new BlockNode(statements);
    }


    @Override
    public ASTNode visitAssignment(MiniPascalParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();

        // Obtener la última expr() → la del lado derecho de :=
        List<MiniPascalParser.ExprContext> exprs = ctx.expr();
        ASTNode value = visit(exprs.get(exprs.size() - 1)); // la última es la expresión asignada

        return new AssignmentNode(varName, value);
    }

    @Override
    public ASTNode visitExpr(MiniPascalParser.ExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            ASTNode left = visit(ctx.getChild(0));
            String op = ctx.getChild(1).getText();
            ASTNode right = visit(ctx.getChild(2));
            return new BinaryOpNode(op, left, right);
        } else {
            return visit(ctx.getChild(0));
        }
    }

    @Override
    public ASTNode visitFactor(MiniPascalParser.FactorContext ctx) {
        if (ctx.NUM() != null) {
            return new LiteralNode(Integer.parseInt(ctx.NUM().getText()));
        } else if (ctx.STRING_LITERAL() != null) {
            // Elimina las comillas
            String raw = ctx.STRING_LITERAL().getText();
            return new LiteralNode(raw.substring(1, raw.length() - 1));
        } else if (ctx.TRUE() != null) {
            return new LiteralNode(true);
        } else if (ctx.FALSE() != null) {
            return new LiteralNode(false);
        } else if (ctx.ID() != null && ctx.expr().isEmpty()) {
            return new VariableNode(ctx.ID().getText());  // solo ID
        } else if (ctx.ID() != null && !ctx.expr().isEmpty()) {
            // Acceso a arreglo: ID [expr (, expr)?]
            String id = ctx.ID().getText();
            List<ASTNode> indices = new ArrayList<>();
            for (var exprCtx : ctx.expr()) {
                indices.add(visit(exprCtx));
            }
            return new ArrayAccessNode(id, indices); // si tienes esta clase
        } else if (ctx.expr().size() == 1) {
            return visit(ctx.expr(0));  // (expr)
        } else if (ctx.NOT() != null) {
            return new UnaryOpNode("not", visit(ctx.factor()));
        }

        return null; // fallback
    }

    @Override
    public ASTNode visitWriteStatement(MiniPascalParser.WriteStatementContext ctx) {
        String text = ctx.STRING_LITERAL().getText();
        text = text.substring(1, text.length() - 1); // eliminar comillas

        ASTNode variable = null;
        if (ctx.ID() != null) {
            variable = new VariableNode(ctx.ID().getText());
        }

        return new WriteNode(text, variable);
    }

    @Override
    public ASTNode visitReadStatement(MiniPascalParser.ReadStatementContext ctx) {
        String varName = ctx.ID().getText();
        return new ReadNode(varName);
    }




    // Agrega más métodos conforme necesites: visitIfStatement, visitWhileLoop, etc.
}

