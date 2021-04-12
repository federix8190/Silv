CREATE PROCEDURE cargarGestionEstrategica @idPei bigint AS
 
 BEGIN TRY

	DECLARE @idCentro bigint

	DECLARE CURSOR_CENTROS CURSOR FOR SELECT id_version FROM centro_responsabilidad where activo = 1 for update

	OPEN CURSOR_CENTROS
	FETCH NEXT FROM CURSOR_CENTROS INTO @idCentro
	WHILE @@FETCH_STATUS = 0
	BEGIN
		insert into gestion_estrategica(pei, centro_responsabilidad) values(@idPei, @idCentro)
		FETCH NEXT FROM CURSOR_CENTROS INTO @idCentro
	END
	CLOSE CURSOR_CENTROS
	DEALLOCATE CURSOR_CENTROS
	SET NOCOUNT OFF

END TRY

BEGIN CATCH
	DEALLOCATE CURSOR_CENTROS
	ROLLBACK
	insert into error(procedimiento, valor, fecha) values('cargarGestionEstrategica', ERROR_MESSAGE(), getdate())
END CATCH
