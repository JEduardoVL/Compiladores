import java.util.List;
/* 
public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public Object executeWithArguments(List<Object> args) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();

        // Configurar un nuevo entorno para la ejecución de la función
        tablaSimbolos.enterNewScope(name.getLexema());

        // Asignar argumentos a parámetros
        for (int i = 0; i < params.size(); i++) {
            tablaSimbolos.declare(params.get(i).getLexema(), args.get(i));
        }

        // Ejecutar el cuerpo de la función
        body.execute();

        // Salir del entorno de la función
        tablaSimbolos.exitCurrentScope();

        // Aquí se puede manejar el valor de retorno si tu lenguaje lo soporta
        return null; // o el valor de retorno, si existe
    }

    @Override
    public void execute() {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        // Registra la función en el entorno global o en el entorno actual
        tablaSimbolos.declare(name.getLexema(), this);
    }
}*/



public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public Object executeWithArguments(List<Object> args) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();

        // Configurar un nuevo entorno para la ejecución de la función
        tablaSimbolos.enterNewScope(name.getLexema());

        // Asignar argumentos a parámetros
        if (args.size() != params.size()) {
            throw new RuntimeException("Número incorrecto de argumentos para la función " + name.getLexema());
        }
        for (int i = 0; i < params.size(); i++) {
            tablaSimbolos.declare(params.get(i).getLexema(), args.get(i));
        }

        // Ejecutar el cuerpo de la función
        body.execute();

        // Salir del entorno de la función
        tablaSimbolos.exitCurrentScope();

        // Aquí se puede manejar el valor de retorno si tu lenguaje lo soporta
        return null; // o el valor de retorno, si existe
    }

    @Override
    public void execute() {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        // Registra la función en el entorno global o en el entorno actual
        tablaSimbolos.declare(name.getLexema(), this);
    }

    @Override
    public String toString() {
        StringBuilder parametros = new StringBuilder();
        for (Token token : params) {
            parametros.append(token.getLexema()).append(", ");
        }
        if (!params.isEmpty()) {
            parametros.delete(parametros.length() - 2, parametros.length());
        }
        return "fun " + name.getLexema() + "(" + parametros + ") " + body;
    }
}

