# Portal CTE
Este proyecto corresponde a la capa de presentación o frontend de la aplicación. Se encuentra desarrollada principalmente con JavaScript, CSS3 y HTML el stack tecnológico se encuentra compuesta por las siguientes tecnologías:
* [Grunt](https://gruntjs.com/) : Task runner utilizado para la planificación y ejecución de tareas para la construcción y optimización del portal.
* [Bower](https://bower.io/): Gestor de dependencias utilizado para la resolución de dependencias de las librerías de terceros a utilizar.
* [AngularJS](https://angularjs.org/): Framework SPA utilizado para para la estructuración general del portal.
* [Highcharts](https://www.highcharts.com/): Librería JavaScript utilizada para la construcción de gráficos.
* [Bootstrap](http://getbootstrap.com/): Framework SASS utilizado para el prototipado de las interfaces con CSS y HTML, además cuenta con componentes pre construidos con JavaScript. 
* [Ngtasty](https://github.com/Zizzamia/ng-tasty): Librería JavaScript utilizada para la implementación de grillas paginadas.

El proyecto se exporta como un proyecto HTML, CSS y JavaScript desplegable en el servidor HTTP Apache2 que fue definido previamente.


## Construcción

### Instalar las dependencias
Primero se debe descargar el instalador de nodejs desde su [página oficial](http://nodejs.org/download/).
Luego debe ejecutar el siguiente comando para descargar las dependencias del proyecto.

```sh
$ npm install
```

Este comando ya descaga todas las dependencias de npm y dispara el `bower install`
para descargar las liberías de terceros. Las liberías js descargadas con bower se
encuentran en `src/vendors`.

Este proyecto utiliza SASS para compilar los scss a css, por lo tanto hay que instalar el compilador que se encuentradesarrollado en ruby con los siguientes comandos:

```
$ sudo apt-get install ruby
$ sudo apt-get install rubygems
$ sudo apt-get install ruby-dev
$ sudo gem install sass
```

### Iniciar el proyecto
Para levantar el proyecto en modo livereload para desarrollo se debe ejecutar el siguiente comando:

```sh
$ grunt server
```

Para compliar y empaquetar la aplicación debe ejcutar el siguiente comando:

```sh
$ grunt
```

El código compilado se ecuentra disponible en `dist`
