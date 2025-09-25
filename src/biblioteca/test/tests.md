# Comandos de Prueba Automatizados

Este archivo contiene comandos automatizados para probar todas las funcionalidades del sistema de biblioteca.

## Configuración

Todos los comandos deben ejecutarse desde la carpeta raíz del proyecto:

```bash
cd /Users/mrgoropeza/Projects/java/Siglo21-POO-TP2
```

## 🏗️ Compilación

```bash
javac -d bin -cp "lib/*" src/**/*.java
```

---

## 👥 GESTIÓN DE SOCIOS

### Registrar Nuevos Socios

**Registrar socio estándar (ID auto-generado):**

```bash
echo -e "2\n1\n\nJuan Pérez Automatico\njuan.auto@email.com\n555-9999\n1\nS\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Registrar socio estudiante:**

```bash
echo -e "2\n1\n\nAna García Estudiante\nana.estudiante@email.com\n555-8888\n2\nS\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Registrar socio jubilado:**

```bash
echo -e "2\n1\n\nCarlos López Jubilado\ncarlos.jubilado@email.com\n555-7777\n3\nS\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Registrar socio con ID específico:**

```bash
echo -e "2\n1\n9999\nMaría Torres Con ID\nmaria.id@email.com\n555-6666\n1\nS\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Cancelar registro de socio:**

```bash
echo -e "2\n1\n\nTest Cancelado\ntest@email.com\n555-0000\n1\nN\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

### Buscar y Consultar Socios

**Buscar socio por ID (con detalles):**

```bash
echo -e "2\n3\n1\n1001\nS\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar socio por ID (sin detalles):**

```bash
echo -e "2\n3\n1\n1001\nN\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar socio por nombre (con detalles del primero):**

```bash
echo -e "2\n3\n2\nJuan\nS\n1\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar socio por nombre (sin detalles):**

```bash
echo -e "2\n3\n2\nCarlos\nN\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar socio inexistente:**

```bash
echo -e "2\n3\n1\n9999\n5\n4\nS" | java -cp "bin:lib/*" App
```

### Modificar Socios

**Modificar socio por ID:**

```bash
echo -e "2\n2\n1\n1001\n1\nJuan Pérez Modificado\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Modificar email de socio por nombre:**

```bash
echo -e "2\n2\n2\nAna\n1\n2\nnuevo.email@test.com\n\n5\n4\nS" | java -cp "bin:lib/*" App
```

### Pagar Multas

**Intentar pagar multa de socio sin multas:**

```bash
echo -e "2\n4\n1\n1001\n1\n5\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar socio para pago por nombre:**

```bash
echo -e "2\n4\n2\nJuan\n1\n5\n4\nS" | java -cp "bin:lib/*" App
```

---

## 📚 GESTIÓN DE LIBROS

### Registrar Nuevos Libros

**Registrar libro nuevo:**

```bash
echo -e "1\n1\nISBN123456789\nLibro de Prueba\nAutor Test\nEditorial Test\n2024\n1\n5\n\n6\n4\nS" | java -cp "bin:lib/*" App
```

**Registrar libro con múltiples autores:**

```bash
echo -e "1\n1\nISBN987654321\nLibro Colaborativo\nAutor Uno, Autor Dos\nEditorial Colaborativa\n2024\n2\n3\n\n6\n4\nS" | java -cp "bin:lib/*" App
```

### Buscar Libros

**Buscar libro por ISBN:**

```bash
echo -e "1\n2\n1\nISBN123456789\n6\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar libro por título:**

```bash
echo -e "1\n2\n2\nCien años\n6\n4\nS" | java -cp "bin:lib/*" App
```

**Buscar libro por autor:**

```bash
echo -e "1\n2\n3\nGarcía Márquez\n6\n4\nS" | java -cp "bin:lib/*" App
```

### Modificar Libros

**Modificar título de libro:**

```bash
echo -e "1\n3\n1\nISBN123456789\n1\n2\nNuevo Título del Libro\n\n6\n4\nS" | java -cp "bin:lib/*" App
```

### Ingresar Stock

**Agregar ejemplares a libro existente:**

```bash
echo -e "1\n4\n1\nISBN123456789\n1\n3\n2\nS\n\n6\n4\nS" | java -cp "bin:lib/*" App
```

### Eliminar Libros

**Eliminar libro por ISBN:**

```bash
echo -e "1\n5\n1\nISBN123456789\n1\nS\n\n6\n4\nS" | java -cp "bin:lib/*" App
```

---

## 📊 ESTADÍSTICAS DEL SISTEMA

**Ver estadísticas generales:**

```bash
echo -e "3\n\n4\nS" | java -cp "bin:lib/*" App
```

---

## 🧪 PRUEBAS DE VALIDACIÓN

### Validación de Entrada

**Ingresar opción inválida en menú principal:**

```bash
echo -e "99\n4\nS" | java -cp "bin:lib/*" App
```

**Ingresar texto en lugar de número:**

```bash
echo -e "abc\n4\nS" | java -cp "bin:lib/*" App
```

**Dejar campos obligatorios vacíos (registro socio):**

```bash
echo -e "2\n1\n\n\n\n\n1\nN\n5\n4\nS" | java -cp "bin:lib/*" App
```

### Pruebas de Navegación

**Navegar por todos los menús sin hacer cambios:**

```bash
echo -e "1\n6\n2\n5\n3\n\n4\nS" | java -cp "bin:lib/*" App
```

**Salir desde cada submenu:**

```bash
echo -e "1\n6\n4\nS" | java -cp "bin:lib/*" App
```

---

## 🔄 PRUEBAS DE FLUJO COMPLETO

### Flujo Completo de Socio

**Registrar → Buscar → Modificar → Consultar:**

```bash
echo -e "2\n1\n\nFlujo Completo\nflujo@test.com\n555-1111\n1\nS\n\n3\n2\nFlujo\nS\n1\n2\n1\nFlujo Completo Modificado\n\n3\n2\nFlujo\nS\n1\n5\n4\nS" | java -cp "bin:lib/*" App
```

### Flujo Completo de Libro

**Registrar → Buscar → Modificar → Agregar Stock:**

```bash
echo -e "1\n1\nISBNFLUJO123\nLibro Flujo\nAutor Flujo\nEditorial Flujo\n2024\n1\n2\n\n2\n1\nISBNFLUJO123\n3\n1\nISBNFLUJO123\n1\n2\nLibro Flujo Modificado\n\n4\n1\nISBNFLUJO123\n1\n2\n1\nS\n\n6\n4\nS" | java -cp "bin:lib/*" App
```

---

## 📝 NOTAS DE USO

### Estructura del Comando

```bash
echo -e "entrada1\nentrada2\nentrada3" | java -cp "bin:lib/*" App
```

- Cada `\n` representa presionar Enter
- Las entradas se procesan secuencialmente
- Un `\n` extra después de confirmaciones maneja el "Presione Enter para continuar..."

### Códigos de Menú

**Menú Principal:**

- `1` = Gestión de Libros
- `2` = Gestión de Socios
- `3` = Ver estadísticas
- `4` = Salir

**Gestión de Socios:**

- `1` = Registrar nuevo socio
- `2` = Modificar datos del socio
- `3` = Buscar y consultar socio
- `4` = Pagar multas
- `5` = Volver al menú principal

**Gestión de Libros:**

- `1` = Registrar nuevo libro
- `2` = Buscar libros
- `3` = Modificar datos del libro
- `4` = Ingresar stock
- `5` = Eliminar libro
- `6` = Volver al menú principal

**Tipos de Socio:**

- `1` = Estándar
- `2` = Estudiante
- `3` = Jubilado

**Métodos de Búsqueda:**

- `1` = Buscar por ID
- `2` = Buscar por nombre/título/autor

**Confirmaciones:**

- `S` = Sí
- `N` = No

---

## 🚀 EJECUCIÓN RÁPIDA

Para ejecutar múltiples pruebas seguidas, puedes usar:

```bash
# Compilar una vez
javac -d bin -cp "lib/*" src/**/*.java

# Ejecutar pruebas
echo "Registrando socio..." && echo -e "2\n1\n\nTest 1\ntest1@email.com\n555-0001\n1\nS\n\n5\n4\nS" | java -cp "bin:lib/*" App
echo "Registrando otro socio..." && echo -e "2\n1\n\nTest 2\ntest2@email.com\n555-0002\n2\nS\n\n5\n4\nS" | java -cp "bin:lib/*" App
echo "Buscando socios..." && echo -e "2\n3\n2\nTest\nN\n5\n4\nS" | java -cp "bin:lib/*" App
```

---

_Última actualización: Septiembre 2025_
