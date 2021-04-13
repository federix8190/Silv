/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso legajos.
 *
 * @name gfd.service#LegajoService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('LegajoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
        recurso: "/legajos/",

        /**
         * Se sobrescribe el método base para invocar a la url del recurso paginado.
         * Esto es debido a que se utiliza un json server para simular una api rest.
         * @function
         */
        listar: function (params) {
            return $http.get(App.REST_BASE + this.recurso, {
                params: params
            });
        },

        listarLegajos: function (params) {
            return $http.get(App.REST_BASE + this.recurso, {
                params: params
            });
        },

        /**
         * Realiza la asignacion de Cpt EE al Legajo
         * @function
         */
        asignarCptE: function (params) {
            return $http.put(App.REST_BASE + this.recurso + params.cedulaIdentidad + '/asignar-cpt-ee', params);
        },
		
		getVinculaciones: function () {
            return $http.get(App.REST_BASE + this.recurso + 'vinculaciones');
        },
        
        getAmbitos: function () {
            return $http.get(App.REST_BASE + this.recurso + 'ambitos');
        },
        getOrientacionFunc: function () {
            return $http.get(App.REST_BASE + this.recurso + 'orientacionFunc');
        },

        getConceptos: function () {
            return $http.get(App.REST_BASE + this.recurso + 'conceptos');
        },

        getDepartamentos: function () {
            return $http.get(App.REST_BASE + this.recurso + 'departamentos');
        },

        getDistritos: function (codigoDepto) {
            return $http.get(App.REST_BASE + this.recurso + codigoDepto + '/distritos');
        },

        getCargos: function (cedulaIdentidad) {
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/cargos');
        },
        
        getFormacionAcademica: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/formacion-academica');
        },

        getFormacionAcademicaHacienda: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/formacion-academica-hacienda');
        },
        
        getRecorridoLaboral: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/recorrido-laboral');
        },

        getCarreraAdministrativa: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/carrera-administrativa');
        },

        getSancionPersonal: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/sancion-personal');
        },

        getSumarioPersonal: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/sumario-personal');
        },

        getEstudiosConcluidos: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/estudios-concluidos');
        },

        getOtrosEstudios: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/otros-estudios');
        },

        getOtrosCursos: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/otros-cursos');
        },

        getCursoInformatica: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/curso-informatica');
        },

        getIdiomas: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/idiomas');
        },

        getMultas: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/multas');
        },

        getMultasControl: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/multas-control');
        },

        getDiasNoTrabajados: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/dias-no-trabajados');
        },

        getExperienciaLaboral: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/experiencia-laboral');
        },

        getEventos: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/eventos');
        },

        getApercibimientos: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/apercibimientos');
        },

        getSumarios: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/sumarios');
        },

        getSuspensiones: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/suspensiones');
        },

        getDestitucion: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/destitucion');
        },

        getSobreseimiento: function (cedulaIdentidad){
            return $http.get(App.REST_BASE + this.recurso + cedulaIdentidad + '/sobreseimiento');
        },
        getCSV: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.titularUnidad === "TODOS")
                delete filters.filters.titularUnidad;
            if(filters.filters.programa === "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma === "TODOS")
                delete filters.filters.subprograma;
            if(filters.filters.ambitoCptF === "TODOS")
                delete filters.filters.ambitoCptF;
            if(filters.filters.ambitoCptE === "TODOS")
                delete filters.filters.ambitoCptE;
            if(filters.filters.orientacionFuncional === "TODOS")
                delete filters.filters.orientacionFuncional;
            if(filters.filters.numeroTramo === "TODOS")
                delete filters.filters.numeroTramo;
            if(filters.filters.vinculacionFuncionario === "TODOS")
                delete filters.filters.vinculacionFuncionario;

            return $http.get(App.REST_BASE + this.recurso + "data/csv",{
                params: filters
            });
        },
        getXLS: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.titularUnidad === "TODOS")
                delete filters.filters.titularUnidad;
            if(filters.filters.programa === "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma === "TODOS")
                delete filters.filters.subprograma;
            if(filters.filters.ambitoCptF === "TODOS")
                delete filters.filters.ambitoCptF;
            if(filters.filters.ambitoCptE === "TODOS")
                delete filters.filters.ambitoCptE;
            if(filters.filters.orientacionFuncional === "TODOS")
                delete filters.filters.orientacionFuncional;
            if(filters.filters.numeroTramo === "TODOS")
                delete filters.filters.numeroTramo;
            
            return $http.get(App.REST_BASE + this.recurso + "data/xls",{
                params: filters
            });
        },
        getPDF: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.titularUnidad === "TODOS")
                delete filters.filters.titularUnidad;
            if(filters.filters.programa === "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma === "TODOS")
                delete filters.filters.subprograma;
            if(filters.filters.ambitoCptF === "TODOS")
                delete filters.filters.ambitoCptF;
            if(filters.filters.ambitoCptE === "TODOS")
                delete filters.filters.ambitoCptE;
            if(filters.filters.orientacionFuncional === "TODOS")
                delete filters.filters.orientacionFuncional;
            if(filters.filters.numeroTramo === "TODOS")
                delete filters.filters.numeroTramo;
            
            return $http.get(App.REST_BASE + this.recurso + "data/pdf",{
                params: filters
            });
        }

    });
}]);
