select CAST ('' AS VARCHAR(5)) as caso1, CAST ('' AS VARCHAR(5)) as caso2, CAST ('' AS VARCHAR(5)) as caso3, 
CAST ('' AS VARCHAR(5)) as caso4, CAST ('X' AS VARCHAR(5)) as caso5, CAST ('' AS VARCHAR(5)) as caso6,
CAST ('' AS VARCHAR(5)) as cedula_identidad, CAST ('' AS VARCHAR(5)) as apellido, 
CAST ('' AS VARCHAR(5)) as nombre, CAST ('' AS VARCHAR(5)) as fecha_nacimiento, 
CAST ('' AS VARCHAR(5)) as fecha_ingreso, CAST ('' AS VARCHAR(5)) as sexo, 
CAST ('' AS VARCHAR(5)) as edad_f, 
-- Periodo Final
CAST ('' AS VARCHAR(5)) as profesion_f, CAST ('' AS VARCHAR(5)) as Universitario, 
CAST ('' AS VARCHAR(5)) as Secundaria, CAST ('' AS VARCHAR(5)) as PRIMARIO_COMPLETO, 
CAST ('' AS VARCHAR(5)) as PRIMARIO_INCOMPLETO,CAST ('' AS VARCHAR(5)) as "Nivel educativo", 
CAST ('' AS VARCHAR(5)) as orientacion_misional_f, CAST ('' AS VARCHAR(5)) as orientacion_derecho_f, 
CAST ('' AS VARCHAR(5)) as orientacion_informatica_f, CAST ('' AS VARCHAR(5)) as orientacion_sociales_f, 
CAST ('' AS VARCHAR(5)) as orientacion_otras_f,CAST ('' AS VARCHAR(5)) as orientacion, 
CAST ('' AS VARCHAR(5)) as antiguedad_f, :anho anho_f, :mes as mes_f, CAST ('12' AS VARCHAR(5)) as nivel_entidad_f, 
CAST ('6' AS VARCHAR(5)) as entidad_f, CAST ('1' AS VARCHAR(5)) as oee, 
a.linea, a.fuente_financiamiento, a.programa, a.subprograma, a.dependencia, 
CAST ('' AS VARCHAR(5)) as codigo_concurso_f, a.categoria, a.concepto,
a.presupuestado, a.vinculacion_funcionario, CAST ('X' AS VARCHAR(5)) as vacante,
CAST ('' AS VARCHAR(5)) as objeto_111_f, CAST ('' AS VARCHAR(5)) as objeto_113_f, 
CAST ('' AS VARCHAR(5)) as objeto_123_f, CAST ('' AS VARCHAR(5)) as objeto_125_f, 
CAST ('' AS VARCHAR(5)) as objeto_131_f, CAST ('' AS VARCHAR(5)) as objeto_133_f, 
CAST ('' AS VARCHAR(5)) as objeto_137_f, CAST ('' AS VARCHAR(5)) as objeto_141_f, 
CAST ('' AS VARCHAR(5)) as objeto_144_f, CAST ('' AS VARCHAR(5)) as objeto_145_f, 
CAST ('' AS VARCHAR(5)) as objeto_199_f, CAST ('' AS VARCHAR(5)) as objeto_849_f, a.devengado,
a.cargo, a.cargo as "Funcion Real que cumple", CAST ('' AS VARCHAR(5)) as "Cargo de confianza", 
CAST ('' AS VARCHAR(5)) as numero_puesto,
-- CEO y CPT Final
CAST ('' AS VARCHAR(5)) as codigo_ceo, CAST ('' AS VARCHAR(5)) as denominacion_ceo, 
CAST ('' AS VARCHAR(5)) as orient_ceo_1, CAST ('' AS VARCHAR(5)) as orient_ceo_2, 
CAST ('' AS VARCHAR(5)) as orient_ceo_3, CAST ('' AS VARCHAR(5)) as orient_ceo_4, 
CAST ('' AS VARCHAR(5)) as orient_ceo_5,  
CAST ('' AS VARCHAR(5)) as nivel_cuo, CAST ('' AS VARCHAR(5)) as subnivel_cuo, CAST ('' AS VARCHAR(5)) numero_cuo, 
CAST ('' AS VARCHAR(5)) as denominacion_cuo, 
cpt.nivel as nivel_cpt, cpt.sub_nivel as sub_nivel, cpt.nro_g, cpt.den as denominacion_cpt, 
case when cpt.tit_unid then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as tit_unid_f, 
cptf.nro as nro_cpt_ef, cptf.ambito as ambito_cptf, cptf.cod_proceso as cod_proceso_cpt_f, 
cptf.den as denominacion_cptf, 
case when orientacion_F.nombre = 'Conduccion' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as "Conduccion",
case when orientacion_F.nombre = 'Produccion Externa para la Sociedad' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as "Produccion Externa",
case when orientacion_F.nombre = 'Produccion Interna' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as "Produccion Interna",
case when orientacion_F.nombre = 'Produccion Externa para la Administracion Publica' then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as "Prod.Adm.Pub",
/*CAST ('' AS VARCHAR(5)) as orientacion_cpt_f, 
pt_f.numero_tramo, cs1.minimo as minimo_f, cs1.maximo as maximo_f,*/
-- Congruencia Final
case when cte.getTramo(a.presupuestado, :anho,:mes) > (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes) 
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as sobre_f,
case when cte.getTramo(a.presupuestado, :anho,:mes) <= (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes)
and cte.getTramo(a.presupuestado, :anho,:mes) >= (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes)
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as dentro_f,
case when cte.getTramo(a.presupuestado, :anho,:mes) < (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes) 
then CAST ('X' AS VARCHAR(5)) else CAST ('' AS VARCHAR(5)) end as debajo_f, 
case when cte.getTramo(a.presupuestado, :anho,:mes) > (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes) then 'Sobre' 
when cte.getTramo(a.presupuestado, :anho,:mes) <= (select max(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes)
and cte.getTramo(a.presupuestado, :anho,:mes) >= (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes) then 'Dentro'
when cte.getTramo(a.presupuestado, :anho,:mes) < (select min(numero_tramo) from cte.cpt_tramos where nivel_cpt = cpt.nivel and anho = :anho and mes = :mes) then 'Debajo'
else CAST ('' AS VARCHAR(5)) end as congruente
from cte.anexos a 
left join cte.cte_cargo_disponible cd on a.id =  cd.id_cargo_disponible
left join cte.cpt cpt on cd.id_cpt = cpt.id
left join cte.cpt_ef cptf on cd.id_cpt_ef = cptf.id
left join cte.orientacion_funcional orientacion_f on cptf.orientacion_func_id = orientacion_f.id
where a.anho=:anho and a.mes=:mes and a.nombre='VACANTE'