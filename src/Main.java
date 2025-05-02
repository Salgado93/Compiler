import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import parser.MiniPascalLexer;
import parser.MiniPascalParser;
import ast.ASTBuilder;
import ast.ASTNode;

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
            if (tipo == null) continue; // Ignorar tokens ignorados
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

        // Construcción del árbol de análisis sintáctico
        ParseTree tree = parser.program();

        // Construcción del AST personalizado
        ASTBuilder builder = new ASTBuilder();
        ASTNode ast = builder.visit(tree);

        // Mostrar el AST
        System.out.println("\n=== Árbol de Sintaxis Abstracta (AST) ===");
        System.out.println(ast);
    }
}
