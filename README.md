# Sincronizar
Se trata de un cliente de escritorio realizado en Java 17, que sigue el siguiente flujo:

Mediante cron o el programador de tareas, sincroniza todas las noches los datos del servidor con una base local sqlite

Ante cualquier interrupción en la red local y no poder acceder al servidor, y debido a lo crítico del sistema, permite
al usuario ingresar los datos de los pacientes en una versión limitada de la aplicación en la base de datos local.

Una vez restablecida la red, cuenta con métodos que permiten sincronizar los pacientes ingresados en el servidor de 
producción.

Es un sistema de respaldo que permite en el caso de un laboratorio, continuar atendiendo los pacientes garantizando
la integridad de los datos (inicialmente se había contemplado realizar la carga mediante una plantilla xls pero 
esta no garantiza la integridad relacional de la base y presenta inconvenientes con la migración)

