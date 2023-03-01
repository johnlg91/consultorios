<h2>Consultorios T-med</h2>

La estructura de este backend son simples, El codigo se divide principalmente en tres directorios: modelos, controladores y repositorios.

Modelos básicamente incluye las clases de los modelos de datos de la base de datos. Contienen un constructor vacio y uno
completo, y los getters y setter de cada atributo.
Todos materiales necesarios para funcionar con springboot.

En repositorios se encuentran las interfaces para crear o alterar los datos,
en esta interface se encarga de mandar las intrucciones en la base de datos.

Por ultimo los controladores, aqui se arman los endpoints para interactuar con el front
Y es donde se interactua con las interfazes de repositorio para crear o alterar los datos.
Los datos se envian y reciben atraves de objetos JSON entre el front y el back.

Varias intrucciones en el fondo son manejadas por el framework springboot,
el cual se interactua a medida de las anotaciones que empiezan con el caracter "@".