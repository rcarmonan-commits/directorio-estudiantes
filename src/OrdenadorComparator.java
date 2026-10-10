import java.util.Arrays;
import java.util.Comparator;

/**
 * ============================================================================
 * GUIA DE LA CLASE: OrdenadorComparator (Solucion Declarativa Nativa)
 * ============================================================================
 * 
 * 1. ¿QUE ES Y PARA QUE SE USO?
 *    Esta clase implementa el ordenamiento utilizando las herramientas estandar y
 *    nativas de Java (la interfaz funcional 'Comparator' y el metodo 'Arrays.sort').
 *    Se desarrollo especificamente para cumplir el segundo requerimiento del Ejercicio 6:
 *    "Posteriormente realizar el mismo ordenamiento utilizando Comparator", permitiendo
 *    contrastar los resultados del algoritmo manual contra la solucion del lenguaje.
 * 
 * 2. ¿COMO FUNCIONA EL COMPARADOR MULTINIVEL (RANKING)?
 *    Se encadenan dos reglas mediante metodos fluidos de Java 8+:
 *    - PASO 1: 'Comparator.comparingDouble(Estudiante::getPromedio)'
 *      Extrae la calificacion de cada estudiante. En su forma natural ordena de menor a mayor.
 *    - PASO 2: '.reversed()'
 *      Invierte el orden del paso 1, transformandolo en descendente (el mayor promedio va primero).
 *    - PASO 3: '.thenComparing(Estudiante::getNombre, String.CASE_INSENSITIVE_ORDER)'
 *      Se activa UNICAMENTE si dos promedios son exactamente iguales. En caso de empate,
 *      desempata alfabeticamente en orden ascendente (A-Z) sin distinguir mayusculas/minusculas.
 * 
 * 3. ¿QUE ALGORITMO EJECUTA 'Arrays.sort' POR DEBAJO?
 *    Cuando 'Arrays.sort' se aplica sobre un arreglo de objetos ('Estudiante[]'), Java
 *    ejecuta internamente el algoritmo 'TimSort' (hibrido entre Merge Sort e Insertion Sort),
 *    el cual garantiza estabilidad y una complejidad temporal de O(n log n).
 * 
 * 4. ¿COMO FUNCIONA 'mismoOrden'?
 *    Audita posicion por posicion ('a[i].getCodigo().equals(b[i].getCodigo())') para verificar
 *    matematicamente que ambas soluciones (la manual y la de Comparator) producen el
 *    mismo ranking exacto al 100%.
 * 
 * @author Rosary Eilyn Carmona Navarro
 */
public class OrdenadorComparator {

    /**
     * Comparador oficial del ranking universitario con regla compuesta:
     * 1. Promedio descendente.
     * 2. Desempate: nombre ascendente case-insensitive.
     */
    public static final Comparator<Estudiante> RANKING =
            Comparator.comparingDouble(Estudiante::getPromedio).reversed()
                      .thenComparing(Estudiante::getNombre, String.CASE_INSENSITIVE_ORDER);

    /**
     * Ordena el arreglo utilizando la implementacion optimizada nativa de Java.
     *
     * @param estudiantes Arreglo de objetos Estudiante a ordenar.
     */
    public static void ordenar(Estudiante[] estudiantes) {
        if (estudiantes == null) return;
        Arrays.sort(estudiantes, RANKING);
    }

    /**
     * Compara dos arreglos de estudiantes para verificar identidad estructural de ranking.
     *
     * @param a Arreglo resultado del ordenamiento manual.
     * @param b Arreglo resultado del ordenamiento con Comparator.
     * @return true si ambos arreglos contienen exactamente los mismos estudiantes
     *         en los mismos puestos del ranking; false en caso de cualquier discrepancia.
     */
    public static boolean mismoOrden(Estudiante[] a, Estudiante[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!a[i].getCodigo().equals(b[i].getCodigo())) return false;
        }
        return true;
    }
}
