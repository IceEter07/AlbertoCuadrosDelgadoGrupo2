Hola mi nombre es Alberto Cuadros Delgado.

Desde el día que se mando el assessment diagnostico he estado viendo cursos básicos,
documentación y demás información de spring boot, con el objetivo de conocer y entender
el uso del framework.

Una vez que tuve los conocimientos básicos pude comenzar a trabajar con el
pequeño proyecto de diagnóstico.

A continuación, listaré punto por punto lo que hice, incluyendo por supuesto las dificultades que se presentaron.


1. Creación de un microservicio de tareas (CRUD): esta fue la parte "simple" del proyecto,
a pesar de no tener experiencia trabajando con API REST pude sobrellevarlo muy bien con el
material que estuve revisando.

2. Manejo de excepciones: esta parte me presento problemas al principio, debido a que no entendía
como realizarlas con "ResponseEntity", sin embargo, despues de revisar material relacionado pude crear
excepciones controladas básicas, las cuales arrojan información detallada de lo que sucede al momento
de mandar una petición.

3. Endpoints: Al igual que en el punto 1, pude crear lo endpoints para cada tarea CRUD,
no supuso mucho problema el crearlos.

4. Pruebas: este punto fue el segundo que más se me dificulto. En primer lugar porque apenas es la
segunda vez que trabajo con pruebas unitarias (ambas ocasiones por mi cuenta). A pesar de todo, pude
crear 2 pruebas: una para el endpoint de "registrar tarea" y para el endpoint de "editar tarea".
Sin embargo, me quedaron muchas dudad de como implementarlas de una buena manera.

5. Seguridad: este punto fue el que más se me dificulto. Al igual que en los puntos anteriores, estuve revisando
material relacionado (principalmente con la creación de tokens JWT).
Sin embargo, no me fue posible implementar spring security porque no pude comprender muy bien
como configurarlo con el tiempo restante.

6. Git: este punto no supuso problema, porque anteriormente estuve trabajando con git y se usarlo de forma
básica.

7. Base de datos: este punto tampoco fue problema, porque pude configurar la BD y cersiorarme que las operaciones
CRUD se hicieran adecuadamente durante el desarrollo del proyecto de diagnóstico.

Nota: Debido a que no pude implemetar el punto de 5 de seguridad, me abstuve de crear una tabla de usuarios
para crear una autenticación. Por lo que mi aplicacion se centra en un CRUD de tareas que cualquiera puede manipular.


Como comentario final, me gustaría decir que fue desafiante el diagnóstico y fructífero, aprendí mucho en el corto
tiempo que tuvimos para desarrollarlo, por lo que me gustaría solidificar mis conocimientos a lo largo del booster.
Sin mas que agregar muchas gracias por detenerse a leer mi mensaje :) .


****************************************************************************************************
Notas importantes del funcionamiento de la aplicación:

Para realizar las operaciones CRUD de la mejor manera:

Toda usan la dirección "/tareas", lo único que cambia es el la petición HTTP utilizada para realizar la operación CRUD
en cuestión.

1. Registrar tarea (método POST, "/tareas"): mandar un body en formato JSON con la descripción y el estado de la tarea.
2. Eliminar tarea (método DELETE, "/tareas/id"): colocar en la URL el id de la tarea que se quiere eliminar.
3. Editar tarea (método PUT, "/tareas/id"): colocar en la URL el id de la tarea que se quiere editar y un body en formato
JSON con la descripción y el estado de la tarea.
4. Consultar tarea (método GET, "tareas"): solo enviar la petición get para obtener el registro de tareas.


*****************************************************************************************************
