-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-08-2023 a las 07:21:20
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `crudjava`
--
CREATE DATABASE IF NOT EXISTS `crudjava` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `crudjava`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tareas`
--

CREATE TABLE `tareas` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `tareas`
--

INSERT INTO `tareas` (`id`, `descripcion`, `estado`) VALUES
(3, 'Irse a bañar pero ya', 'En progreso'),
(4, 'Probando el POST desde Postman', 'En progreso'),
(7, 'Probando actualizar elementos', 'Terminado'),
(12, 'Probando actualizar elementos usando Entity 3', 'Terminado'),
(13, 'Probando actualizar elementos usando Entity 4567', 'Terminado'),
(14, 'Probando actualizar elementos usando Entity 4567', 'Pendiente'),
(15, 'Probando la actualización con EX', 'Completado'),
(16, 'Probando actualizar elementos con excepciones', 'Terminado'),
(17, 'Prueba moviendo la lógica del controlador a la capa de servicio', 'En progreso'),
(18, 'Prueba nueva de edición con excepciones', 'En progreso'),
(19, 'Otra prueba de edición JAJAJAJ', 'En progreso'),
(20, 'Otra prueba de edición con excepciones', 'En progreso'),
(21, 'Otra prueba de edición JAJAJAJ', 'En progreso'),
(22, 'Probando la insercion con EX', 'Completado'),
(23, 'Probando la insercion 2 con EX', 'Completado');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tareas`
--
ALTER TABLE `tareas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tareas`
--
ALTER TABLE `tareas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
