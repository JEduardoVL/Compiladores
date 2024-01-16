import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ASDR implements Program{

    private String nombreAlcanceActual;
    private Stack<String> scopeNamesStack = new Stack<>();
    private Map<String, TablaSimbolos> symbolTables = new HashMap<>();
   
    private int scopeCounter = 0;


    private int i = 0;
    //private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);

        // Inicializar la tabla de símbolos global
        TablaSimbolos tablaGlobal = new TablaSimbolos();
        scopeNamesStack.push("global");
        symbolTables.put("global", tablaGlobal);
    }
    
    private String generateUniqueScopeName(String baseName) {
        return baseName + "_" + scopeCounter++;
    }


    public void entrarNuevoAlcance(String baseName) {
        String uniqueScopeName = generateUniqueScopeName(baseName);
        TablaSimbolos nuevaTabla = new TablaSimbolos();
        symbolTables.put(uniqueScopeName, nuevaTabla);
        scopeNamesStack.push(uniqueScopeName);
        nombreAlcanceActual = uniqueScopeName; // Asegúrate de actualizar el alcance actual después de crear uno nuevo
    }
    
    public void salirAlcanceActual() {
        if (!scopeNamesStack.isEmpty() && !getCurrentScope().equals("global")) {
            String scopeName = scopeNamesStack.pop();
            symbolTables.remove(scopeName);
        } else {
            throw new RuntimeException("No se puede salir del alcance global."); // Cambiado de IllegalArgumentException a RuntimeException para mantener la consistencia
        }
        if (!scopeNamesStack.isEmpty()) {
            nombreAlcanceActual = getCurrentScope(); // Actualiza el nombre del alcance actual al último en la pila
        } else {
            nombreAlcanceActual = "global"; // Si la pila está vacía, el alcance actual debe ser el global
        }
    }

    
    

    private String getCurrentScope() {
        if (!scopeNamesStack.isEmpty()) {
            return scopeNamesStack.peek(); // Devuelve el nombre del alcance actual que está en la cima de la pila
        }
        return "global"; // Devuelve "global" si la pila está vacía
    }
    
    private void agregarIdentificador(String identificador, Object valor) {
        TablaSimbolos currentTable = symbolTables.get(scopeNamesStack.peek());
        currentTable.declare(identificador, valor);
    }

    private boolean existeIdentificador(String identificador) {
        // Se busca desde el alcance actual hacia el alcance global
        for (int i = scopeNamesStack.size() - 1; i >= 0; i--) {
            String scopeName = scopeNamesStack.get(i);
            TablaSimbolos scopeTable = symbolTables.get(scopeName);
            if (scopeTable != null && scopeTable.isDeclared(identificador)) {
                return true;
            }
        }
        return false; // Si no se encuentra en ningún alcance
    }

    /*private Object obtener(String identificador) {
        for (int i = scopeNamesStack.size() - 1; i >= 0; i--) {
            String scopeName = scopeNamesStack.get(i);
            TablaSimbolos scopeTable = symbolTables.get(scopeName);
            if (scopeTable != null && scopeTable.isDeclared(identificador)) {
                return scopeTable.get(identificador);
            }
        }
        throw new RuntimeException("Variable no definida: " + identificador);
    }*/

    public boolean progra() {
        try {
            declaration();
            if (preanalisis.tipo == TipoToken.EOF) {
                System.out.println("ASDR correcto");
                return true;
            } else {
                System.out.println("Se encontraron errores ASDR");
            }
        } catch (ParserException e) {
            System.out.println("Se encontraron errores ASDR: " + e.getMessage());
            return false;
        }
        return false;
    }

    /*private Statement declaration() throws ParserException {
        Statement result = null;
        while (true) {
            if (preanalisis.getTipo() == TipoToken.FUN) {
                result = fun_decl();
            } else if (preanalisis.getTipo() == TipoToken.VAR) {
                result = var_decl();
            } else if (checkStatementStart(preanalisis.getTipo())) {
                result = statement();
                // Elimina la llamada recursiva innecesaria aquí
            } else {
                break; // Rompe el bucle si no es un inicio de declaración
            }
        }
        return result;
    }*/
    private List<Statement> declaration() throws ParserException {
        List<Statement> declarations = new ArrayList<>();
        while (true) {
            if (preanalisis.getTipo() == TipoToken.FUN) {
                declarations.add(fun_decl());
            } else if (preanalisis.getTipo() == TipoToken.VAR) {
                declarations.add(var_decl());
            } else if (checkStatementStart(preanalisis.getTipo())) {
                declarations.add(statement());
            } else {
                break; // Sale del bucle si no es un inicio de declaración
            }
        }
        return declarations;
    }
    
  
    private boolean checkStatementStart(TipoToken tipo) {
        // Retorna true si el tipo del token es el inicio de una sentencia
        return tipo == TipoToken.IF || tipo == TipoToken.FOR || tipo == TipoToken.PRINT
               || tipo == TipoToken.RETURN || tipo == TipoToken.WHILE || tipo == TipoToken.LEFT_BRACE;
    }

    private Statement fun_decl() throws ParserException {
    match(TipoToken.FUN);
    return function(); 
}

    private Statement var_decl() throws ParserException {
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFIER);
        Token variableName = previous();
        Expression initializer = var_init();
    
        // Agrega el identificador a la tabla de símbolos global
        agregarIdentificador(variableName.getLexema(), null);//agregarIdentificador(variableName.getLexema(), null); // Puedes asignar un valor inicial a null
    
        match(TipoToken.SEMICOLON);
        return new StmtVar(variableName, initializer);
    }

    private Expression var_init() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            return expression();
        }
        return null; // E
    }

    //////////////////////////////////// SENTENCIAS

    /*private Statement statement() throws ParserException {
        switch (preanalisis.getTipo()) {
            case IF:
                return if_stmt(); 
            case FOR:
                return for_stmt(); 
            case PRINT:
                return print_stmt(); 
            case RETURN:
                return return_stmt(); 
            case WHILE:
                return while_stmt(); 
            case LEFT_BRACE:
                return block(); 
            case IDENTIFIER:
            case NUMBER:
            case STRING:
            case TRUE:
            case FALSE:
            case NULL:
            case BANG:
            case MINUS:    
                return expr_stmt();
            default:
                throw new ParserException("Declaracion no valida. ");
        }
    }*/
    private Statement statement() throws ParserException {
        // Asegúrate de que preanalisis no es nulo
        if (preanalisis == null) {
            throw new ParserException("Token inesperado: fin de entrada.");
        }
    
        switch (preanalisis.getTipo()) {
            case IF:
                return if_stmt();
            case FOR:
                return for_stmt();
            case PRINT:
                return print_stmt();
            case RETURN:
                return return_stmt();
            case WHILE:
                return while_stmt();
            case LEFT_BRACE:
                return block();
            default:
                // Para todos los demás casos que comienzan una expresión
                return expr_stmt();
        }
    }
    

    private Statement expr_stmt() throws ParserException {
        Expression expr = expression();
        match(TipoToken.SEMICOLON); 
        return new StmtExpression(expr);
    }

  
    private Statement for_stmt() throws ParserException {
    match(TipoToken.FOR);
    match(TipoToken.LEFT_PAREN);
    // Crear un nuevo alcance para el bucle for
    entrarNuevoAlcance("for-loop");

    Statement initialization = for_stmt_1();
    Expression condition = for_stmt_2();
    Expression increment = for_stmt_3();
    match(TipoToken.RIGHT_PAREN);
    // Cuerpo del bucle
    Statement body = statement();
    // Salir del alcance del bucle for
    //salirAlcanceActual(getCurrentScope());
    salirAlcanceActual();
    if (initialization != null) {
        if (increment != null) {
            body = new StmtBlock(Arrays.asList(body, new StmtExpression(increment)));
        }
        return new StmtBlock(Arrays.asList(initialization, new StmtLoop(condition, body)));
    } else {
        if (increment != null) {
            body = new StmtBlock(Arrays.asList(body, new StmtExpression(increment)));
        }
        return new StmtLoop(condition, body);
    }
}
    
    private Statement for_stmt_1() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.VAR) {
            return var_decl(); 
        } else if (preanalisis.getTipo() != TipoToken.SEMICOLON) {
            return expr_stmt();
        } else {
            match(TipoToken.SEMICOLON);
            return null; 
        }
    }
    
    private Expression for_stmt_2() throws ParserException {
        if (preanalisis.getTipo() != TipoToken.SEMICOLON) {
            Expression condition = expression();
            match(TipoToken.SEMICOLON);
            return condition;
        } else {
            match(TipoToken.SEMICOLON);
            return null; 
        }
    }
    
    private Expression for_stmt_3() throws ParserException {
        if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
            return expression(); 
        } else {
            return null; // Sin incremento
        }
    }

    private Statement if_stmt() throws ParserException {
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        Expression condition = expression();
        match(TipoToken.RIGHT_PAREN);
    
        // Crear un nuevo alcance para el bloque if
        entrarNuevoAlcance("if-block");
        Statement thenBranch = statement();
        salirAlcanceActual();  // Salir del alcance del bloque if
    
        Statement elseBranch = else_statement();
        return new StmtIf(condition, thenBranch, elseBranch);
    }
    

    private Statement else_statement() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.ELSE) {
            match(TipoToken.ELSE);
            // Crear un nuevo alcance para el bloque else
            entrarNuevoAlcance("else-block");
            Statement elseBranch = statement();
            salirAlcanceActual();  // Salir del alcance del bloque else
            return elseBranch;
        }
        return null; // E
    }

    private Statement print_stmt() throws ParserException {
        match(TipoToken.PRINT);
        Expression value = expression();
        match(TipoToken.SEMICOLON);
        return new StmtPrint(value);
    }

    private Statement return_stmt() throws ParserException {
        match(TipoToken.RETURN);
        Expression value = return_exp_opc(); 
        match(TipoToken.SEMICOLON);
        return new StmtReturn(value);
    }

    private Expression return_exp_opc() throws ParserException {
        if (preanalisis.getTipo() != TipoToken.SEMICOLON) {
            return expression();
        }
        return null; // E
    }

    private Statement while_stmt() throws ParserException {
        match(TipoToken.WHILE);
        match(TipoToken.LEFT_PAREN);
        Expression condition = expression();
        match(TipoToken.RIGHT_PAREN);
        // Crear un nuevo alcance para el bloque while
        entrarNuevoAlcance("while-block");
        Statement body = statement();
        // Salir del alcance del bloque while
        //salirAlcanceActual(getCurrentScope());
        salirAlcanceActual();

        return new StmtLoop(condition, body);
    }

    /*private StmtBlock block() throws ParserException {
        match(TipoToken.LEFT_BRACE);

        // Crear un nuevo alcance para el bloque
        entrarNuevoAlcance("block");
        List<Statement> statements = new ArrayList<>();
        while (!check(TipoToken.RIGHT_BRACE) && !check(TipoToken.EOF)) {
            statements.add(declaration());
        }
        match(TipoToken.RIGHT_BRACE);
        salirAlcanceActual();
        return new StmtBlock(statements);
    }

    private StmtBlock block() throws ParserException {
        match(TipoToken.LEFT_BRACE);
    
        // Crear un nuevo alcance para el bloque
        entrarNuevoAlcance("block");
        
        List<Statement> statements = new ArrayList<>();
        while (true) {
            if (check(TipoToken.RIGHT_BRACE)) {
                break; // Sale del bucle si encuentra el token RIGHT_BRACE
            }
            
            // Asume que declaration() devuelve List<Statement>
            List<Statement> newStatements = declaration(); 
            if (newStatements != null) {
                statements.addAll(newStatements); // Añade todos los Statement a la lista
            }
        }
    
        match(TipoToken.RIGHT_BRACE);
        salirAlcanceActual(); // Salir del alcance del bloque
        
        return new StmtBlock(statements);
    }
    
    

    private boolean check(TipoToken tipo) {
        if (preanalisis.getTipo() == tipo) {
            return true;
        }
        return false;
    }*/
    private StmtBlock block() throws ParserException {
        match(TipoToken.LEFT_BRACE);
    
        // Crear un nuevo alcance para el bloque
        entrarNuevoAlcance("block");
    
        List<Statement> statements = new ArrayList<>();
        while (!check(TipoToken.RIGHT_BRACE)) {
            // Asume que declaration() devuelve una List<Statement>
            List<Statement> newStatements = declaration();
            if (newStatements != null) {
                statements.addAll(newStatements); // Añade todos los Statement a la lista
            }
        }
    
        match(TipoToken.RIGHT_BRACE);
        salirAlcanceActual(); // Salir del alcance del bloque
    
        return new StmtBlock(statements);
    }
    
    private boolean check(TipoToken tipo) {
        if (preanalisis.getTipo() == tipo) {
            return true;
        }
        return false;
    }
    
    

    //////////////////////////////Expresiones

    private Expression expression() throws ParserException{
        return assignment();
    }

    private Expression assignment() throws ParserException{
        Expression expr = logic_or();
        return assignment_opc(expr);
    }

    private Expression assignment_opc(Expression expr) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            Token variableToken = previous();
            
            // Verifica si el identificador existe en la tabla de símbolos
            if (!existeIdentificador(variableToken.getLexema())) {
                throw new ParserException("Asignación a identificador no declarado: " + variableToken.getLexema());
            }
            
            Expression value = assignment();
    
            // Asigna el valor a la variable en la tabla de símbolos
            agregarIdentificador(variableToken.getLexema(), null);//agregarIdentificador(variableToken.getLexema(), value);
    
            expr = new ExprAssign(variableToken, value);
        }
        return expr;
    }

     private Expression logic_or() throws ParserException {
        Expression expr = logic_and();
        return logic_or_2(expr);
    }
    
    /*private Expression logic_or_2(Expression expr) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.OR) {
            match(TipoToken.OR);
            Expression right = logic_and();
            expr = new ExprBinary(expr, new Token(TipoToken.OR, "or", null), right);
            return logic_or_2(expr);
        }
        return expr;
    }*/

    private Expression logic_or_2(Expression expr) throws ParserException {
        while (preanalisis.getTipo() == TipoToken.OR) {
            match(TipoToken.OR);
            Expression right = logic_and();
            expr = new ExprLogical(expr, new Token(TipoToken.OR, "or",null), right);
            expr = logic_or_2(expr);
        }
        return expr;
    }

    private Expression logic_and() throws ParserException {
        Expression expr = equality();
        return logic_and_2(expr);
    }
    
    /*private Expression logic_and_2(Expression expr) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.AND) {
            match(TipoToken.AND);
            Expression right = equality();
            expr = new ExprBinary(expr, new Token(TipoToken.AND, "and", null), right);
            return logic_and_2(expr);
        }
        return expr;
    }*/
    private Expression logic_and_2(Expression expr) throws ParserException {
        while (preanalisis.getTipo() == TipoToken.AND) {
            match(TipoToken.AND);
            Expression right = equality();
            expr = new ExprLogical(expr, new Token(TipoToken.AND, "and", null), right);
            expr = logic_and_2(expr);
        }
        return expr;
    }

    private Expression equality() throws ParserException {
        Expression expr = comparison();
        return equality_2(expr);
    }

    private Expression equality_2(Expression expr) throws ParserException {
        while (preanalisis.getTipo() == TipoToken.BANG_EQUAL || preanalisis.getTipo() == TipoToken.EQUAL_EQUAL) {
            Token operator = preanalisis;
            match(preanalisis.getTipo());
            Expression right = comparison();
            expr = new ExprBinary(expr, operator, right);
            expr = equality_2(expr);
        }
        return expr;
    }

    private Expression comparison() throws ParserException {
        Expression expr = term();
        return comparison_2(expr);
    }

    private Expression comparison_2(Expression expr) throws ParserException {
        while (preanalisis.getTipo() == TipoToken.GREATER || preanalisis.getTipo() == TipoToken.GREATER_EQUAL
               || preanalisis.getTipo() == TipoToken.LESS || preanalisis.getTipo() == TipoToken.LESS_EQUAL) {
            Token operator = preanalisis;
            match(preanalisis.getTipo());
            Expression right = term();
            expr = new ExprBinary(expr, operator, right);
        }
        return expr;
    }

    private Expression term() throws ParserException {
        Expression expr = factor();
        return term_2(expr);
    }

    private Expression term_2(Expression expr) throws ParserException {
        while (preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.PLUS) {
            Token operator = preanalisis;
            match(preanalisis.getTipo());
            Expression right = factor();
            expr = new ExprBinary(expr, operator, right);
        }
        return expr;
    }

    private Expression factor() throws ParserException{
        Expression expr = unary();
        expr = factor2(expr);
        return expr;
    }

    private Expression factor2(Expression expr) throws ParserException{
        switch (preanalisis.getTipo()){
            case SLASH:
                match(TipoToken.SLASH);
                Token operador = previous();
                Expression expr2 = unary();
                ExprBinary expb = new ExprBinary(expr, operador, expr2);
                return factor2(expb);
            case STAR:
                match(TipoToken.STAR);
                operador = previous();
                expr2 = unary();
                expb = new ExprBinary(expr, operador, expr2);
            default:
                
        }
        return expr;
    }

    private Expression unary() throws ParserException{
        switch (preanalisis.getTipo()){
            case BANG:
                match(TipoToken.BANG);
                Token operador = previous();
                Expression expr = unary();
                return new ExprUnary(operador, expr);
            case MINUS:
                match(TipoToken.MINUS);
                operador = previous();
                expr = unary();
                return new ExprUnary(operador, expr);
            default:
                return call();
        }
    }

    private Expression call() throws ParserException{
        Expression expr = primary();
        expr = call2(expr);
        return expr;
    }

    private Expression call2(Expression expr) throws ParserException {
        switch (preanalisis.getTipo()){
            case LEFT_PAREN:
                match(TipoToken.LEFT_PAREN);
                List<Expression> lstArguments = arguments_opc();
                match(TipoToken.RIGHT_PAREN);
                ExprCallFunction ecf = new ExprCallFunction(expr, lstArguments);
                return call2(ecf);
            default:
            // algo no esperado
        }
        return expr;
    }

    private Expression primary() throws ParserException{
        switch (preanalisis.getTipo()){
            case TRUE:
                match(TipoToken.TRUE);
                return new ExprLiteral(true);
            case FALSE:
                match(TipoToken.FALSE);
                return new ExprLiteral(false);
            case NULL:
                match(TipoToken.NULL);
                return new ExprLiteral(null);
            case NUMBER:
                match(TipoToken.NUMBER);
                Token numero = previous();
                return new ExprLiteral(numero.getLiteral());
            case STRING:
                match(TipoToken.STRING);
                Token cadena = previous();
                return new ExprLiteral(cadena.getLiteral());
            case IDENTIFIER:
                match(TipoToken.IDENTIFIER);
                Token id = previous();
                if (!existeIdentificador(id.getLexema())) {
                    throw new ParserException("Identificador no declarado: " + id.getLexema());
                }
                return new ExprVariable(id);
            case LEFT_PAREN:
                match(TipoToken.LEFT_PAREN);
                Expression expr = expression();
                match(TipoToken.RIGHT_PAREN);
                return new ExprGrouping(expr);
            default:
            //Algo diferente de lo anterior
        }
        return null;
    }

///////////////////////////Otras 
    
/*private Statement function() throws ParserException {
    match(TipoToken.IDENTIFIER);
    Token functionName = previous();
    String functionScope = generateUniqueScopeName(functionName.getLexema());

    // Siempre crea un nuevo alcance para la función
    entrarNuevoAlcance(functionScope);

    // Registra la función en la tabla de símbolos del alcance actual
    agregarIdentificador(functionName.getLexema(), null);

    match(TipoToken.LEFT_PAREN);
    List<Token> parameters = parameters_opc();
    match(TipoToken.RIGHT_PAREN);

    // Maneja los parámetros de la función en el alcance actual
    for (Token parameter : parameters) {
        String paramName = parameter.getLexema();
        agregarIdentificador(paramName, null);
    }

    StmtBlock body = block();

    // Siempre sale del alcance de la función después de procesar su cuerpo
    salirAlcanceActual();

    return new StmtFunction(functionName, parameters, body);
}

private List<Token> parameters_opc() throws ParserException {
    if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
        return parameters();
    }
    return Collections.emptyList(); // E
}

private List<Token> parameters() throws ParserException {
    List<Token> params = new ArrayList<>();
    if (preanalisis.getTipo() == TipoToken.IDENTIFIER) {
        do {
            match(TipoToken.IDENTIFIER);
            Token paramToken = previous();

            // Registra el parámetro en la tabla de símbolos de la función actual
            agregarIdentificador(paramToken.getLexema(), null);

            params.add(paramToken);
            params = parameters_2(params);
        } while (preanalisis.getTipo() == TipoToken.COMMA);
    }
    return params;
}

private List<Token> parameters_2(List<Token> existingParams) throws ParserException {
    while (preanalisis.getTipo() == TipoToken.COMMA) {
        match(TipoToken.COMMA);
        match(TipoToken.IDENTIFIER);
        existingParams.add(previous());
    }
    return existingParams;
}

private List<Expression> arguments_opc() throws ParserException {
    if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
        return arguments();
    }
    return Collections.emptyList(); // E
}

private List<Expression> arguments() throws ParserException {
    List<Expression> args = new ArrayList<>();
    if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
        do {
            args.add(expression());
            if (preanalisis.getTipo() == TipoToken.COMMA) {
                match(TipoToken.COMMA);
            }
        } while (preanalisis.getTipo() != TipoToken.RIGHT_PAREN);
    }
    return args;
}*/

private Statement function() throws ParserException {
    match(TipoToken.IDENTIFIER);
    Token functionName = previous();
    String functionScope = generateUniqueScopeName(functionName.getLexema());

    // Siempre crea un nuevo alcance para la función
    entrarNuevoAlcance(functionScope);

    // Registra la función en la tabla de símbolos del alcance actual
    agregarIdentificador(functionName.getLexema(), null);

    match(TipoToken.LEFT_PAREN);
    List<Token> parameters = parameters_opc();
    match(TipoToken.RIGHT_PAREN);

    // Maneja los parámetros de la función en el alcance actual
    for (Token parameter : parameters) {
        String paramName = parameter.getLexema();
        agregarIdentificador(paramName, null);
    }

    StmtBlock body = block();

    // Siempre sale del alcance de la función después de procesar su cuerpo
    salirAlcanceActual();

    return new StmtFunction(functionName, parameters, body);
}

private List<Token> parameters_opc() throws ParserException {
    if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
        return parameters();
    }
    return Collections.emptyList(); // E
}

private List<Token> parameters() throws ParserException {
    List<Token> params = new ArrayList<>();
    while (preanalisis.getTipo() == TipoToken.IDENTIFIER) {
        params.add(previous());
        match(TipoToken.IDENTIFIER);
        if (preanalisis.getTipo() == TipoToken.COMMA) {
            match(TipoToken.COMMA);
        } else {
            break;
        }
    }
    return params;
}

private List<Expression> arguments_opc() throws ParserException {
    if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
        return arguments();
    }
    return Collections.emptyList(); // E
}

private List<Expression> arguments() throws ParserException {
    List<Expression> args = new ArrayList<>();
    while (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
        args.add(expression());
        if (preanalisis.getTipo() == TipoToken.COMMA) {
            match(TipoToken.COMMA);
        } else {
            break;
        }
    }
    return args;
}


    private void match(TipoToken tt) throws ParserException {
        if (preanalisis.getTipo() == tt) {
            i++;
            if (i < tokens.size()) {
                preanalisis = tokens.get(i);
            }
        } else {
            String message = "Error en la línea " +
                    preanalisis.getPosicion() +
                    ". Se esperaba " + tt +
                    " pero se encontró " + preanalisis.getTipo();
            throw new ParserException(message);
        }
    }

    private Token previous() {
        return this.tokens.get(i - 1);
    }
}

// Anterior codigo que solo corresponde a la gramtica. 
    /*@Override
    public boolean progra() {
        Declaration();
        if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
            System.out.println("ASDR correcto");
            return  true;
        }else {
            System.out.println("Se encontraron errores ASDR");
        }
        return false;
    }

    // Declaraciones
    private void Declaration(){
       if(hayErrores) return;
       if(preanalisis.tipo == TipoToken.FUN){
            Fun_Decl();
            Declaration();
       }else if(preanalisis.tipo == TipoToken.VAR){
            Var_Decl();
            Declaration();
       }else if(preanalisis.tipo == TipoToken.FOR || preanalisis.tipo == TipoToken.IF || preanalisis.tipo == TipoToken.PRINT || preanalisis.tipo == TipoToken.RETURN || preanalisis.tipo == TipoToken.WHILE || preanalisis.tipo == TipoToken.LEFT_BRACE){
            Statement();
            Declaration();
       }else{
        // Vacio 
       }

    }

    private void Fun_Decl(){
        if(hayErrores)
        return;
        if(preanalisis.tipo==TipoToken.FUN){
            match(TipoToken.FUN);
            Function();
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }

    }

    private void Var_Decl(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.VAR ){
            match(TipoToken.VAR);
            if(preanalisis.tipo == TipoToken.IDENTIFIER){
                match(TipoToken.IDENTIFIER);
                Var_Init();
                if(preanalisis.tipo == TipoToken.SEMICOLON){
                    match(TipoToken.SEMICOLON);
                }
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Var_Init(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            Expression();
        }else{
            // vacio
        }
    }
// Sentencias

    private void Statement(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.FOR){
            For_Stmt();
        }else if(preanalisis.tipo == TipoToken.IF){
            If_Stmt();
        }else if(preanalisis.tipo == TipoToken.PRINT){
            Print_Stmt();
        }else if(preanalisis.tipo == TipoToken.RETURN){
            Return_Stmt();
        }else if(preanalisis.tipo == TipoToken.WHILE){
            While_Stmt();
        }else if(preanalisis.tipo == TipoToken.LEFT_BRACE){
            Block();
        }else if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER){
            Expr_Stmt();
        }
    }

    private void Expr_Stmt(){
        if(hayErrores)
        return;
        Expression();
        if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void For_Stmt(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.FOR){
            match(TipoToken.FOR);
            if(preanalisis.tipo ==TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                For_Stmt_1();
                For_Stmt_2();
                For_Stmt_3();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Statement();
                }
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
        
    }

    private void For_Stmt_1(){
        if(hayErrores)
        return;
       if(preanalisis.tipo == TipoToken.VAR){
        Var_Decl();
       }else if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER){
            Expr_Stmt();
        }else if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
}

    private void For_Stmt_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER){
            Expr_Stmt();
            if(preanalisis.tipo == TipoToken.SEMICOLON){
                match(TipoToken.SEMICOLON);
            }
        }else if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void For_Stmt_3(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER){
            Expr_Stmt();
        }else{
           // vacio
        }
    }

    private void If_Stmt(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.IF){
            match(TipoToken.IF);
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                Expression();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Statement();
                    Else_Statement();
                }
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Else_Statement(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.ELSE){
            match(TipoToken.ELSE);
            Statement();
        }else{
            // vacio
        }
    }

    private void Print_Stmt(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.PRINT){
            match(TipoToken.PRINT);
            Expression();
            if(preanalisis.tipo == TipoToken.SEMICOLON){
                match(TipoToken.SEMICOLON);
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Return_Stmt(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.RETURN){
            match(TipoToken.RETURN);
            Return_Exp_Opc();
            if(preanalisis.tipo == TipoToken.SEMICOLON){
                match(TipoToken.SEMICOLON);
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Return_Exp_Opc(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER){
            Expression();
        }
        else{
           // vacio
        }
    }

    private void While_Stmt(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.WHILE){
            match(TipoToken.WHILE);
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                Expression();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Statement();
                }
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Block(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.LEFT_BRACE){
            match(TipoToken.LEFT_BRACE);
            Declaration();
            if(preanalisis.tipo == TipoToken.RIGHT_BRACE){
                match(TipoToken.RIGHT_BRACE);
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Expression(){
        if(hayErrores) 
        return;
        Assignment();
    }

    private void Assignment(){
        if(hayErrores) 
        return;
        Logic_Or();
        Assignment_Opc();
    }

    private void Logic_Or(){
        if(hayErrores) 
        return;
        Logic_And();
        Logic_Or_2();
    }

    private void Logic_And(){
        if(hayErrores) 
        return;
        Equality();
        Logic_And_2();
    }

    private void Logic_And_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.AND){
            match(TipoToken.AND);
            Equality();
            Logic_And_2();
        }else{
            // vacio
        }
    }

    private void Equality(){
        if(hayErrores)
        return;
        Comparison();
        Equality_2();
    }

    private void Equality_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.BANG_EQUAL){
            match(TipoToken.BANG_EQUAL);
            Comparison();
            Equality_2();
        }else if(preanalisis.tipo == TipoToken.EQUAL_EQUAL){
            match(TipoToken.EQUAL_EQUAL);
            Comparison();
            Equality_2();
        }else{
            // vacio
        }
    }
    
    private void Comparison(){
        if(hayErrores)
        return;
        Term();
        Comparison_2();
    }

    private void Comparison_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.GREATER){
            match(TipoToken.GREATER);
            Term();
            Comparison_2();
        }else if(preanalisis.tipo == TipoToken.GREATER_EQUAL){
            match(TipoToken.GREATER_EQUAL);
            Term();
            Comparison_2();
        }else if(preanalisis.tipo == TipoToken.LESS){
            match(TipoToken.LESS);
            Term();
            Comparison_2();
        }else if(preanalisis.tipo == TipoToken.LESS_EQUAL){
            match(TipoToken.LESS_EQUAL);
            Term();
            Comparison_2();
        }else{
            //vacio
        }
    }

    private void Term(){
        if(hayErrores)
        return;
        Factor();
        Term_2();
    }

    private void Term_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            Factor();
            Term_2();
        }else if(preanalisis.tipo == TipoToken.PLUS){
            match(TipoToken.PLUS);
            Factor();
            Term_2();
        }else{
            // vacio
        }
    }

    private void Factor(){
        if(hayErrores)
        return;
        Unary();
        Factor_2();
    }

    private void Factor_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.SLASH){
            match(TipoToken.SLASH);
            Unary();
            Factor_2();
        }else if(preanalisis.tipo == TipoToken.STAR){
            match(TipoToken.STAR);
            Unary();
            Factor_2();
        }else{
            // vacio
        }
    }

    private void Unary(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.BANG){
            match(TipoToken.BANG);
            Unary();
        }else  if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            Unary();
        }else{
            Call();
        }
    }

    private void Call(){
        if(hayErrores)
        return;
        Primary();
        Call_2();
    }

    private void Call_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            Arguments_Opc();
            if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                match(TipoToken.RIGHT_PAREN);
                Call_2();
            }
        }
        // duda
        else if(preanalisis.tipo == TipoToken.DOT){
            match(TipoToken.DOT);
            if(preanalisis.tipo == TipoToken.IDENTIFIER){
                match(TipoToken.IDENTIFIER);
                Call_2();
            }
        }
        else{
            // vacio
        }
    }

    private void Primary(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.TRUE){
            match(TipoToken.TRUE);
        }else if(preanalisis.tipo == TipoToken.FALSE){
            match(TipoToken.FALSE);
        }else if(preanalisis.tipo == TipoToken.NULL){
            match(TipoToken.NULL);
        }else if(preanalisis.tipo == TipoToken.NUMBER){
            match(TipoToken.NUMBER);
        }else if(preanalisis.tipo == TipoToken.STRING){
            match(TipoToken.STRING);
        }else if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
        }else if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            Expression();
            if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                match(TipoToken.RIGHT_PAREN);
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }
    
    private void Logic_Or_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.OR){
            match(TipoToken.OR);
            Logic_And();
            Logic_Or_2();
        }else{
            // vacio
        }
    }

    private void Function(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                Parameters_Opc();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Block();
                }
            }
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }

    private void Parameters_Opc(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            Parameters();
        }else{
            // vacio
        }
    }

    private void Parameters(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            Parameters_2();
        }else{
            hayErrores = true;
            System.out.println("Error de Análisis");
        }
    }
    
    private void Parameters_2(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            if(preanalisis.tipo == TipoToken.IDENTIFIER){
                match(TipoToken.IDENTIFIER);
                Parameters_2();
            }
        }else{
            // vacio
        }
    }
    
    private void Assignment_Opc(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            Expression();
        }else{
            // vacio
        }
    }

    private void Arguments_Opc(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER){
            Expression(); // duda
            Arguments();
        }else{
            // vacio
        }
    }

    private void Arguments(){
        if(hayErrores)
        return;
        if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            Expression();
            Arguments();
        }else{
            // vacio
        }
    }

    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error encontrado");
        }

    }
} */
