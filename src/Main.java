import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Leer archivo fuente
        CharStream input = CharStreams.fromFileName("program.pas");

        // Lexer
        MiniPascalLexer lexer = new MiniPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Mostrar análisis léxico
        System.out.println("=== Análisis Léxico ===");
        tokens.fill(); // Cargar todos los tokens
        for (Token token : tokens.getTokens()) {
            String tipo = MiniPascalLexer.VOCABULARY.getSymbolicName(token.getType());
            if (tipo == null) continue; // Ignorar tokens ignorados (como WS o comentarios)
            System.out.printf("TOKEN %-15s en línea %d, columna %d: '%s'%n",
                    tipo, token.getLine(), token.getCharPositionInLine(), token.getText());
        }

        // Parser
        MiniPascalParser parser = new MiniPascalParser(tokens);

        // Manejo de errores personalizado
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line,
                                    int column,
                                    String message,
                                    RecognitionException e) {
                System.err.printf("❌ Error de sintaxis en línea %d, columna %d: %s%n", line, column, message);
            }
        });

        // Análisis sintáctico
        System.out.println("\n=== Análisis Sintáctico (AST) ===");
        ParseTree tree = parser.program();
        printTree(tree, parser, 0);
    }

    private static void printTree(ParseTree tree, MiniPascalParser parser, int indent) {
        String indentStr = " ".repeat(indent * 2);
        if (tree instanceof TerminalNode) {
            Token token = ((TerminalNode) tree).getSymbol();
            String name = MiniPascalLexer.VOCABULARY.getSymbolicName(token.getType());
            if (name != null)
                System.out.printf("%s%s : '%s'%n", indentStr, name, token.getText());
        } else {
            String ruleName = parser.getRuleNames()[((ParserRuleContext) tree).getRuleIndex()];
            System.out.printf("%s%s%n", indentStr, ruleName);
            for (int i = 0; i < tree.getChildCount(); i++) {
                printTree(tree.getChild(i), parser, indent + 1);
            }
        }
    }
}