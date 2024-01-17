import java.util.ArrayList;
import java.util.List;

/*public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee,  List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }
}*/


public class ExprCallFunction extends Expression {
    final Expression callee;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee, List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public Object solve() {
        // Resolver la expresión 'callee' que debería devolver un objeto StmtFunction
        StmtFunction function = (StmtFunction) callee.solve();

        // Evaluar los argumentos
        List<Object> evaluatedArgs = new ArrayList<>();
        for (Expression arg : arguments) {
            evaluatedArgs.add(arg.solve());
        }

        // Ejecutar la función con los argumentos evaluados
        return function.executeWithArguments(evaluatedArgs);
    }

    @Override
    public String toString() {
        String lista = "";
        // Construye una cadena con los argumentos separados por comas.
        for (Expression expression : arguments) { 
            lista += expression.toString();
            lista += ", ";
        }
        // Elimina la última coma y espacio del string de argumentos.
        lista = lista.substring(0, lista.length() - 2);
        // Devuelve la representación en cadena de la llamada a la función.
        return callee + "(" + lista + ");";
    }
}

