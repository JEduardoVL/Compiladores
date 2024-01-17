/*class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }
    public Object solve() {
        return TablaSimbolos.getInstance().get(this.name.getLexema());
    }
}*/

class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    @Override
    public Object solve() {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        if (!tablaSimbolos.isDeclared(name.getLexema())) {
            throw new RuntimeException("La variable " + name.getLexema() + " no ha sido declarada");
        }
        return tablaSimbolos.get(name.getLexema());
    }

    @Override
    public String toString() {
        return name.getLexema();
    }
}
