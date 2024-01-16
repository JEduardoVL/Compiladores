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
        // Manejar la lógica de retorno aquí
    }
}

