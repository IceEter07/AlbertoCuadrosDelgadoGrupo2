package com.alberto.tienda.service;

import com.alberto.tienda.data.Producto;
import com.alberto.tienda.data.Resena;
import com.alberto.tienda.data.Usuario;
import com.alberto.tienda.data.dto.ResenasDto;
import com.alberto.tienda.repository.ProductoRepository;
import com.alberto.tienda.repository.ResenaRepository;
import com.alberto.tienda.repository.UsuarioRepository;
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

    public ResenasDto guardarResena(ResenasDto resenasDto){
        Resena nuevaResena = new Resena();
        //ID del usuario
        Usuario user = buscarUsuarioPorId(resenasDto.getIdUsuario());
        nuevaResena.setIdUsuario(user);
        //ID del producto
        Producto product = buscarProductoPorId(resenasDto.getIdProducto());
        nuevaResena.setIdProducto(product);
        nuevaResena.setComentario(resenasDto.getComentario());
        nuevaResena.setFecha(new Date());
        resenaRepository.save(nuevaResena);
        resenasDto.setId(nuevaResena.getIdResena());
        resenasDto.setFecha(nuevaResena.getFecha());

        return resenasDto;
    }

    private Usuario buscarUsuarioPorId(int idUsuario){
        Usuario user = usuarioRepository.getReferenceById(idUsuario);
        return user;
    }

    private Producto buscarProductoPorId(int idProducto){
        Producto product = productoRepository.getReferenceById(idProducto);
        return product;
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
}
