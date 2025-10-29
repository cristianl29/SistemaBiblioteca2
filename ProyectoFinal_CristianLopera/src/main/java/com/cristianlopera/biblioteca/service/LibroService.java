package com.cristianlopera.biblioteca.service;

import com.cristianlopera.biblioteca.dto.LibroDTO;
import com.cristianlopera.biblioteca.entity.Libro;
import com.cristianlopera.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final LibroRepository repo;

    public LibroService(LibroRepository repo) {
        this.repo = repo;
    }

    private LibroDTO mapToDto(Libro l){
        return new LibroDTO(l.getId(), l.getTitulo(), l.getAutor(), l.getAnio());
    }

    private Libro mapToEntity(LibroDTO dto){
        return new Libro(dto.getId(), dto.getTitulo(), dto.getAutor(), dto.getAnio());
    }

    public LibroDTO crear(LibroDTO dto){
        Libro saved = repo.save(mapToEntity(dto));
        return mapToDto(saved);
    }

    public List<LibroDTO> listar(){
        return repo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public LibroDTO obtener(Long id){
        return repo.findById(id).map(this::mapToDto).orElse(null);
    }

    public LibroDTO actualizar(Long id, LibroDTO dto){
        return repo.findById(id).map(l -> {
            l.setTitulo(dto.getTitulo());
            l.setAutor(dto.getAutor());
            l.setAnio(dto.getAnio());
            return mapToDto(repo.save(l));
        }).orElse(null);
    }

    public boolean eliminar(Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
