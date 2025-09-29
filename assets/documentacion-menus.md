# 📋 DOCUMENTACIÓN DE MENÚS DEL SISTEMA

## 🎯 **ESTRUCTURA GENERAL DE NAVEGACIÓN**

El sistema está organizado en una estructura jerárquica de menús que permite acceder a todas las funcionalidades de gestión de biblioteca de manera intuitiva.

---

## 🏠 **MENÚ PRINCIPAL**

**Ubicación**: `MainController.java`  
**Función**: Pun## 📝 **DATOS CAPTURADOS POR FORMULARIOS**

### **📚 Registro de Libros**:

- **ID** (generado automáticamente como entero)
- **Título** (texto libre obligatorio)
- **Autor** (selección/creación con ID automático)
- **Categoría** (selección/creación con ID automático)
- **Editorial** (selección/creación con ID automático)
- **Año** (número entero)

### **📦 Ingreso de Stock**:

- **Libro** (búsqueda y selección de existente)
- **Origen** (COMPRA/DONACIÓN)
- **Cantidad** (rango 1-100)
- **Confirmación** (resumen antes de procesar)

### **👥 Registro de Socios**:

- **ID** (opcional, auto-generado si vacío)
- **Nombre completo** (texto obligatorio)
- **Email** (validación de formato obligatoria)
- **Teléfono** (texto obligatorio)
- **Tipo** (STANDARD/STUDENT/RETIRED con beneficios)

### **🔍 Búsquedas**:

- **Libros**: Texto libre (busca en título, autor, categoría, editorial, año)
- **Socios**: Nombre (coincidencias parciales) o ID exacto
- **Ejemplares**: Código exacto con validación de existencia

### **📋 Carrito de Préstamos**:

- **Socio** (búsqueda por nombre/ID con validación)
- **Ejemplares** (códigos individuales con verificación)
- **Fechas** (cálculo automático según tipo de socio)
- **Confirmación** (resumen completo antes de procesar)

---

## 📡 **EXPERIENCIA DE USUARIO**o de entrada principal del sistema

```
════════════════════════════════════════
    SISTEMA DE GESTIÓN DE BIBLIOTECA
════════════════════════════════════════

1. Gestión de Libros
2. Gestión de Socios
3. Gestión de Préstamos
4. Ver estadísticas del sistema
5. Salir
```

### **Opciones del Menú Principal**:

- **Opción 1**: Accede al módulo de gestión de libros
- **Opción 2**: Accede al módulo de gestión de socios
- **Opción 3**: Accede al módulo de gestión de préstamos
- **Opción 4**: Muestra estadísticas generales del sistema
- **Opción 5**: Salida del sistema con confirmación

### **Estadísticas del Sistema** (Opción 4):

Muestra un resumen con:

- 📚 Total de libros registrados
- ✍️ Total de autores
- 📂 Total de categorías
- 🏢 Total de editoriales
- 👥 Total de socios
- Lista de últimos 3 libros registrados
- Lista de últimos 3 socios registrados

---

## 📚 **MÓDULO: GESTIÓN DE LIBROS**

**Ubicación**: `BookController.java`  
**Función**: Administración completa del catálogo de libros

```
════════════════════════════════════════
         GESTIÓN DE LIBROS
════════════════════════════════════════

1. Registrar nuevo libro
2. Ingresar stock (compra/donación)
3. Modificar libro
4. Eliminar libro
5. Buscar libro
6. Volver al menú principal
```

### **Funcionalidades Detalladas**:

#### **1. Registrar nuevo libro**

- **Datos solicitados**:
  - 📖 Título del libro (obligatorio)
  - ✍️ Autor (selección de lista existente o crear nuevo)
  - 📂 Categoría (selección de lista existente o crear nueva)
  - 🏢 Editorial (selección de lista existente o crear nueva)
  - 📅 Año de publicación (número entero)
  - 🆔 ID (generado automáticamente)
- **Proceso**: Genera ID único automático, crea entidades relacionadas si no existen
- **Formulario**: `RegisterBookForm`

#### **2. Ingresar stock (compra/donación)**

- **Datos solicitados**:
  - 🔍 Búsqueda y selección de libro existente
  - 📦 Origen del ejemplar: COMPRA o DONACIÓN
  - 🔢 Cantidad de ejemplares (1-100)
  - ✅ Confirmación de la operación
- **Proceso**: Genera códigos únicos automáticos para cada ejemplar
- **Formulario**: `AddStockForm`

#### **3. Modificar libro**

- **Datos modificables**:
  - 📖 Título del libro
  - ✍️ Autor (selección/creación)
  - 📂 Categoría (selección/creación)
  - 🏢 Editorial (selección/creación)
  - 📅 Año de publicación
- **Proceso**: ID permanece inmutable, actualiza referencias a entidades
- **Formulario**: `ModifyBookForm`

#### **4. Eliminar libro**

- **Datos solicitados**:
  - 🔍 Búsqueda y selección de libro a eliminar
  - ⚠️ Confirmación de eliminación
- **Validaciones**: Solo elimina libros sin ejemplares asociados
- **Formulario**: `DeleteBookForm`

#### **5. Buscar libro**

- **Datos solicitados**:
  - 🔍 Texto de búsqueda (título, autor, categoría, editorial o año)
- **Resultados mostrados**:
  - Lista de libros coincidentes
  - Información detallada del libro seleccionado
  - Estado y disponibilidad de todos los ejemplares
  - Navegación interactiva para ver detalles específicos
- **Formulario**: `FindBookForm`

---

## 👥 **MÓDULO: GESTIÓN DE SOCIOS**

**Ubicación**: `MemberController.java`  
**Función**: Administración completa de la membresía

```
════════════════════════════════════════
         GESTIÓN DE SOCIOS
════════════════════════════════════════

1. Registrar nuevo socio
2. Modificar datos del socio
3. Buscar y consultar socio
4. Pagar multas
5. Volver al menú principal
```

### **Funcionalidades Detalladas**:

#### **1. Registrar nuevo socio**

- **Datos solicitados**:
  - 🆔 ID socio (opcional, se auto-genera si no se proporciona)
  - 👤 Nombre y apellido (obligatorio)
  - 📧 Email (obligatorio, validación de formato)
  - 📞 Teléfono (obligatorio)
  - 🎓 Categoría de socio:
    - **STANDARD**: Sin beneficios especiales
    - **STUDENT**: 50% descuento en multas
    - **RETIRED**: +3 días al plazo de préstamo
- **Proceso**: Estado inicial ACTIVO, sin multas pendientes
- **Formulario**: `RegisterMemberForm`

#### **2. Modificar datos del socio**

- **Datos modificables**:
  - 👤 Nombre y apellido
  - 📧 Email (con validación)
  - 📞 Teléfono
  - 🎓 Tipo de membresía
  - 📊 Estado del socio
- **Validaciones**: Integridad de datos y formato de email
- **Formulario**: `ModifyMemberForm`

#### **3. Buscar y consultar socio**

- **Criterios de búsqueda**:
  - 👤 Por nombre (coincidencias parciales)
  - 🆔 Por ID/DNI exacto
- **Información mostrada**:
  - 📋 Datos personales completos
  - 📊 Estado de la membresía y beneficios
  - 💰 Multas pendientes (monto total)
  - 📚 Préstamos activos con fechas
  - 📈 Resumen de actividad histórica
- **Formulario**: `FindMemberForm`

#### **4. Pagar multas**

- Permite saldar multas pendientes
- Actualiza estado financiero del socio
- Registra fecha y monto del pago
- **Formulario**: `PayFineForm`

---

## 📋 **MÓDULO: GESTIÓN DE PRÉSTAMOS**

**Ubicación**: `LoanController.java`  
**Función**: Control completo del sistema de préstamos

```
════════════════════════════════════════
       GESTIÓN DE PRÉSTAMOS
════════════════════════════════════════

1. Nuevo préstamo
2. Ver préstamos activos
3. Ver préstamos vencidos
4. Buscar préstamos por socio
5. Estadísticas de préstamos
6. Volver al menú principal
```

### **Funcionalidades Detalladas**:

#### **1. Nuevo préstamos**

- **Sistema de carrito inteligente** (`LoanCartForm`)
- **Proceso paso a paso**:
  1. **Búsqueda de socio**:
     - 🔍 Por nombre o ID
     - ✅ Validación automática de elegibilidad
     - 📊 Muestra resumen de préstamos actuales
  2. **Gestión del carrito**:
     - ➕ Agregar ejemplares (búsqueda por título/código)
     - 👁️ Ver contenido del carrito
     - 🗑️ Eliminar ejemplares del carrito
     - ✅ Confirmar y procesar préstamo
     - ❌ Cancelar operación
- **Validaciones en tiempo real**:
  - 👤 Estado del socio (ACTIVO requerido)
  - 💰 Sin multas pendientes
  - 📚 Límites por tipo de socio
  - 📦 Disponibilidad de ejemplares
  - 🚫 Prevención de duplicados
- **Formularios**: `LoanCartForm`, `AddToCartForm`, `ConfirmLoanForm`

#### **2. Ver préstamos activos**

- Lista todos los préstamos con estado ACTIVE
- Información detallada por préstamo:
  - ID del préstamo
  - Socio y datos de contacto
  - Libro y ejemplar específico
  - Fechas de préstamo y vencimiento
  - Días restantes/vencidos

#### **3. Ver préstamos vencidos**

- Filtra préstamos con fecha de vencimiento superada
- Identifica automáticamente préstamos en mora
- Información para gestión de multas

#### **4. Buscar préstamos por socio**

- Búsqueda personalizada por socio específico
- Filtros por estado del préstamo
- Historial completo de préstamos del socio

#### **5. Estadísticas de préstamos**

- **Métricas del sistema**:
  - Total de préstamos activos
  - Total de préstamos vencidos
  - Tasa de retorno puntual
  - Socios con más préstamos
  - Libros más prestados
- **Gráficos y resúmenes** estadísticos

---

## 🔧 **COMPONENTES TÉCNICOS**

### **Formularios Especializados**:

#### **📚 Formularios de Libros**:

- `RegisterBookForm` - Registro con selección/creación de autores, categorías y editoriales
- `AddStockForm` - Ingreso de stock con selección de origen (COMPRA/DONACIÓN)
- `ModifyBookForm` - Modificación de datos básicos (excepto ISBN)
- `DeleteBookForm` - Eliminación con validaciones de integridad
- `FindBookForm` - Búsqueda multi-criterio con navegación de resultados

#### **👥 Formularios de Socios**:

- `RegisterMemberForm` - Registro con validación de email y selección de tipo
- `ModifyMemberForm` - Modificación de datos personales y estado
- `FindMemberForm` - Búsqueda por nombre/ID con perfil completo
- `PayFineForm` - Gestión de pagos de multas

#### **📋 Formularios de Préstamos** (Sistema Avanzado):

**`LoanCartForm`** - **Controlador principal**:

- 🔍 Búsqueda y selección de socio
- ✅ Validación automática de elegibilidad
- 📊 Muestra información de préstamos del socio
- 🧾 Orquesta todo el flujo del carrito

**`AddToCartForm`** - **Agregado de ejemplares**:

- 📝 Entrada: Código del ejemplar o 'salir'
- 🔍 Búsqueda automática en repositorio
- ⚡ Validaciones en tiempo real:
  - Existencia del ejemplar
  - Estado DISPONIBLE requerido
  - No duplicados en carrito
- ➕ Agregado con confirmación visual

**`ConfirmLoanForm`** - **Confirmación y procesamiento**:

- 📊 Resumen completo del préstamo:
  - Lista de ejemplares seleccionados
  - Fechas de préstamo y vencimiento
  - Información del socio
- ❓ Confirmación final del usuario
- ⚡ Procesamiento con feedback detallado

### **Utilidades de Interfaz**:

- `DisplayHelper` - Renderizado de títulos, mensajes, colores y formato
- `InputHelper` - Captura, validación y confirmaciones de usuario
- `MemberSearchHelper` - Búsqueda especializada y navegación de socios

### **Características Avanzadas de Formularios**:

- ✨ **Navegación dinámica** con opciones contextuales
- 🔄 **Validación en tiempo real** durante la entrada
- 📋 **Selección de listas** con creación automática
- ✅ **Confirmaciones inteligentes** con resúmenes
- 🚫 **Cancelación segura** en cualquier punto
- 📊 **Feedback visual** inmediato
- 🔍 **Búsquedas integradas** en formularios complejos

### **Patrones Implementados**:

- **MVC** (Model-View-Controller) en la capa de presentación
- **Clean Architecture** con separación clara de responsabilidades
- **Use Cases** para lógica de negocio
- **Repository Pattern** para acceso a datos
- **Form Pattern** para captura de datos estructurada

---

## 🎯 **FLUJOS DE NAVEGACIÓN PRINCIPALES**

### **Flujo de Préstamo Completo**:

```
Menú Principal → Gestión de Préstamos → Nuevo préstamo
    ↓
🔍 Buscar socio por nombre/ID
    ↓
✅ Validación automática:
   • Estado ACTIVO ✓
   • Sin multas pendientes ✓
   • Límite de préstamos disponible ✓
    ↓
📊 Mostrar resumen del socio y préstamos actuales
    ↓
🛒 CARRITO DE PRÉSTAMOS:
   1️⃣ Agregar ejemplar → Buscar por título/código
   2️⃣ Ver carrito → Lista actual con validaciones
   3️⃣ Quitar ejemplar → Selección y confirmación
   4️⃣ Confirmar préstamo → Fechas y resumen final
   5️⃣ Cancelar → Salir sin procesar
    ↓
⚡ Procesamiento automático:
   • Actualización estados de ejemplares
   • Registro de préstamo con fechas
   • Confirmación de operación exitosa
```

### **Flujo de Consulta de Socio**:

```
Menú Principal → Gestión de Socios → Buscar y consultar socio
    ↓
Buscar por nombre o DNI
    ↓
Seleccionar socio de resultados
    ↓
Ver perfil detallado con préstamos activos
    ↓
Opciones: modificar datos, pagar multas, ver historial
```

### **Flujo de Gestión de Libros**:

```
Menú Principal → Gestión de Libros
    ↓
Registro: datos básicos → autores/categorías → confirmación
    ↓
Stock: seleccionar libro → especificar cantidad/origen → generar ejemplares
    ↓
Búsqueda: criterios → resultados → detalles → ejemplares
```

---

## 📱 **EXPERIENCIA DE USUARIO**

### **Características de Usabilidad**:

- ✅ **Navegación intuitiva** con numeración clara
- ✅ **Validaciones en tiempo real** con mensajes descriptivos
- ✅ **Confirmaciones** para operaciones críticas
- ✅ **Mensajes de estado** colorados (éxito/error/información)
- ✅ **Pausas automáticas** para lectura de resultados
- ✅ **Limpieza de pantalla** entre operaciones
- ✅ **Breadcrumbs implícitos** con títulos contextuales

### **Validaciones Implementadas**:

#### **📚 Libros**:

- ✅ **ID único** generado automáticamente
- ✅ **Campos obligatorios** no vacíos (título, autor, categoría, editorial)
- ✅ **Año numérico** válido
- ✅ **Integridad referencial** antes de eliminar

#### **👥 Socios**:

- ✅ **Email formato válido** (regex pattern)
- ✅ **Campos obligatorios** completos
- ✅ **ID único** si se proporciona manualmente
- ✅ **Estado consistente** para operaciones

#### **📋 Préstamos**:

- ✅ **Socio ACTIVO** requerido
- ✅ **Sin multas pendientes**
- ✅ **Límites por tipo** (STANDARD: 3, STUDENT: 5, RETIRED: 4)
- ✅ **Ejemplar DISPONIBLE**
- ✅ **No duplicados** en carrito
- ✅ **Existencia de ejemplar** en sistema

### **Manejo de Errores**:

- ⚠️ **Captura de excepciones** en todos los niveles
- 💬 **Mensajes descriptivos** y orientativos
- 🔄 **Recuperación automática** sin pérdida de contexto
- ⚙️ **Validaciones preventivas** antes de operaciones críticas
- 🚫 **Prevención de estados inconsistentes**

El sistema proporciona una experiencia completa y profesional para la gestión de biblioteca, con todas las funcionalidades implementadas siguiendo principios de Clean Architecture y patrones de diseño establecidos.
