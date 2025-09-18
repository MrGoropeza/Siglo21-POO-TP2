## Requerimientos

• El sistema permitirá registrar libros nuevos con ID de libro, título, autor(es), categoría, editorial y
año de publicación.

• El sistema permitirá ingresar ejemplares indicando origen (compra o donación); si el libro no
existe, primero se dará de alta y luego se asociará el stock.

• El sistema permitirá identificar cada ejemplar con un código único y gestionar su estado
(disponible, prestado, reservado).

• El sistema permitirá actualizar el stock de un libro al recibir nuevos ejemplares, manteniendo el
historial de ingresos (compra/donación).

• El sistema permitirá registrar y actualizar socios con sus datos de contacto y categoría (Estándar,
Estudiante, Jubilado).

• El sistema permitirá aplicar beneficios por categoría de socio:

o Estándar: sin beneficios.  
o Estudiante 50% de descuento en multas.  
o Jubilado: +3 días al plazo estándar de préstamo.

• El sistema permitirá configurar parámetros generales: días estándar de préstamo, multa diaria por
atraso, cupo máximo de préstamos por socio y tope de reservas activas por socio.

• El sistema permitirá iniciar una operación de préstamo para un socio y añadir varios libros en
una sola acción; al confirmar, creará un préstamo independiente por cada ejemplar
seleccionado (con vencimientos y multas independientes).

• El sistema permitirá registrar devoluciones y, si hubiese atraso, calcular y asignar la multa en el
momento (considerando beneficios); además, registrar el pago de multas.

• El sistema permitirá crear reservas por título (una reserva = un libro); cuando no haya ejemplares,
el socio quedará en la lista por orden de llegada para ese título.

• El sistema permitirá atender reservas priorizando al primer socio en lista cuando se libere un
ejemplar, y convertir la reserva en préstamo.

• El sistema permitirá consultas: disponibilidad por título/autor/categoría, historial por socio,
préstamos activos y vencidos, reservas por libro.

• El sistema permitirá enviar recordatorios de vencimiento y avisos de disponibilidad (salida por
consola).

• El sistema permitirá generar reportes por período: ranking de libros más prestados, socios con
más retrasos y recaudación total por multas.

## Funcionalidades

### Menú principal

• El sistema mostrará el menú principal en consola:

1. Libros 2) Socios 3) Préstamos 4) Devoluciones 5) Reservas 6) Consultas 7) Reportes 8)
   Configuración 9) Salir  
   El sistema permitirá elegir una opción por número y, si la entrada no es válida, mostrará Opción
   inválida. Intente nuevamente.

### Libros

• El sistema mostrará el submenú Libros:

1. Registrar libro 2) Ingresar stock (compra/donación) 3) Modificar libro 4) Eliminar libro 5) Buscar
   libro 6) Volver

• El sistema, al elegir Registrar libro, solicitará:  
ID de libro: Título: Autor(es) (separados por coma): Categoría: Editorial: Año de publicación
(número):  
El sistema validará campos obligatorios, año numérico e ID único; mostrará Libro creado con ID
<id>. o Error: ID ya existente / datos inválidos.

• El sistema, al elegir Ingresar stock (compra/donación), solicitará:  
Buscar libro por (1=ID, 2=Título): → Ingrese valor de búsqueda: → Origen (1=Compra, 2=Donación):
→ Cantidad de ejemplares (número > 0):  
El sistema validará existencia del libro y cantidad > 0; generará N códigos de ejemplar con
estado DISPONIBLE y mostrará Se añadieron <N> ejemplares al libro <ID> (origen:
<Compra/Donación>).

• El sistema, al elegir Modificar libro, solicitará:  
Buscar por (1=ID, 2=Título): → mostrará datos actuales → pedirá nuevos valores (ENTER para
mantener): Nuevo título: Nuevos autor(es): Nueva categoría: Nueva editorial: Nuevo año:  
El sistema validará formato y mostrará Libro <ID> actualizado.

• El sistema, al elegir Eliminar libro, solicitará:  
Ingrese ID o Título: → si hay ejemplares prestados, mostrará No se puede eliminar: existen
préstamos activos.; si no, pedirá confirmación ¿Confirma eliminar libro y ejemplares asociados?
(S/N) y mostrará Libro eliminado.

• El sistema, al elegir Buscar libro, solicitará:  
Buscar por (1=ID, 2=Título, 3=Autor, 4=Categoría): → listará coincidencias con ID | Título | Autor(es)
| Categoría | Editorial | Año | Ejemplares DISPONIBLES / PRESTADOS.

### Socios

• El sistema mostrará el submenú Socios:

1. Registrar socio 2) Modificar socio 3) Consultar socio 4) Registrar pago de multa 5) Volver

• El sistema, al elegir Registrar socio, solicitará:  
ID socio: Nombre y apellido: Email: Teléfono: Categoría (1=Estándar, 2=Estudiante, 3=Jubilado):  
El sistema validará ID único y formato de email; mostrará Socio <ID> creado (Categoría:
<nombre>).

• El sistema, al elegir Modificar socio, solicitará:  
Buscar socio por (1=ID, 2=Nombre): → permitirá editar Email, Teléfono y Categoría → confirmará
con Socio <ID> actualizado.

• El sistema, al elegir Consultar socio, solicitará:  
Ingrese ID socio: → mostrará Préstamos activos (con vencimiento) | Multas impagas (monto) |
Reservas vigentes (títulos).

• El sistema, al elegir Registrar pago de multa, solicitará:  
Ingrese ID socio: → listará multas impagas ID multa | Préstamo | Monto → permitirá seleccionar
una o varias → pedirá confirmación y mostrará Pago registrado para <k> multas (total: $<monto>).

### Préstamos

• El sistema mostrará el submenú Préstamos:

1. Nueva operación de préstamo (carrito) 2) Volver

• El sistema, al elegir Nueva operación de préstamo (carrito), solicitará:  
Ingrese ID de socio: → validará existencia y mostrará Operación #<n> para Socio <ID>. Cupo
disponible: <cupo>.

• El sistema permitirá agregar ítems repitiendo:  
Buscar libro por (1=ID, 2=Título, 3=Autor): → mostrará coincidencias → al elegir un título:

o Si hay ejemplares disponibles: listará Ejemplares DISPONIBLES (código): <c1>, <c2>, ... →
Seleccione código de ejemplar:

o Si no hay ejemplares: mostrará Sin ejemplares disponibles para este título. Puede crear
una reserva desde el menú Reservas.  
El sistema validará por ítem: ejemplar disponible, que no esté ya en el carrito y cupo
restante del socio > 0; calculará vencimiento por ítem según Configuración y beneficios
(Jubilado: +3 días) y mostrará Ítem añadido: <Título> [<código>] – Vence: <fecha> | Carrito:
<k> ítem(s) | Cupo restante: <n>.

• El sistema permitirá gestionar el carrito mostrando:

1. Añadir otro libro 2) Quitar ítem del carrito 3) Confirmar operación 4) Cancelar

o Al quitar: solicitará número de ítem y mostrará Ítem eliminado.  
o Al cancelar: pedirá ¿Cancelar operación? (S/N) y volverá al submenú.

• El sistema, al Confirmar operación, validará globalmente: cupo suficiente para k ítems y
(opcional) bloqueo por multas impagas; si todo es válido, creará un préstamo por cada ítem del
carrito y mostrará por línea:  
Préstamo creado – Socio <ID> – <Título> [<código>] – Vence <fecha>  
Al finalizar, mostrará Operación completada: <k> préstamos generados.

• El sistema, si el cupo no alcanza o hay bloqueo, mostrará No es posible confirmar: cupo
insuficiente / multas impagas.

### Devoluciones

• El sistema mostrará el submenú Devoluciones:

1. Registrar devolución 2) Volver

• El sistema, al elegir Registrar devolución, solicitará:  
Ingrese código de ejemplar: → localizará el préstamo activo correspondiente; si no existe,
mostrará No se encontró préstamo activo para el código ingresado.

• El sistema calculará días de atraso y la multa del préstamo individual, aplicará beneficios
(Estudiante: -50%, Jubilado: +días al plazo ya aplicado en el préstamo) y mostrará:  
Devolución de <Título> [<código>] – Vence: <fechaVto> – Entregado: <fechaHoy> – Atraso: <días>
– Multa: $<monto>

• El sistema pedirá confirmación; al confirmar, marcará el ejemplar DISPONIBLE, cerrará el
préstamo y registrará la multa (si corresponde) mostrando Devolución registrada. Multa:
$<monto>.

• El sistema, si existe lista de espera del título, mostrará Se notificará al primer socio en la lista por
orden de llegada.

### Reservas

• El sistema mostrará el submenú Reservas:

1. Crear reserva 2) Cancelar reserva 3) Atender reserva 4) Volver

• El sistema, al elegir Crear reserva, solicitará:  
Ingrese ID de socio: → Buscar libro por (1=ID, 2=Título, 3=Autor):

o Si no hay ejemplares disponibles, añadirá al socio a la lista por orden de llegada de ese
título y mostrará Reserva <ID> creada para <Título>. Posición: <n>.

o Si hay ejemplares, mostrará Hay ejemplares disponibles. Realice el préstamo desde el
menú Préstamos.

• El sistema, al elegir Cancelar reserva, solicitará:  
Ingrese ID socio o ID de reserva: → listará reservas activas → permitirá seleccionar una → pedirá
confirmación y mostrará Reserva <ID> cancelada.

• El sistema, al elegir Atender reserva, solicitará:  
Seleccione libro con lista de espera: → mostrará el primer socio por orden de llegada → pedirá
confirmación para convertir en préstamo; al confirmar, creará el préstamo asignando un
ejemplar disponible y mostrará Reserva atendida: préstamo generado para Socio <ID> – <Título>
[<código>] – Vence <fecha>.

### Consultas

• El sistema mostrará el submenú Consultas:

1. Disponibilidad 2) Historial por socio 3) Préstamos activos 4) Préstamos vencidos 5) Reservas
   por libro 6) Volver

• El sistema, al elegir Disponibilidad, solicitará:  
Buscar por (1=Título, 2=Autor, 3=Categoría): → listará ID | Título | Autor(es) | DISPONIBLES |
PRESTADOS.

• El sistema, al elegir Historial por socio, solicitará:  
Ingrese ID socio: → mostrará Préstamos activos | Préstamos finalizados | Multas
pagadas/impagas.

• El sistema, al elegir Préstamos activos, permitirá filtrar por fecha y/o socio y listará Socio | Libro |
Código ejemplar | Fecha de vencimiento.

• El sistema, al elegir Préstamos vencidos, listará Socio | Libro | Días de atraso | Multa estimada.

• El sistema, al elegir Reservas por libro, solicitará:  
Ingrese ID/Título: → listará la cola Posición | ID socio | Fecha de solicitud.

### Reportes

• El sistema mostrará el submenú Reportes:

1. Libros más prestados 2) Socios con más retrasos 3) Recaudación por multas 4) Volver

• El sistema, al elegir Libros más prestados, solicitará:  
Desde (AAAA-MM-DD): Hasta (AAAA-MM-DD): → mostrará # | Título | Cantidad de préstamos
(ordenado desc).

• El sistema, al elegir Socios con más retrasos, solicitará:  
Desde: Hasta: → mostrará # | ID socio | Nombre | Préstamos vencidos | Días de mora acumulados.

• El sistema, al elegir Recaudación por multas, solicitará:  
Desde: Hasta: → mostrará Total recaudado: $<monto> y, si se pide detalle, ID socio | Nombre |
Monto.

### Configuración

• El sistema mostrará el submenú Configuración:

1. Días estándar de préstamo 2) Multa diaria 3) Cupo máximo por socio 4) Tope de reservas
   activas por socio 5) Beneficios por categoría 6) Volver

• El sistema, al elegir Días estándar de préstamo, mostrará Valor actual: <días>. Ingrese nuevo
valor: → validará número entero > 0 y mostrará Actualizado.

• El sistema, al elegir Multa diaria, mostrará Valor actual: $<monto>. Ingrese nuevo valor: →
validará número ≥ 0 y mostrará Actualizado.

• El sistema, al elegir Cupo máximo por socio, mostrará Valor actual: <cupo>. Ingrese nuevo valor:
→ validará entero ≥ 0 y mostrará Actualizado.

• El sistema, al elegir Tope de reservas activas por socio, mostrará Valor actual: <tope>. Ingrese
nuevo valor: → validará entero ≥ 0 y mostrará Actualizado.

• El sistema, al elegir Beneficios por categoría, mostrará:  
Estándar: sin beneficios  
Estudiante: descuento en multas (%) – actual: 50  
Jubilado: días extra al préstamo – actual: 3  
El sistema permitirá editar valores numéricos y confirmará Beneficios actualizados.
