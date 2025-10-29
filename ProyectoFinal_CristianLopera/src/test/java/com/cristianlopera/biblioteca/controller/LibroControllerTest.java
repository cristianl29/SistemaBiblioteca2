package com.cristianlopera.biblioteca.controller;

import com.cristianlopera.biblioteca.dto.LibroDTO;
import com.cristianlopera.biblioteca.service.LibroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService service;

    @Test
    void listarLibros() throws Exception {
        when(service.listar()).thenReturn(List.of(new LibroDTO(1L,"T","A",2000)));
        mockMvc.perform(get("/api/v1/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("T"));
    }
}
