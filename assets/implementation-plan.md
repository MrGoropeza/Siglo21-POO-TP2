# Plan de Implementación - Sistema de Gestión de Biblioteca

## 🎯 **NUEVAS OPCIONES DE MENÚ**

Según el plan replanteado, el menú principal sería:

```
============================================================
              SISTEMA DE GESTIÓN DE BIBLIOTECA
============================================================

1. Gestión de Libros
2. Gestión de Socios
3. Gestión de Préstamos
4. Gestión de Devoluciones
5. Configuración del Sistema
6. Reportes y Estadísticas
7. Salir
```

## 📁 **ARCHIVOS A CREAR**

### **1. ENTIDADES DEL DOMINIO (Domain Layer)** ✅ **C**📊 Estado inicial del socio: 1 préstamo activo

✅ Préstamo creado exitosamente
📊 Estado final del socio: 2 préstamos activos
🎉 ¡ÉXITO! Sincronización correcta

```

**🔄 MÓDULO DE DEVOLUCIONES:** ✅ **COMPLETADO**

- [x] **Entidades y estructura**: Fine con estado de pago, fechas y relación con Member
- [x] **Casos de uso implementados**:
  - RegisterReturnUseCase con cálculo automático de multas
  - QueryReturnsUseCase con 6 tipos de filtros diferentes
- [x] **Formularios completos**:
  - RegisterReturnForm con preview de multa antes de confirmar
  - QueryReturnsForm con 7 opciones de consulta y display mejorado
- [x] **Integración con MainController**: Opción "4. Gestión de Devoluciones" completamente funcional
- [x] **Repositorios**: FineRepository y SystemParametersRepository
- [x] **Datos dummy**: FineDummyData con 4 multas de prueba (pagadas y pendientes)
- [x] **Características avanzadas**:
  - Cálculo de multas: $5/día × días de retraso × descuento por tipo de socio
  - Descuentos: 50% estudiantes, 50% jubilados
  - Preview completo antes de confirmar devolución
  - Filtros de búsqueda: todos, por socio, por libro, por fecha, con/sin multa
- [x] **Actualización de estados**: LoanState.ACTIVE → LoanState.RETURNED, CopyState.LOANED → CopyState.AVAILABLE
- [x] **Integración con otros módulos**:
  - PayFineUseCase actualizado para pagos reales
  - QueryMemberUseCase muestra multas pendientes y últimas 5 devoluciones
  - FindMemberForm incluye sección "DEVOLUCIONES RECIENTES"
- [x] **✅ MÓDULO 100% COMPLETADO Y VERIFICADO**

**🔧 FUNCIONALIDAD IMPLEMENTADA Y VERIFICADA:**

**Módulo de Devoluciones 100% funcional:**
- ✅ Registrar devoluciones con cálculo automático de multas por retraso
- ✅ Preview de multa antes de confirmar (muestra días de retraso, monto, descuentos)
- ✅ Aplicación de descuentos según tipo de socio (50% estudiantes/jubilados)
- ✅ Actualización automática de estados (Loan: ACTIVE→RETURNED, Copy: LOANED→AVAILABLE)
- ✅ Generación automática de multas cuando hay retraso
- ✅ Consultar historial con 7 opciones de filtrado
- ✅ Display detallado: fechas, días de retraso, monto de multa, estado de pago

**Correcciones críticas aplicadas:**
- ✅ FineRepository.save() ahora preserva el estado de pago cuando genera ID
- ✅ QueryReturnsForm eliminó doble "Presione Enter para continuar"
- ✅ FineDummyData incluye multa para préstamo devuelto de Roberto Silva ($17.50 pagada)
- ✅ formatReturn() muestra monto y estado de multa (💰 $XX.XX Pagada/Pendiente)

**Verificación completa exitosa:**
```

📋 Devolución registrada:
Socio: Roberto Silva (3001)
Libro: Ficciones
Ejemplar: COPY-004-1
Fecha devolución: 07/10/2025
⚠️ Devolución con 7 día(s) de retraso
💰 Multa generada: $17.50 (Pagada)
🎉 ¡ÉXITO! Multa calculada, generada y mostrada correctamente

```TADO**

```

src/biblioteca/domain/entities/
├── Author.java // ✅ Autor
├── Book.java // ✅ Libro
├── Category.java // ✅ Categoría
├── Copy.java // ✅ Ejemplar
├── Publisher.java // ✅ Editorial
├── Member.java // ✅ Socio
├── Loan.java // ✅ Préstamo
├── Fine.java // ✅ Multa
├── Reservation.java // ✅ Reserva
├── Notification.java // ✅ Notificación
└── SystemParameters.java // ✅ Parámetros del Sistema

src/biblioteca/domain/enums/
├── CopyOrigin.java // ✅ Origen del ejemplar (PURCHASE, DONATION)
├── CopyState.java // ✅ Estado del ejemplar (AVAILABLE, LOANED, RESERVED)
├── MemberType.java // ✅ Tipo de socio (STANDARD, STUDENT, RETIRED)
├── LoanState.java // ✅ Estado préstamo (ACTIVE, RETURNED, OVERDUE)
├── ReservationState.java // ✅ Estado reserva (ACTIVE, CANCELLED, FULFILLED)
└── NotificationChannel.java // ✅ Canal (CONSOLE)

```

### **2. CASOS DE USO (Application Layer)**

```

src/biblioteca/application/
├── socios/
│ ├── registrar/
│ │ ├── RegisterMemberRequest.java
│ │ ├── RegisterMemberResult.java
│ │ └── RegisterMemberUseCase.java
│ ├── modificar/
│ │ ├── ModifyMemberRequest.java
│ │ ├── ModifyMemberResult.java
│ │ └── ModifyMemberUseCase.java
│ ├── consultar/
│ │ ├── QueryMemberRequest.java
│ │ ├── QueryMemberResult.java
│ │ └── QueryMemberUseCase.java
│ └── pagar_multa/
│ ├── PayFineRequest.java
│ ├── PayFineResult.java
│ └── PayFineUseCase.java
├── prestamos/
│ ├── crear/
│ │ ├── CreateLoanRequest.java
│ │ ├── CreateLoanResult.java
│ │ └── CreateLoanUseCase.java
│ └── carrito/
│ ├── LoanCart.java
│ ├── LoanCartItem.java
│ ├── AddToCartRequest.java
│ ├── AddToCartResult.java
│ ├── AddToCartUseCase.java
│ ├── RemoveFromCartRequest.java
│ ├── RemoveFromCartResult.java
│ ├── RemoveFromCartUseCase.java
│ ├── ConfirmCartRequest.java
│ ├── ConfirmCartResult.java
│ └── ConfirmCartUseCase.java
├── devoluciones/
│ ├── registrar/
│ │ ├── RegisterReturnRequest.java
│ │ ├── RegisterReturnResult.java
│ │ └── RegisterReturnUseCase.java
├── reservas/
│ ├── crear/
│ │ ├── CreateReservationRequest.java
│ │ ├── CreateReservationResult.java
│ │ └── CreateReservationUseCase.java
│ ├── cancelar/
│ │ ├── CancelReservationRequest.java
│ │ ├── CancelReservationResult.java
│ │ └── CancelReservationUseCase.java
│ └── atender/
│ ├── AttendReservationRequest.java
│ ├── AttendReservationResult.java
│ └── AttendReservationUseCase.java
├── consultas/
│ ├── disponibilidad/
│ │ ├── CheckAvailabilityRequest.java
│ │ ├── CheckAvailabilityResult.java
│ │ └── CheckAvailabilityUseCase.java
│ ├── historial_socio/
│ │ ├── MemberHistoryRequest.java
│ │ ├── MemberHistoryResult.java
│ │ └── MemberHistoryUseCase.java
│ ├── prestamos_activos/
│ │ ├── ActiveLoansRequest.java
│ │ ├── ActiveLoansResult.java
│ │ └── ActiveLoansUseCase.java
│ ├── prestamos_vencidos/
│ │ ├── OverdueLoansRequest.java
│ │ ├── OverdueLoansResult.java
│ │ └── OverdueLoansUseCase.java
│ └── reservas_por_libro/
│ ├── ReservationsByBookRequest.java
│ ├── ReservationsByBookResult.java
│ └── ReservationsByBookUseCase.java
├── reportes/
│ ├── libros_mas_prestados/
│ │ ├── MostLoanedBooksRequest.java
│ │ ├── MostLoanedBooksResult.java
│ │ └── MostLoanedBooksUseCase.java
│ ├── socios_morosos/
│ │ ├── DelayedMembersRequest.java
│ │ ├── DelayedMembersResult.java
│ │ └── DelayedMembersUseCase.java
│ └── recaudacion_multas/
│ ├── FineRevenueRequest.java
│ ├── FineRevenueResult.java
│ └── FineRevenueUseCase.java
└── configuracion/
├── actualizar_parametros/
│ ├── UpdateParametersRequest.java
│ ├── UpdateParametersResult.java
│ └── UpdateParametersUseCase.java

```

### **3. REPOSITORIOS (Data Layer)**

```

src/biblioteca/data/database/
├── MemberRepository.java
├── LoanRepository.java
├── FineRepository.java
├── ReservationRepository.java
├── NotificationRepository.java
└── SystemParametersRepository.java

```

### **3.1. DATOS DUMMY (Data Layer)**

```

src/biblioteca/data/dummy/
├── AuthorDummyData.java // ✅ Ya existe
├── BookDummyData.java // ✅ Ya existe
├── CategoryDummyData.java // ✅ Ya existe
├── PublisherDummyData.java // ✅ Ya existe
├── CopyDummyData.java // ✅ Datos dummy de ejemplares
├── MemberDummyData.java // ✅ Datos dummy de socios
├── LoanDummyData.java // ✅ Datos dummy de préstamos
├── FineDummyData.java // 🆕 Datos dummy de multas
├── ReservationDummyData.java // 🆕 Datos dummy de reservas
└── SystemParametersDummyData.java // 🆕 Datos dummy de parámetros del sistema

```

### **4. FORMULARIOS (Console Layer)**

```

src/biblioteca/console/forms/
├── member/
│ ├── RegisterMemberForm.java
│ ├── ModifyMemberForm.java
│ ├── QueryMemberForm.java
│ ├── FindMemberForm.java
│ └── PayFineForm.java
├── loan/
│ ├── LoanCartForm.java
│ ├── AddToCartForm.java
│ └── ConfirmLoanForm.java
├── return/
│ └── RegisterReturnForm.java
├── reservation/
│ ├── CreateReservationForm.java
│ ├── CancelReservationForm.java
│ └── AttendReservationForm.java
├── query/
│ ├── CheckAvailabilityForm.java
│ ├── MemberHistoryForm.java
│ ├── ActiveLoansForm.java
│ ├── OverdueLoansForm.java
│ └── ReservationsByBookForm.java
├── report/
│ ├── MostLoanedBooksForm.java
│ ├── DelayedMembersForm.java
│ └── FineRevenueForm.java
└── config/
└── UpdateParametersForm.java

```

### **5. CONTROLADORES (Console Layer)**

```

src/biblioteca/console/controllers/
├── MemberController.java // Gestión de socios
├── LoanController.java // Gestión de préstamos
├── ReturnController.java // Gestión de devoluciones
├── ReservationController.java // Gestión de reservas
├── QueryController.java // Consultas
├── ReportController.java // Reportes
└── ConfigController.java // Configuración

```

### **6. SERVICIOS ADICIONALES**

```

src/biblioteca/domain/services/
├── LoanCalculatorService.java // Cálculos de préstamos y multas
├── ReservationQueueService.java // Gestión de colas de reservas
├── NotificationService.java // Envío de notificaciones
└── ValidationService.java // Validaciones de negocio

```

### **7. ACTUALIZACIONES A ARCHIVOS EXISTENTES**

```

src/biblioteca/console/controllers/MainController.java // ← ACTUALIZAR menú principal
src/biblioteca/console/ioc/DependencyContainer.java // ← ACTUALIZAR dependencias

````

## 🎯 **PRINCIPIOS DE DISEÑO ORIENTADO A OBJETOS**

### **📋 REGLAS OBLIGATORIAS DE DISEÑO:**

**CRÍTICO**: El diseño debe seguir estrictamente los principios de OOP y el diagrama `class-diagram.mermaid` original.

#### **✅ REFERENCIAS DIRECTAS A OBJETOS:**

```java
// ✅ CORRECTO - OOP
private Member member;       // Referencia al objeto Member
private Copy copy;          // Referencia al objeto Copy
private Book book;          // Referencia al objeto Book

// ❌ INCORRECTO - Anti-patrón
private String memberId;    // Solo ID, viola encapsulación
private String copyCode;    // Solo código, rompe relaciones OOP
private String bookId;      // Solo ID, pierde información del objeto
````

#### **✅ SEGUIR DIAGRAMA ORIGINAL:**

- El diseño debe reflejar exactamente las relaciones del `class-diagram.mermaid`
- `Prestamo` → `Socio` y `Ejemplar` (referencias directas)
- `Ejemplar` → `Libro` (referencia directa)
- `Multa` → `Socio` (referencia directa)

#### **✅ LIMPIEZA DEL PROYECTO:**

```bash
# Eliminar archivos .class del proyecto
find src -name "*.class" -type f -delete
```

### **❌ ANTI-PATRONES A EVITAR:**

1. **IDs como referencias principales**: Usar String memberId en lugar de Member member
2. **Strings donde van objetos**: Rompe encapsulación y navegación
3. **Archivos .class en repositorio**: Contamina el código fuente
4. **Violación de relaciones**: No seguir el diagrama de clases original

### **🛠️ VIOLACIONES IDENTIFICADAS A CORREGIR:**

#### **Loan.java** ❌ **CRÍTICO**

```java
// ACTUAL (Incorrecto)
private String memberId;     // ❌ Debe ser Member member
private String copyCode;     // ❌ Debe ser Copy copy

// DEBE SER (Correcto OOP)
private Member member;       // ✅ Referencia directa
private Copy copy;          // ✅ Referencia directa
```

#### **Fine.java** ❌ **CRÍTICO**

```java
// ACTUAL (Incorrecto)
private String memberId;     // ❌ Debe ser Member member

// DEBE SER (Correcto OOP)
private Member member;       // ✅ Referencia directa
```

#### **Reservation.java** ❌ **CRÍTICO**

```java
// ACTUAL (Incorrecto)
private String memberId;     // ❌ Debe ser Member member
private String bookId;       // ❌ Debe ser Book book o List<Book>

// DEBE SER (Correcto OOP)
private Member member;       // ✅ Referencia directa
private Book book;          // ✅ Referencia directa (o List<Book>)
```

#### **Notification.java** ❌ **CRÍTICO**

```java
// ACTUAL (Incorrecto)
private String memberId;     // ❌ Debe ser Member member

// DEBE SER (Correcto OOP)
private Member member;       // ✅ Referencia directa
```

## 🛠️ **ESTÁNDARES DE IMPLEMENTACIÓN - UTILIDADES DE CONSOLA**

### **📋 REGLAS OBLIGATORIAS PARA FORMS:**

Todos los nuevos formularios **DEBEN** utilizar las utilidades estándar ya implementadas:

#### **InputHelper.java - Manejo de Entrada:**

```java
// ✅ USAR SIEMPRE estas utilidades
InputHelper.leerTexto(mensaje)                    // Entrada de texto
InputHelper.leerTextoObligatorio(mensaje)         // Texto obligatorio
InputHelper.leerEntero(mensaje)                   // Números enteros
InputHelper.leerEnteroEnRango(mensaje, min, max)  // Enteros en rango
InputHelper.seleccionar(lista, mensaje)           // Selección única de lista
InputHelper.seleccionarMultiple(lista, mensaje)   // Selección múltiple
InputHelper.confirmar(mensaje)                    // Confirmación S/N
InputHelper.pausar(mensaje)                       // Pausa para continuar

// ❌ NO recrear lógica manual de menús o selección
```

#### **DisplayHelper.java - Presentación:**

```java
// ✅ USAR SIEMPRE estas utilidades
DisplayHelper.renderTitle(titulo)                 // Títulos principales
DisplayHelper.renderSubtitle(subtitulo)          // Subtítulos con decoración
DisplayHelper.printSuccess(mensaje)              // Mensajes de éxito
DisplayHelper.printErrorMessage(mensaje)         // Mensajes de error
DisplayHelper.printWarning(mensaje)              // Mensajes de advertencia
DisplayHelper.printInfo(mensaje)                 // Mensajes informativos
DisplayHelper.renderNumberedList(lista, titulo)  // Listas numeradas
DisplayHelper.renderBulletList(lista, titulo)    // Listas con viñetas
DisplayHelper.renderTable(headers, rows)         // Tablas formateadas
DisplayHelper.renderMessageContainer(mensaje)    // Mensajes en cajas

// ❌ NO usar System.out.println directamente para UI
```

### **✅ EJEMPLOS DE USO CORRECTO:**

#### **Menús de Opciones:**

```java
// ✅ CORRECTO - Usar InputHelper.seleccionar()
List<String> opciones = List.of("Opción 1", "Opción 2", "Opción 3");
String seleccion = InputHelper.seleccionar(opciones, "Seleccione una opción:");

// ❌ INCORRECTO - Lógica manual
System.out.println("1. Opción 1");
System.out.println("2. Opción 2");
int opcion = InputHelper.leerEnteroEnRango("Seleccione", 1, 2);
```

#### **Selección de Entidades:**

```java
// ✅ CORRECTO - Selección directa de objetos
List<Member> miembros = repository.findAll();
Member seleccionado = InputHelper.seleccionar(miembros, "Seleccione un socio:");

// ❌ INCORRECTO - Mostrar manualmente y seleccionar por índice
for (int i = 0; i < miembros.size(); i++) {
    System.out.println((i+1) + ". " + miembros.get(i));
}
```

### **📝 CHECKLIST PARA NUEVOS FORMS:**

- [ ] ✅ Usa `DisplayHelper.renderTitle()` para título principal
- [ ] ✅ Usa `DisplayHelper.renderSubtitle()` para secciones
- [ ] ✅ Usa `InputHelper.seleccionar()` para opciones/entidades
- [ ] ✅ Usa `InputHelper.confirmar()` para confirmaciones S/N
- [ ] ✅ Usa `DisplayHelper.printSuccess/Error/Warning/Info()` para mensajes
- [ ] ✅ Usa `InputHelper.pausar()` al final si es necesario
- [ ] ❌ NO usa `System.out.println()` para UI
- [ ] ❌ NO recrea lógica de menús manualmente
- [ ] ❌ NO implementa selección de listas manualmente

### **🎯 BENEFICIOS DE SEGUIR ESTOS ESTÁNDARES:**

1. **Consistencia visual** en toda la aplicación
2. **Código más limpio** y mantenible
3. **Reutilización** de componentes probados
4. **UX uniforme** para el usuario
5. **Menos bugs** por lógica duplicada

## 🎯 **PLAN REPLANTEADO - ACTUALIZADO 13/10/2025**

### **📦 DISTRIBUCIÓN POR TRABAJOS PRÁCTICOS:**

**TP2 (ENTREGADO):**

- ✅ Módulo LIBROS (completo)
- ✅ Módulo SOCIOS (completo)
- ✅ Módulo PRÉSTAMOS (completo)

**TP3 (PLANEADO): DEVOLUCIONES + CONFIGURACIÓN**

- ✅ Módulo DEVOLUCIONES (completo)
- 🆕 Módulo CONFIGURACIÓN (a implementar)

**TP4 (PLANEADO): REPORTES**

- 🆕 Módulo REPORTES completo (a implementar)

### **❌ DESCARTADO DEL PLAN ORIGINAL:**

- ❌ **RESERVAS**: No prioritario, funcionalidad compleja con poco valor educativo
- ❌ **CONSULTAS como módulo separado**: Ya implementadas orgánicamente en cada módulo
  - Libros: Búsqueda y estadísticas
  - Socios: Consulta completa de información
  - Préstamos: Ver préstamos actuales y estadísticas
  - Devoluciones: Historial con 7 tipos de filtros

**Total de archivos a crear:** ~45 archivos (Config: ~15, Reportes: ~30)  
**Controladores nuevos:** 2 controladores (ConfigController, ReportController)

## 📋 **PROGRESO DE IMPLEMENTACIÓN**

### ✅ **COMPLETADO**

**🏗️ ENTIDADES DEL DOMINIO:**

- [x] **Todas las entidades principales** (Author, Book, Category, Copy, Publisher, Member, Loan, Fine, Reservation, Notification, SystemParameters)
- [x] **Todos los enums** (CopyOrigin, CopyState, MemberType, LoanState, ReservationState, NotificationChannel)
- [x] **Estructura del dominio reorganizada** (entities/ y enums/ separados correctamente)
- [x] **Imports y packages actualizados** para la nueva estructura

**📚 MÓDULO DE LIBROS:**

- [x] BookController con patrón de formularios
- [x] BookController refactorizado completamente para usar forms
- [x] Use Cases de libros (RegisterBook, AddStock, ModifyBook, DeleteBook)
- [x] Forms de libros (RegisterBookForm, AddStockForm, ModifyBookForm, DeleteBookForm, FindBookForm)
- [x] DependencyContainer actualizado para libros
- [x] Arquitectura Clean Architecture establecida
- [x] Imports optimizados en BookController
- [x] **Módulo de Libros 100% completado**

**👥 MÓDULO DE SOCIOS:**

- [x] Entidades Member y MemberType con beneficios por tipo de socio
- [x] Use Cases de socios (RegisterMember, ModifyMember, QueryMember, PayFine)
- [x] MemberRepository con operaciones CRUD y búsqueda
- [x] MemberDummyData con datos de prueba
- [x] Forms de socios (RegisterMemberForm, ModifyMemberForm, QueryMemberForm, FindMemberForm, PayFineForm)
- [x] MemberController con menú de 6 opciones
- [x] DependencyContainer actualizado con componentes de socios
- [x] MainController integrado con opción "2. Gestión de Socios"
- [x] **Módulo de Socios 100% completado**

**📋 MÓDULO DE PRÉSTAMOS:** ✅ **COMPLETADO**

- [x] **Entidades y estructura básica**: Loan, LoanState, CreateLoanUseCase, LoanRepository, LoanController
- [x] **Funcionalidad del carrito**: LoanCartForm, funcionalidad completa de agregar/quitar ejemplares
- [x] **Integración con MainController**: Opción "3. Gestión de Préstamos" completamente funcional
- [x] **Datos dummy**: LoanDummyData con préstamos de prueba
- [x] **Validaciones de negocio**: Estado del socio, límite de préstamos, multas pendientes
- [x] **Actualización de estados**: CopyState.AVAILABLE → CopyState.LOANED automático
- [x] **Sincronización de datos**: Préstamos nuevos aparecen correctamente en todas las consultas
- [x] **✅ MÓDULO 100% COMPLETADO Y VERIFICADO**

**🔧 FUNCIONALIDAD IMPLEMENTADA Y VERIFICADA:**

**Módulo de Préstamos 100% funcional:**

- ✅ Crear préstamos usando carrito de compras
- ✅ Validación de socios (estado ACTIVO, sin multas, límite de préstamos)
- ✅ Actualización automática del estado del ejemplar (AVAILABLE → LOANED)
- ✅ Integración completa con MainController

**Correcciones críticas aplicadas:**

- ✅ CreateLoanUseCase corregido para usar CopyRepository.update()
- ✅ Lógica añadida para cambiar CopyState.AVAILABLE → CopyState.LOANED
- ✅ DependencyContainer actualizado con CopyRepository como dependencia

**Verificación completa exitosa:**

```
📖 Estado inicial: COPY-005-2 (AVAILABLE)
🛒 Préstamo creado: 1 ejemplares prestados hasta 2025-10-06
📖 Estado final: COPY-005-2 (LOANED)
🎉 FUNCIONALIDAD VERIFICADA: El estado se actualiza correctamente
```

**🔧 PROBLEMA ADICIONAL RESUELTO - SINCRONIZACIÓN DE DATOS:**

**Problema identificado**: Los préstamos recién creados no aparecían en las consultas de socio debido a inconsistencia en la generación de IDs.

**Causa raíz**:

- `CreateLoanUseCase.generateLoanId()` generaba IDs como `"LOAN-timestamp-random"`
- `LoanRepository.save()` sobrescribía con IDs como `"LOAN0006"`
- Esto causaba problemas de sincronización entre casos de uso

**Solución aplicada**:

- ✅ Eliminado `generateLoanId()` de CreateLoanUseCase
- ✅ LoanRepository ahora genera IDs de forma consistente
- ✅ Préstamos nuevos se sincronizan correctamente con todas las consultas

**Verificación exitosa**:

```
📊 Estado inicial del socio: 1 préstamo activo
✅ Préstamo creado exitosamente
📊 Estado final del socio: 2 préstamos activos
🎉 ¡ÉXITO! Sincronización correcta
```

### 🆕 **MÓDULOS PENDIENTES SEGÚN NUEVO PLAN**

#### **🔧 CONFIGURACIÓN (Prioridad 1 - TP3)**

**Archivos a crear:**

```
src/biblioteca/application/configuracion/
├── ver/
│   ├── ViewConfigRequest.java
│   ├── ViewConfigResult.java
│   └── ViewConfigUseCase.java
└── actualizar/
    ├── UpdateConfigRequest.java
    ├── UpdateConfigResult.java
    └── UpdateConfigUseCase.java

src/biblioteca/console/configuracion/
├── ConfigController.java
├── ViewConfigForm.java
└── UpdateConfigForm.java
```

**Funcionalidades:**

- Ver todos los parámetros actuales del sistema
- Modificar días de préstamo (base y extras por tipo de socio)
- Modificar límite de préstamos simultáneos por tipo de socio
- Modificar monto de multa por día de retraso
- Modificar descuentos en multas por tipo de socio
- Validación de valores (no permitir negativos, límites razonables)

**Estimación:** 2-3 horas, ~15 archivos

#### **📊 REPORTES (Prioridad 2 - TP4)**

**Archivos a crear:**

```
src/biblioteca/application/reportes/
├── libros_mas_prestados/
│   ├── MostLoanedBooksRequest.java
│   ├── MostLoanedBooksResult.java
│   └── MostLoanedBooksUseCase.java
├── socios_activos/
│   ├── ActiveMembersRequest.java
│   ├── ActiveMembersResult.java
│   └── ActiveMembersUseCase.java
├── analisis_multas/
│   ├── FineAnalysisRequest.java
│   ├── FineAnalysisResult.java
│   └── FineAnalysisUseCase.java
├── analisis_temporal/
│   ├── TemporalAnalysisRequest.java
│   ├── TemporalAnalysisResult.java
│   └── TemporalAnalysisUseCase.java
└── inventario/
    ├── InventoryReportRequest.java
    ├── InventoryReportResult.java
    └── InventoryReportUseCase.java

src/biblioteca/console/reportes/
├── ReportController.java
├── MostLoanedBooksForm.java
├── ActiveMembersForm.java
├── FineAnalysisForm.java
├── TemporalAnalysisForm.java
└── InventoryReportForm.java

src/biblioteca/console/utils/
└── ChartHelper.java  // Nueva utility para gráficos ASCII
```

**Funcionalidades:**

1. **Libros más prestados**

   - Top 10 libros con más préstamos
   - Gráfico de barras ASCII
   - Detalles: título, autor, cantidad de préstamos

2. **Socios más activos**

   - Ranking de socios por cantidad de préstamos
   - Clasificación por tipo de socio
   - Préstamos activos vs históricos

3. **Análisis de multas**

   - Total recaudado por período (mes/año)
   - Multas pendientes vs pagadas (cantidad y monto)
   - Top socios morosos (con multas sin pagar)
   - Proyección de recaudación

4. **Análisis temporal**

   - Préstamos por mes/semana/día
   - Tendencias de uso (gráfico de línea ASCII)
   - Comparativa entre períodos
   - Predicciones simples

5. **Reporte de inventario**
   - Ejemplares disponibles vs prestados por libro
   - Libros con bajo stock (< 2 ejemplares disponibles)
   - Tasa de rotación por libro
   - Sugerencias de compra basadas en demanda

**Estimación:** 3-4 horas, ~30 archivos

### 🆕 **SERVICIOS ADICIONALES PARA REPORTES**

```
src/biblioteca/domain/services/
├── ChartGeneratorService.java    // Generación de gráficos ASCII
└── StatisticsCalculatorService.java  // Cálculos estadísticos
```

### **✅ Todos los TODOs Críticos Completados:**

- [x] **PayFineUseCase**: ✅ FineRepository integrado - pagos reales de multas funcionando
- [x] **QueryMemberUseCase**: ✅ LoanRepository integrado - muestra préstamos activos reales
- [x] **QueryMemberUseCase**: ✅ FineRepository integrado - muestra multas reales y últimas 5 devoluciones
- [x] **FindMemberForm**: ✅ Sección "DEVOLUCIONES RECIENTES" agregada con indicadores visuales

**🔧 TODOs Pendientes Identificados:**

- [x] **FindMemberForm - Detalle de beneficios del socio**: ✅ **COMPLETADO**

  - **Problema resuelto**: Los beneficios ahora se muestran dinámicamente usando `SystemParametersRepository`
  - **Solución implementada**:
    - `FindMemberForm.java`: Nuevo método `displayMemberBenefits()` agregado
    - Muestra valores reales: días de préstamo (base + extra), límite de préstamos, descuentos en multas
    - Incluye ejemplo de cálculo: "$X.XX por día (en lugar de $Y.YY)" para tipos con descuento
  - **Archivos modificados**:
    - `FindMemberForm.java`: Integración con SystemParametersRepository
    - `DependencyContainer.java`: Actualizado constructor de FindMemberForm
  - **Estado**: ✅ **COMPLETADO**

- [x] **FineDummyData - Agregar escenario de prueba**: ✅ **COMPLETADO**
  - **Objetivo cumplido**: Facilitar testing de pago de multas con todos los tipos de socio
  - **Implementación**:
    - Fine 5: **ESTUDIANTE** - Ana Martínez (2001) → $25.00 (10 días × $5 × 50% descuento)
    - Fine 6: **JUBILADO** - Elena Morales (3002) → $15.00 (6 días × $5 × 50% descuento)
    - Fine 7: **ESTÁNDAR** - Pedro Sánchez (1004) → $40.00 (8 días × $5 × sin descuento)
  - **Características**: Todas las multas sin pagar (isPaid = false) para probar PayFineForm
  - **Display mejorado**: Mensaje en consola con resumen de escenarios de prueba
  - **Configuración**: SystemParametersRepository actualizado a $5.00/día (valor realista)
  - **Estado**: ✅ **COMPLETADO**

**✅ MEJORAS COMPLETADAS PARA MÓDULO SOCIOS:**

- [x] **Revisar QueryMemberForm vs FindMemberForm**:

  - ✅ **ANÁLISIS COMPLETADO**: SÍ eran redundantes después de revisión práctica
  - ✅ **SOLUCIÓN**: QueryMemberForm eliminado, funcionalidad consolidada en FindMemberForm
  - ✅ **FindMemberForm mejorado**: Ahora incluye resumen de actividad completo usando QueryMemberUseCase
  - ✅ **Menú simplificado**: MemberController reducido de 6 a 5 opciones (3. "Buscar y consultar socio")

- [x] **Mejorar PayFineForm**:

  - ✅ Implementada búsqueda de socio por ID o por nombre usando InputHelper.seleccionar()
  - ✅ Eliminado código duplicado de menús manuales
  - ✅ Mejorada experiencia de usuario con selección intuitiva

- [x] **Optimizar uso de InputHelper**:
  - ✅ **PayFineForm**: Refactorizado para usar InputHelper.seleccionar() en lugar de lógica manual
  - ✅ **FindMemberForm**: Optimizado para usar InputHelper.seleccionar() para opciones de búsqueda
  - ✅ **Código más limpio**: Eliminados métodos redundantes (displaySearchOptions, getSearchOption, selectFromMultipleResults)
  - ✅ **Consistencia**: Todas las forms ahora usan las utilidades estándar de InputHelper

#### **✅ PRÉSTAMOS (COMPLETADO)**

- [x] Crear entidades: Loan, LoanState
- [x] Crear use cases: CreateLoan, LoanCart (AddToCart, RemoveFromCart, ConfirmCart)
- [x] Crear forms: LoanCartForm, AddToCartForm, ConfirmLoanForm
- [x] Crear LoanController
- [x] Crear LoanRepository
- [x] Crear datos dummy: LoanDummyData
- [x] Actualizar DependencyContainer
- [x] **Actualizar MainController** - Agregar opción "3. Préstamos" al menú principal
- [x] **Completar TODO**: Integrar LoanRepository en QueryMemberUseCase para mostrar préstamos activos reales
- [x] **Corrección crítica**: Sincronización de datos entre repositorios
- [x] **Verificación completa**: Módulo probado y funcionando al 100%

#### **✅ DEVOLUCIONES (COMPLETADO)**

- [x] Crear entidades: Fine con estado de pago y fechas
- [x] Crear use cases: RegisterReturn con cálculo automático de multas
- [x] Crear use cases: QueryReturns con 6 tipos de filtros (todos, por socio, por libro, por fecha, con/sin multa)
- [x] Crear forms: RegisterReturnForm con preview de multa antes de confirmar
- [x] Crear forms: QueryReturnsForm con 7 opciones de consulta y display mejorado
- [x] Crear ReturnController con menú de 3 opciones
- [x] Crear FineRepository con operaciones CRUD completas
- [x] Crear SystemParametersRepository para configuración de multas
- [x] Crear datos dummy: FineDummyData con multas de prueba (4 multas)
- [x] Actualizar DependencyContainer con componentes de devoluciones
- [x] **Actualizar MainController** - Agregar opción "4. Gestión de Devoluciones" al menú principal
- [x] **Completar TODOs**:
  - [x] Integrar FineRepository en PayFineUseCase para pagos reales de multas
  - [x] Integrar FineRepository en QueryMemberUseCase para mostrar multas reales y últimas 5 devoluciones
  - [x] Mejorar FindMemberForm para mostrar sección "DEVOLUCIONES RECIENTES"
- [x] **Características avanzadas implementadas**:
  - [x] Cálculo automático de multas según días de retraso ($5/día base)
  - [x] Descuentos por tipo de socio (50% para estudiantes, 50% para jubilados)
  - [x] Preview de multa antes de confirmar devolución
  - [x] Historial de devoluciones con múltiples filtros
  - [x] Display de monto de multa en historial (Pagada/Pendiente)
  - [x] Indicadores visuales (⚠️ retraso, ✓ a tiempo, 💰 multa)
- [x] **Correcciones críticas aplicadas**:
  - [x] FineRepository.save() preserva estado de pago al generar ID
  - [x] QueryReturnsForm elimina doble "Presione Enter"
  - [x] FineDummyData incluye multa para préstamo devuelto de Roberto Silva
- [x] **Módulo 100% completado y verificado**

#### **🔧 CONFIGURACIÓN (Prioridad 1 - TP3)**

- [ ] Crear use cases: ViewConfig, UpdateConfig
- [ ] Crear forms: ViewConfigForm, UpdateConfigForm
- [ ] Crear ConfigController
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "5. Configuración del Sistema" al menú principal
- [ ] Validaciones de parámetros (valores positivos, límites razonables)
- [ ] Testing de modificación de parámetros en vivo

**Estado:** ⚡ SystemParametersRepository ya existe (50% completado)

#### **📊 REPORTES (Prioridad 2 - TP4)**

- [ ] Crear use cases: MostLoanedBooks, ActiveMembers, FineAnalysis, TemporalAnalysis, InventoryReport
- [ ] Crear forms: 5 forms de reportes
- [ ] Crear ReportController con menú de 6 opciones
- [ ] Crear ChartHelper para gráficos ASCII
- [ ] Crear servicios: ChartGeneratorService, StatisticsCalculatorService
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "6. Reportes y Estadísticas" al menú principal
- [ ] Implementar gráficos de barras ASCII
- [ ] Implementar gráficos de línea ASCII
- [ ] Testing de todos los reportes con datos dummy

#### **❌ DESCARTADOS DEL PLAN ORIGINAL:**

- ~~Reservas~~ - No prioritario, complejidad alta, poco valor educativo
- ~~Consultas como módulo separado~~ - Ya implementadas en cada módulo
- ~~NotificationService~~ - Fuera del alcance actual
- ~~ReservationQueueService~~ - No aplica sin módulo de reservas

#### **✅ INTEGRACIÓN DE TODOs PENDIENTES (COMPLETADO)**

**Esta fase se completó durante la implementación del módulo DEVOLUCIONES:**

- [x] **Integrar LoanRepository en QueryMemberUseCase**:

  - Archivo: `src/biblioteca/application/socios/consultar/QueryMemberUseCase.java`
  - ~~Línea 14: `// TODO: Add LoanRepository and FineRepository when implemented`~~ **ELIMINADO**
  - ~~Línea 34: `// TODO: Replace with real data from LoanRepository and FineRepository`~~ **ELIMINADO**
  - ✅ **Completado**: Muestra préstamos activos reales del socio
  - ✅ **Completado**: Muestra últimas 5 devoluciones ordenadas por fecha

- [x] **Integrar FineRepository en PayFineUseCase**:

  - Archivo: `src/biblioteca/application/socios/pagar_multa/PayFineUseCase.java`
  - ~~Línea 14: `// TODO: Add FineRepository when implemented`~~ **ELIMINADO**
  - ~~Línea 37: `// TODO: Implement fine payment logic when FineRepository is available`~~ **ELIMINADO**
  - ✅ **Completado**: Implementa pagos reales de multas
  - ✅ **Completado**: Actualiza estado de multa (Pendiente → Pagada)

- [x] **Integrar FineRepository en QueryMemberUseCase**:

  - Archivo: `src/biblioteca/application/socios/consultar/QueryMemberUseCase.java`
  - ✅ **Completado**: Muestra multas pendientes reales del socio
  - ✅ **Completado**: Calcula total de multas pendientes

- [x] **Mejorar FindMemberForm con devoluciones recientes**:
  - Archivo: `src/biblioteca/console/socios/FindMemberForm.java`
  - ✅ **Completado**: Nueva sección "DEVOLUCIONES RECIENTES"
  - ✅ **Completado**: Indicadores visuales (✓ a tiempo / ⚠️ con retraso)
  - ✅ **Completado**: Muestra días de retraso cuando aplica

**🎉 Resultado**: Todos los módulos ahora están completamente integrados. No quedan TODOs pendientes en el código base.

#### **FINALIZACIÓN**

- [x] Revisión parcial del MainController - 4 módulos implementados
- [x] Testing de módulos implementados - Libros, Socios, Préstamos, Devoluciones funcionando al 100%
- [x] ✅ **Todos los TODOs críticos completados** - No quedan integraciones pendientes
- [ ] **TP3**: Implementar módulo de Configuración (2-3 horas)
- [ ] **TP4**: Implementar módulo de Reportes (3-4 horas)
- [ ] Testing integral de todos los módulos
- [ ] Documentación final del sistema (manuales de usuario)

## 🎯 **ESTRATEGIA DE DESARROLLO - PLAN ACTUALIZADO**

### **Metodología de Implementación por TPs:**

**TP2 (ENTREGADO - 13/10/2025):**

- ✅ Libros: Sistema completo de gestión de inventario
- ✅ Socios: Gestión de membresías y beneficios
- ✅ Préstamos: Sistema de carrito y validaciones de negocio

**TP3 (PRÓXIMO - Fecha estimada: 20/10/2025):**

- ✅ Devoluciones: Sistema completo con multas y consultas (YA IMPLEMENTADO)
- 🆕 Configuración: Parametrización dinámica del sistema
- Tema: "Gestión de Devoluciones, Multas y Parametrización"
- Complejidad: BAJA (80% ya completado)
- Tiempo estimado: 2-3 horas

**TP4 (FUTURO - Fecha estimada: 27/10/2025):**

- 🆕 Reportes: Sistema completo de analytics y visualización
- Tema: "Sistema de Reportes y Analytics"
- Complejidad: MEDIA
- Tiempo estimado: 3-4 horas
- Incluye: 5 tipos de reportes + gráficos ASCII

### **Testing Incremental:**

Cada módulo se prueba individualmente antes de integrar:

1. **✅ TP2 Verificado**

   - Menú principal con opciones 1, 2, 3 funcionales
   - Testing completo: CRUD de libros, socios y préstamos

2. **✅ Devoluciones Verificado (parte de TP3)**

   - Menú principal opción 4 funcional
   - Testing: Registrar devoluciones, consultar historial, cálculo de multas

3. **🔜 Configuración (TP3)**

   - Al completar: Menú principal opción 5 funcional
   - Testing: Ver y modificar parámetros del sistema

4. **🔜 Reportes (TP4)**
   - Al completar: Menú principal opción 6 funcional
   - Testing: Generar todos los tipos de reportes con datos dummy

### **✅ TODOs Completados con el Módulo DEVOLUCIONES:**

Durante la implementación del módulo DEVOLUCIONES, se completaron todos los TODOs identificados:

1. **✅ PayFineUseCase** - ~~Requiere FineRepository~~ **COMPLETADO**

   - Archivo: `src/biblioteca/application/socios/pagar_multa/PayFineUseCase.java`
   - ✅ FineRepository integrado completamente
   - ✅ Implementada lógica real de pago de multas
   - ✅ Actualización de estado: Pendiente → Pagada

2. **✅ QueryMemberUseCase** - ~~Requiere LoanRepository y FineRepository~~ **COMPLETADO**

   - Archivo: `src/biblioteca/application/socios/consultar/QueryMemberUseCase.java`
   - ✅ LoanRepository integrado (completado en módulo PRÉSTAMOS)
   - ✅ FineRepository integrado (completado en módulo DEVOLUCIONES)
   - ✅ Muestra préstamos activos reales del socio
   - ✅ Muestra multas pendientes con montos reales
   - ✅ Incluye últimas 5 devoluciones del socio

3. **✅ FindMemberForm** - ~~Necesita mostrar devoluciones recientes~~ **COMPLETADO**
   - Archivo: `src/biblioteca/console/socios/FindMemberForm.java`
   - ✅ Nueva sección "DEVOLUCIONES RECIENTES" agregada
   - ✅ Muestra últimas 5 devoluciones con indicadores visuales
   - ✅ Indica si fueron a tiempo o con retraso
   - ✅ Calcula días de retraso cuando aplica

### **✅ Mejoras de UX Completadas:**

Durante la revisión y desarrollo de los módulos, se completaron todas las mejoras de UX identificadas:

1. **✅ QueryMemberForm vs FindMemberForm** - Redundancia eliminada

   - QueryMemberForm eliminado completamente
   - Funcionalidad consolidada en FindMemberForm mejorado

2. **✅ PayFineForm** - Búsqueda de socio implementada
   - Implementada búsqueda por ID o nombre usando InputHelper.seleccionar()
   - Experiencia de usuario mejorada significativamente

### **Estado Actual de TODOs en el Código:**

**Todos los TODOs críticos del código han sido resueltos:**

- ✅ `PayFineUseCase.java` línea 14: ~~`// TODO: Add FineRepository when implemented`~~ **RESUELTO**
- ✅ `PayFineUseCase.java` línea 37: ~~`// TODO: Implement fine payment logic`~~ **RESUELTO**
- ✅ `QueryMemberUseCase.java` línea 14: ~~`// TODO: Add LoanRepository and FineRepository`~~ **RESUELTO**
- ✅ `QueryMemberUseCase.java` línea 34: ~~`// TODO: Replace with real data`~~ **RESUELTO**

### **Estrategia de TODOs (Ya No Aplica - Todos Completados):**

- ✅ **TODOs Críticos**: ~~Se implementan en la fase de "Integración de TODOs Pendientes"~~ **COMPLETADOS**
- ✅ **TODOs Menores**: ~~Se pueden completar durante el desarrollo de cada módulo~~ **COMPLETADOS**
- ✅ **TODOs Completados**: Todos marcados y documentados ✨

### **🛠️ RECORDATORIO CRÍTICO - UTILIDADES DE CONSOLA:**

**ANTES de implementar CUALQUIER nuevo form, SIEMPRE revisar:**

- ✅ **InputHelper.java**: `seleccionar()`, `confirmar()`, `leerTexto()`, etc.
- ✅ **DisplayHelper.java**: `renderTitle()`, `printSuccess()`, `renderSubtitle()`, etc.
- ❌ **NO recrear** lógica de menús, selección o formateo manualmente
- ❌ **NO usar** `System.out.println()` directamente para UI

**Módulos implementados CORRECTAMENTE usando utilities:**

- ✅ LIBROS: BookController y forms optimizados
- ✅ SOCIOS: MemberController y forms optimizados (PayFineForm, FindMemberForm)
- ✅ PRÉSTAMOS: LoanController y LoanCartForm optimizados con InputHelper/DisplayHelper

**Al implementar DEVOLUCIONES, RESERVAS, CONSULTAS, etc.:**

- ✅ Seguir los patrones ya establecidos en LIBROS, SOCIOS y PRÉSTAMOS
- ✅ Usar el checklist de utilities documentado arriba

## 📝 **GESTIÓN DE TODOs Y DEPENDENCIAS**
