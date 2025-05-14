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
        ASTNode variable = visit(ctx.variable());
        ASTNode value = visit(ctx.expr());
        return new AssignmentNode(variable, value);
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
        if (ctx.NUMBER() != null) {
            return new LiteralNode(Integer.parseInt(ctx.NUMBER().getText()));
        } else if (ctx.CHAR_LITERAL() != null) {
            String raw = ctx.CHAR_LITERAL().getText();
            return new LiteralNode(raw.charAt(1));
        } else if (ctx.STRING_LITERAL() != null) {
            String raw = ctx.STRING_LITERAL().getText();
            return new LiteralNode(raw.substring(1, raw.length() - 1));
        } else if (ctx.TRUE() != null) {
            return new LiteralNode(true);
        } else if (ctx.FALSE() != null) {
            return new LiteralNode(false);
        } else if (ctx.expr(0) != null) {
            return visit(ctx.expr(0));
        } else if (ctx.NOT() != null) {
            return new UnaryOpNode("not", visit(ctx.factor()));
        } else if (ctx.variable() != null) {
            return visit(ctx.variable());
        }

        return null;
    }

    @Override
    public ASTNode visitVariable(MiniPascalParser.VariableContext ctx) {
        String name = ctx.ID().getText();

        if (ctx.expr().isEmpty()) {
            return new VariableNode(name);
        } else {
            List<ASTNode> indices = new ArrayList<>();
            for (var exprCtx : ctx.expr()) {
                indices.add(visit(exprCtx));
            }
            return new ArrayAccessNode(name, indices);
        }
    }

    @Override
    public ASTNode visitWriteStatement(MiniPascalParser.WriteStatementContext ctx) {
        if (ctx.getChildCount() == 1 && ctx.WRITELN() != null) {
            return new WriteLineNode();
        }

        String text = ctx.STRING_LITERAL().getText();
        text = text.substring(1, text.length() - 1); // quitar comillas

        ASTNode variable = null;
        if (ctx.expr() != null) {
            variable = visit(ctx.expr());
        }

        return new WriteNode(text, variable);
    }

    @Override
    public ASTNode visitReadStatement(MiniPascalParser.ReadStatementContext ctx) {
        ASTNode variable = visit(ctx.variable());
        return new ReadNode(variable);
    }

    @Override
    public ASTNode visitIfStatement(MiniPascalParser.IfStatementContext ctx) {
        ASTNode condition = visit(ctx.expr());
        ASTNode thenBranch = visit(ctx.statement(0));
        ASTNode elseBranch = ctx.statement().size() > 1 ? visit(ctx.statement(1)) : null;
        return new IfNode(condition, thenBranch, elseBranch);
    }

    @Override
    public ASTNode visitWhileStatement(MiniPascalParser.WhileStatementContext ctx) {
        ASTNode condition = visit(ctx.expr());
        ASTNode body = visit(ctx.statement());
        return new WhileNode(condition, body);
    }

    @Override
    public ASTNode visitForStatement(MiniPascalParser.ForStatementContext ctx) {
        String var = ctx.ID().getText();
        ASTNode start = visit(ctx.expr(0));
        ASTNode end = visit(ctx.expr(1));
        boolean isTo = ctx.TO() != null;
        ASTNode body = visit(ctx.statement());
        return new ForNode(var, start, end, isTo, body);
    }

    @Override
    public ASTNode visitRepeatStatement(MiniPascalParser.RepeatStatementContext ctx) {
        List<ASTNode> body = new ArrayList<>();
        for (var stmtCtx : ctx.statementList().statement()) {
            ASTNode stmt = visit(stmtCtx);
            if (stmt != null) body.add(stmt);
        }
        ASTNode condition = visit(ctx.expr());
        return new RepeatNode(body, condition);
    }

    @Override
    public ASTNode visitFunctionCall(MiniPascalParser.FunctionCallContext ctx) {
        String name = ctx.ID().getText();
        List<ASTNode> args = new ArrayList<>();
        if (ctx.expr() != null) {
            for (var exprCtx : ctx.expr()) {
                args.add(visit(exprCtx));
            }
        }
        return new FunctionCallNode(name, args);
    }

    @Override
    public ASTNode visitFunctionDecl(MiniPascalParser.FunctionDeclContext ctx) {
        String name = ctx.ID().getText();
        List<ParamNode> params = new ArrayList<>();

        if (ctx.formalParams() != null) {
            for (var paramCtx : ctx.formalParams().param()) {
                boolean byRef = paramCtx.VAR() != null;
                String type = paramCtx.baseType().getText();
                for (var id : paramCtx.ID()) {
                    params.add(new ParamNode(id.getText(), type, byRef));
                }
            }
        }

        String returnType = ctx.baseType().getText();
        ASTNode body = visit(ctx.block());
        return new FunctionDeclNode(name, params, returnType, body);
    }

    @Override
    public ASTNode visitProcedureDecl(MiniPascalParser.ProcedureDeclContext ctx) {
        String name = ctx.ID().getText();
        List<ParamNode> params = new ArrayList<>();

        if (ctx.formalParams() != null) {
            for (var paramCtx : ctx.formalParams().param()) {
                boolean byRef = paramCtx.VAR() != null;
                String type = paramCtx.baseType().getText();
                for (var id : paramCtx.ID()) {
                    params.add(new ParamNode(id.getText(), type, byRef));
                }
            }
        }

        ASTNode body = visit(ctx.block());
        return new ProcedureDeclNode(name, params, body);
    }
}
