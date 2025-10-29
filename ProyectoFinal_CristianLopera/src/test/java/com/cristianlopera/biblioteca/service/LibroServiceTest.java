package com.cristianlopera.biblioteca.service;

import com.cristianlopera.biblioteca.dto.LibroDTO;
import com.cristianlopera.biblioteca.entity.Libro;
import com.cristianlopera.biblioteca.repository.LibroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroServiceTest {

    @Test
    void crearYListar() {
        LibroRepository repo = mock(LibroRepository.class);
        Libro l = new Libro(1L, "Titulo", "Autor", 2020);
        when(repo.save(any())).thenReturn(l);
        when(repo.findAll()).thenReturn(List.of(l));

        LibroService s = new LibroService(repo);
        LibroDTO created = s.crear(new LibroDTO(null, "Titulo", "Autor", 2020));
        assertNotNull(created);
        assertEquals("Titulo", created.getTitulo());

        List<LibroDTO> list = s.listar();
        assertEquals(1, list.size());
        verify(repo, times(1)).save(any());
        verify(repo, times(1)).findAll();
    }
}
