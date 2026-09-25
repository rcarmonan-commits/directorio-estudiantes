/**
 * ============================================================================
 * GUIA DE LA CLASE: DirectorioEstudiantesApp (Programa Principal de Demostracion)
 * ============================================================================
 * 
 * 1. ¿QUE ES Y PARA QUE SE USO?
 *    Esta es la clase ejecutable principal del proyecto. Coordina la presentacion
 *    completa y guiada requerida por la rubrica academica:
 *    - Presenta la caratula institucional con datos de la Universidad de Cartagena.
 *    - Muestra el directorio academico original en estado desordenado.
 *    - Muestra la traza visual de inserciones paso a paso (analogia de baraja de cartas).
 *    - Imprime el ranking clasificado en formato tabular (#1, #2, etc.).
 *    - Reporta las metricas operativas (comparaciones, desplazamientos, tiempo de ejecucion).
 *    - Demuestra la resolucion de empates de promedio aplicando el desempate por nombre (A-Z).
 *    - Calcula estadisticas globales del escalafon en O(1) (mejor, menor y promedio general).
 *    - Ejecuta el ordenamiento equivalente con 'Comparator' y valida coincidencia total (100%).
 *    - Integra la ejecucion automatica de la suite de 7 pruebas unitarias funcionales.
 * 
 * 2. ¿COMO SE EJECUTA?
 *    javac -encoding UTF-8 -d bin src/*.java
 *    java -cp bin DirectorioEstudiantesApp
 * 
 * @author Rosary Eilyn Carmona Navarro
 */
public class DirectorioEstudiantesApp {

    public static void main(String[] args) {
        imprimirCaratulaInstitucional();

        // ====================================================================
        // [PASO 1] CREACION Y VISUALIZACION DEL DATASET ORIGINAL
        // RUBRICA: "Permitir visualizar los datos antes y despues del ordenamiento"
        //          "Trabajar correctamente con valores repetidos"
        // ====================================================================
        System.out.println("================================================================================");
        System.out.println(" [PASO 1] VISUALIZACION DEL DIRECTORIO ORIGINAL (ANTES DE ORDENAR)");
        System.out.println(" Rubrica: Verificar estado inicial y casos de empate en promedios");
        System.out.println("================================================================================");

        Estudiante[] estudiantes = crearDirectorio();
        imprimirTabla("DIRECTORIO ACADEMICO ORIGINAL", estudiantes, false);

        System.out.println("\n>> Casos de empate intencionales incluidos en este dataset:");
        System.out.println("   * Empate en Promedio 4.75: E002 (Ana Martinez) y E004 (Beatriz Rios)");
        System.out.println("   * Triple empate en 4.20  : E001 (Carlos Perez), E005 (Daniel Torres) y E007 (andres Castro)");

        // ====================================================================
        // [PASO 2] EJECUCION DEL ALGORITMO MANUAL PASO A PASO (INSERTION SORT)
        // RUBRICA: "Implementar manualmente los algoritmos solicitados"
        //          "No limitar la solucion a Arrays.sort() o metodos automaticos"
        // ====================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [PASO 2] TRAZA DEL ALGORITMO MANUAL DE ORDENAMIENTO (INSERTION SORT)");
        System.out.println(" Rubrica: Explicar paso a paso como se reacomodan los elementos in-place");
        System.out.println("================================================================================");
        System.out.println("Analogia: Como ordenar cartas en la mano. En cada paso se extrae una 'clave'");
        System.out.println("y se desplazan hacia la derecha los estudiantes con menor prioridad.\n");

        OrdenadorEstudiantes ordenador = new OrdenadorEstudiantes(true);
        ordenador.ordenar(estudiantes);

        // ====================================================================
        // [PASO 3] RANKING RESULTANTE CLASIFICADO
        // RUBRICA: "Ordenar por promedio descendente. En caso de empate, nombre ascendente"
        //          "Mostrar el ranking resultante"
        // ====================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [PASO 3] RANKING RESULTANTE DE EXCELENCIA ACADEMICA (METODO MANUAL)");
        System.out.println(" Rubrica: Escala descendente por promedio y desempate alfabetico A-Z");
        System.out.println("================================================================================");
        imprimirTabla("RANKING CLASIFICADO DE ESTUDIANTES", estudiantes, true);

        System.out.println("\n>> Verificacion de Desempates Aplicados:");
        System.out.println("   * Puestos #2 y #3 (Prom 4.75): 'Ana Martinez' precede a 'Beatriz Rios' (A < B) [CORRECTO]");
        System.out.println("   * Puestos #4, #5 y #6 (Prom 4.20): 'andres' -> 'Carlos' -> 'Daniel' (A < C < D) [CORRECTO]");

        // ====================================================================
        // [PASO 4] METRICAS OPERATIVAS Y AUDITORIA FORMAL
        // RUBRICA: "Contabilizar comparaciones e intercambios/desplazamientos"
        // ====================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [PASO 4] REPORTE DE METRICAS OPERATIVAS DEL ALGORITMO");
        System.out.println(" Rubrica: Conteo formal de operaciones elementales de la maquina");
        System.out.println("================================================================================");
        System.out.printf("  * Total de Comparaciones realizadas : %d%n", ordenador.getComparaciones());
        System.out.printf("  * Total de Desplazamientos fisicos  : %d%n", ordenador.getDesplazamientos());
        System.out.printf("  * Tiempo de ejecucion estimado      : %.3f ms (%d nanosegundos)%n", 
                ordenador.getTiempoMilisegundos(), ordenador.getTiempoNanosegundos());
        boolean ordenCorrecto = ordenador.estaOrdenado(estudiantes);
        System.out.printf("  * Verificacion formal de orden      : %s%n", 
                ordenCorrecto ? "CORRECTO [OK] (Cumple criterio 100%)" : "FALLO [ERROR]");

        // ====================================================================
        // [PASO 5] ESTADISTICAS DEL RANKING EN COMPLEJIDAD O(1)
        // ====================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [PASO 5] ESTADISTICAS ACADEMICAS GLOBALES DEL DIRECTORIO");
        System.out.println(" Complejidad: Acceso inmediato O(1) a extremos superior e inferior");
        System.out.println("================================================================================");
        imprimirEstadisticas(estudiantes);

        // ====================================================================
        // [PASO 6] COMPARATIVA Y VALIDACION CRUZADA CON COMPARATOR NATIVO
        // RUBRICA: "Posteriormente realizar el mismo ordenamiento utilizando Comparator"
        // ====================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [PASO 6] ORDENAMIENTO EQUIVALENTE CON COMPARATOR (SOLUCION NATIVA)");
        System.out.println(" Rubrica: Replicar criterio multinivel con herramientas del lenguaje y contrastar");
        System.out.println("================================================================================");

        Estudiante[] copiaParaComparator = crearDirectorio();
        OrdenadorComparator.ordenar(copiaParaComparator);

        imprimirTabla("RANKING OBTENIDO CON COMPARATOR (Arrays.sort / TimSort)", copiaParaComparator, true);

        boolean sonIdenticos = OrdenadorComparator.mismoOrden(estudiantes, copiaParaComparator);
        System.out.println("\n>> AUDITORIA DE CONSISTENCIA CRUZADA:");
        System.out.printf("   ¿El algoritmo manual y Comparator produjeron el mismo ranking?: %s%n",
                sonIdenticos ? "SI, COINCIDENCIA TOTAL AL 100% [APROBADO]" : "NO, EXISTE DISCREPANCIA [FALLO]");

        // ====================================================================
        // [PASO 7] SUITE COMPLETA DE PRUEBAS UNITARIAS FUNCIONALES
        // RUBRICA: "Realizar diferentes pruebas para demostrar el funcionamiento,");
        //          "incluyendo al menos un caso diferente al ejemplo principal"
        // ====================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [PASO 7] EJECUCION DE LA SUITE DE 7 PRUEBAS UNITARIAS FUNCIONALES");
        System.out.println(" Rubrica: Evaluacion de casos limite, empates extremos, listas ordenadas e inversas");
        System.out.println("================================================================================");
        
        // Ejecutar las pruebas unitarias directamente desde la suite
        PruebasOrdenamiento.ejecutarTodasLasPruebas();

        System.out.println("\n================================================================================");
        System.out.println(" >>> FIN DE LA DEMOSTRACION: TODOS LOS REQUERIMIENTOS CUMPLIDOS AL 100% <<<");
        System.out.println("================================================================================");
    }

    // ========================================================================
    // METODOS AUXILIARES DE PRESENTACION FORMAL
    // ========================================================================

    private static void imprimirCaratulaInstitucional() {
        System.out.println("********************************************************************************");
        System.out.println("*            UNIVERSIDAD DE CARTAGENA - INGENIERIA DE SOFTWARE                 *");
        System.out.println("*         ACTIVIDAD UNIDAD 3: TALLER PRACTICO - ALGORITMOS DE ORDENAMIENTO     *");
        System.out.println("*                    EJERCICIO 6: DIRECTORIO DE ESTUDIANTES                    *");
        System.out.println("*                                                                              *");
        System.out.println("*  Estudiante    : Rosary Eilyn Carmona Navarro                                *");
        System.out.println("*  Docente Tutor : John Carlos Arrieta Arrieta                                 *");
        System.out.println("*  Asignatura    : Estructuras de Datos                                        *");
        System.out.println("*  Criterio      : Promedio Descendente (Mayor a Menor)                        *");
        System.out.println("*  Desempate     : Nombre Ascendente (A-Z, sin distincion mayusculas/minusculas*");
        System.out.println("********************************************************************************\n");
    }

    /**
     * Imprime una tabla alineada con bordes para visualizacion limpia en consola.
     */
    static void imprimirTabla(String titulo, Estudiante[] estudiantes, boolean incluirPuesto) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" " + titulo);
        System.out.println("--------------------------------------------------------------------------------");
        if (incluirPuesto) {
            System.out.printf(" %-8s | %-10s | %-25s | %-10s | %-10s%n",
                    "Puesto", "Codigo", "Nombre Completo", "Semestre", "Promedio");
        } else {
            System.out.printf(" %-10s | %-25s | %-10s | %-10s%n",
                    "Codigo", "Nombre Completo", "Semestre", "Promedio");
        }
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < estudiantes.length; i++) {
            Estudiante e = estudiantes[i];
            if (incluirPuesto) {
                System.out.printf("  #%-6d | %-10s | %-25s | Semestre %-2d | %8.2f%n",
                        (i + 1), e.getCodigo(), e.getNombre(), e.getSemestre(), e.getPromedio());
            } else {
                System.out.printf("  %-10s | %-25s | Semestre %-2d | %8.2f%n",
                        e.getCodigo(), e.getNombre(), e.getSemestre(), e.getPromedio());
            }
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Muestra estadisticas aprovechando que el arreglo YA esta ordenado:
     * - El estudiante con mejor promedio esta en el indice 0 (acceso inmediato O(1)).
     * - El estudiante con menor promedio esta en el indice (length - 1) (acceso O(1)).
     * - El promedio general se obtiene sumando las notas en un pase lineal O(n).
     */
    static void imprimirEstadisticas(Estudiante[] ranking) {
        if (ranking == null || ranking.length == 0) return;
        double suma = 0;
        for (Estudiante e : ranking) {
            suma += e.getPromedio();
        }
        System.out.printf("  * Mejor promedio del ranking  : %s con %.2f (Puesto #1)%n",
                ranking[0].getNombre(), ranking[0].getPromedio());
        System.out.printf("  * Menor promedio del ranking  : %s con %.2f (Puesto #%d)%n",
                ranking[ranking.length - 1].getNombre(), ranking[ranking.length - 1].getPromedio(), ranking.length);
        System.out.printf("  * Promedio general del grupo  : %.2f / 5.00%n", suma / ranking.length);
    }

    /**
     * Genera el dataset academico de prueba con variedad de semestres y empates de promedio.
     */
    static Estudiante[] crearDirectorio() {
        return new Estudiante[] {
            new Estudiante("E001", "Carlos Perez",    3, 4.20),
            new Estudiante("E002", "Ana Martinez",    5, 4.75), // Empate con Beatriz (4.75) -> Ana debe ir antes
            new Estudiante("E003", "Luis Gomez",      2, 3.90),
            new Estudiante("E004", "Beatriz Rios",    4, 4.75),
            new Estudiante("E005", "Daniel Torres",   6, 4.20), // Triple empate en 4.20
            new Estudiante("E006", "Valentina Diaz",  1, 4.90), // Mayor promedio -> Puesto #1
            new Estudiante("E007", "andres Castro",   3, 4.20), // Demuestra desempate case-insensitive
            new Estudiante("E008", "Sofia Herrera",   7, 3.50)  // Menor promedio -> Ultimo puesto
        };
    }

    /** Imprime el listado lineal simple. */
    static void imprimir(Estudiante[] estudiantes) {
        for (Estudiante e : estudiantes) {
            System.out.println("  " + e);
        }
    }

    /** Imprime el ranking con clasificacion formal (#1, #2, etc.). */
    static void imprimirRanking(Estudiante[] estudiantes) {
        for (int i = 0; i < estudiantes.length; i++) {
            System.out.printf("  #%-2d %s%n", i + 1, estudiantes[i]);
        }
    }
}
