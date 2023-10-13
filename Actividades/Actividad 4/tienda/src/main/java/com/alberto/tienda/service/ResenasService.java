package com.alberto.tienda.service;

import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Resena;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.ResenasDto;
import com.alberto.tienda.exceptions.EntityNotFoundException;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.ResenaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResenasService {

    @Autowired
    ResenaRepository resenaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProductoRepository productoRepository;

    public ResenasDto guardarResena(@Valid ResenasDto resenasDto){
        Resena nuevaResena = new Resena();
        //ID del usuario
        Usuario user = usuarioRepository.findById(resenasDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        nuevaResena.setIdUsuario(user);
        //ID del producto
        Producto product = productoRepository.findById(resenasDto.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException("El producto no existe"));
        nuevaResena.setIdProducto(product);
        nuevaResena.setComentario(resenasDto.getComentario());
        nuevaResena.setFecha(new Date());
        resenaRepository.save(nuevaResena);
        resenasDto.setId(nuevaResena.getIdResena());
        resenasDto.setFecha(nuevaResena.getFecha());

        return resenasDto;
    }

    public List<ResenasDto> obtenerResenas(){
        List<ResenasDto> listaResenas = new ArrayList<>();

        for (Resena comment: resenaRepository.findAll()){
            ResenasDto resenasDto = new ResenasDto();
            resenasDto.setId(comment.getIdResena());
            //Obtener el ID del usuario
            Usuario idUsuario = comment.getIdUsuario();
            resenasDto.setIdUsuario(idUsuario.getId());
            //Obtener el ID del producto
            Producto idProducto = comment.getIdProducto();
            resenasDto.setIdProducto(idProducto.getIdProducto());
            resenasDto.setComentario(comment.getComentario());
            resenasDto.setFecha(comment.getFecha());

            listaResenas.add(resenasDto);
        }
        return  listaResenas;
    }

    public List<ResenasDto> obtenerResenasPorUsuario(Integer idUsuario){
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        List<ResenasDto> listaResenas = new ArrayList<>();

        for (Resena comment: resenaRepository.findByIdUsuario(user)){
            ResenasDto resenasDto = new ResenasDto();
            resenasDto.setId(comment.getIdResena());
            //Obtener el ID del usuario
            resenasDto.setIdUsuario(idUsuario);
            //Obtener el ID del producto
            Producto idProducto = comment.getIdProducto();
            resenasDto.setIdProducto(idProducto.getIdProducto());
            resenasDto.setComentario(comment.getComentario());
            resenasDto.setFecha(comment.getFecha());

            listaResenas.add(resenasDto);
        }
        return  listaResenas;
    }
}
