# Microservicios-Ventas
Proyecto de Microservicios base con Java 11 y Spring Boot 2.7.x
* Cada Microservico funciona de forma independiente y se puede testar mediando Postamn.
* No se ha realizado la capa frontend

## Microservicios-Config-Data
Este proyecto sirve como un repositorio centralizado para todas las configuraciones de los microservicios. Actúa como una fuente única de verdad para la configuración, asegurando la consistencia y facilitando la gestión y el mantenimiento de las configuraciones en un entorno de microservicios. Puede almacenar configuraciones como propiedades de aplicación, parámetros de entorno, y más.

## Microservicios-Config-Service
Este servicio actúa como un servidor de configuración para los microservicios. Proporciona una interfaz para acceder y gestionar de forma centralizada las configuraciones almacenadas en 'Microservicios-Config-Data'. Este servicio permite a los microservicios individuales obtener su configuración específica al inicio y actualizarse dinámicamente en caso de cambios.

## Microservicios-Customer-Service
Este microservicio gestiona todo lo relacionado con los clientes. Incluye funcionalidades como la creación de cuentas de clientes, gestión de perfiles, historial de pedidos, y asistencia al cliente. Este servicio es fundamental para manejar la interacción directa con los clientes y asegurar una experiencia de usuario óptima.

![image](https://github.com/canaritel/Microservicios-Ventas/assets/57302177/409ed7b9-109f-4809-95b5-87bac09293f9)


## Microservicios-Product-Service
Product-Service es un microservicio diseñado para manejar todas las operaciones relacionadas con productos en un sistema de comercio electrónico. Este servicio es responsable de la gestión de datos de productos, incluyendo su creación, actualización, recuperación y eliminación. Está construido con Spring Boot, lo que facilita su integración con otros microservicios en la arquitectura.

### Estructura del Proyecto
El proyecto está organizado en varias carpetas, cada una con un propósito específico:

* config: Contiene archivos de configuración para el microservicio. Esto incluye configuraciones para conexiones de base de datos, seguridad y otras integraciones necesarias.

* controller: Alberga los controladores que manejan las solicitudes HTTP. Los controladores interactúan con la lógica de negocio y envían respuestas a las solicitudes de los usuarios o de otros servicios.

* entity: Define las clases de entidad que representan los modelos de datos en la base de datos. Cada entidad corresponde a una tabla en la base de datos y es utilizada para operaciones CRUD (Crear, Leer, Actualizar, Eliminar).

* exceptions: Incluye clases personalizadas para manejar diferentes tipos de excepciones. Estas clases ayudan a proporcionar respuestas de error más informativas y específicas.

* repository: Contiene interfaces JPA para el acceso y manipulación de datos en la base de datos. Estos repositorios abstraen las operaciones de la base de datos y simplifican la interacción con los datos.

* service: Almacena clases de servicio que encapsulan la lógica de negocio principal del microservicio. Estas clases son utilizadas por los controladores para realizar operaciones de negocio.

* DataInitializer.java: Clase utilizada para inicializar la base de datos con datos predeterminados en el arranque de la aplicación.

* Imagen uso con Postman, indicando URL y puerto:

![image](https://github.com/canaritel/Microservicios-Ventas/assets/57302177/971c4e39-a58c-44b4-83d7-0928c8cd29a5)


## Microservicios-Shooping-Service
Este microservicio maneja todo lo relacionado con el proceso de compra y carrito de compras. Permite a los clientes agregar productos a su carrito, gestionar los artículos en el carrito, realizar pedidos, y procesar pagos. Es un componente clave para facilitar una experiencia de compra fluida y eficiente.

![image](https://github.com/canaritel/Microservicios-Ventas/assets/57302177/5ff9bc2f-8a6a-4284-8b31-e32686cb8415)

