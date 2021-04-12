CREATE PROCEDURE replicar_materia_juridica 
	@id bigint, @codigo varchar(255), @nombre varchar(255), @descripcion varchar(255), @userId bigint AS
	
BEGIN TRY

	BEGIN TRANSACTION
	
	DECLARE @nuevoId bigint, @idActual bigint
		
	SELECT @idActual = id_version FROM materia_juridica WHERE id = @id AND actual = 1
	
	-- 1 Actualizamos la version anterior
	update materia_juridica set actual = 0, fecha_modificacion = getdate(), usuario_modificacion = @userId 
		where actual = 1 and id = @id
	
	-- 2 Creamos una nueva version
	insert into materia_juridica(id, codigo, nombre, descripcion, actual, activo, fecha_creacion, usuario_creacion) 
		values(@id, @codigo, @nombre, @descripcion, 1, 1, getdate(), @userId)
	
	-- 3 Obtenemos el nuevo ID
	SELECT @nuevoId = id_version FROM materia_juridica WHERE id = @id AND actual = 1
	
	-- 4 Actualizamos tablas relacionadas
	update marco_presupuestario set materia_juridica = @nuevoId, fecha_modificacion = getdate(), usuario_modificacion = @userId 
	where materia_juridica = @idActual
	
	update linea_accion set materia_juridica = @nuevoId, fecha_modificacion = getdate(), usuario_modificacion = @userId 
	where materia_juridica = @idActual
	
	update centro_responsabilidad set materia_juridica = @nuevoId, fecha_modificacion = getdate(), usuario_modificacion = @userId 
	where materia_juridica = @idActual
		
	COMMIT TRANSACTION

END TRY

BEGIN CATCH
	ROLLBACK
	insert into error(procedimiento, valor) values('replicar_materia_juridica', ERROR_MESSAGE())
END CATCH