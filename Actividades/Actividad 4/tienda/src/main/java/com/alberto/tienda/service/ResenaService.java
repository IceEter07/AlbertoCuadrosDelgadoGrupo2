package com.alberto.tienda.service;

import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Resena;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.ResenaDto;
import com.alberto.tienda.data.dto.RespuestaGenerica;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.ResenaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import com.alberto.tienda.utils.Constantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResenaService {

    @Autowired
    ResenaRepository resenaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProductoRepository productoRepository;

    public RespuestaGenerica guardarResena(@Valid ResenaDto resenaDto){
        //ID del usuario
        Usuario user = usuarioRepository.findById(resenaDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        //ID del producto
        Producto product = productoRepository.findById(resenaDto.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_PRODUCTO_NO_EXISTENTE+ resenaDto.getIdProducto()));

        Resena nuevaResena = new Resena();
        RespuestaGenerica respuesta = new RespuestaGenerica();

        nuevaResena.setIdUsuario(user);
        nuevaResena.setIdProducto(product);
        nuevaResena.setComentario(resenaDto.getComentario());
        nuevaResena.setFecha(new Date());
        resenaRepository.save(nuevaResena);
        resenaDto.setId(nuevaResena.getIdResena());
        resenaDto.setFecha(nuevaResena.getFecha());
        respuesta.getDatos().add(resenaDto);

        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CAMPO_REGISTRADO_EXISTOSAMENTE);
        return respuesta;
    }

    public RespuestaGenerica obtenerResenasPorProducto(Integer idProducto){
        Producto productoId = productoRepository.findById(idProducto)
                .orElseThrow(()-> new EntityNotFoundException(Constantes.MENSAJE_PRODUCTO_NO_EXISTENTE+idProducto));

        //Obtener las reseñas por ID del producto
        List<Resena> resenasProductos = resenaRepository.findByIdProducto(productoId);
        if (resenasProductos.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_SIN_HISTORIAL_DE_RESENAS);
        }
        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Resena comment: resenasProductos){
            ResenaDto resenaDto = new ResenaDto();
            resenaDto.setId(comment.getIdResena());
            //Obtener el ID del usuario
            Usuario idUsuario = comment.getIdUsuario();
            resenaDto.setIdUsuario(idUsuario.getId());
            //Obtener el ID del producto
            resenaDto.setIdProducto(idProducto);
            resenaDto.setComentario(comment.getComentario());
            resenaDto.setFecha(comment.getFecha());

            respuesta.getDatos().add(resenaDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return  respuesta;
    }

    public RespuestaGenerica obtenerResenasPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.MENSAJE_USUARIO_NO_EXISTENTE));

        List<Resena> resenasUsuarios = resenaRepository.findByIdUsuario(user);
        if (resenasUsuarios.isEmpty()){
            throw new EntityNotFoundException(Constantes.MENSAJE_SIN_HISTORIAL_DE_RESENAS);
        }
        RespuestaGenerica respuesta = new RespuestaGenerica();

        for (Resena comment: resenasUsuarios){
            ResenaDto resenaDto = new ResenaDto();
            resenaDto.setId(comment.getIdResena());
            //Obtener el ID del usuario
            resenaDto.setIdUsuario(idUsuario);
            //Obtener el ID del producto
            Producto idProducto = comment.getIdProducto();
            resenaDto.setIdProducto(idProducto.getIdProducto());
            resenaDto.setComentario(comment.getComentario());
            resenaDto.setFecha(comment.getFecha());

            respuesta.getDatos().add(resenaDto);
        }
        respuesta.setExito(true);
        respuesta.setMensaje(Constantes.MENSAJE_CONSULTA_EXITOSA);
        return  respuesta;
    }
}
