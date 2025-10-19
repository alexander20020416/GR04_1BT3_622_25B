# 📚 Gestor de Tareas Universitarias - GR04_1BT3_622_25B

## 👥 Equipo - Grupo 4

- **Cholango Lanchimba Belén Elizabeth**
- **Guallichico Nacimba Carlos Javier**
- **Morales Navas Dennis Alexander**

**Asignatura:** Metodologías Ágiles (ISWD622)  
**Institución:** Escuela Politécnica Nacional  
**Carrera:** Ingeniería de Software - Sexto Semestre

---

## 📋 Descripción del Proyecto

Sistema web de gestión de tareas universitarias que permite a los estudiantes registrar, organizar y dar seguimiento a sus tareas académicas de manera eficiente y personalizada.

**⚠️ Refactorización Importante:** Se eliminó la redundancia entre `Actividad` y `Tarea`. Ahora el sistema usa únicamente la entidad `Tarea` como modelo principal.

---

## 🏗️ Arquitectura y Patrones de Diseño

### Patrones Implementados

1. **Repository Pattern**
    - Separa la lógica de negocio de la capa de persistencia
    - Interfaces: `TareaRepository`, `AlertaRepository`
    - Implementaciones con JPA/Hibernate

2. **Strategy Pattern**
    - Diferentes estrategias de ordenamiento de tareas
    - Implementaciones: `OrdenPorPrioridad`, `OrdenPorFecha`, `OrdenPorTitulo`
    - Permite cambiar dinámicamente el criterio de ordenamiento

3. **Observer Pattern**
    - Sistema de notificaciones para alertas
    - Interface `AlertaListener` con implementación `AlertaListenerImpl`
    - Notifica eventos de creación, activación y desactivación de alertas

---

## 🚀 Tecnologías Utilizadas

- **Backend:** Java 11
- **Framework Web:** Java Servlets (javax.servlet 4.0.1)
- **Vista:** JSP (JavaServer Pages) + JSTL
- **ORM:** Hibernate 5.6.15
- **Base de Datos:** H2 Database (en memoria/archivo)
- **Testing:** JUnit 5.12.1 + Mockito 5.14.2
- **Build Tool:** Maven
- **Servidor:** Apache Tomcat 9
- **IDE:** IntelliJ IDEA / VS Code

---

## 📦 Estructura del Proyecto

```
GR04_1BT3_622_25B/
├── src/
│   ├── main/
│   │   ├── java/com/gr4/
│   │   │   ├── controller/          # Servlets (Controladores)
│   │   │   │   ├── BaseServlet.java
│   │   │   │   ├── GestorPlanificacionServlet.java
│   │   │   │   ├── GestorAdministracionServlet.java
│   │   │   │   ├── GestorListadoServlet.java
│   │   │   │   ├── GestorAlertasServlet.java
│   │   │   │   └── ListarTareasServlet.java
│   │   │   ├── model/               # Entidades JPA
│   │   │   │   ├── Tarea.java      # ✨ ENTIDAD PRINCIPAL
│   │   │   │   └── Alerta.java
│   │   │   ├── repository/          # Patrón Repository
│   │   │   │   ├── TareaRepository.java (I)
│   │   │   │   ├── TareaRepositoryImpl.java
│   │   │   │   ├── AlertaRepository.java (I)
│   │   │   │   └── AlertaRepositoryImpl.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── TareaDTO.java
│   │   │   │   └── AlertaDTO.java
│   │   │   ├── strategy/            # Patrón Strategy
│   │   │   │   ├── OrdenStrategy.java (I)
│   │   │   │   ├── OrdenPorPrioridad.java
│   │   │   │   ├── OrdenPorFecha.java
│   │   │   │   └── OrdenPorTitulo.java
│   │   │   ├── observer/            # Patrón Observer
│   │   │   │   ├── AlertaListener.java (I)
│   │   │   │   └── AlertaListenerImpl.java
│   │   │   ├── service/             # Servicios de negocio
│   │   │   │   └── TareaFilterService.java
│   │   │   └── util/                # Utilidades
│   │   │       ├── JPAUtil.java
│   │   │       └── ParametroParser.java
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml  # Configuración JPA
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── jsp/                 # Vistas JSP
│   │       │   ├── index.jsp
│   │       │   ├── planificar.jsp
│   │       │   ├── organizar.jsp
│   │       │   ├── consultar.jsp
│   │       │   ├── listar.jsp
│   │       │   ├── configurar_alerta.jsp
│   │       │   └── success.jsp
│   │       └── css/
│   │           └── styles.css
│   └── test/
│       └── java/                    # Tests unitarios (JUnit + Mockito)
└── pom.xml                          # Configuración Maven
```

---

## 📊 Modelo de Datos Simplificado

### Entidad Principal: `Tarea`

La entidad `Tarea` representa cualquier actividad o tarea académica que el estudiante debe completar.

**Atributos:**
- `id` (Long) - Identificador único
- `titulo` (String) - Título de la tarea
- `descripcion` (String) - Descripción detallada
- `fechaVencimiento` (LocalDate) - Fecha límite de entrega
- `estado` (String) - Pendiente | En Progreso | Completada
- `prioridad` (String) - Alta | Media | Baja

### Entidad Secundaria: `Alerta`

Representa recordatorios asociados a tareas.

**Atributos:**
- `id` (Long) - Identificador único
- `mensaje` (String) - Texto del recordatorio
- `fechaHora` (LocalDateTime) - Fecha y hora de la alerta
- `tipo` (String) - Recordatorio | Urgente | Informativa
- `activa` (Boolean) - Estado de la alerta

---

## 📊 Incrementos Funcionales

### Incremento 1: Planificación y Organización

#### Caso de Uso 1: Planificar Tareas
- **Descripción:** Registro de nuevas tareas académicas
- **Controlador:** `GestorPlanificacionServlet`
- **Vista:** `planificar.jsp`
- **Patrón:** Repository
- **URL:** `/planificar`

#### Caso de Uso 2: Organizar Tareas
- **Descripción:** Visualización y ordenamiento dinámico de tareas
- **Controlador:** `GestorAdministracionServlet`
- **Vista:** `organizar.jsp`
- **Patrón:** Strategy (3 estrategias de ordenamiento)
- **URL:** `/organizar?orden=[prioridad|fecha|titulo]`

### Incremento 2: Consulta y Alertas

#### Caso de Uso 3: Consultar Tareas
- **Descripción:** Consulta y filtrado de tareas por estado
- **Controlador:** `GestorListadoServlet`
- **Vista:** `consultar.jsp`
- **Patrón:** Repository
- **URL:** `/consultar?filtro=[Pendiente|En Progreso|Completada]`

#### Caso de Uso 4: Configurar Alertas
- **Descripción:** Creación de alertas personalizadas
- **Controlador:** `GestorAlertasServlet`
- **Vista:** `configurar_alerta.jsp`
- **Patrón:** Observer + Repository
- **URL:** `/alertas`

---

## 🔧 Instalación y Configuración

### Prerrequisitos

- Java JDK 11 o superior
- Apache Maven 3.6+
- Apache Tomcat 9.x
- IntelliJ IDEA (recomendado) o Eclipse

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/GR04_1BT3_622_25B.git
cd GR04_1BT3_622_25B
```

2. **Compilar el proyecto con Maven**
```bash
mvn clean install
```

3. **Configurar Tomcat en el IDE**
    - En IntelliJ: Run → Edit Configurations → Add Tomcat Server
    - Deployment: Add war exploded
    - Application context: `/`

4. **Ejecutar la aplicación**
    - Iniciar el servidor Tomcat
    - Acceder a: `http://localhost:8080/`

### Configuración de Base de Datos

La aplicación usa H2 Database por defecto. La configuración está en `persistence.xml`:

```xml
<!-- Modo FILE: Los datos persisten -->
<property name="javax.persistence.jdbc.url" 
          value="jdbc:h2:file:./data/gestor_tareas;AUTO_SERVER=TRUE"/>

<!-- Modo MEMORY: Datos temporales (para pruebas) -->
<!-- <property name="javax.persistence.jdbc.url" 
          value="jdbc:h2:mem:gestor_tareas;DB_CLOSE_DELAY=-1"/> -->
```

**Usuario por defecto:** `sa`  
**Contraseña:** (vacía)

---

## 🧪 Casos de Prueba Implementados

### Incremento 1

#### Planificar Actividades
- **CP01:** Mostrar formulario de actividad
- **CP02:** Enviar formulario con datos válidos
- **CP03:** Validación exitosa de DTO
- **CP04:** Creación de entidad Actividad
- **CP05:** Guardado exitoso de actividad
- **CP06:** Confirmación al usuario

#### Organizar Tareas
- **CP07:** Mostrar lista de tareas
- **CP08:** Seleccionar estrategia de orden
- **CP09:** Aplicar estrategia de ordenamiento
- **CP10:** Mostrar tareas ordenadas
- **CP11:** Confirmación de organización

### Incremento 2

#### Consultar Tareas
- **CP09:** Mostrar listado de tareas
- **CP10:** Consultar todas las tareas
- **CP11:** Consultar tareas por estado
- **CP12:** Consultar tareas inexistentes
- **CP13:** Error al consultar tareas
- **CP14:** Validar integridad de datos

#### Configurar Alertas
- **CP01:** Mostrar formulario de alerta
- **CP02:** Enviar formulario con datos válidos
- **CP03:** Validación exitosa de DTO
- **CP04:** Creación de entidad Alerta
- **CP05:** Guardado exitoso de alerta
- **CP06:** Notificación de alerta creada (Observer)
- **CP07:** Error al guardar alerta
- **CP08:** Fecha inválida

---

## 🎯 Trazabilidad: Diagramas → Código

### 1. Diagrama de Clases → Entidades Java

| Clase del Diagrama | Archivo Java | Tipo |
|-------------------|--------------|------|
| Actividad | `com.gr4.model.Actividad` | Entity |
| Tarea | `com.gr4.model.Tarea` | Entity |
| Alerta | `com.gr4.model.Alerta` | Entity |
| ActividadDTO | `com.gr4.dto.ActividadDTO` | DTO |
| AlertaDTO | `com.gr4.dto.AlertaDTO` | DTO |
| ActividadRepository | `com.gr4.repository.ActividadRepository` | Interface |
| TareaRepository | `com.gr4.repository.TareaRepository` | Interface |
| AlertaRepository | `com.gr4.repository.AlertaRepository` | Interface |
| OrdenStrategy | `com.gr4.strategy.OrdenStrategy` | Interface |
| OrdenPorPrioridad | `com.gr4.strategy.OrdenPorPrioridad` | Strategy |
| AlertaListener | `com.gr4.observer.AlertaListener` | Interface |

### 2. Diagrama de Secuencia → Métodos en Servlets

#### Planificar Actividades
```
Usuario → MainPlanificacionTareas → GestorPlanificacionController
```
**Implementación:**
- `GestorPlanificacionServlet.doPost()`
- Pasos del diagrama:
    1. Recibir `ActividadDTO`
    2. `validar(dto)` → `ActividadDTO.validar()`
    3. Crear `Actividad` → `crearActividadDesdeDTO()`
    4. `save(actividad)` → `ActividadRepository.save()`
    5. Retornar confirmación

#### Organizar Tareas (con Strategy)
```
Usuario → MainAdministracionTareas → GestorAdministracionController → OrdenStrategy
```
**Implementación:**
- `GestorAdministracionServlet.doGet()`
- Pasos del diagrama:
    1. Obtener criterio de orden
    2. `findAll()` → `TareaRepository.findAll()`
    3. Seleccionar estrategia → `seleccionarEstrategia()`
    4. `ordenar(tareas)` → `OrdenStrategy.ordenar()`
    5. Retornar tareas ordenadas

#### Configurar Alertas (con Observer)
```
Usuario → MainFormularioRecordatorio → GestorAlertsController → AlertaListener
```
**Implementación:**
- `GestorAlertasServlet.doPost()`
- Pasos del diagrama:
    1. Recibir `AlertaDTO`
    2. `validar(dto)` → `AlertaDTO.validar()`
    3. Crear `Alerta`
    4. `save(alerta)` → `AlertaRepository.save()`
    5. `onAlertaCreada(alerta)` → `AlertaListener.onAlertaCreada()` ⭐ **Observer**
    6. Retornar confirmación

### 3. Diagrama de Robustez → Capas de la Aplicación

| Tipo de Objeto | Implementación |
|----------------|----------------|
| **Boundary** | Archivos JSP (planificar.jsp, organizar.jsp, etc.) |
| **Control** | Servlets (GestorPlanificacionServlet, etc.) |
| **Entity** | Clases modelo (Actividad, Tarea, Alerta) |

---

## 🔍 Guía de Uso de la Aplicación

### 1. Página Principal
Acceder a `http://localhost:8080/` para ver el menú principal con todas las funcionalidades.

### 2. Planificar una Actividad
1. Click en "Planificar Actividades"
2. Completar el formulario:
    - Título (requerido)
    - Descripción (requerido)
    - Fecha de entrega (requerido)
    - Prioridad (Alta/Media/Baja)
3. Click en "Guardar Actividad"
4. Ver confirmación con los detalles

### 3. Organizar Tareas
1. Click en "Organizar Tareas"
2. Seleccionar criterio de ordenamiento:
    - **Por Prioridad:** Alta → Media → Baja
    - **Por Fecha:** Más próximas primero
    - **Alfabético:** A → Z
3. Ver tareas ordenadas según el criterio seleccionado

### 4. Consultar Tareas
1. Click en "Consultar Tareas"
2. Filtrar por estado:
    - Todas
    - Pendientes
    - En Progreso
    - Completadas
3. Ver tabla con todas las tareas filtradas

### 5. Configurar Alertas
1. Click en "Configurar Alertas"
2. Completar el formulario:
    - Seleccionar actividad asociada
    - Mensaje de la alerta
    - Fecha y hora (debe ser futura)
    - Tipo (Recordatorio/Urgente/Informativa)
3. Click en "Configurar Alerta"
4. El sistema ejecuta el **Observer** y notifica en consola

---

## 📝 Notas Técnicas

### Configuración de Hibernate
- **hibernate.hbm2ddl.auto:** `update` (crea/actualiza tablas automáticamente)
- **hibernate.show_sql:** `true` (muestra queries SQL en consola)
- **hibernate.format_sql:** `true` (formatea SQL para mejor lectura)

### Persistencia de Datos
- **Modo FILE:** Los datos se guardan en `./data/gestor_tareas.mv.db`
- **Modo MEMORY:** Los datos se pierden al cerrar la aplicación

### Logs en Consola
El sistema imprime información útil en la consola del servidor:
- ✓ Operaciones exitosas (verde)
- ✗ Errores (rojo)
- 📥 Datos recibidos
- 📊 Resultados de consultas
- ⚡ Eventos del Observer

---

## 🐛 Solución de Problemas Comunes

### Error: "EntityManagerFactory no se puede crear"
**Solución:** Verificar que `persistence.xml` esté en `src/main/resources/META-INF/`

### Error: "No se encuentra la clase Servlet"
**Solución:** Verificar que Tomcat esté correctamente configurado en el IDE

### Error: "404 Not Found"
**Solución:**
- Verificar el Application Context en la configuración de Tomcat (debe ser `/`)
- Verificar que el WAR esté correctamente desplegado

### Error: "Las tablas no se crean"
**Solución:** Verificar `hibernate.hbm2ddl.auto` en `persistence.xml` (debe ser `update` o `create`)

### Las vistas JSP no encuentran los estilos CSS
**Solución:** Verificar que la carpeta `css` esté en `webapp/css/` (no en `WEB-INF`)

---

## 📚 Referencias y Documentación

- [Hibernate Documentation](https://hibernate.org/orm/documentation/5.6/)
- [Java Servlets Tutorial](https://docs.oracle.com/javaee/7/tutorial/servlets.htm)
- [JSP Tutorial](https://docs.oracle.com/javaee/7/tutorial/jsf-intro.htm)
- [Maven Documentation](https://maven.apache.org/guides/)
- [H2 Database](http://www.h2database.com/html/main.html)

---

## 👨‍💻 Autores

**Grupo 4 - Metodologías Ágiles**

- Cholango Lanchimba Belén Elizabeth
- Guallichico Nacimba Carlos Javier
- Morales Navas Dennis Alexander

**Institución:** Escuela Politécnica Nacional  
**Año:** 2025

---

## 📄 Licencia

Este proyecto es parte de un trabajo académico para la asignatura de Metodologías Ágiles.

---

## 🎓 Conclusiones

Este proyecto implementa exitosamente:

1. ✅ **Patrón Repository:** Separación clara entre lógica de negocio y persistencia
2. ✅ **Patrón Strategy:** Flexibilidad en el ordenamiento de tareas sin modificar controladores
3. ✅ **Patrón Observer:** Sistema de notificaciones desacoplado para alertas
4. ✅ **Arquitectura MVC:** Separación de responsabilidades (Model-View-Controller)
5. ✅ **ORM con Hibernate:** Mapeo objeto-relacional transparente
6. ✅ **Trazabilidad completa:** Desde diagramas UML hasta código Java funcional

El sistema cumple con todos los requisitos especificados en los diagramas de casos de uso, actividades, robustez, clases y secuencia, implementando los dos incrementos funcionales de manera exitosa.