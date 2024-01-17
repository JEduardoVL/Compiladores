public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    void solve(TablaSimbolos tabla){
        TablaSimbolos tablaReturn;
        if(tabla.existeIdentificador("return")){
            tablaReturn = tabla.obtenerTabla("return", tabla);
            tablaReturn.asignar("return", value.solve(tablaReturn));
        }
        else
            throw new RuntimeException("Error semantico: uso incorrecto de return");
    }

    @Override
    public String toString() {
        return "return " + value +";\n";
    }
}


/*
 * 
 * class Return extends RuntimeException {
    final Object value;

    Return(Object value) {
        super(null, null, false, false);
        this.value = value;
    }
}
 */
