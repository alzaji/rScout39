INSERT INTO SCOUT39.ROLES (ID, NOMBREROL) VALUES (0, 'Coordinador')
INSERT INTO SCOUT39.ROLES (ID, NOMBREROL) VALUES (1, 'Scouter')
INSERT INTO SCOUT39.ROLES (ID, NOMBREROL) VALUES (2, 'Educando')
INSERT INTO SCOUT39.PRIVILEGIOS (ID, BORRADO, ESCRITURA, LECTURA) VALUES (0, 'S', 'S', 'S')
INSERT INTO SCOUT39.PRIVILEGIOS (ID, BORRADO, ESCRITURA, LECTURA) VALUES (1, 'N', 'S', 'S')
INSERT INTO SCOUT39.PRIVILEGIOS (ID, BORRADO, ESCRITURA, LECTURA) VALUES (2, 'N', 'N', 'S')
INSERT INTO SCOUT39.ROLES_PRIVILEGIOS (ROLES_ID, PRIVILEGIOS_ID) VALUES (0, 0)
INSERT INTO SCOUT39.ROLES_PRIVILEGIOS (ROLES_ID, PRIVILEGIOS_ID) VALUES (1, 0)
INSERT INTO SCOUT39.ROLES_PRIVILEGIOS (ROLES_ID, PRIVILEGIOS_ID) VALUES (1, 1)
INSERT INTO SCOUT39.ROLES_PRIVILEGIOS (ROLES_ID, PRIVILEGIOS_ID) VALUES (2, 1)
INSERT INTO SCOUT39.GRUPO (ID, DESCRIPCION, NOMBRE) VALUES (0, '', 'Manada de Tha')
INSERT INTO SCOUT39.GRUPO (ID, DESCRIPCION, NOMBRE) VALUES (1, '', 'Tropa de Kim')
INSERT INTO SCOUT39.GRUPO (ID, DESCRIPCION, NOMBRE) VALUES (2, '', 'Unidad Esculta Siryu')
INSERT INTO SCOUT39.GRUPO (ID, DESCRIPCION, NOMBRE) VALUES (3, '', 'Clan Rover Almogama')
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (0, 'Coord', '1', NULL, '1234', 'coordinador@scout39.org', '2018-05-24', NULL, 'Coordinador', 0)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (1, 'ScouterTHA', 'THA', NULL, '1234', 'scoutertha@scout39.org', '2018-05-25', NULL, 'Scouter', 1)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (2, 'ScouterKIM', 'KIM', NULL, '1234', 'scouterkim@scout39.org', '2018-05-25', NULL, 'Scouter', 1)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (3, 'ScouterSIRYU', 'SIRYU', NULL, '1234', 'scoutersiryu@scout39.org', '2018-05-25', NULL, 'Scouter', 1)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (4, 'ScouterALMOGAMA', 'ALMOGAMA', NULL, '1234', 'scouteralmogama@scout39.org', '2018-05-25', NULL, 'Scouter', 1)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (5, 'EducandoTHA', 'THA', NULL, '1234', 'educandotha@scout39.org', '2018-05-25', NULL, 'Educando', 2)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (6, 'EducandoKIM', 'KIM', NULL, '1234', 'educandokim@scout39.org', '2018-05-25', NULL, 'Educando', 2)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (7, 'EducandoSIRYU', 'SIRYU', NULL, '1234', 'educandosiryu@scout39.org', '2018-05-25', NULL, 'Educando', 2)
INSERT INTO SCOUT39.USUARIO (ID, "ALIAS", APELLIDOS, AVATAR, DIGEST, EMAIL, FECHA_ALTA, FECHA_BAJA, NOMBRE, ROLES_ID) VALUES (8, 'EducandoALMOGAMA', 'ALMOGAMA', NULL, '1234', 'educandoalmogama@scout39.org', '2018-05-25', NULL, 'Educando', 2)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (0, 0)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (1, 1)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (1, 2)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (1, 3)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (1, 4)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (2, 5)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (2, 6)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (2, 7)
INSERT INTO SCOUT39.ROLES_USUARIO (ROLES_ID, USUARIOS_ID) VALUES (2, 8)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (0, '2018-05-25', NULL, 1, 0)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (1, '2018-05-25', NULL, 2, 1)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (2, '2018-05-25', NULL, 3, 2)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (3, '2018-05-25', NULL, 4, 3)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (4, '2018-05-25', NULL, 5, 0)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (5, '2018-05-25', NULL, 6, 1)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (6, '2018-05-25', NULL, 7, 2)
INSERT INTO SCOUT39.ACCESOGRUPO (ID, FECHA_ALTA_GRUPO, FECHA_BAJA_GRUPO, USUARIO_GRUPO_ID, GRUPO_ID) VALUES (7, '2018-05-25', NULL, 8, 3)
INSERT INTO SCOUT39.EVENTOS (ID, DESCRIPCION, FECHA, LATITUD, LONGITUD, NOMBRE) VALUES (0, 'Día en el que se entrega esta aplicación.', '2018-06-03 23:55:00.0', 36.71470930, -4.47571480, 'Deadline')
INSERT INTO SCOUT39.EVENTOS (ID, DESCRIPCION, FECHA, LATITUD, LONGITUD, NOMBRE) VALUES (1, 'Se realiza la presentación en este día.', '2018-06-06 10:45:00.0', 36.71470930, -4.47571480, 'Presentación')
INSERT INTO SCOUT39.EVENTOS (ID, DESCRIPCION, FECHA, LATITUD, LONGITUD, NOMBRE) VALUES (2, '¡Nos vamos de vacaciones!', '2018-07-01 00:00:00.0', 36.71470930, -4.47571480, 'Vacaciones')
INSERT INTO SCOUT39.EVENTOS (ID, DESCRIPCION, FECHA, LATITUD, LONGITUD, NOMBRE) VALUES (3, 'Comienzan los exámenes de septiembre', '2018-09-03 00:00:00.0', 36.71470930, -4.47571480, 'Vuelta a la rutina')
INSERT INTO SCOUT39.COMENTARIOS (CUERPO, IDCOMENTARIOS, IDEVENTO, IDUSUARIO) VALUES ('Ya hemos terminado y podemos descansar', 0, 0, 0)
INSERT INTO SCOUT39.COMENTARIOS (CUERPO, IDCOMENTARIOS, IDEVENTO, IDUSUARIO) VALUES ('Este es un ejemplo de comentario dentro de un evento', 0, 1, 0)
INSERT INTO SCOUT39.COMENTARIOS (CUERPO, IDCOMENTARIOS, IDEVENTO, IDUSUARIO) VALUES ('Acabamos por este curso, nos vemos en septiembre', 0, 2, 0)
INSERT INTO SCOUT39.COMENTARIOS (CUERPO, IDCOMENTARIOS, IDEVENTO, IDUSUARIO) VALUES ('Noooooo', 0, 3, 0)
INSERT INTO SCOUT39.OBJETO (ID, NOMBRE) VALUES (0, 'evento0')
INSERT INTO SCOUT39.OBJETO (ID, NOMBRE) VALUES (1, 'evento1')
INSERT INTO SCOUT39.OBJETO (ID, NOMBRE) VALUES (2, 'evento2')
INSERT INTO SCOUT39.OBJETO (ID, NOMBRE) VALUES (3, 'evento3')
INSERT INTO SCOUT39.OBJETO_EVENTOS (OBJETO_ID, LISTAEVENTOS_ID) VALUES (0, 0)
INSERT INTO SCOUT39.OBJETO_EVENTOS (OBJETO_ID, LISTAEVENTOS_ID) VALUES (1, 1)
INSERT INTO SCOUT39.OBJETO_EVENTOS (OBJETO_ID, LISTAEVENTOS_ID) VALUES (2, 2)
INSERT INTO SCOUT39.OBJETO_EVENTOS (OBJETO_ID, LISTAEVENTOS_ID) VALUES (3, 3)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (0, 0)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (0, 1)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (0, 2)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (0, 3)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (1, 0)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (1, 1)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (1, 2)
INSERT INTO SCOUT39.OBJETO_PRIVILEGIOS (LISTAPRIVILEGIOS_ID, LISTAOBJETOS_ID) VALUES (1, 3)




