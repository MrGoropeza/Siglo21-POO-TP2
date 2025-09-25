# Plan de Implementación - Sistema de Gestión de Biblioteca

## 🎯 **NUEVAS OPCIONES DE MENÚ**

Según los requisitos, el menú principal sería:

```
============================================================
              SISTEMA DE GESTIÓN DE BIBLIOTECA
============================================================

1. Libros
2. Socios
3. Préstamos
4. Devoluciones
5. Reservas
6. Consultas
7. Reportes
8. Configuración
9. Salir
```

## 📁 **ARCHIVOS A CREAR**

### **1. ENTIDADES DEL DOMINIO (Domain Layer)**

```
src/biblioteca/domain/entities/
├── Member.java              // Socio
├── MemberType.java          // TipoSocio (Enum: STANDARD, STUDENT, RETIRED)
├── Loan.java               // Prestamo
├── Fine.java               // Multa
├── Reservation.java        // Reserva
├── Notification.java       // Notificacion
├── SystemParameters.java   // ParametrosSistema
└── enums/
    ├── LoanState.java      // Estado préstamo (ACTIVE, RETURNED, OVERDUE)
    ├── ReservationState.java // Estado reserva (ACTIVE, CANCELLED, FULFILLED)
    └── NotificationChannel.java // Canal (CONSOLE)
```

### **2. CASOS DE USO (Application Layer)**

```
src/biblioteca/application/
├── socios/
│   ├── registrar/
│   │   ├── RegisterMemberRequest.java
│   │   ├── RegisterMemberResult.java
│   │   └── RegisterMemberUseCase.java
│   ├── modificar/
│   │   ├── ModifyMemberRequest.java
│   │   ├── ModifyMemberResult.java
│   │   └── ModifyMemberUseCase.java
│   ├── consultar/
│   │   ├── QueryMemberRequest.java
│   │   ├── QueryMemberResult.java
│   │   └── QueryMemberUseCase.java
│   └── pagar_multa/
│       ├── PayFineRequest.java
│       ├── PayFineResult.java
│       └── PayFineUseCase.java
├── prestamos/
│   ├── crear/
│   │   ├── CreateLoanRequest.java
│   │   ├── CreateLoanResult.java
│   │   └── CreateLoanUseCase.java
│   └── carrito/
│       ├── LoanCart.java
│       ├── LoanCartItem.java
│       ├── AddToCartRequest.java
│       ├── AddToCartResult.java
│       ├── AddToCartUseCase.java
│       ├── RemoveFromCartRequest.java
│       ├── RemoveFromCartResult.java
│       ├── RemoveFromCartUseCase.java
│       ├── ConfirmCartRequest.java
│       ├── ConfirmCartResult.java
│       └── ConfirmCartUseCase.java
├── devoluciones/
│   ├── registrar/
│   │   ├── RegisterReturnRequest.java
│   │   ├── RegisterReturnResult.java
│   │   └── RegisterReturnUseCase.java
├── reservas/
│   ├── crear/
│   │   ├── CreateReservationRequest.java
│   │   ├── CreateReservationResult.java
│   │   └── CreateReservationUseCase.java
│   ├── cancelar/
│   │   ├── CancelReservationRequest.java
│   │   ├── CancelReservationResult.java
│   │   └── CancelReservationUseCase.java
│   └── atender/
│       ├── AttendReservationRequest.java
│       ├── AttendReservationResult.java
│       └── AttendReservationUseCase.java
├── consultas/
│   ├── disponibilidad/
│   │   ├── CheckAvailabilityRequest.java
│   │   ├── CheckAvailabilityResult.java
│   │   └── CheckAvailabilityUseCase.java
│   ├── historial_socio/
│   │   ├── MemberHistoryRequest.java
│   │   ├── MemberHistoryResult.java
│   │   └── MemberHistoryUseCase.java
│   ├── prestamos_activos/
│   │   ├── ActiveLoansRequest.java
│   │   ├── ActiveLoansResult.java
│   │   └── ActiveLoansUseCase.java
│   ├── prestamos_vencidos/
│   │   ├── OverdueLoansRequest.java
│   │   ├── OverdueLoansResult.java
│   │   └── OverdueLoansUseCase.java
│   └── reservas_por_libro/
│       ├── ReservationsByBookRequest.java
│       ├── ReservationsByBookResult.java
│       └── ReservationsByBookUseCase.java
├── reportes/
│   ├── libros_mas_prestados/
│   │   ├── MostLoanedBooksRequest.java
│   │   ├── MostLoanedBooksResult.java
│   │   └── MostLoanedBooksUseCase.java
│   ├── socios_morosos/
│   │   ├── DelayedMembersRequest.java
│   │   ├── DelayedMembersResult.java
│   │   └── DelayedMembersUseCase.java
│   └── recaudacion_multas/
│       ├── FineRevenueRequest.java
│       ├── FineRevenueResult.java
│       └── FineRevenueUseCase.java
└── configuracion/
    ├── actualizar_parametros/
    │   ├── UpdateParametersRequest.java
    │   ├── UpdateParametersResult.java
    │   └── UpdateParametersUseCase.java
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
├── AuthorDummyData.java      // ✅ Ya existe
├── BookDummyData.java        // ✅ Ya existe
├── CategoryDummyData.java    // ✅ Ya existe
├── PublisherDummyData.java   // ✅ Ya existe
├── MemberDummyData.java      // 🆕 Datos dummy de socios
├── LoanDummyData.java        // 🆕 Datos dummy de préstamos
├── FineDummyData.java        // 🆕 Datos dummy de multas
├── ReservationDummyData.java // 🆕 Datos dummy de reservas
└── SystemParametersDummyData.java // 🆕 Datos dummy de parámetros del sistema
```

### **4. FORMULARIOS (Console Layer)**

```
src/biblioteca/console/forms/
├── member/
│   ├── RegisterMemberForm.java
│   ├── ModifyMemberForm.java
│   ├── QueryMemberForm.java
│   ├── FindMemberForm.java
│   └── PayFineForm.java
├── loan/
│   ├── LoanCartForm.java
│   ├── AddToCartForm.java
│   └── ConfirmLoanForm.java
├── return/
│   └── RegisterReturnForm.java
├── reservation/
│   ├── CreateReservationForm.java
│   ├── CancelReservationForm.java
│   └── AttendReservationForm.java
├── query/
│   ├── CheckAvailabilityForm.java
│   ├── MemberHistoryForm.java
│   ├── ActiveLoansForm.java
│   ├── OverdueLoansForm.java
│   └── ReservationsByBookForm.java
├── report/
│   ├── MostLoanedBooksForm.java
│   ├── DelayedMembersForm.java
│   └── FineRevenueForm.java
└── config/
    └── UpdateParametersForm.java
```

### **5. CONTROLADORES (Console Layer)**

```
src/biblioteca/console/controllers/
├── MemberController.java       // Gestión de socios
├── LoanController.java         // Gestión de préstamos
├── ReturnController.java       // Gestión de devoluciones
├── ReservationController.java  // Gestión de reservas
├── QueryController.java        // Consultas
├── ReportController.java       // Reportes
└── ConfigController.java       // Configuración
```

### **6. SERVICIOS ADICIONALES**

```
src/biblioteca/domain/services/
├── LoanCalculatorService.java      // Cálculos de préstamos y multas
├── ReservationQueueService.java    // Gestión de colas de reservas
├── NotificationService.java        // Envío de notificaciones
└── ValidationService.java          // Validaciones de negocio
```

### **7. ACTUALIZACIONES A ARCHIVOS EXISTENTES**

```
src/biblioteca/console/controllers/MainController.java  // ← ACTUALIZAR menú principal
src/biblioteca/console/ioc/DependencyContainer.java     // ← ACTUALIZAR dependencias
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

## 🎯 **RESUMEN DE IMPLEMENTACIÓN**

**Total de archivos nuevos:** ~85 archivos  
**Controladores nuevos:** 6 controladores  
**Nuevas funcionalidades del menú:**

- ✅ Libros (ya implementado)
- 🆕 Socios (4 operaciones)
- 🆕 Préstamos (carrito de préstamos)
- 🆕 Devoluciones (1 operación)
- 🆕 Reservas (3 operaciones)
- 🆕 Consultas (5 tipos de consultas)
- 🆕 Reportes (3 tipos de reportes)
- 🆕 Configuración (5 parámetros configurables)

## 📋 **PROGRESO DE IMPLEMENTACIÓN**

### ✅ **COMPLETADO**

- [x] BookController con patrón de formularios
- [x] BookController refactorizado completamente para usar forms
- [x] Use Cases de libros (RegisterBook, AddStock, ModifyBook, DeleteBook)
- [x] Forms de libros (RegisterBookForm, AddStockForm, ModifyBookForm, DeleteBookForm, FindBookForm)
- [x] DependencyContainer actualizado para libros
- [x] Arquitectura Clean Architecture establecida
- [x] Imports optimizados en BookController
- [x] **Módulo de Libros 100% completado**

- [x] Entidades Member y MemberType con beneficios por tipo de socio
- [x] Use Cases de socios (RegisterMember, ModifyMember, QueryMember, PayFine)
- [x] MemberRepository con operaciones CRUD y búsqueda
- [x] MemberDummyData con datos de prueba
- [x] Forms de socios (RegisterMemberForm, ModifyMemberForm, QueryMemberForm, FindMemberForm, PayFineForm)
- [x] MemberController con menú de 6 opciones
- [x] DependencyContainer actualizado con componentes de socios
- [x] MainController integrado con opción "2. Gestión de Socios"
- [x] **Módulo de Socios 100% completado**

### 🆕 **PENDIENTE POR MÓDULO**

**📋 TODOs pendientes para completar en módulos futuros:**

- [ ] **PayFineUseCase**: Integrar FineRepository para pagos reales de multas (Prioridad 3)
- [ ] **QueryMemberUseCase**: Integrar LoanRepository y FineRepository para mostrar préstamos activos y multas reales (Prioridad 2-3)

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

#### **PRÉSTAMOS (Prioridad 2)**

- [ ] Crear entidades: Loan, LoanState
- [ ] Crear use cases: CreateLoan, LoanCart (AddToCart, RemoveFromCart, ConfirmCart)
- [ ] Crear forms: LoanCartForm, AddToCartForm, ConfirmLoanForm
- [ ] Crear LoanController
- [ ] Crear LoanRepository
- [ ] Crear datos dummy: LoanDummyData
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "3. Préstamos" al menú principal
- [ ] **Completar TODO**: Integrar LoanRepository en QueryMemberUseCase para mostrar préstamos activos reales

#### **DEVOLUCIONES (Prioridad 3)**

- [ ] Crear entidades: Fine
- [ ] Crear use cases: RegisterReturn
- [ ] Crear forms: RegisterReturnForm
- [ ] Crear ReturnController
- [ ] Crear FineRepository
- [ ] Crear datos dummy: FineDummyData
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "4. Devoluciones" al menú principal
- [ ] **Completar TODOs**:
  - Integrar FineRepository en PayFineUseCase para pagos reales de multas
  - Integrar FineRepository en QueryMemberUseCase para mostrar multas reales

#### **RESERVAS (Prioridad 4)**

- [ ] Crear entidades: Reservation, ReservationState
- [ ] Crear use cases: CreateReservation, CancelReservation, AttendReservation
- [ ] Crear forms: CreateReservationForm, CancelReservationForm, AttendReservationForm
- [ ] Crear ReservationController
- [ ] Crear ReservationRepository
- [ ] Crear datos dummy: ReservationDummyData
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "5. Reservas" al menú principal

#### **CONSULTAS (Prioridad 5)**

- [ ] Crear use cases: CheckAvailability, MemberHistory, ActiveLoans, OverdueLoans, ReservationsByBook
- [ ] Crear forms: CheckAvailabilityForm, MemberHistoryForm, ActiveLoansForm, OverdueLoansForm, ReservationsByBookForm
- [ ] Crear QueryController
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "6. Consultas" al menú principal

#### **REPORTES (Prioridad 6)**

- [ ] Crear use cases: MostLoanedBooks, DelayedMembers, FineRevenue
- [ ] Crear forms: MostLoanedBooksForm, DelayedMembersForm, FineRevenueForm
- [ ] Crear ReportController
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "7. Reportes" al menú principal

#### **CONFIGURACIÓN (Prioridad 7)**

- [ ] Crear entidades: SystemParameters
- [ ] Crear use cases: UpdateParameters
- [ ] Crear forms: UpdateParametersForm
- [ ] Crear ConfigController
- [ ] Crear SystemParametersRepository
- [ ] Crear datos dummy: SystemParametersDummyData
- [ ] Actualizar DependencyContainer
- [ ] **Actualizar MainController** - Agregar opción "8. Configuración" al menú principal

#### **SERVICIOS ADICIONALES (Prioridad 8)**

- [ ] Crear LoanCalculatorService
- [ ] Crear ReservationQueueService
- [ ] Crear NotificationService
- [ ] Crear ValidationService

#### **INTEGRACIÓN DE TODOs PENDIENTES (Prioridad 7.5)**

Esta fase se encarga de completar todas las integraciones pendientes entre módulos:

- [ ] **Integrar LoanRepository en QueryMemberUseCase**:

  - Archivo: `src/biblioteca/application/socios/consultar/QueryMemberUseCase.java`
  - Línea 14: `// TODO: Add LoanRepository and FineRepository when implemented`
  - Línea 34: `// TODO: Replace with real data from LoanRepository and FineRepository`
  - Acción: Mostrar préstamos activos reales del socio

- [ ] **Integrar FineRepository en PayFineUseCase**:

  - Archivo: `src/biblioteca/application/socios/pagar_multa/PayFineUseCase.java`
  - Línea 14: `// TODO: Add FineRepository when implemented`
  - Línea 37: `// TODO: Implement fine payment logic when FineRepository is available`
  - Acción: Implementar pagos reales de multas

- [ ] **Integrar FineRepository en QueryMemberUseCase**:
  - Archivo: `src/biblioteca/application/socios/consultar/QueryMemberUseCase.java`
  - Acción: Mostrar multas pendientes reales del socio

#### **FINALIZACIÓN (Prioridad 8)**

- [ ] Revisión final del MainController con todas las opciones
- [ ] Testing integral de todos los módulos
- [ ] Verificación de que todos los TODOs han sido completados
- [ ] Documentación final del sistema

## 📝 **GESTIÓN DE TODOs Y DEPENDENCIAS**

### **TODOs Identificados en el Código:**

Durante la implementación inicial del módulo SOCIOS, se crearon TODOs que marcan integraciones futuras:

1. **PayFineUseCase** - Requiere FineRepository (Módulo DEVOLUCIONES)
2. **QueryMemberUseCase** - Requiere LoanRepository y FineRepository (Módulos PRÉSTAMOS y DEVOLUCIONES)

### **Mejoras de UX Identificadas:**

Durante la revisión del módulo SOCIOS, se identificaron las siguientes mejoras de experiencia de usuario:

1. **QueryMemberForm vs FindMemberForm** - Posible redundancia funcional a evaluar
2. **PayFineForm** - Necesita implementar búsqueda de socio por ID/nombre en lugar de pedir ID directamente

### **Estrategia de TODOs:**

- 🔴 **TODOs Críticos**: Se implementan en la fase de "Integración de TODOs Pendientes"
- 🟡 **TODOs Menores**: Se pueden completar durante el desarrollo de cada módulo
- ✅ **TODOs Completados**: Se marcan como completados y se documentan

### **🛠️ RECORDATORIO CRÍTICO - UTILIDADES DE CONSOLA:**

**ANTES de implementar CUALQUIER nuevo form, SIEMPRE revisar:**

- ✅ **InputHelper.java**: `seleccionar()`, `confirmar()`, `leerTexto()`, etc.
- ✅ **DisplayHelper.java**: `renderTitle()`, `printSuccess()`, `renderSubtitle()`, etc.
- ❌ **NO recrear** lógica de menús, selección o formateo manualmente
- ❌ **NO usar** `System.out.println()` directamente para UI

**Módulos implementados CORRECTAMENTE usando utilities:**

- ✅ LIBROS: BookController y forms optimizados
- ✅ SOCIOS: MemberController y forms optimizados (PayFineForm, FindMemberForm)

**Al implementar PRÉSTAMOS, DEVOLUCIONES, RESERVAS, etc.:**

- ✅ Seguir los patrones ya establecidos
- ✅ Usar el checklist de utilities documentado arriba

## 🎯 **ESTRATEGIA DE DESARROLLO Y TESTING POR FASES**

### **Metodología de Implementación:**

Cada prioridad incluye la actualización del `MainController` para permitir testing incremental:

1. **Socios** - Base fundamental para préstamos

   - Al completar: Menú principal mostrará "2. Socios" funcional
   - Testing: Registrar, modificar, consultar socios y pagar multas

2. **Préstamos** - Funcionalidad principal de la biblioteca

   - Al completar: Menú principal mostrará "3. Préstamos" funcional
   - Testing: Crear préstamos con carrito de libros

3. **Devoluciones** - Cierra el ciclo de préstamos

   - Al completar: Menú principal mostrará "4. Devoluciones" funcional
   - Testing: Procesar devoluciones y calcular multas

4. **Reservas** - Funcionalidad avanzada

   - Al completar: Menú principal mostrará "5. Reservas" funcional
   - Testing: Crear, cancelar y atender reservas

5. **Consultas** - Información del sistema

   - Al completar: Menú principal mostrará "6. Consultas" funcional
   - Testing: Consultar disponibilidad, historiales, préstamos activos/vencidos

6. **Reportes** - Analytics del sistema

   - Al completar: Menú principal mostrará "7. Reportes" funcional
   - Testing: Generar reportes de libros más prestados, socios morosos, recaudación

7. **Configuración** - Parametrización del sistema

   - Al completar: Menú principal mostrará "8. Configuración" funcional
   - Testing: Modificar parámetros del sistema

8. **Finalización** - Testing integral y documentación
