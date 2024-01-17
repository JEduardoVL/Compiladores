import java.util.ArrayList;
import java.util.List;

public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    /*public Object executeWithArguments(List<Object> args, TablaSimbolos tabla) {
        // Configurar un nuevo entorno para la ejecución de la función
        TablaSimbolos tablaFuncion = new TablaSimbolos(tabla);

        // Asignar argumentos a parámetros
        if (args.size() != params.size()) {
            throw new RuntimeException("Número incorrecto de argumentos para la función " + name.getLexema());
        }
        for (int i = 0; i < params.size(); i++) {
            tablaFuncion.asignar(params.get(i).getLexema(), args.get(i));
        }

        // Ejecutar el cuerpo de la función
        body.solve(tablaFuncion);

        // Aquí se puede manejar el valor de retorno si tu lenguaje lo soporta
        return tablaFuncion.obtener("return"); // o el valor de retorno, si existe
    }*/

    @Override
    public String toString()  {
        String parametros = "";
        for (Token token : params) {
            parametros += token.lexema;
            parametros += ", ";
        }
        parametros = parametros.substring(0, parametros.length() - 2);
        return "fun " +name.lexema + "(" + parametros + ")" + body;
    }
    @Override
    void solve(TablaSimbolos tabla) {
        List<Object> funcion = new ArrayList<Object>();
        TablaSimbolos tablaFuncion = new TablaSimbolos(tabla);
        for (Token elemento : params) {
            tablaFuncion.asignar(elemento.lexema, null);
        }
        tablaFuncion.asignar("return", null);
        funcion.add(tablaFuncion);
        funcion.add(body);
        funcion.add(params);
        tabla.asignar(name.lexema, funcion);
        
    }
}
