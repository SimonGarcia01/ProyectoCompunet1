# Proyecto Final Computación en Internet I
# Números Perfectos

El objetivo de este proyecto es que los estudiantes implementen un sistema distribuido
para encontrar números perfectos en un rango dado alto, haciendo uso del modelo
Cliente-Maestro-Trabajadores (Master-Workers) con llamadas asíncronas sobre ICE
(Internet Communications Engine). El sistema debe permitir escalar la computación
distribuyendo el trabajo entre varios nodos trabajadores y permitir al cliente consultar de
manera eficiente los resultados


## Integrantes:
 * ### Daniela Castaño Moreno - A00401805
 * ### Simón García Zuluaga - A00371828
 * ### Juan José Ramos Henao - A00294612

## Instruciones de ejecución
### Detalles para compilar el Slice
Para compilar el Slice de Zero Ice, Graddle se encarga de compilarlo a través del comando:
"""
./gradlew build
"""
Para poder ejecutar este comando, se debe ir a la carpeta raíz del proyecto, ingresar el comando y da "Enter".

### ¿Cómo ejecutar cada componente?
Para la ejecución de cada componente (master, workers y client) se debe realizar después de hacer ".\gradlew build"; se debe ubicar en la carpeta 
"""
/ProyectoCompunet1/master/build/libs
"""
Y desde ahí ejecutar el comando
"""
java -jar master.jar
"""

**Consideraciones importantes:** Se debe asegurar que la carpeta con la versión de Ice esté en la misma carpeta "libs" con el archivo .jar
"""
ice-3.7.6.jar
"""

### Parámetros que deben ingresarse al cliente
El cliente recibe los parámetros de:

*Nombre del cliente:*
- Nombre con el que el cliente conectado quiere ser identificaodo.
  
*Rango:*
- Número de inicio.
- Número de fin.
  
*Números de nodos o workers:*
- Cantidad de workers que quiere utilizar el cliente para realizar el proceso de evaluación e identificación de números perfectos.
