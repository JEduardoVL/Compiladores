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
        // Aquí asumimos que callee.resolve() devuelve una instancia de StmtFunction o similar
        StmtFunction function = (StmtFunction)callee.solve();

        // Evaluar los argumentos
        List<Object> evaluatedArgs = new ArrayList<>();
        for (Expression arg : arguments) {
            evaluatedArgs.add(arg.solve());
        }

        // Ejecutar la función con los argumentos evaluados
        return function.executeWithArguments(evaluatedArgs);
    }
}


