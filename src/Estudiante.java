import java.util.Objects;

/**
 * ============================================================================
 * GUIA DE LA CLASE: Estudiante
 * ============================================================================
 * 
 * 1. ¿QUE ES Y PARA QUE SE USO?
 *    Esta clase representa la entidad del modelo de datos para cada alumno dentro
 *    del directorio academico universitario. Modela de forma encapsulada los cuatro
 *    atributos requeridos por el Ejercicio 6 de la rubrica:
 *    - codigo   : Identificador unico institucional (String).
 *    - nombre   : Nombre completo del estudiante (String).
 *    - semestre : Nivel academico actual que cursa (int >= 1).
 *    - promedio : Promedio acumulado de calificaciones (double entre 0.0 y 5.0).
 * 
 * 2. ¿POR QUE SE IMPLEMENTO CON ATRIBUTOS 'final' (INMUTABILIDAD)?
 *    Al declarar los campos como 'final', garantizamos que una vez creado el objeto,
 *    sus datos no puedan ser alterados accidentalmente. Esto es fundamental para los
 *    algoritmos de ordenamiento, ya que durante los desplazamientos o intercambios
 *    se mueven las REFERENCIAS de los objetos completos dentro del arreglo, evitando
 *    que los datos de un estudiante se mezclen con los de otro.
 * 
 * 3. ¿COMO FUNCIONA LA VALIDACION DEFENSIVA?
 *    En el constructor se valida cada parametro antes de asignarlo. Si se recibe un
 *    codigo vacio, un nombre nulo o un promedio fuera de la escala (0.0 a 5.0), se
 *    lanza una excepcion 'IllegalArgumentException', impidiendo la creacion de estados
 *    academicos inconsistentes.
 * 
 * @author Rosary Eilyn Carmona Navarro
 */
public class Estudiante {

    // --- Atributos Inmutables del Estudiante ---
    private final String codigo;
    private final String nombre;
    private final int semestre;
    private final double promedio;

    /**
     * Constructor principal con validacion defensiva de parametros.
     *
     * @param codigo   Identificador unico institucional no vacio.
     * @param nombre   Nombre completo del estudiante no vacio.
     * @param semestre Semestre cursado (debe ser mayor o igual a 1).
     * @param promedio Calificacion ponderada acumulada (entre 0.0 y 5.0).
     */
    public Estudiante(String codigo, String nombre, int semestre, double promedio) {
        // PASO 1: Validar que el codigo no sea nulo ni consista solo en espacios
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo institucional no puede ser nulo ni vacio.");
        }
        // PASO 2: Validar que el nombre del estudiante sea valido
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del estudiante no puede ser nulo ni vacio.");
        }
        // PASO 3: Validar que el semestre academico sea un numero positivo
        if (semestre < 1) {
            throw new IllegalArgumentException("El semestre debe ser mayor o igual a 1.");
        }
        // PASO 4: Validar el rango numerico estandar de calificaciones universitarias (0.0 a 5.0)
        if (promedio < 0.0 || promedio > 5.0) {
            throw new IllegalArgumentException("El promedio academico debe estar en el rango de 0.0 a 5.0.");
        }

        // PASO 5: Asignar valores limpios de espacios residuales
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.semestre = semestre;
        this.promedio = promedio;
    }

    // --- Metodos de Acceso (Getters) ---

    public String getCodigo()   { return codigo; }
    public String getNombre()   { return nombre; }
    public int getSemestre()    { return semestre; }
    public double getPromedio() { return promedio; }

    /**
     * Representacion textual formateada para impresion tabular en consola.
     * 
     * ¿COMO FUNCIONA?
     * Utiliza especificadores de formato con anchos fijos:
     * - '%-8s'  : Alinea el codigo a la izquierda con 8 caracteres de espacio.
     * - '%-22s' : Alinea el nombre a la izquierda con 22 caracteres de espacio.
     * - '%2d'   : Imprime el semestre ocupando 2 digitos.
     * - '%.2f'  : Redondea y muestra el promedio con exactamente 2 decimales.
     */
    @Override
    public String toString() {
        return String.format("%-8s %-22s Sem: %2d  Prom: %.2f",
                codigo, nombre, semestre, promedio);
    }

    /**
     * Igualdad semantica basada en el identificador unico del estudiante.
     * 
     * ¿POR QUE SE USO?
     * Permite comprobar formalmente si dos referencias representan al mismo
     * alumno en las pruebas unitarias y en la validacion cruzada.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudiante)) return false;
        Estudiante that = (Estudiante) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
