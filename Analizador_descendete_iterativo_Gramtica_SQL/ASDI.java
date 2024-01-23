import java.util.*;

public class ASDI implements Parser {

    private final List<Token> tokens;
    private Token preanalisis;
    private int i = 0;

    private static final Map<Pair<String, TipoToken>, Integer> M = new HashMap<>();
    static {
        M.put(new Pair<>("Q", TipoToken.SELECT), 1);
        M.put(new Pair<>("D", TipoToken.DISTINCT), 2);
        M.put(new Pair<>("D", TipoToken.ASTERISCO), 3);
        M.put(new Pair<>("D", TipoToken.IDENTIFICADOR), 4);
        M.put(new Pair<>("P", TipoToken.ASTERISCO), 5);
        M.put(new Pair<>("P", TipoToken.IDENTIFICADOR), 6);
        M.put(new Pair<>("A", TipoToken.IDENTIFICADOR), 7);
        M.put(new Pair<>("A1", TipoToken.COMA), 8);
        M.put(new Pair<>("A1", TipoToken.FROM), 9); 
        M.put(new Pair<>("A2", TipoToken.IDENTIFICADOR), 10);
        M.put(new Pair<>("A3", TipoToken.PUNTO), 11);
        M.put(new Pair<>("A3", TipoToken.FROM), 12); 
        M.put(new Pair<>("T", TipoToken.IDENTIFICADOR), 13);
        M.put(new Pair<>("T1", TipoToken.COMA), 14);
        M.put(new Pair<>("T1", TipoToken.EOF), 15); 
        M.put(new Pair<>("T2", TipoToken.IDENTIFICADOR), 16);
        M.put(new Pair<>("T3", TipoToken.IDENTIFICADOR), 17);
        M.put(new Pair<>("T3", TipoToken.EOF), 18); 
    }

    public ASDI(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    @Override
    public boolean parse() {
        Stack<Object> pila = new Stack<>();
        pila.push(TipoToken.EOF);
        pila.push("Q");
    
        Object X = pila.peek();
    
        while (X != TipoToken.EOF) { // Mientras la pila no esté vacía
            TipoToken a = preanalisis.tipo;
    
            if (X == a) {
                pila.pop();
                avanzar();
            } else if (X instanceof TipoToken) { // X es un terminal
                Principal.error(preanalisis.posicion, "Error de sintaxis.");
                return false;
            } else {
                Pair<String, TipoToken> clave = new Pair<>((String) X, a);
                Integer produccion = M.get(clave);
    
                if (produccion == null) {
                    Principal.error(preanalisis.posicion, "Error de sintaxis.");
                    return false;
                } else {
                    pila.pop();
                    switch (produccion) {
                        case 1:
                            pila.push("T");
                            pila.push(TipoToken.FROM);
                            pila.push("D");
                            pila.push(TipoToken.SELECT);
                            break;
                        case 2:
                            pila.push("P");
                            pila.push(TipoToken.DISTINCT);
                            break;
                        case 3:
                        case 4:
                            pila.push("P");
                            break;
                        case 5:
                            pila.push(TipoToken.ASTERISCO);
                            break;
                        case 6:
                            pila.push("A");
                            break;
                        case 7:
                            pila.push("A1");
                            pila.push("A2");
                            break;
                        case 8:
                            pila.push("A");
                            pila.push(TipoToken.COMA);
                            break;
                        case 9:
                            break; 
                        case 10:
                            pila.push("A3");
                            pila.push(TipoToken.IDENTIFICADOR);
                            break;
                        case 11:
                            pila.push(TipoToken.IDENTIFICADOR);
                            pila.push(TipoToken.PUNTO);
                            break;
                        case 12:
                            break;
                        case 13:
                            pila.push("T1");
                            pila.push("T2");
                            break;
                        case 14:
                            pila.push("T");
                            pila.push(TipoToken.COMA);
                            break;
                        case 15:
                            break; 
                        case 16:
                            pila.push("T3");
                            pila.push(TipoToken.IDENTIFICADOR);
                            break;
                        case 17:
                            pila.push(TipoToken.IDENTIFICADOR);
                            break;
                        case 18:
                            break; 
                    }
                }
            }
            X = pila.peek(); // Actualizar X con el símbolo de la parte superior de la pila
        }
    
        System.out.println("Consulta correcta ASDI");
        return true;
    }

    private void avanzar() {
        i++;
        if (i < tokens.size()) {
            preanalisis = tokens.get(i);
        } else {
            preanalisis = new Token(TipoToken.EOF, "", i);
        }
    }

    static class Pair<K, V> {
        final K key;
        final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
