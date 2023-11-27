# Microservicios-Ventas
Proyecto de Microservicios base con Java 11 y Spring Boot 2.7.x

## Microservicios-Config-Data
Este proyecto sirve como un repositorio centralizado para todas las configuraciones de los microservicios. Actúa como una fuente única de verdad para la configuración, asegurando la consistencia y facilitando la gestión y el mantenimiento de las configuraciones en un entorno de microservicios. Puede almacenar configuraciones como propiedades de aplicación, parámetros de entorno, y más.

## Microservicios-Config-Service
Este servicio actúa como un servidor de configuración para los microservicios. Proporciona una interfaz para acceder y gestionar de forma centralizada las configuraciones almacenadas en 'Microservicios-Config-Data'. Este servicio permite a los microservicios individuales obtener su configuración específica al inicio y actualizarse dinámicamente en caso de cambios.

## Microservicios-Customer-Service
Este microservicio gestiona todo lo relacionado con los clientes. Incluye funcionalidades como la creación de cuentas de clientes, gestión de perfiles, historial de pedidos, y asistencia al cliente. Este servicio es fundamental para manejar la interacción directa con los clientes y asegurar una experiencia de usuario óptima.

## Microservicios-Product-Service
Este servicio se encarga de la gestión de productos. Incluye funcionalidades como el catálogo de productos, información detallada del producto, gestión de inventario, y precios. Es esencial para mantener actualizada la información del producto y asegurarse de que los clientes tengan acceso a la información más reciente y precisa.

## Microservicios-Shooping-Service
Este microservicio maneja todo lo relacionado con el proceso de compra y carrito de compras. Permite a los clientes agregar productos a su carrito, gestionar los artículos en el carrito, realizar pedidos, y procesar pagos. Es un componente clave para facilitar una experiencia de compra fluida y eficiente.
