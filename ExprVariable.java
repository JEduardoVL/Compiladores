class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    @Override
    public Object solve(TablaSimbolos tabla) {
        // Verificar si la variable está declarada en la tabla de símbolos
        if (!tabla.existeIdentificador(name.getLexema())) {
            throw new RuntimeException("La variable " + name.getLexema() + " no ha sido declarada");
        }
        // Obtener el valor de la variable de la tabla de símbolos
        return tabla.obtener(name.getLexema());
    }

    @Override
    public String toString() {
        return name.getLexema();
    }
}
