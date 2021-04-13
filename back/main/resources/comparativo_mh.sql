select
case when t1.caso = '1' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as caso1, 
case when t1.caso = '2' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as caso2,
case when t1.caso = '3' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as caso3, 
case when t1.caso = '4' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as caso4,
case when t1.caso = '5' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as caso5, 
case when t1.caso = '6' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as caso6,
t1.cedula_identidad, t1.apellido, t1.nombre, t1.fecha_nacimiento, 
split_part(t1.anexos_f, ';', 18) as fecha_ingreso, t1.sexo, t1.edad_f, 
-- Periodo Final
split_part(t1.anexos_f, ';', 17) as profesion_f,
case when t1.grado_academico_f = 'UNIVERSITARIO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as Universitario,
case when t1.grado_academico_f = 'SECUNDARIO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as Secundaria,
case when t1.grado_academico_f = 'PRIMARIO COMPLETO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as PRIMARIO_COMPLETO,
case when t1.grado_academico_f = 'PRIMARIO INCOMPLETO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as PRIMARIO_INCOMPLETO,
t1.grado_academico_f as nivel_educativo_f, CAST ('X' AS VARCHAR(5)) as orientacion_misional_f, 
CAST ('' AS VARCHAR(5)) as orientacion_derecho_f, CAST ('' AS VARCHAR(5)) as orientacion_informatica_f, 
CAST ('' AS VARCHAR(5)) as orientacion_sociales_f, CAST ('' AS VARCHAR(5)) as orientacion_otras_f,
CAST ('' AS VARCHAR(5)) as orientacion_f,  
cte.getAntiguedad(split_part(t1.anexos_f, ';', 18), ':anhoFinal-:mesFinal-01') as antiguedad_f,
split_part(t1.anexos_f, ';', 1) as anho_f, split_part(t1.anexos_f, ';', 2) as mes_f, split_part(t1.anexos_f, ';', 3) as nivel_entidad_f, 
split_part(t1.anexos_f, ';', 4) as entidad_f, split_part(t1.anexos_f, ';', 5) as oee_f, split_part(t1.anexos_f, ';', 6) as linea_f, 
split_part(t1.anexos_f, ';', 7) as fuente_financiamiento_f, split_part(t1.anexos_f, ';', 8) as programa_f, 
split_part(t1.anexos_f, ';', 9) as subprograma_f, split_part(t1.anexos_f, ';', 10) as dependencia_f, 
split_part(t1.anexos_f, ';', 11) as codigo_concurso_f, split_part(t1.anexos_f, ';', 12) as categoria_f, split_part(t1.anexos_f, ';', 13) as concpepto_f,
split_part(t1.anexos_f, ';', 14) as presupuesto_f, split_part(t1.anexos_f, ';', 15) as vinculo_laboral_f, 
CAST ('' AS VARCHAR(5)) as vacante_f,
objeto_111_f, objeto_113_f, objeto_123_f, objeto_125_f, objeto_131_f, objeto_133_f, objeto_137_f, 
-- Devengado Final
objeto_141_f, objeto_144_f, objeto_145_f, objeto_199_f, objeto_849_f,
COALESCE(objeto_111_f, 0) + COALESCE(objeto_113_f, 0) + COALESCE(objeto_123_f, 0) + COALESCE(objeto_125_f, 0) 
+ COALESCE(objeto_131_f, 0) + COALESCE(objeto_133_f, 0) + COALESCE(objeto_137_f, 0) + COALESCE(objeto_141_f, 0) 
+ COALESCE(objeto_144_f, 0) + COALESCE(objeto_145_f, 0) + COALESCE(objeto_199_f, 0) + COALESCE(objeto_849_f, 0) as devengado_f,
split_part(t1.anexos_f, ';', 16) as cargo_F, split_part(t1.anexos_f, ';', 16) as funcion_real_f, 
split_part(t1.anexos_f, ';', 19) as cargo_confianza_f, pt_f.numero_puesto as numero_puesto_f,
-- CEO y CPT Final
ceo_f.codigo as codigo_ceo_f, ceo_f.den as denominacion_ceo_f, CAST ('' AS VARCHAR(5)) as orient_ceo_f_1, 
CAST ('' AS VARCHAR(5)) as orient_ceo_f_2, CAST ('' AS VARCHAR(5)) as orient_ceo_f_3, 
CAST ('' AS VARCHAR(5)) as orient_ceo_f_4, CAST ('' AS VARCHAR(5)) as orient_ceo_f_5,  
cuo_f.nivel as nivel_cuo_f, cuo_f.subnivel as subnivel_cuo_f, cuo_f.numero as nro_cuo_f, 
cuo_f.denominacion as denominacion_cuo_f, cpt_f.nivel as nivel_cpt_f, cpt_f.sub_nivel as subnivel_cpt_f, 
cpt_f.nro_g as nro_g_f, cpt_f.den as denominacion_cpt_f, 
case when cpt_f.tit_unid then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as tit_unid_f, 
cptf_f.nro as nro_cpt_ef_f, cptf_f.ambito as ambito_f, cptf_f.cod_proceso as cod_proceso_f, cptf_f.den as denominacion_cpt_ef_f, 
case when orientacion_F.nombre = 'Conduccion' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as conduccion_f,
case when orientacion_F.nombre = 'Produccion Externa para la Sociedad' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as produccion_externa_f,
case when orientacion_F.nombre = 'Produccion Interna' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as produccion_interna_f,
case when orientacion_F.nombre = 'Produccion Externa para la Administracion Publica' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as prod_adm_pub_f,
CAST ('' AS VARCHAR(5)) as orientacion_cpt_f, pt_f.numero_tramo as numero_tramo_f, cs1.minimo as minimo_f, cs1.maximo as maximo_f,
-- Congruencia Final
case when pt_f.numero_tramo > (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal) 
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as sobre_f,
case when pt_f.numero_tramo <= (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal)
and pt_f.numero_tramo >= (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal)
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as dentro_f,
case when pt_f.numero_tramo < (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal) 
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as debajo_f, 
case when pt_f.numero_tramo > (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal) then 'Sobre' 
when pt_f.numero_tramo <= (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal)
and pt_f.numero_tramo >= (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal) then 'Dentro'
when pt_f.numero_tramo < (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_f.nivel and anho = :anhoFinal and mes = :mesFinal) then 'Debajo' 
else CAST ('' AS VARCHAR(5)) end as congruente_f,
-- Periodo Inicial
split_part(t1.anexos_I, ';', 17) as profesion_I,
case when t1.grado_academico_I = 'UNIVERSITARIO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as Universitario_I,
case when t1.grado_academico_I = 'SECUNDARIO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as Secundaria_I,
case when t1.grado_academico_I = 'PRIMARIO COMPLETO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as PRIMARIO_COMPLETO_I,
case when t1.grado_academico_I = 'PRIMARIO INCOMPLETO' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as PRIMARIO_INCOMPLETO_I,
t1.grado_academico_I as nivel_educativo_I, CAST ('' AS VARCHAR(5)) as orientacion_misional_I, 
CAST ('' AS VARCHAR(5)) as orientacion_derecho_I, CAST ('' AS VARCHAR(5)) as orientacion_informatica_I, 
CAST ('' AS VARCHAR(5)) as orientacion_sociales_I, CAST ('' AS VARCHAR(5)) as orientacion_otras_I,
CAST ('' AS VARCHAR(5)) as orientacion_I, edad_I, 
cte.getAntiguedad(split_part(t1.anexos_f, ';', 18), ':anhoInicio-:mesInicio-01') as antiguedad_I,
split_part(t1.anexos_I, ';', 1) as anho_I, split_part(t1.anexos_I, ';', 2) as mes_I, split_part(t1.anexos_I, ';', 3) as nivel_entidad_I, 
split_part(t1.anexos_I, ';', 4) as entidad_I, split_part(t1.anexos_I, ';', 5) as oee_I, split_part(t1.anexos_I, ';', 6) as linea_I, 
split_part(t1.anexos_I, ';', 7) as fuente_Financiamiento_I, split_part(t1.anexos_I, ';', 8) as programa_I, 
split_part(t1.anexos_I, ';', 9) as subprograma_I, split_part(t1.anexos_I, ';', 10) as dependencia_I, 
split_part(t1.anexos_I, ';', 11) as codigo_concurso_I, split_part(t1.anexos_I, ';', 12) as categoria_I, split_part(t1.anexos_I, ';', 13) as concpepto_I,
split_part(t1.anexos_I, ';', 14) as presupuesto_I, split_part(t1.anexos_I, ';', 15) as vinculo_laboral_I, CAST ('' AS VARCHAR(5)) as vacante_I,
objeto_111_I, objeto_113_I, objeto_123_I, objeto_125_I, objeto_131_I, objeto_133_I, objeto_137_I, 
-- Devengado Inicial
objeto_141_I, objeto_144_I, objeto_145_I, objeto_199_I, objeto_849_I,
COALESCE(objeto_111_I, 0) + COALESCE(objeto_113_I, 0) + COALESCE(objeto_123_I, 0) + COALESCE(objeto_125_I, 0) 
+ COALESCE(objeto_131_I, 0) + COALESCE(objeto_133_I, 0) + COALESCE(objeto_137_I, 0) + COALESCE(objeto_141_I, 0) 
+ COALESCE(objeto_144_I, 0) + COALESCE(objeto_145_I, 0) + COALESCE(objeto_199_I, 0) + COALESCE(objeto_849_I, 0) as devengado_I,
split_part(t1.anexos_I, ';', 16) as cargo_I, split_part(t1.anexos_I, ';', 16) as funcion_real_I, 
split_part(t1.anexos_I, ';', 19) as cargo_confianza_I, pt_I.numero_puesto as nro_puesto_I,
-- CEO y CPT Inicial
ceo_I.codigo as codigo_ceo_I, ceo_I.den as denominacion_ceo_I, CAST ('' AS VARCHAR(5)) as orient_ceo_I_1, CAST ('' AS VARCHAR(5)) as orient_ceo_I_2, 
CAST ('' AS VARCHAR(5)) as orient_ceo_I_3, CAST ('' AS VARCHAR(5)) as orient_ceo_I_4, CAST ('' AS VARCHAR(5)) as orient_ceo_I_5,  
cuo_I.nivel as nivel_cuo_I, cuo_I.subnivel as sub_nivel_cuo_I, cuo_I.numero as nro_cuo_I, cuo_I.denominacion as denominacion_cuo_I, 
cpt_I.nivel as nivel_Cpt_I, cpt_I.sub_nivel as subnivel_Cpt_I, cpt_I.nro_g as nro_cpt_I, cpt_I.den as denominacion_cpt_I, 
case when cpt_I.tit_unid then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as tit_unid_I, 
cptf_I.nro as nro_cptf_I, cptf_I.ambito as ambito_I, cptf_I.cod_proceso as cod_proceso_I, cptf_I.den as denominacion_cptf_I, 
case when orientacion_F.nombre = 'Conduccion' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as Conduccion_I,
case when orientacion_F.nombre = 'Produccion Externa para la Sociedad' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as Produccion_Externa_I,
case when orientacion_F.nombre = 'Produccion Interna' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as ProduccionInterna_I,
case when orientacion_F.nombre = 'Produccion Externa para la Administracion Publica' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as ProdAdmPub_I,
CAST ('' AS VARCHAR(5)) as orientacion_cpt_I, pt_I.numero_tramo as nro_tramo_I, cs2.minimo as minimo_I, cs2.maximo as maximo_I,
-- Congruencia Inicial
case when pt_I.numero_tramo > (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio) 
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as sobre_I,
case when pt_I.numero_tramo <= (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio)
and pt_I.numero_tramo >= (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio)
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as dentro_I,
case when pt_I.numero_tramo < (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio) 
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as debajo_I, 
case when pt_I.numero_tramo > (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio) then 'Sobre' 
when pt_I.numero_tramo <= (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio)
and pt_I.numero_tramo >= (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio) then 'Dentro'
when pt_I.numero_tramo < (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt_I.nivel and anho = :anhoInicio and mes = :mesInicio) then 'Debajo' 
else CAST ('' AS VARCHAR(5)) end as congruente_I
 from (
	select distinct f.cedula_identidad, f.nombre, f.apellido, f.fecha_nacimiento, f.sexo, cte.getDatosAnexo(f.cedula_identidad, :anhoFinal, :mesFinal) as anexos_f,
	cte.getcaso(f.cedula_identidad, :anhoInicio, :mesInicio, :anhoFinal, :mesFinal) as caso, cte.getGradoAcademico(f.cedula_identidad, :anhoFinal, :mesFinal) as grado_academico_f,
	--date_part('year', now()) - date_part('year', f.fecha_nacimiento) as edad_f, 
	cte.getEdad(f.fecha_nacimiento, ':anhoFinal-:mesFinal-01') as edad_f,
	--date_part('year', age(':anhoFinal-:mesFinal-01'::date, f.fecha_nacimiento))::integer as edad_f,
	--cte.getProfesion(f.cedula_identidad, :anhoFinal, :mesFinal) as profesion_f,
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '111') as objeto_111_f, 
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '113') as objeto_113_f,
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '123') as objeto_123_f, 
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '125') as objeto_125_f,
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '131') as objeto_131_f, 
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '133') as objeto_133_f,
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '137') as objeto_137_f, 
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '141') as objeto_141_f,
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '144') as objeto_144_f, 
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '145') as objeto_145_f,
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '199') as objeto_199_f, 
	cte.getDevengado(f.cedula_identidad, :anhoFinal, :mesFinal, '849') as objeto_849_f,
	cte.getGradoAcademico(f.cedula_identidad, :anhoInicio, :mesInicio) as grado_academico_I, 
	--cte.getProfesion(f.cedula_identidad, :anhoInicio, :mesInicio) as profesion_I,
	cte.getEdad(f.fecha_nacimiento, ':anhoInicio-:mesInicio-01') as edad_I,
	--(date_part('year', now()) - date_part('year', f.fecha_nacimiento) - (:anhoFinal - :anhoInicio)) as edad_I,
	--date_part('year', age(':anhoInicio-:mesInicio-01'::date, f.fecha_nacimiento))::integer as edad_I,
	cte.getDatosAnexo(f.cedula_identidad, :anhoInicio, :mesInicio) as anexos_I,
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '111') as objeto_111_I, 
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '113') as objeto_113_I,
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '123') as objeto_123_I, 
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '125') as objeto_125_I,
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '131') as objeto_131_I, 
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '133') as objeto_133_I,
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '137') as objeto_137_I, 
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '141') as objeto_141_I,
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '144') as objeto_144_I, 
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '145') as objeto_145_I,
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '199') as objeto_199_I, 
	cte.getDevengado(f.cedula_identidad, :anhoInicio, :mesInicio, '849') as objeto_849_I
	from cte.v_cte_funcionario f
	where f.cedula_identidad in 
	(select distinct cedula_identidad as cedula_identidad from cte.v_cte_funcionario where (anho=:anhoInicio and mes = :mesInicio) or (anho=:anhoFinal and mes = :mesFinal))
) t1 
-- Left Join final
left join cte.puesto_trabajo pt_f on t1.cedula_identidad = pt_f.cedula_identidad and pt_f.anho=:anhoFinal and pt_f.mes=4
left join cte.ceo ceo_f on pt_f.id_ceo = ceo_f.id
left join cte.cuo cuo_f on ceo_f.id_cuo = cuo_f.id
left join cte.cpt cpt_f on pt_f.id_cpt = cpt_f.id
left join cte.cpt_ef cptf_f on pt_f.id_cpt_ef = cptf_f.id
left join cte.orientacion_funcional orientacion_f on cptf_f.orientacion_func_id = orientacion_f.id
left join cte.cts cs1 on pt_f.numero_tramo = cs1.numero_tramo and cs1.anho = :anhoFinal and cs1.mes = :mesFinal
-- Left Join Inicial
left join cte.puesto_trabajo pt_I on t1.cedula_identidad = pt_I.cedula_identidad and pt_I.anho=:anhoInicio and pt_I.mes=1
left join cte.ceo ceo_I on pt_I.id_ceo = ceo_I.id
left join cte.cuo cuo_I on ceo_I.id_cuo = cuo_I.id
left join cte.cpt cpt_I on pt_I.id_cpt = cpt_I.id
left join cte.cpt_ef cptf_I on pt_I.id_cpt_ef = cptf_I.id
left join cte.orientacion_Funcional orientacion_I on cptf_I.orientacion_Func_id = orientacion_I.id
left join cte.cts cs2 on pt_I.numero_tramo = cs2.numero_tramo and cs2.anho = :anhoInicio and cs2.mes = :mesInicio