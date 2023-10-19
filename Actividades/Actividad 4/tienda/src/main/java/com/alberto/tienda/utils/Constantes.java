package com.alberto.tienda.utils;

public class Constantes {
    //NO EXISTE
    public static final String MENSAJE_USUARIO_NO_EXISTENTE = "El usuario no existe";
    public static final String MENSAJE_ROL_NO_EXISTENTE = "No existe ningún rol";
    public static final String MENSAJE_PRODUCTO_NO_EXISTENTE = "No existe el producto con id ";
    public static final String MENSAJE_PRODUCTOS_TIENDA_NO_EXISTENTES = "No existe ningún producto en la tienda con id ";
    public static final String MENSAJE_PRODUCTOS_CATEGORIA_NO_EXISTENTES = "No existe ningún producto en la categoria con id ";
    public static final String MENSAJE_CARRITO_NO_EXISTENTE = "No existe el carrito con id ";
    public static final String MENSAJE_CATEGORIA_NO_EXISTENTE = "No existe la categoria con id ";
    public static final String MENSAJE_TIENDA_NO_EXISTENTE = "No existe la tienda con id ";
    public static final String MENSAJE_DIRECCION_NO_EXISTENTE = "No existe la direccion con id ";
    public static final String MENSAJE_METODO_PAGO_NO_EXISTENTE = "No existe el metodo de pago con id ";
    public static final String MENSAJE_CARRITO_NO_EXISTENTE_PARA_USUARIO = "No existe ningún carrito activo para el usuario ";
    public static final String MENSAJE_PEDIDO_NO_EXISTENTE = "El pedido especificado no existe o no está activo";
    public static final String MENSAJE_NOTIFICACION_NO_EXISTENTE = "No existe ninguna notificacion";


    //NO REGISTRADAS POR EL USUARIO
    public static final String MENSAJE_USUARIO_DIRECCION_NO_REGISTRADA = "El usuario no tiene registrada la direccion proporcionada";
    public static final String MENSAJE_USUARIO_METODO_PAGO_NO_REGISTRADO = "El usuario no tiene registrado el metodo de pago proporcionada";

    //YA EXISTE
    public static final String MENSAJE_EMAIL_YA_REGISTRADO = "El email ya esta registrado";
    public static final String MENSAJE_ROL_YA_REGISTRADO = "El rol ya esta registrado";
    public static final String MENSAJE_CATEGORIA_YA_REGISTRADA = "La categoria ya esta registrada";
    public static final String MENSAJE_RFC_YA_REGISTRADO = "El RFC ya esta registrado";
    public static final String MENSAJE_NOMBRE_TIENDA_YA_REGISTRADO = "Ya existe una tienda con ese nombre";
    public static final String MENSAJE_CARRITO_YA_REGISTRADO = "Ya existe una carrito activo para el usuario con id ";
    public static final String MENSAJE_PRODUCTO_TIENDA_YA_REGISTRADO = "La tienda con id ";
    public static final String MENSAJE_PRODUCTO_CODIGO_YA_REGISTRADO = " ya tiene registrado un producto con el codigo ";

    //REGISTROS EXITOSOS VARIADOS
    public static final String MENSAJE_USUARIO_REGISTRADO_EXISTOSAMENTE = "Usuario registrado con exito. ID asignado: ";
    public static final String MENSAJE_PRODUCTO_ACTUALIZADO_EXITOSAMENTE = "El producto se actualizo correctamente. El codigo y el ID de la tienda proporcionados coincidieron con un registro ya existente en el servidor";
    public static final String MENSAJE_CARRITO_PRODUCTOS_INSERTADOS_EXITOSAMENTE = "Los productos fueron agregados y/o actualizados en el carrito del usuario correctamente. En este mensaje solo se muestran los productos ingresados en la petición POST. Para comprobar todos los productos del carrito haga la peticion GET al endpoint adecuado.";

    //REGISTROS EXITOSOS GENERICOS
    public static final String MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE = "El registro se realizo con exito.";
    public static final String MENSAJE_PETICION_EXITOSA = "La peticion fue realizada con exito";


    //MENSAJES VARIADOS (CONSULTAS)
    public static final String MENSAJE_CANTIDAD_PRODUCTO_EXCESIVA = "No se pude validar la compra porque se excede el stock del producto con id ";
    public static final String MENSAJE_PEDIDO_SIN_HISTORIAL = "No existe ningún pedido en el historial del usuario";
    public static final String MENSAJE_PRODUCTOS_SIN_HISTORIAL = "No existe ningún producto";
    public static final String MENSAJE_CARRITO_SIN_PRODUCTO = "No existe ningún producto en el carrito";
    public static final String MENSAJE_CATEGORIAS_SIN_HISTORIAL = "No existe ninguna categoria";
    public static final String MENSAJE_USUARIO_SIN_DIRECCION = "El usuario no tiene ninguna dirección";
    public static final String MENSAJE_SIN_HISTORIAL_DE_DIRECCIONES = "No existe ninguna direccion";
    public static final String MENSAJE_SIN_HISTORIAL_DE_METODOS_DE_PAGO = "No existe ningun metodo de pago";
    public static final String MENSAJE_SIN_HISTORIAL_DE_RESENAS = "No existe ningun comentario en el historial";
    public static final String MENSAJE_SIN_HISTORIAL_DE_TIENDAS = "No existe ninguna tienda";
    public static final String MENSAJE_PEDIDO_SIN_ACTIVOS = "No existe ningún pedido activo del usuario";
    public static final String MENSAJE_CONSULTA_EXITOSA = "La consulta se realizo con exito";
    public static final String MENSAJE_CONSULTA_FALLIDA = "La consulta no se realizo con exito";




    //EXCEPCIONES
    public static final String MENSAJE_EXCEPCION_TIPO_PARAMETRO_INCORRECTO = "Ocurrió un error. La solicitud no se pudo completar porque el parametro ";
    public static final String MENSAJE_EXCEPCION_TIPO_PARAMETRO_INCORRECTO_COMPLEMENTO = " no es de tipo ";
    public static final String MENSAJE_EXCEPCION_BODY_INCORRECTO = "Ocurió un problema. La solicitud no se pudo completar porque el mensaje del cuerpo de la solicitud esta mal formado. Verifica la estructura del mensaje";


}
