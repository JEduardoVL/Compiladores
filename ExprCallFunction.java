/*import java.util.List;

public class ExprCallFunction extends Expression {
    final Expression callee;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee, List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public Object solve(TablaSimbolos tabla) {
        // Resolver la expresión 'callee' para obtener el nombre de la función
        String functionName = callee.toString().trim();

        // Verificar si la función existe en la tabla de símbolos
        if (!tabla.existeIdentificador(functionName)) {
            throw new RuntimeException("Error semantico: La función " + functionName + " no está definida.");
        }

        // Obtener la función (StmtFunction) de la tabla de símbolos
        StmtFunction function = (StmtFunction) tabla.obtener(functionName);

        // Crear una nueva tabla de símbolos para el alcance de la función
        TablaSimbolos localTabla = new TablaSimbolos(tabla);

        // Evaluar los argumentos y asignarlos a los parámetros de la función
        List<Token> params = function.params;
        StmtBlock body = function.body;
        if (params.size() != arguments.size()) {
            throw new RuntimeException("Error semantico: Número incorrecto de argumentos para la función " + functionName);
        }
        for (int i = 0; i < arguments.size(); i++) {
            Object argValue = arguments.get(i).solve(tabla);
            localTabla.asignar(params.get(i).lexema, argValue);
        }

        // Ejecutar el cuerpo de la función con la tabla de símbolos local
        body.solve(localTabla);

        // Retornar el valor del retorno, si existe
        return localTabla.obtener("return");
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
*/
import java.util.List;

// Define la clase ExprCallFunction que extiende de Expression, usada para representar la llamada a una función.
public class ExprCallFunction extends Expression{
    // Expresión que representa la función a ser llamada.
    final Expression callee;
    // Lista de argumentos para la función.
    final List<Expression> arguments;

    // Constructor que inicializa la función a ser llamada y sus argumentos.
    ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee; // Inicializa la función a ser llamada.
        // this.paren = paren; // Comentado: podría haber sido utilizado para manejar paréntesis o simbología similar.
        this.arguments = arguments; // Inicializa la lista de argumentos.
    }

    // Sobrescribe el método toString para representar la llamada a la función como una cadena de texto.
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

    // Sobrescribe el método solve para resolver la llamada a la función.
    @Override
    Object solve(TablaSimbolos tabla) {
        // Convierte la expresión de la función a un string para usar como identificador.
        String identifcadorString = callee.toString();
        // Verifica si la función existe en la tabla de símbolos.
        if(tabla.existeIdentificador(identifcadorString)){
            // Obtiene la función de la tabla de símbolos.
            Object funcion = tabla.obtener(identifcadorString);
            // Convierte el objeto función a una lista para acceder a sus componentes.
            List<Object> lista = (List<Object>) funcion;
            // Inicializa una tabla de símbolos para la función.
            TablaSimbolos tablaFuncion;
            // Verifica si el número de argumentos es el correcto.
            if(((List<Token>)(lista.get(2))).size() == arguments.size()){
                // Inicializa la tabla de símbolos de la función con su alcance.
                tablaFuncion = (TablaSimbolos) (lista.get(0));
                // Asigna los valores de los argumentos a los parámetros de la función.
                for (int i = 0; i < arguments.size(); i++){
                    tablaFuncion.asignar(((List<Token>) (lista.get(2))).get(i).lexema, arguments.get(i).solve(tabla));
                }
                // Ejecuta el cuerpo de la función y retorna el valor resultante.
                ((StmtBlock) lista.get(1)).solve(tablaFuncion);
                return tablaFuncion.obtener("return");
            }
            else
                // Lanza una excepción si el número de argumentos no coincide.
                throw new RuntimeException("Error semantico: Parametros faltantes en " + identifcadorString);
        }
        else
            // Lanza una excepción si la función no está definida en la tabla de símbolos.
            throw new RuntimeException("Error semantico: La función no esta definida.");
    }
}
