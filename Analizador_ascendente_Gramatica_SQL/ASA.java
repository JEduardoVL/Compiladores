import java.util.List;
import java.util.Stack;

public class ASA implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private final List<Token> tokens;
    Stack<Integer> pila = new Stack<>();
    Object simboloActual;
    int estadoActual;

    public ASA(List<Token> tokens){
        this.tokens = tokens;
    }

    @Override
    public boolean parse() {
        pila.push(0);
        simboloActual = tokens.get(i).tipo;

        while(true){
            estadoActual = pila.peek();
            if(pila.peek() == 0){
                if(simboloActual == TipoToken.SELECT){
                    shift(2);
                } else
                if(simboloActual == "Q"){
                    shift(1);
                } else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 1){
                if(simboloActual == TipoToken.EOF){
                    break;
                }
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 2){
                if(simboloActual == TipoToken.DISTINCT)
                    shift(4);
                else if(simboloActual == TipoToken.ASTERISCO)
                    shift(6);
                else if(simboloActual == TipoToken.IDENTIFICADOR)
                    shift(9);
                else if(simboloActual == "D")
                    shift(3);
                else if(simboloActual == "P")
                    shift(5);
                else if(simboloActual == "A")
                    shift(7);
                else if(simboloActual == "A2")
                    shift(8);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 3){
                if(simboloActual == TipoToken.FROM)
                    shift(31);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 4){
                if(simboloActual == TipoToken.ASTERISCO)
                    shift(6);
                else if(simboloActual == TipoToken.IDENTIFICADOR)
                    shift(9);
                else if(simboloActual == "P")
                    shift(15);
                else if(simboloActual == "A")
                    shift(7);
                else if(simboloActual == "A2")
                    shift(8);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 5){
                if(simboloActual == TipoToken.FROM)
                    reduccion(1, "D");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 6){
                if(simboloActual == TipoToken.FROM)
                    reduccion(1, "P");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 7){
                if(simboloActual == TipoToken.FROM)
                    reduccion(1, "P");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 8){
                if(simboloActual == TipoToken.FROM)
                    reduccion(0, "A1");
                else if(simboloActual == TipoToken.COMA)
                    shift(10);
                else if(simboloActual == "A1"){
                    shift(16);
                }
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 9){
                if(simboloActual == TipoToken.FROM || simboloActual == TipoToken.COMA)
                    reduccion(0, "A3");
                else if(simboloActual == TipoToken.PUNTO)
                    shift(12);
                else if(simboloActual == "A3")
                    shift(11);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 10){
                if(simboloActual == TipoToken.IDENTIFICADOR)
                    shift(9);
                else if(simboloActual == "A")
                    shift(14);
                else if(simboloActual == "A2")
                    shift(8);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 11){
                if(simboloActual == TipoToken.FROM || simboloActual == TipoToken.COMA)
                    reduccion(2, "A2");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 12){
                if(simboloActual == TipoToken.IDENTIFICADOR)
                    shift(13);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 13){
                if(simboloActual == TipoToken.FROM || simboloActual == TipoToken.COMA)
                    reduccion(2, "A3");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 14){
                if(simboloActual == TipoToken.FROM)
                    reduccion(2,"A1");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 15){
                if(simboloActual == TipoToken.FROM)
                    reduccion(2,"D");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 16){
                if(simboloActual == TipoToken.FROM)
                    reduccion(2,"A");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 31){//31=19
                if(simboloActual == TipoToken.IDENTIFICADOR)
                    shift(313);
                else if(simboloActual == "T")
                    shift(311);
                else if(simboloActual == "T2")
                    shift(312);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 311){
                if(simboloActual == TipoToken.EOF)
                    reduccion(4, "Q");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 312){
                if(simboloActual == "T1")
                    shift(3121);
                else if(simboloActual == TipoToken.COMA)
                    shift(3122);
                else if(simboloActual == TipoToken.EOF)
                    reduccion(0, "T1");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 313){
                if(simboloActual == TipoToken.IDENTIFICADOR)
                    shift(3132);
                else if(simboloActual == TipoToken.COMA || simboloActual == TipoToken.EOF)
                    reduccion(0,"T3");
                else if(simboloActual == "T3"){
                    shift(3131);
                }
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 3121){
                if(simboloActual == TipoToken.EOF){
                    reduccion(2, "T");
                }
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 3122){
                if(simboloActual == TipoToken.IDENTIFICADOR){
                    shift(313);
                }
                else if(simboloActual == "T")
                    shift(31221);
                else if(simboloActual == "T2")
                    shift(312);
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 3131){
                if(simboloActual == TipoToken.COMA || simboloActual == TipoToken.EOF)
                    reduccion(2,"T2");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 3132){
                if(simboloActual == TipoToken.EOF)
                    reduccion(1,"T3");
                else{
                    hayErrores = true;
                    break;
                }
            }
            else if(pila.peek() == 31221){
                if(simboloActual == TipoToken.EOF)
                    reduccion(2, "T1");
                else{
                    hayErrores = true;
                    break;
                }
            }
        }
        if(hayErrores){
            System.out.println("Error Sintactico");
            return false;
        }
        else{
            System.out.println("La sintaxis es correcta");
            return true;
        }
    }
    public void shift(int estadoSiguiente){
        pila.push(estadoSiguiente);
        i++;
        simboloActual = tokens.get(i).tipo;
    }
    public void reduccion(int borrar, String simbolo){
        simboloActual = simbolo;
        i--;
        for(int k = 0; k<borrar; k++)
            pila.pop();
}
}






/* 
    Intento fallido.
public class ASA implements Parser {

    private final List<Token> tokens;
    private Token preanalisis;
    private int i = 0;
    
    // Agrega estructuras para las acciones y transiciones
    private final Map<Pair<Integer, TipoToken>, Accion> accion;
    private final Map<Pair<Integer, String>, Integer> irA;
    
    // Constructor donde inicializamos las tablas accion y irA
    public ASA(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
        
        accion = new HashMap<>();
        irA = new HashMap<>();
        
        
        accion.put(new Pair<>(0, TipoToken.SELECT), new Accion('s', 2));
        //2
        accion.put(new Pair<>(2, TipoToken.DISTINCT), new Accion('s', 4));
        accion.put(new Pair<>(2, TipoToken.ASTERISCO), new Accion('s', 6));
        accion.put(new Pair<>(2, TipoToken.IDENTIFICADOR), new Accion('s', 9));
        //3
        accion.put(new Pair<>(3, TipoToken.FROM), new Accion('s', 10));
        //4
        accion.put(new Pair<>(4, TipoToken.ASTERISCO), new Accion('s', 6));
        accion.put(new Pair<>(4, TipoToken.IDENTIFICADOR), new Accion('s', 9));
        //5
        accion.put(new Pair<>(5, TipoToken.FROM), new Accion('r', 2)); 
        //6
        accion.put(new Pair<>(6, TipoToken.FROM), new Accion('r', 3)); 
        //7
        accion.put(new Pair<>(7, TipoToken.FROM), new Accion('r', 5)); 
        //8
        accion.put(new Pair<>(8, TipoToken.COMA), new Accion('s', 13)); 
        //9
        accion.put(new Pair<>(9, TipoToken.PUNTO), new Accion('s', 15)); 
        //10
        accion.put(new Pair<>(10, TipoToken.IDENTIFICADOR), new Accion('s', 18)); 
        //11
        accion.put(new Pair<>(11, TipoToken.FROM), new Accion('r', 2)); 
        //12
        accion.put(new Pair<>(12, TipoToken.FROM), new Accion('r', 6)); 
        //13
        accion.put(new Pair<>(13, TipoToken.IDENTIFICADOR), new Accion('s', 9));
        //14
        accion.put(new Pair<>(14, TipoToken.COMA), new Accion('r', 7));
        accion.put(new Pair<>(14, TipoToken.FROM), new Accion('r', 7));
        //15
        accion.put(new Pair<>(15, TipoToken.PUNTO), new Accion('s', 15));
        //16
        accion.put(new Pair<>(16, TipoToken.EOF), new Accion('r', 1));
        //17
        accion.put(new Pair<>(17, TipoToken.COMA), new Accion('s', 21));
        //18
        accion.put(new Pair<>(18, TipoToken.IDENTIFICADOR), new Accion('s', 23));
        //19
        accion.put(new Pair<>(19, TipoToken.FROM), new Accion('r', 8));
        //20
        accion.put(new Pair<>(20, TipoToken.EOF), new Accion('r', 9));
        //21
        accion.put(new Pair<>(21, TipoToken.IDENTIFICADOR), new Accion('s', 18));
        //22
        accion.put(new Pair<>(22, TipoToken.COMA), new Accion('r', 10));
        accion.put(new Pair<>(22, TipoToken.EOF), new Accion('r', 10));
        //23
        accion.put(new Pair<>(23, TipoToken.COMA), new Accion('r', 11));
        accion.put(new Pair<>(23, TipoToken.EOF), new Accion('r', 11));
        //24
        accion.put(new Pair<>(24, TipoToken.EOF), new Accion('r', 12));

        accion.put(new Pair<>(1, TipoToken.EOF), new Accion('a', 0)); // Aceptar

        // Tabla de ir_a
        irA.put(new Pair<>(0, "Q"), 1);
        //2
        irA.put(new Pair<>(2, "D"), 3);
        irA.put(new Pair<>(2, "P"), 5);
        irA.put(new Pair<>(2, "A"), 7);
        irA.put(new Pair<>(2, "A2"), 8);
        //4
        irA.put(new Pair<>(4, "P"), 11);
        irA.put(new Pair<>(4, "A"), 7);
        irA.put(new Pair<>(4, "A2"), 8);
        //8
        irA.put(new Pair<>(8, "A1"), 12);
        //9
        irA.put(new Pair<>(9, "A3"), 14);
        //10
        irA.put(new Pair<>(10, "T"), 16);
        irA.put(new Pair<>(10, "T2"), 17);
        //13
        irA.put(new Pair<>(13, "A"), 19);
        irA.put(new Pair<>(13, "A2"), 8);
        //17
        irA.put(new Pair<>(17, "T1"), 20);
        //18
        irA.put(new Pair<>(18, "T3"), 22);
        //21
        irA.put(new Pair<>(21, "T"), 24);
        irA.put(new Pair<>(21, "T2"), 17);
    }
    

    @Override
    public boolean parse() {
        // Inicializa la pila de estados con el estado inicial 0
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        preanalisis = tokens.get(i); // Inicializa el primer símbolo de preanálisis
    
        // Mapa para la longitud de las producciones
        Map<Integer, Integer> longitudProducciones = new HashMap<>();
        longitudProducciones.put(1, 3); // "Q → select D from T"
        longitudProducciones.put(2, 2); // "D → distinct P" | "D → P"
        longitudProducciones.put(3, 1); // "P → *" | "P → A"
        longitudProducciones.put(4, 2); // "A → A2 A1"
        longitudProducciones.put(5, 2); // "A1 → , A" | "A1 → Ɛ"
        longitudProducciones.put(6, 2); // "A2 → id A3"
        longitudProducciones.put(7, 2); // "A3 → . id" | "A3 → Ɛ"
        longitudProducciones.put(8, 2); // "T → T2 T1"
        longitudProducciones.put(9, 2); // "T1 → , T" | "T1 → Ɛ"
        longitudProducciones.put(10, 2); // "T2 → id T3"
        longitudProducciones.put(11, 1); // "T3 → id" | "T3 → Ɛ"

        // Mapa para los no terminales resultantes de las producciones
        Map<Integer, Character> noTerminalesProducciones = new HashMap<>();
        noTerminalesProducciones.put(1, 'Q'); // La producción 1 produce el no terminal Q
        noTerminalesProducciones.put(2, 'D'); // La producción 2 produce el no terminal D
        noTerminalesProducciones.put(3, 'P'); // La producción 3 produce el no terminal P
        noTerminalesProducciones.put(4, 'A'); // La producción 4 produce el no terminal A
        noTerminalesProducciones.put(5, 'A'); // La producción 5 produce el no terminal A
        noTerminalesProducciones.put(6, 'A'); // La producción 6 produce el no terminal A
        noTerminalesProducciones.put(7, 'A'); // La producción 7 produce el no terminal A
        noTerminalesProducciones.put(8, 'T'); // La producción 8 produce el no terminal T
        noTerminalesProducciones.put(9, 'T'); // La producción 9 produce el no terminal T
        noTerminalesProducciones.put(10, 'T'); // La producción 10 produce el no terminal T
        noTerminalesProducciones.put(11, 'T'); // La producción 11 produce el no terminal T
    
        while (true) {
            int estado = stack.peek();
            Accion acc = accion.get(new Pair<>(estado, preanalisis.tipo));
    
            if (acc == null) {
                error(preanalisis.posicion, "Error de sintaxis");
                return false;
            }
    
            switch (acc.tipo) {
                case 's':
                    // Desplazar
                    stack.push(acc.estado);
                    if (i < tokens.size() - 1) {
                        preanalisis = tokens.get(++i);
                    } else {
                        error(preanalisis.posicion, "Fin de entrada inesperado");
                        return false;
                    }
                    break;
                case 'r':
                    // Reducir
                    int longitud = longitudProducciones.get(acc.estado);
                    for (int j = 0; j < longitud; j++) {
                        stack.pop();
                    }
                    int estadoLuegoDePop = stack.peek();
                    Character noTerminal = noTerminalesProducciones.get(acc.estado);
                    Integer siguienteEstado = irA.get(new Pair<>(estadoLuegoDePop, noTerminal));
                    if (siguienteEstado != null) {
                        stack.push(siguienteEstado);
                    } else {
                        error(preanalisis.posicion, "Error de sintaxis después de la reducción");
                        return false;
                    }
                    // No avanzar el preanálisis en una reducción
                    break;
                case 'a':
                    // Aceptar
                    return true;
                default:
                    error(preanalisis.posicion, "Acción desconocida");
                    return false;
            }
        }
    }
    
    private void error(int posicion, String mensaje) {
        System.err.println("Error en posición " + posicion + ": " + mensaje);
    }
}

// Una clase simple para representar una acción con un tipo y un estado
class Accion {
    char tipo; // 's' para desplazar, 'r' para reducir, 'a' para aceptar
    int estado; // El número de estado o producción

    Accion(char tipo, int estado) {
        this.tipo = tipo;
        this.estado = estado;
    }
    
    // Omitidos los getters y setters por brevedad
}

class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // Omitidos los getters y setters por brevedad

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
               Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
*/