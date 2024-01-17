/*public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }
}*/

public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object solve(TablaSimbolos tabla) {
        // Resolver la expresión interna y devolver su resultado
        // Pasando la tabla de símbolos para permitir la resolución de cualquier variable o función
        return expression.solve(tabla);
    }

    @Override
    public String toString() {
        // Devuelve la representación en cadena de la expresión, encerrada entre paréntesis
        return "(" + expression + ")";
    }
}


