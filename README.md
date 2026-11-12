# Directorio de Estudiantes

Actividad Unidad 3 - Taller Práctico: Algoritmos de Ordenamiento  
Asignatura: Estructuras de Datos  
Programa: Ingeniería de Software  
IES: Universidad de Cartagena  
Docente Tutor: John Carlos Arrieta Arrieta  
Estudiante: Rosary Eilyn Carmona Navarro  
Ejercicio Asignado: Ejercicio 6 - Directorio de Estudiantes  

---

## 1. Descripción del Ejercicio Asignado: Ejercicio 6 - Directorio de Estudiantes

El sistema implementa la gestión y ordenamiento del directorio académico de una institución mediante un arreglo de objetos `Estudiante` con sus respectivos atributos:

* **código**: Identificador alfanumérico único del estudiante (ej. `E001`, `E002`, `E006`).
* **nombre**: Nombre completo del estudiante.
* **semestre**: Nivel o período académico que cursa actualmente.
* **promedio**: Calificación académica numérica ponderada (escala de 0.0 a 5.0).

### Requerimientos Implementados:
1. **Ordenamiento manual por promedio descendente**: Ubica a los estudiantes con mejores promedios en las primeras posiciones del escalafón o ranking.
2. **Criterio de desempate por nombre ascendente**: En caso de empate exacto en el promedio, se desempata alfabéticamente de la A a la Z (ignorando diferencias de mayúsculas/minúsculas).
3. **Visualización completa del ranking**: Presenta el directorio original y la tabla de posiciones clasificada con numeración (#1, #2, ...).
4. **Implementación equivalente con `Comparator`**: Replicación exacta del criterio multinivel utilizando las herramientas nativas de Java (`Comparator.comparingDouble(...).reversed().thenComparing(...)`).
5. **Métricas de ejecución**: Conteo manual riguroso de comparaciones y desplazamientos efectuados por el algoritmo.

---

## 2. Estructura del Proyecto

El código fuente se organizó modularmente bajo principios de alta cohesión y responsabilidad única:

| Archivo | Responsabilidad Principal |
|---|---|
| `src/Estudiante.java` | Clase del modelo de dominio (atributos inmutables `final`, datos íntegros). |
| `src/OrdenadorEstudiantes.java` | **Insertion Sort manual** estable con criterio multinivel y contadores operativos. |
| `src/OrdenadorComparator.java` | Solución declarativa equivalente con `Comparator` y `Arrays.sort` (TimSort). |
| `src/DirectorioEstudiantesApp.java` | Programa principal: directorio original, proceso paso a paso, ranking y estadísticas. |
| `src/PruebasOrdenamiento.java` | Suite de 7 pruebas unitarias (empates, ordenado, inverso, casi ordenado, unitario, vacío, mayúsculas). |

---

## 3. Algoritmo Implementado: Insertion Sort (Inserción)

El algoritmo de inserción simula la forma natural en que una persona ordena una baraja de cartas en la mano:

1. Comienza en el segundo elemento ($i = 1$) asumiendo que el primer elemento ya forma un subarreglo ordenado.
2. Extrae el elemento actual como la **clave** a insertar.
3. Compara hacia la izquierda ($j = i - 1$) desplazando hacia la derecha a todo estudiante que deba ir ubicado después de la clave.
4. Inserta la clave en el espacio libre ($j + 1$).

### Criterio de Comparación Multinivel (`comparar(a, b)`):
```java
// 1) Promedio descendente: se invierte el orden natural pasando (b, a)
int porPromedio = Double.compare(b.getPromedio(), a.getPromedio());
if (porPromedio != 0) {
    return porPromedio;
}
// 2) Desempate por nombre alfabético ignorando mayúsculas/minúsculas
return a.getNombre().compareToIgnoreCase(b.getNombre());
```

### Justificación de los Atributos Seleccionados:
* **Promedio ponderado:** Es el atributo representativo del mérito académico; un ranking meritocrático ubica primero al de mayor rendimiento.
* **Nombre alfabético:** Es un criterio de desempate determinista, neutro y legible para el usuario humano.
* **Atributos excluidos:** El código y el semestre son identificadores administrativos y temporales, no indicadores de mérito académico.

---

## 4. Análisis de Complejidad Temporal y Espacial

| Métrica / Caso | Insertion Sort Manual | Comparator (`Arrays.sort` / TimSort) |
|---|---|---|
| **Mejor Caso** (Ya ordenado) | $\mathcal{O}(n)$ — $n-1$ comparaciones, 0 desplazamientos | $\mathcal{O}(n)$ |
| **Caso Promedio** (Aleatorio) | $\mathcal{O}(n^2)$ | $\mathcal{O}(n \log n)$ |
| **Peor Caso** (Orden inverso) | $\mathcal{O}(n^2)$ | $\mathcal{O}(n \log n)$ |
| **Memoria Auxiliar** | $\mathcal{O}(1)$ (In-place, no consume memoria adicional) | $\mathcal{O}(n)$ |
| **Estabilidad** | **Estable** (Conserva orden original en elementos iguales) | **Estable** |

---

## 5. Instrucciones de Compilación y Ejecución

El proyecto utiliza Java estándar (JDK 17 o superior) sin dependencias externas:

```bash
# 1. Compilar todas las clases en el directorio bin:
javac -encoding UTF-8 -d bin src/*.java

# 2. Ejecutar la aplicación principal (Demostración y Ranking):
java -cp bin DirectorioEstudiantesApp

# 3. Ejecutar la suite completa de pruebas unitarias:
java -cp bin PruebasOrdenamiento
```

---

## 6. Resultados y Casos de Prueba

### Salida del Programa Principal:
```text
==============================================================
   EJERCICIO 6 - DIRECTORIO DE ESTUDIANTES (Insertion Sort)
   Criterio: promedio DESC, empate -> nombre ASC
==============================================================

--- RANKING (ordenamiento manual) ---
  #1  E006     Valentina Diaz         Sem:  1  Prom: 4.90
  #2  E002     Ana Martinez           Sem:  5  Prom: 4.75
  #3  E004     Beatriz Rios           Sem:  4  Prom: 4.75
  #4  E007     andres Castro          Sem:  3  Prom: 4.20
  #5  E001     Carlos Perez           Sem:  3  Prom: 4.20
  #6  E005     Daniel Torres          Sem:  6  Prom: 4.20
  #7  E003     Luis Gomez             Sem:  2  Prom: 3.90
  #8  E008     Sofia Herrera          Sem:  7  Prom: 3.50

Comparaciones   : 17
Desplazamientos : 12
Tiempo estimado : 2.237 ms (2236600 ns)
Ordenado?       : true

--- Estadisticas del ranking ---
Mejor promedio  : Valentina Diaz (4.90)
Menor promedio  : Sofia Herrera (3.50)
Promedio general: 4.30

Ambas soluciones producen el mismo ranking? : true
```

### Matriz de Pruebas Unitarias (`PruebasOrdenamiento`):
| # | Caso de Prueba Evaluado | Comparaciones | Desplazamientos | Estado |
|---|---|:---:|:---:|:---:|
| 1 | Mismo promedio en todos (desempate exclusivo por nombre) | 6 | 5 | ✅ Aprobado |
| 2 | Directorio ya ordenado (Mejor caso teórico $\mathcal{O}(n)$) | 4 | 0 | ✅ Aprobado |
| 3 | Orden inverso completo (Peor caso teórico $\mathcal{O}(n^2)$) | 10 | 10 | ✅ Aprobado |
| 4 | Arreglo casi ordenado (un solo elemento fuera de posición) | 5 | 1 | ✅ Aprobado |
| 5 | Caso borde: Un solo estudiante | 0 | 0 | ✅ Aprobado |
| 6 | Caso borde: Directorio vacío | 0 | 0 | ✅ Aprobado |
| 7 | Sensibilidad a mayúsculas/minúsculas y promedios cercanos | 7 | 4 | ✅ Aprobado |

---

## 7. Comparativa: Algoritmo Manual vs. `Comparator`

* **Implementación Manual:** Brinda control granular sobre el paso a paso, permitiendo auditar la estabilidad y contabilizar exactamente las operaciones elementales de la máquina.
* **Implementación con `Comparator`:** Sintaxis concisa y declarativa de alto nivel que aprovecha el algoritmo híbrido **TimSort**, optimizado para grandes volúmenes de información en producción ($\mathcal{O}(n \log n)$).
* **Verificación:** Ambas implementaciones producen exactamente el mismo ranking en el 100% de los casos evaluados.

---

## 8. Flujo de Trabajo en Git

Siguiendo las directrices del taller, el desarrollo se gestionó mediante ramas independientes y avance progresivo:
1. `git switch -c desarrollo-ejercicio`
2. Commits periódicos y semánticos (`feat`, `test`, `docs`).
3. Integración limpia y documentada en la rama principal:
   ```bash
   git switch main
   git merge --no-ff desarrollo-ejercicio -m "merge: integrar rama desarrollo-ejercicio en main"
   git push origin main
   ```

---

## 9. Video de Sustentación

🎥 **Enlace de visualización:** *(Pendiente de grabación por el estudiante)*  
*El guion y estructura para la grabación se encuentra preparado en la documentación de apoyo.*
