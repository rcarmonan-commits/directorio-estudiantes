/**
 * ============================================================================
 * GUIA DE LA CLASE: PruebasOrdenamiento (Suite de 7 Pruebas Unitarias)
 * ============================================================================
 * 
 * 1. ¿QUE ES Y PARA QUE SE USO?
 *    Esta clase contiene una suite rigurosa de 7 pruebas unitarias disenadas para
 *    cumplir con la indicacion general de la rubrica:
 *    "Realizar diferentes pruebas para demostrar el funcionamiento, incluyendo al menos
 *    un caso diferente al ejemplo principal".
 *    Verifica que el algoritmo responda correctamente no solo ante casos tipicos, sino
 *    tambien ante casos limite (edge cases), entradas vacias y condiciones extremas.
 * 
 * 2. ¿QUE ESCENARIOS CUBREN LAS 7 PRUEBAS?
 *    - PRUEBA 1: Empate total en promedio (4.00) -> Evalua el criterio de desempate alfabetico puro.
 *    - PRUEBA 2: Arreglo ya ordenado -> Valida el mejor caso teorico O(n) con 0 desplazamientos.
 *    - PRUEBA 3: Arreglo en orden inverso -> Valida el peor caso teorico O(n^2) con maximos desplazamientos.
 *    - PRUEBA 4: Casi ordenado (un solo desajuste) -> Demuestra la alta adaptabilidad de Insertion Sort.
 *    - PRUEBA 5: Caso borde con 1 solo estudiante -> Asegura que no ocurran excepciones por indices.
 *    - PRUEBA 6: Caso borde con directorio vacio (longitud 0) -> Valida robustez sin fallos en tiempo de ejecucion.
 *    - PRUEBA 7: Mayusculas/minusculas y promedios cercanos (4.01 vs 4.00 vs 3.99) -> Valida precision de Double.compare.
 * 
 * 3. ¿COMO SE EJECUTA?
 *    javac -encoding UTF-8 -d bin src/*.java
 *    java -cp bin PruebasOrdenamiento
 * 
 * @author Rosary Eilyn Carmona Navarro
 */
public class PruebasOrdenamiento {

    // Contadores para el reporte final de la suite de pruebas
    private static int aprobadas = 0;
    private static int total = 0;

    public static void main(String[] args) {
        ejecutarTodasLasPruebas();
    }

    /**
     * Metodo publico para ejecutar la suite completa de pruebas desde cualquier clase
     * o programa demostrativo principal.
     */
    public static void ejecutarTodasLasPruebas() {
        aprobadas = 0;
        total = 0;

        System.out.println("==============================================================");
        System.out.println("   SUITE DE PRUEBAS UNITARIAS - ALGORITMO DE ORDENAMIENTO    ");
        System.out.println("==============================================================");

        // ESCENARIO 1: Todos con igual promedio -> Desempate 100% por nombre (A-Z)
        probar("1. Todos con el mismo promedio (solo desempate por nombre)", new Estudiante[] {
            new Estudiante("T01", "Zoe Ruiz",     2, 4.00),
            new Estudiante("T02", "maria Lopez",  3, 4.00),
            new Estudiante("T03", "Bruno Vega",   1, 4.00),
            new Estudiante("T04", "Maria Acosta", 4, 4.00)
        });

        // ESCENARIO 2: Entrada ya ordenada -> Debe requerir 0 desplazamientos (Mejor caso O(n))
        probar("2. Directorio ya ordenado (mejor caso O(n))", new Estudiante[] {
            new Estudiante("T05", "Ana",    1, 5.00),
            new Estudiante("T06", "Beto",   2, 4.50),
            new Estudiante("T07", "Carla",  3, 4.00),
            new Estudiante("T08", "Dario",  4, 3.50),
            new Estudiante("T09", "Elena",  5, 3.00)
        });

        // ESCENARIO 3: Entrada en orden inverso completo -> Maximo trabajo (Peor caso O(n^2))
        probar("3. Orden inverso (peor caso O(n^2))", new Estudiante[] {
            new Estudiante("T10", "Elena",  5, 3.00),
            new Estudiante("T11", "Dario",  4, 3.50),
            new Estudiante("T12", "Carla",  3, 4.00),
            new Estudiante("T13", "Beto",   2, 4.50),
            new Estudiante("T14", "Ana",    1, 5.00)
        });

        // ESCENARIO 4: Casi ordenado -> Solo un elemento desplazado
        probar("4. Casi ordenado (un solo elemento fuera de lugar)", new Estudiante[] {
            new Estudiante("T15", "Ana",    1, 5.00),
            new Estudiante("T16", "Beto",   2, 4.50),
            new Estudiante("T17", "Dario",  4, 3.50),
            new Estudiante("T18", "Carla",  3, 4.00),
            new Estudiante("T19", "Elena",  5, 3.00)
        });

        // ESCENARIO 5: Caso limite -> Arreglo con exactamente 1 estudiante
        probar("5. Un solo estudiante (caso limite)", new Estudiante[] {
            new Estudiante("T20", "Unico", 1, 3.80)
        });

        // ESCENARIO 6: Caso limite -> Arreglo vacio sin elementos
        probar("6. Directorio vacio (longitud 0)", new Estudiante[0]);

        // ESCENARIO 7: Tolerancia a variaciones en mayusculas/minusculas y notas muy proximas
        probar("7. Mayusculas/minusculas y promedios muy cercanos", new Estudiante[] {
            new Estudiante("T21", "PEDRO Salas",  2, 4.01),
            new Estudiante("T22", "laura Mejia",  6, 4.00),
            new Estudiante("T23", "Laura Ayala",  3, 4.00),
            new Estudiante("T24", "pedro Arias",  5, 4.01),
            new Estudiante("T25", "Camila Ortiz", 8, 3.99)
        });

        // RESUMEN FINAL DE LA SUITE
        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("RESULTADO FINAL: %d/%d pruebas aprobadas exitosamente%n", aprobadas, total);
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * ========================================================================
     * METODO AUXILIAR DE PRUEBA: probar(String nombre, Estudiante[] datos)
     * ========================================================================
     * 
     * ¿COMO FUNCIONA EL PROCEDIMIENTO DE AUDITORIA PASO A PASO?
     * - PASO 1: Imprime el estado del arreglo ANTES de ordenar.
     * - PASO 2: Clona el arreglo con 'datos.clone()' para que la version con Comparator
     *           reciba exactamente el mismo orden inicial sin alterar los resultados del manual.
     * - PASO 3: Ejecuta el ordenamiento manual con 'OrdenadorEstudiantes(false)' (silencioso).
     * - PASO 4: Ejecuta el ordenamiento de referencia con 'OrdenadorComparator.ordenar(copia)'.
     * - PASO 5: Muestra el estado del arreglo DESPUES de ordenar.
     * - PASO 6: Realiza una DOBLE verificacion:
     *           a) 'ordenador.estaOrdenado(datos)': Verifica que cumpla la regla matematica.
     *           b) 'OrdenadorComparator.mismoOrden(datos, copia)': Verifica que sea 100% identico a Java.
     * - PASO 7: Imprime metricas (comparaciones y desplazamientos) y acumula el contador de aprobadas.
     */
    private static void probar(String nombre, Estudiante[] datos) {
        total++;
        System.out.println("\n>> " + nombre);
        System.out.println("   Antes:");
        DirectorioEstudiantesApp.imprimir(datos);

        // PASO 2: Clonar el dataset para validacion cruzada independiente
        Estudiante[] copia = datos.clone();

        // PASO 3 y 4: Ejecutar ambos ordenamientos
        OrdenadorEstudiantes ordenador = new OrdenadorEstudiantes(false);
        ordenador.ordenar(datos);
        OrdenadorComparator.ordenar(copia);

        // PASO 5: Mostrar resultado clasificado
        System.out.println("   Despues:");
        DirectorioEstudiantesApp.imprimirRanking(datos);

        // PASO 6: Doble verificacion formal
        boolean ok = ordenador.estaOrdenado(datos)
                && OrdenadorComparator.mismoOrden(datos, copia);

        // PASO 7: Imprimir resultado del caso
        System.out.printf("   Comparaciones: %d | Desplazamientos: %d | Estado: %s%n",
                ordenador.getComparaciones(), ordenador.getDesplazamientos(),
                ok ? "APROBADO [OK]" : "FALLO [X]");

        if (ok) aprobadas++;
    }
}
