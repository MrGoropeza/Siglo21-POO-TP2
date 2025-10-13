# 📋 DOCUMENTACIÓN DE MENÚS DEL SISTEMA

## 🎯 **ESTRUCTURA GENERAL DE NAVEGACIÓN**

El sistema está organizado en una estructura jerárquica de menús que permite acceder a todas las funcionalidades de gestión de biblioteca de manera intuitiva.

---

## 🏠 **MENÚ PRINCIPAL**

**Ubicación**: `MainController.java`  
**Función**: Punto de entrada principal del sistema

```
════════════════════════════════════════
    SISTEMA DE GESTIÓN DE BIBLIOTECA
════════════════════════════════════════

1. Gestión de Libros
2. Gestión de Socios
3. Gestión de Préstamos
4. Gestión de Devoluciones
5. Configuración del Sistema
6. Ver estadísticas del sistema
7. Salir
```

### **Opciones del Menú Principal**:

- **Opción 1**: Accede al módulo de gestión de libros
- **Opción 2**: Accede al módulo de gestión de socios
- **Opción 3**: Accede al módulo de gestión de préstamos
- **Opción 4**: Accede al módulo de gestión de devoluciones
- **Opción 5**: Accede al módulo de configuración del sistema
- **Opción 6**: Muestra estadísticas generales del sistema
- **Opción 7**: Salida del sistema con confirmación

### **Estadísticas del Sistema** (Opción 6):

Muestra un resumen con:

- 📚 Total de libros registrados
- ✍️ Total de autores
- 📂 Total de categorías
- 🏢 Total de editoriales
- 👥 Total de socios
- Lista de últimos 3 libros registrados
- Lista de últimos 3 socios registrados

---

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

### **🔄 Registro de Devoluciones**:

- **Código de ejemplar** (texto obligatorio con validación)
- **Préstamo activo** (búsqueda automática del préstamo)
- **Fecha de devolución** (fecha actual automática)
- **Preview de multa** (cálculo automático si hay retraso):
  - Días de retraso calculados
  - Monto base ($5/día)
  - Descuento aplicado (50% estudiantes/jubilados)
  - Total a pagar
- **Confirmación** (procesar devolución y generar multa)

### **🔍 Consulta de Devoluciones**:

- **Filtros disponibles**:
  - Todas las devoluciones (sin filtro)
  - Por socio (búsqueda por nombre/ID)
  - Por libro (búsqueda por título)
  - Por rango de fechas (desde/hasta DD/MM/YYYY)
  - Solo con multa
  - Solo sin multa
- **Display de resultados**:
  - Información completa del préstamo
  - Días de retraso si aplica
  - Monto de multa y estado (Pagada/Pendiente)

### **🔧 Configuración del Sistema**:

- **Parámetros modificables**:
  - Días estándar de préstamo (número entero > 0)
  - Multa diaria (decimal ≥ 0, formato moneda)
  - Cupo máximo por socio (número entero ≥ 0)
  - Tope de reservas activas por socio (número entero ≥ 0)
- **Información consultable**:
  - Beneficios por categoría de socio (solo lectura)
- **Validaciones**:
  - Formato numérico correcto
  - Rangos permitidos según parámetro
  - Mensajes de error descriptivos

---

## 📡 **EXPERIENCIA DE USUARIO**o de entrada principal del sistema

```
════════════════════════════════════════
    SISTEMA DE GESTIÓN DE BIBLIOTECA
════════════════════════════════════════

1. Gestión de Libros
2. Gestión de Socios
3. Gestión de Préstamos
4. Gestión de Devoluciones
5. Ver estadísticas del sistema
6. Salir
```

### **Opciones del Menú Principal**:

- **Opción 1**: Accede al módulo de gestión de libros
- **Opción 2**: Accede al módulo de gestión de socios
- **Opción 3**: Accede al módulo de gestión de préstamos
- **Opción 4**: Accede al módulo de gestión de devoluciones
- **Opción 5**: Muestra estadísticas generales del sistema
- **Opción 6**: Salida del sistema con confirmación

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

## � **MÓDULO: GESTIÓN DE DEVOLUCIONES**

**Ubicación**: `ReturnController.java`  
**Función**: Control completo del sistema de devoluciones y multas

```
════════════════════════════════════════
       GESTIÓN DE DEVOLUCIONES
════════════════════════════════════════

1. Registrar devolución
2. Consultar historial de devoluciones
3. Volver al menú principal
```

### **Funcionalidades Detalladas**:

#### **1. Registrar devolución**

**Sistema de devolución con cálculo automático de multas** (`RegisterReturnForm`)

**Proceso paso a paso**:

1. **Búsqueda de préstamo activo**:

   - 🔍 Ingreso de código de ejemplar
   - ✅ Validación de préstamo activo existente
   - 📊 Muestra información del préstamo

2. **Preview de devolución**:

   - 📋 Información del socio y libro
   - 📅 Fecha de préstamo, vencimiento y devolución actual
   - ⚠️ **Cálculo automático de días de retraso**
   - 💰 **Preview de multa si hay retraso**:
     - Monto base: $5 por día de retraso
     - Descuento aplicado según tipo de socio:
       - 50% para ESTUDIANTES
       - 50% para JUBILADOS
       - 0% para ESTÁNDAR
     - Monto final calculado
   - ✅ Confirmación para procesar

3. **Procesamiento automático**:
   - 📝 Actualización de estado del préstamo: ACTIVE → RETURNED
   - 📦 Actualización de estado del ejemplar: LOANED → AVAILABLE
   - 💰 Generación automática de multa si hay retraso
   - 🎯 Registro de fecha de devolución

**Validaciones en tiempo real**:

- ✅ Ejemplar debe existir en el sistema
- ✅ Debe tener préstamo activo (estado ACTIVE)
- ✅ Cálculo correcto de días de retraso
- ✅ Aplicación de descuentos por tipo de socio

**Ejemplo de preview de multa**:

```
════════════════════════════════════════
📋 PREVIEW DE DEVOLUCIÓN
════════════════════════════════════════

📚 Libro: Ficciones
👤 Socio: Roberto Silva (3001) - RETIRED
📅 Fecha préstamo: 13/09/2025
📅 Fecha vencimiento: 30/09/2025
📅 Fecha devolución: 07/10/2025

⚠️  ATENCIÓN: Devolución con RETRASO
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📆 Días de retraso: 7 días
💵 Tarifa base: $5.00 por día
💰 Monto sin descuento: $35.00
🎁 Descuento (RETIRED 50%): -$17.50
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
💵 TOTAL A PAGAR: $17.50
```

**Formulario**: `RegisterReturnForm`

#### **2. Consultar historial de devoluciones**

**Sistema avanzado de consultas con múltiples filtros** (`QueryReturnsForm`)

**Opciones de filtrado** (7 opciones):

1. **Ver todas las devoluciones**

   - Lista completa del historial
   - Sin restricciones de filtro

2. **Filtrar por socio**

   - 🔍 Búsqueda por nombre o ID
   - 📋 Historial de devoluciones del socio seleccionado

3. **Filtrar por libro**

   - 🔍 Búsqueda por título
   - 📚 Todas las devoluciones de ese libro

4. **Filtrar por rango de fechas**

   - 📅 Fecha desde (DD/MM/YYYY)
   - 📅 Fecha hasta (DD/MM/YYYY)
   - 🔍 Devoluciones en ese período

5. **Ver solo devoluciones con multa**

   - ⚠️ Filtra préstamos devueltos con retraso
   - 💰 Muestra monto y estado de cada multa

6. **Ver solo devoluciones sin multa**

   - ✓ Filtra devoluciones a tiempo
   - Sin generación de multas

7. **Volver**
   - Regresa al menú de devoluciones

**Información mostrada por devolución**:

```
1. Socio: Roberto Silva (3001)
   Libro: Ficciones
   Ejemplar: COPY-004-1
   Fecha préstamo: 13/09/2025
   Fecha vencimiento: 30/09/2025
   Fecha devolución: 07/10/2025
   ⚠️  Devolución con 7 día(s) de retraso
   💰 Multa generada: $17.50 (Pagada)
```

**Indicadores visuales**:

- ⚠️ Devolución con retraso
- ✓ Devolución a tiempo - Sin multa
- 💰 Multa generada con monto
- Estado: (Pagada) o (Pendiente)

**Formulario**: `QueryReturnsForm`

### **Integración con otros módulos**:

**Consulta de Socio (FindMemberForm)** - Sección "DEVOLUCIONES RECIENTES":

- Muestra las últimas 5 devoluciones del socio
- Indica si fueron a tiempo o con retraso
- Calcula días de retraso cuando aplica
- Formato compacto: Fecha | Libro | Estado

**Pago de Multas (PayFineForm)**:

- Acceso directo a multas pendientes del socio
- Selección de multa a pagar
- Confirmación y procesamiento de pago
- Actualización de estado: Pendiente → Pagada

---

## 🔧 **MÓDULO: CONFIGURACIÓN DEL SISTEMA**

**Ubicación**: `ConfigController.java`  
**Función**: Administración de parámetros globales del sistema

```
════════════════════════════════════════
       CONFIGURACIÓN DEL SISTEMA
════════════════════════════════════════

1. Días estándar de préstamo
2. Multa diaria
3. Cupo máximo por socio
4. Tope de reservas activas por socio
5. Beneficios por categoría
6. Volver
```

### **Funcionalidades Detalladas**:

#### **1. Días estándar de préstamo**

**Descripción**: Configura la cantidad de días base para calcular el vencimiento de préstamos.

**Proceso**:

1. Muestra el valor actual (ej: "14 días")
2. Solicita nuevo valor numérico
3. Valida que sea mayor a 0
4. Aplica el cambio inmediatamente
5. Confirma actualización

**Validaciones**:

- ✅ Debe ser un número entero
- ✅ Debe ser mayor a 0 (estrictamente positivo)
- ⚠️ Error si es 0 o negativo

**Ejemplo de uso**:

```
Días Estándar de Préstamo
----------------------------------------
Valor actual: 14 días
Ingrese nuevo valor: : 21
✓ Actualizado
```

**Impacto**: Los nuevos préstamos usarán el nuevo valor para calcular fecha de vencimiento.

---

#### **2. Multa diaria**

**Descripción**: Configura el monto en pesos que se cobra por cada día de retraso.

**Proceso**:

1. Muestra el valor actual en formato moneda (ej: "$5.00")
2. Solicita nuevo valor decimal
3. Valida que no sea negativo (puede ser 0)
4. Aplica el cambio inmediatamente
5. Confirma actualización

**Validaciones**:

- ✅ Debe ser un número decimal válido
- ✅ Debe ser mayor o igual a 0
- ⚠️ Error si es negativo
- ⚠️ Error si no es un número válido

**Ejemplo de uso**:

```
Multa Diaria
----------------------------------------
Valor actual: $5.00
Ingrese nuevo valor: : 8.50
✓ Actualizado
```

**Impacto**: Las nuevas multas (devoluciones con retraso) usarán el nuevo monto base.

---

#### **3. Cupo máximo por socio**

**Descripción**: Configura la cantidad máxima de préstamos simultáneos que puede tener un socio.

**Proceso**:

1. Muestra el valor actual (ej: "3 préstamos")
2. Solicita nuevo valor numérico
3. Valida que no sea negativo (puede ser 0)
4. Aplica el cambio inmediatamente
5. Confirma actualización

**Validaciones**:

- ✅ Debe ser un número entero
- ✅ Debe ser mayor o igual a 0
- ⚠️ Error si es negativo

**Ejemplo de uso**:

```
Cupo Máximo por Socio
----------------------------------------
Valor actual: 3 préstamos
Ingrese nuevo valor: : 5
✓ Actualizado
```

**Impacto**: Los nuevos préstamos validarán contra el nuevo límite.

**Nota**: Este valor es el límite base. Los beneficios por tipo de socio pueden modificarlo.

---

#### **4. Tope de reservas activas por socio**

**Descripción**: Configura la cantidad máxima de reservas activas que puede tener un socio simultáneamente.

**Proceso**:

1. Muestra el valor actual (ej: "5 reservas")
2. Solicita nuevo valor numérico
3. Valida que no sea negativo (puede ser 0)
4. Aplica el cambio inmediatamente
5. Confirma actualización

**Validaciones**:

- ✅ Debe ser un número entero
- ✅ Debe ser mayor o igual a 0
- ⚠️ Error si es negativo

**Ejemplo de uso**:

```
Tope de Reservas Activas por Socio
----------------------------------------
Valor actual: 5 reservas
Ingrese nuevo valor: : 10
✓ Actualizado
```

**Impacto**: Las nuevas reservas validarán contra el nuevo límite.

**Nota**: Esta funcionalidad está preparada para el módulo de Reservas (TP4 futuro).

---

#### **5. Beneficios por categoría**

**Descripción**: Muestra información de los beneficios asociados a cada tipo de socio.

**Características**:

- 📖 **Solo lectura** - No permite modificaciones
- ℹ️ Los valores están definidos en el código (`MemberType` enum)
- 📊 Muestra información completa y actualizada de todos los tipos

**Información mostrada**:

```
📋 BENEFICIOS ACTUALES POR TIPO DE SOCIO:

🔹 Estándar:
   • Sin beneficios adicionales
   • Descuento en multas: 0%
   • Días extra de préstamo: 0

🔹 Estudiante:
   • Descuento en multas: 50%
   • Días extra de préstamo: 0

🔹 Jubilado:
   • Descuento en multas: 0%
   • Días extra de préstamo: 3

(i) ℹ️  Los beneficios están definidos en el código (enum MemberType)
(i) ℹ️  No son modificables desde la configuración del sistema
```

**Tipos de beneficios**:

| Tipo de Socio            | Descuento en Multas | Días Extra de Préstamo |
| ------------------------ | ------------------- | ---------------------- |
| **STANDARD** (Estándar)  | 0%                  | 0 días                 |
| **STUDENT** (Estudiante) | 50%                 | 0 días                 |
| **RETIRED** (Jubilado)   | 0%                  | 3 días                 |

**Justificación técnica**:

- Los beneficios son valores de negocio críticos
- Están integrados en múltiples puntos del sistema
- Modificarlos requiere cambios en el código y pruebas exhaustivas
- La UI muestra claramente que son "definidos en código"

---

### **Integración con Otros Módulos**:

#### **Módulo de Préstamos**:

- Usa `loanDays` para calcular fecha de vencimiento
- Usa `maxLoansPerMember` como base para validar cupo
- Aplica días extra según tipo de socio (RETIRED: +3 días)

**Fórmula de vencimiento**:

```
Fecha Vencimiento = Fecha Préstamo + loanDays + diasExtraSegunTipoSocio
```

#### **Módulo de Devoluciones**:

- Usa `loanDays` para determinar si hay retraso
- Usa `finePerDay` como monto base para calcular multas
- Aplica descuentos según tipo de socio (STUDENT/RETIRED: 50%)

**Fórmula de multa**:

```
Días Retraso = Fecha Devolución - Fecha Vencimiento
Multa Base = días retraso × finePerDay
Descuento = multa base × porcentajeDescuentoSegunTipoSocio
Multa Final = multa base - descuento
```

---

### **Casos de Uso para Testing**:

#### **Test 1: Modificación exitosa de parámetros**

```
ENTRADA:
1. Menú Principal → Opción 5 (Configuración)
2. Opción 1 → Ingresar: 21
3. Opción 2 → Ingresar: 8.50
4. Opción 3 → Ingresar: 5
5. Opción 4 → Ingresar: 10

RESULTADO ESPERADO:
✓ Días de préstamo: 14 → 21
✓ Multa diaria: $5.00 → $8.50
✓ Cupo máximo: 3 → 5
✓ Tope de reservas: 5 → 10
✓ Mensaje de confirmación en cada cambio
```

#### **Test 2: Validación de valores inválidos**

```
ENTRADA:
1. Opción 1 → Ingresar: -5
2. Opción 1 → Ingresar: 0
3. Opción 2 → Ingresar: -10.50
4. Opción 3 → Ingresar: abc

RESULTADO ESPERADO:
✗ Error: "Los días de préstamo deben ser mayor a 0"
✗ Error: "Los días de préstamo deben ser mayor a 0"
✗ Error: "La multa diaria no puede ser negativa"
✗ Error: "Valor inválido. Debe ser un número."
```

#### **Test 3: Impacto en nuevo préstamo**

```
CONFIGURACIÓN PREVIA:
- Cambiar días de préstamo: 14 → 30

ENTRADA:
1. Crear nuevo préstamo para socio STANDARD
2. Fecha préstamo: 13/10/2025

RESULTADO ESPERADO:
✓ Fecha vencimiento: 12/11/2025 (30 días después)
✓ Préstamo usa nuevo valor configurado
```

#### **Test 4: Impacto en devolución con retraso**

```
CONFIGURACIÓN PREVIA:
- Cambiar multa diaria: $5.00 → $10.00

ENTRADA:
1. Devolver ejemplar con 3 días de retraso
2. Socio tipo: STANDARD (sin descuento)

RESULTADO ESPERADO:
✓ Multa calculada: 3 días × $10.00 = $30.00
✓ Multa usa nuevo valor configurado
```

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
- `FindMemberForm` - Búsqueda por nombre/ID con perfil completo + últimas 5 devoluciones
- `PayFineForm` - Gestión de pagos de multas con integración a FineRepository

#### **🔄 Formularios de Devoluciones** (Sistema Avanzado):

**`RegisterReturnForm`** - **Registro de devolución**:

- 🔍 Búsqueda por código de ejemplar
- ✅ Validación de préstamo activo
- 📊 Preview completo con cálculo de multa
- 💰 Aplicación de descuentos por tipo de socio
- ⚡ Procesamiento con actualización de estados

**`QueryReturnsForm`** - **Consulta de historial**:

- 📋 7 opciones de filtrado diferentes
- 🔍 Búsquedas por socio, libro, fecha
- 💰 Filtros por existencia de multa
- 📊 Display detallado con indicadores visuales
- 💵 Muestra monto y estado de multas

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

#### **🔄 Devoluciones**:

- ✅ **Código de ejemplar válido** y existente
- ✅ **Préstamo activo** asociado al ejemplar
- ✅ **Estado LOANED** del ejemplar
- ✅ **Cálculo correcto** de días de retraso
- ✅ **Aplicación de descuentos** según tipo de socio (50% STUDENT/RETIRED)
- ✅ **Generación automática** de multa cuando hay retraso
- ✅ **Actualización de estados**: Loan (ACTIVE→RETURNED), Copy (LOANED→AVAILABLE)
- ✅ **Validación de fechas** en filtros de búsqueda (formato DD/MM/YYYY)

### **Manejo de Errores**:

- ⚠️ **Captura de excepciones** en todos los niveles
- 💬 **Mensajes descriptivos** y orientativos
- 🔄 **Recuperación automática** sin pérdida de contexto
- ⚙️ **Validaciones preventivas** antes de operaciones críticas
- 🚫 **Prevención de estados inconsistentes**

El sistema proporciona una experiencia completa y profesional para la gestión de biblioteca, con todas las funcionalidades implementadas siguiendo principios de Clean Architecture y patrones de diseño establecidos.
