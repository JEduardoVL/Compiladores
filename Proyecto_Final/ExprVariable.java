class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    @Override
    Object solve(TablaSimbolos tabla){
        if(!tabla.existeIdentificador(name.lexema))
            throw new RuntimeException("La variable" + name.lexema + "n ha sido declarada");
        else
            return tabla.obtener(name.lexema);
    }

    @Override
    public String toString(){
        return name.lexema;
    }
}
