import java.util.List;

public class ExprCallFunction extends Expression {
    final Expression callee;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee, List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    Object solve(TablaSimbolos tabla) {
        String identificadorString = callee.toString();
        if (tabla.existeIdentificador(identificadorString)) {
            Object funcion = tabla.obtener(identificadorString);

            if (!(funcion instanceof List<?>)) {
                throw new RuntimeException("Error semantico: Tipo inesperado para la función.");
            }

            List<?> lista = (List<?>) funcion;
            if (lista.size() != 3 || !(lista.get(0) instanceof TablaSimbolos)
                    || !(lista.get(1) instanceof StmtBlock)
                    || !(lista.get(2) instanceof List<?>)) {
                throw new RuntimeException("Error semantico: Estructura inesperada para la función.");
            }

            TablaSimbolos tablaFuncion = (TablaSimbolos) lista.get(0);
            StmtBlock bloqueFuncion = (StmtBlock) lista.get(1);
            List<?> parametrosFuncion = (List<?>) lista.get(2);

            if (parametrosFuncion.size() == arguments.size()) {
                for (int i = 0; i < arguments.size(); i++) {
                    Object parametro = parametrosFuncion.get(i);
                    if (!(parametro instanceof Token)) {
                        throw new RuntimeException("Error semantico: Tipo de parámetro inesperado en la función.");
                    }
                    Token tokenParametro = (Token) parametro;
                    tablaFuncion.asignar(tokenParametro.lexema, arguments.get(i).solve(tabla));
                }
                bloqueFuncion.solve(tablaFuncion);
                return tablaFuncion.obtener("return");
            } else {
                throw new RuntimeException("Error semantico: Parametros faltantes en " + identificadorString);
            }
        } else {
            throw new RuntimeException("Error semantico: La función no esta definida.");
        }
    }

    @Override
    public String toString() {
        String lista = "";
        for (Expression expression : arguments) {
            lista += expression.toString();
            lista += ", ";
        }
        lista = lista.substring(0, lista.length() - 2);
        return callee + "(" + lista + ");";
    }
}

