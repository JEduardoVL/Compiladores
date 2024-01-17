public class Token {

    final TipoToken tipo;
    final String lexema;
    final int posicion;
    final Object literal;

    public Token(TipoToken tipo, String lexema, int posicion) { // 
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = posicion;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
       this.posicion = 0;
        this.literal = literal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)o).tipo){
            return true;
        }

        return false;
    }


    public String toString() {
        return "<" + tipo + " " + lexema + " " + literal + ">";
    }



 public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public Object getLiteral() {
        return literal;
    }

    public int getPosicion() {
        return posicion;
    }
}