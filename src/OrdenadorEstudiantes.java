/**
 * ============================================================================
 * GUIA DE LA CLASE: OrdenadorEstudiantes (Algoritmo Manual)
 * ============================================================================
 * 
 * 1. ¿QUE ES Y PARA QUE SE USO?
 *    Esta clase contiene la implementacion MANUAL pura del algoritmo de ordenamiento
 *    requerido por el profesor, cumpliendo con la prohibicion explicita de la rubrica:
 *    "No limitar la solucion al uso de Arrays.sort() o metodos automaticos".
 * 
 * 2. ¿POR QUE SE ELIGIO EL ALGORITMO INSERTION SORT (INSERCION)?
 *    Se selecciono Insertion Sort debido a cuatro ventajas fundamentales:
 *    a) ESTABILIDAD: Si dos estudiantes tienen igual promedio e igual nombre, conservan
 *       su orden relativo inicial sin alternancias innecesarias.
 *    b) IN-PLACE (Memoria O(1)): Opera directamente sobre el arreglo original 'Estudiante[]',
 *       moviendo unicamente referencias sin duplicar estructuras en memoria RAM.
 *    c) MEJOR CASO O(n): Si el directorio ya esta previamente ordenado o casi ordenado,
 *       el algoritmo solo realiza (n - 1) comparaciones y 0 desplazamientos.
 *    d) ANALOGIA DIDACTICA: Funciona exactamente igual a como una persona ordena cartas
 *       en su mano, lo cual facilita explicarlo y sustentarlo claramente en el video.
 * 
 * 3. ¿COMO FUNCIONA EL CRITERIO COMPUESTO (PROMEDIO DESC Y NOMBRE ASC)?
 *    - Criterio Primario: Promedio Descendente (Mayor a menor). Los estudiantes con
 *      mejores calificaciones ocupan los primeros lugares del ranking.
 *    - Criterio Secundario (Desempate): Si dos estudiantes tienen exactamente el mismo
 *      promedio, se compara su nombre alfabeticamente en orden ascendente (A-Z)
 *      sin distinguir entre mayusculas y minusculas (case-insensitive).
 * 
 * 4. ¿QUE METRICAS SE REGISTRAN?
 *    Se contabiliza en tiempo real el total de comparaciones realizadas, el numero de
 *    desplazamientos fisicos de elementos y el tiempo transcurrido en milisegundos/nanosegundos.
 * 
 * @author Rosary Eilyn Carmona Navarro
 */
public class OrdenadorEstudiantes {

    // --- Contadores Operativos y Metricas ---
    private long comparaciones;
    private long desplazamientos;
    private long tiempoNanosegundos;

    // Bandera para activar o desactivar la traza visual paso a paso en consola
    private final boolean mostrarPasos;

    /**
     * Constructor del ordenador manual.
     *
     * @param mostrarPasos true para imprimir en consola cada insercion paso a paso;
     *                     false para ejecucion silenciosa (ideal en pruebas unitarias).
     */
    public OrdenadorEstudiantes(boolean mostrarPasos) {
        this.mostrarPasos = mostrarPasos;
    }

    // --- Metodos de Acceso a Metricas ---
    public long getComparaciones()        { return comparaciones; }
    public long getDesplazamientos()      { return desplazamientos; }
    public long getTiempoNanosegundos()   { return tiempoNanosegundos; }
    public double getTiempoMilisegundos() { return tiempoNanosegundos / 1_000_000.0; }

    /**
     * ========================================================================
     * METODO: comparar(Estudiante a, Estudiante b)
     * ========================================================================
     * 
     * ¿QUE HACE?
     * Evalua dos estudiantes 'a' y 'b' y determina cual debe ir primero en el ranking.
     * 
     * ¿COMO FUNCIONA PASO A PASO?
     * - PASO 1: Incrementa el contador global de comparaciones ('comparaciones++').
     * - PASO 2: Evalua el promedio de forma DESCENDENTE.
     *           ¿Por que se pasa (b, a) en lugar de (a, b)?
     *           El metodo 'Double.compare(x, y)' retorna un numero negativo si x < y.
     *           Al evaluar 'Double.compare(b.getPromedio(), a.getPromedio())', si el
     *           estudiante 'a' tiene mayor promedio que 'b' (es decir b < a), el metodo
     *           retorna un numero negativo, senalando que 'a' tiene mayor prioridad
     *           y debe posicionarse ANTES que 'b' en el ranking.
     * - PASO 3: Si los promedios son distintos (retorno != 0), se retorna este valor de inmediato.
     * - PASO 4: Si ocurre un EMPATE exacto (retorno == 0), entra en accion el criterio de
     *           desempate: compara los nombres alfabeticamente con 'compareToIgnoreCase'.
     *           Las letras iniciales del alfabeto (A, B, C...) retornan un valor negativo,
     *           garantizando que en caso de empate, el nombre menor alfabeticamente vaya primero.
     * 
     * @return Valor negativo si 'a' precede a 'b' (mayor prioridad);
     *         Valor positivo si 'b' precede a 'a' (menor prioridad);
     *         0 si ambos estudiantes son equivalentes segun el ranking.
     */
    public int comparar(Estudiante a, Estudiante b) {
        // PASO 1: Registrar operacion elemental de comparacion
        comparaciones++;

        // PASO 2: Comparar promedios en orden descendente (mayor calificacion primero)
        int porPromedio = Double.compare(b.getPromedio(), a.getPromedio());
        if (porPromedio != 0) {
            return porPromedio;
        }

        // PASO 3: Desempate por nombre en orden ascendente (A-Z) ignorando mayusculas
        return a.getNombre().compareToIgnoreCase(b.getNombre());
    }

    /**
     * ========================================================================
     * METODO: ordenar(Estudiante[] estudiantes)
     * ========================================================================
     * 
     * ¿QUE HACE?
     * Ordena in-place el arreglo de estudiantes utilizando el algoritmo Insertion Sort.
     * 
     * ¿COMO FUNCIONA PASO A PASO?
     * - PASO 1: Reinicia las metricas a 0 e inicia el cronometro de alta resolucion
     *           con 'System.nanoTime()'.
     * - PASO 2: Valida casos triviales (si el arreglo es nulo o tiene menos de 2 elementos,
     *           no requiere trabajo y termina de inmediato).
     * - PASO 3: Se asume que el primer elemento (posicion 0) ya conforma un subarreglo
     *           ordenado. Por lo tanto, el ciclo externo comienza desde 'i = 1'.
     * - PASO 4: Se toma el elemento en la posicion 'i' como la 'clave' a insertar (como una
     *           nueva carta tomada del mazo).
     * - PASO 5: Se posiciona un puntero 'j = i - 1' en el elemento inmediatamente a la izquierda.
     * - PASO 6: Ciclo interno de desplazamiento: mientras 'j >= 0' y el elemento 'estudiantes[j]'
     *           deba ubicarse DESPUES de la 'clave' (segun el metodo 'comparar'):
     *           - Se corre el elemento a la derecha: 'estudiantes[j + 1] = estudiantes[j]'.
     *           - Se incrementa el contador de desplazamientos ('desplazamientos++').
     *           - Se retrocede el puntero ('j--').
     * - PASO 7: Se inserta la 'clave' en la posicion correcta liberada: 'estudiantes[j + 1] = clave'.
     * - PASO 8: Si 'mostrarPasos' es true, imprime en pantalla que estudiante se coloco y en que puesto.
     * - PASO 9: Captura el tiempo final y calcula la duracion en nanosegundos.
     * 
     * @param estudiantes Arreglo de objetos Estudiante a ordenar in-place.
     */
    public void ordenar(Estudiante[] estudiantes) {
        // PASO 1: Reinicializar metricas operativas
        comparaciones = 0;
        desplazamientos = 0;
        tiempoNanosegundos = 0;

        // PASO 2: Validacion de caso base / arreglo vacio o unitario
        if (estudiantes == null || estudiantes.length < 2) {
            return;
        }

        long inicio = System.nanoTime();

        // PASO 3: Recorrer desde el segundo elemento (indice 1) hasta el ultimo
        for (int i = 1; i < estudiantes.length; i++) {
            // PASO 4: Extraer la "clave" que se va a insertar en la parte ordenada
            Estudiante clave = estudiantes[i];
            int j = i - 1;

            // PASO 6: Desplazar a la derecha mientras los elementos previos tengan menor prioridad
            while (j >= 0 && comparar(estudiantes[j], clave) > 0) {
                estudiantes[j + 1] = estudiantes[j];
                desplazamientos++;
                j--;
            }

            // PASO 7: Colocar la clave en el espacio libre encontrado
            estudiantes[j + 1] = clave;

            // PASO 8: Impresion opcional de la traza de ejecucion paso a paso
            if (mostrarPasos) {
                System.out.printf("  Paso %d: se inserta '%s' en la posicion %d%n",
                        i, clave.getNombre(), j + 1);
            }
        }

        // PASO 9: Registrar tiempo total transcurrido
        long fin = System.nanoTime();
        tiempoNanosegundos = fin - inicio;
    }

    /**
     * ========================================================================
     * METODO: estaOrdenado(Estudiante[] estudiantes)
     * ========================================================================
     * 
     * ¿QUE HACE?
     * Auditoria formal independiente que recorre el arreglo comprobando que cada
     * elemento satisfaga la condicion de ranking respecto a su predecesor.
     * 
     * ¿COMO FUNCIONA?
     * - Si encuentra algun estudiante con MAYOR promedio que el anterior, retorna false.
     * - Si tienen igual promedio pero el anterior es alfabeticamente MAYOR, retorna false.
     * - Si todos los pares cumplen, retorna true.
     */
    public boolean estaOrdenado(Estudiante[] estudiantes) {
        if (estudiantes == null) return true;
        for (int i = 1; i < estudiantes.length; i++) {
            int r = Double.compare(estudiantes[i].getPromedio(), estudiantes[i - 1].getPromedio());
            if (r > 0) return false;
            if (r == 0 && estudiantes[i - 1].getNombre()
                    .compareToIgnoreCase(estudiantes[i].getNombre()) > 0) return false;
        }
        return true;
    }
}
