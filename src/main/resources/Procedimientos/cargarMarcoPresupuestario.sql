CREATE PROCEDURE cargarMarcoPresupuestario @idPei bigint, @idPoi bigint, @userId bigint AS
 
 BEGIN TRY

	DECLARE @idCentro bigint, @idMarcoPresupuestario bigint
	DECLARE CURSOR_CENTROS CURSOR FOR SELECT id_version FROM centro_responsabilidad where activo = 1 for update

	OPEN CURSOR_CENTROS
	FETCH NEXT FROM CURSOR_CENTROS INTO @idCentro
	WHILE @@FETCH_STATUS = 0
	BEGIN
		
		insert into marco_presupuestario(pei, poi, centro_responsabilidad, materia_juridica, activo, fecha_creacion, usuario_creacion) 
			select @idPei, @idPoi, @idCentro, materia_juridica, 1, getdate(), @userId
			from centro_responsabilidad
			where id_version = @idCentro
			
		SELECT @idMarcoPresupuestario = id FROM marco_presupuestario WHERE poi = @idPoi AND centro_responsabilidad = @idCentro
			
		insert into meta(centro_responsabilidad, poi, actividad, linea_accion, unidad_medida, meta_semestre_1, meta_semestre_2, 
				activo, fecha_creacion, usuario_creacion)
			select @idCentro, @idPoi, a.id_version, a.linea_accion, a.unidad_medida, 0, 0, 1, getdate(), @userId
			from centro_responsabilidad cr 
			join tipo_centro_responsabilidad tc on cr.tipo_centro = tc.id_version
			join actividad a on tc.id_version = a.tipo_centro
			where cr.id_version = @idCentro
			
		insert into anexo_1(poi, centro_responsabilidad, marco_presupuestario, insumo, cantidad, total, activo, fecha_creacion, usuario_creacion)
			select @idPoi, @idCentro, @idMarcoPresupuestario, id_version, 0, 0, 1, getdate(), @userId
			from insumo
			where actual = 1 and activo = 1
		
		FETCH NEXT FROM CURSOR_CENTROS INTO @idCentro
	END
	CLOSE CURSOR_CENTROS
	DEALLOCATE CURSOR_CENTROS
	SET NOCOUNT OFF

END TRY

BEGIN CATCH
	DEALLOCATE CURSOR_CENTROS
	ROLLBACK
	insert into error(procedimiento, valor, fecha) values('cargarMarcoPresupuestario', ERROR_MESSAGE(), getdate())
END CATCH
