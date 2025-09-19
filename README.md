# Siglo 21 - Programación Orientada a Objetos

## Descripción del Sistema

Este proyecto implementa un **Sistema de Gestión de Biblioteca** desarrollado en Java aplicando los conceptos fundamentales de Programación Orientada a Objetos. El sistema está diseñado para manejar el registro de libros, gestión de ejemplares, administración de socios, préstamos, devoluciones, reservas y reportes completos de una biblioteca moderna.

Como parte del desarrollo de mis conocimientos en programación, he implementado una arquitectura organizada por capas, patrones de diseño profesionales y técnicas avanzadas que van más allá de los requerimientos básicos del trabajo práctico, con el objetivo de crear un código más mantenible y escalable.

## Compilación y Ejecución

### Prerrequisitos

- Java 17 o superior
- Sistema operativo con terminal (Windows, macOS, Linux)

### Compilar el proyecto

```bash
javac -sourcepath src -d bin src/biblioteca/App.java
```

### Ejecutar la aplicación

```bash
java -cp bin biblioteca.App
```

## Arquitectura del Sistema

El proyecto sigue los principios de **Clean Architecture** organizando el código en capas bien definidas:

### 🏗️ Estructura de Carpetas

```
src/biblioteca/
├── App.java                          # Punto de entrada principal
├── application/                      # Capa de Aplicación (Use Cases)
│   ├── libros/
│   │   └── registrar/
│   │       ├── RegisterBookRequest.java
│   │       ├── RegisterBookResult.java
│   │       └── RegisterBookUseCase.java
├── console/                          # Capa de Presentación (UI)
│   ├── controllers/
│   │   ├── BookController.java
│   │   └── MainController.java
│   ├── forms/
│   │   └── RegisterBookForm.java
│   ├── ioc/
│   │   └── DependencyContainer.java  # Contenedor IoC personalizado
│   └── utils/
│       ├── DisplayHelper.java
│       └── InputHelper.java
├── data/                            # Capa de Datos
│   ├── database/                    # Repositorios (simulación BD)
│   │   ├── AuthorRepository.java
│   │   ├── BookRepository.java
│   │   ├── CategoryRepository.java
│   │   └── PublisherRepository.java
│   └── dummy/                       # Datos de prueba
│       ├── AuthorDummyData.java
│       ├── BookDummyData.java
│       ├── CategoryDummyData.java
│       └── PublisherDummyData.java
├── domain/                          # Capa de Dominio (Entidades)
│   └── entities/
│       ├── Author.java
│       ├── Book.java
│       ├── Category.java
│       └── Publisher.java
└── test/                            # Tests unitarios
```

### 🔄 Flujo de Dependencias

```
Console Layer → Application Layer → Domain Layer ← Data Layer
```

La arquitectura respeta el **Principio de Inversión de Dependencias**, donde las capas internas no dependen de las externas.

### 📦 Descripción de Capas

#### **1. Domain Layer** (`domain/`)

- **Propósito**: Contiene las entidades de negocio y reglas fundamentales
- **Componentes**: `Book`, `Author`, `Category`, `Publisher`
- **Características**: Sin dependencias externas, representa el core del negocio

#### **2. Application Layer** (`application/`)

- **Propósito**: Implementa los casos de uso específicos del sistema
- **Enfoque de aprendizaje**: Separación de lógica de negocio usando objetos Request/Result
- **Ejemplo**: `RegisterBookUseCase` maneja la lógica de registro de libros
- **Características**: Orquesta operaciones entre repositorios y entidades

#### **3. Data Layer** (`data/`)

- **Propósito**: Maneja la persistencia y acceso a datos
- **Enfoque de aprendizaje**: Implementación del patrón Repository para abstraer acceso a datos
- **Componentes**:
  - **Repositories**: Simulan acceso a base de datos con operaciones CRUD
  - **Dummy Data**: Proveedores de datos de prueba en español

#### **4. Console Layer** (`console/`)

- **Propósito**: Interfaz de usuario por consola
- **Enfoque de aprendizaje**: Separación de responsabilidades UI usando controllers y forms
- **Componentes**:
  - **Controllers**: Manejan flujo de navegación y acciones del usuario
  - **Forms**: Capturan y validan entrada de datos
  - **Utils**: Helpers para display e input
  - **IoC Container**: Gestiona inyección de dependencias

### 🏭 Sistema de Dependencias

Como ejercicio de desarrollo de habilidades, he implementado un **contenedor de dependencias personalizado** (`DependencyContainer`) que:

- Inicializa todos los componentes en el orden correcto
- Gestiona las dependencias entre capas
- Carga datos dummy al inicio
- Proporciona acceso controlado a los servicios

### 🔧 Técnicas de Programación Aplicadas (Desarrollo Personal)

Como parte de mi crecimiento como programador, he implementado las siguientes técnicas que van más allá de los requerimientos del curso:

1. **Repository Pattern** - Abstrae el acceso a datos
2. **Use Case Pattern** - Encapsula lógica de negocio específica
3. **Dependency Injection** - Inversión de control personalizada
4. **Form Pattern** - Separación de captura y validación de datos
5. **Helper Pattern** - Utilidades reutilizables para UI
6. **Result Pattern** - Manejo estructurado de respuestas

### 🎯 Estado Actual del Desarrollo

**Requerimientos del TP Implementados:**

- ✅ Registro básico de libros
- ✅ Listado y búsqueda de libros
- ✅ Estadísticas del sistema

**Implementaciones Adicionales para Aprendizaje:**

- ✅ Arquitectura por capas organizada
- ✅ Sistema de inyección de dependencias
- ✅ Separación de responsabilidades avanzada

**Próximas Implementaciones:**

- 🔄 Sistema completo de socios
- 🔄 Gestión de ejemplares y stock
- 🔄 Módulo de préstamos y devoluciones
- 🔄 Sistema de reservas
- 🔄 Consultas y reportes avanzados

### 🚀 Beneficios del Enfoque Implementado

Como parte de mi desarrollo profesional, esta arquitectura me permite:

- Practicar separación de responsabilidades
- Aplicar conceptos avanzados de POO
- Experimentar con patrones de diseño industriales
- Crear código más mantenible y testeable
- Preparar el proyecto para futuras expansiones

---

**Nota**: Este proyecto cumple con los requerimientos del trabajo práctico de Programación Orientada a Objetos, incluyendo implementaciones adicionales realizadas para fortalecer mis conocimientos en desarrollo de software profesional.
