class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    @Override
    public Object solve(TablaSimbolos tabla) {
        // Devuelve directamente el valor del literal.
        // Dado que es un literal, no necesita realizar ninguna operación adicional.
        // La tabla de símbolos se incluye por consistencia con la estructura de la clase padre,
        // pero no se usa en este caso.
        return this.value;
    }

    @Override
    public String toString() {
        // Devuelve la representación en cadena del valor.
        // Utiliza el método toString() del objeto para obtener su representación en cadena.
        return value.toString();
    }
}
