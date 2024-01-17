/*public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }
}*/

public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public void execute() {
        // Evalúa la expresión y lanza una excepción personalizada con el valor de retorno
        Object returnValue = value.solve();
        throw new Return(returnValue);
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
