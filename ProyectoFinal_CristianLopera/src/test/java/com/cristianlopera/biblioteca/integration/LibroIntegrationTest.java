package com.cristianlopera.biblioteca.integration;

import com.cristianlopera.biblioteca.dto.AuthRequest;
import com.cristianlopera.biblioteca.dto.LibroDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LibroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private String token;

    @BeforeEach
    void setup() throws Exception {
        // Register user
        AuthRequest reg = new AuthRequest("testuser", "secret");
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reg)))
            .andExpect(status().isOk());

        // Login and obtain token
        var loginResult = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reg)))
            .andExpect(status().isOk())
            .andReturn();

        String resp = loginResult.getResponse().getContentAsString();
        var node = mapper.readTree(resp);
        token = node.get("token").asText();
    }

    @Test
    void fullCrudFlow() throws Exception {
        // Create a book
        LibroDTO dto = new LibroDTO(null, "Integracion Titulo", "Autor Test", 2021);
        var create = mockMvc.perform(post("/api/v1/libros")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.titulo").value("Integracion Titulo"))
            .andReturn();

        var created = mapper.readTree(create.getResponse().getContentAsString());
        Long id = created.get("id").asLong();

        // Get by id
        mockMvc.perform(get("/api/v1/libros/" + id)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.autor").value("Autor Test"));

        // List
        mockMvc.perform(get("/api/v1/libros")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

        // Update
        dto.setTitulo("Integracion Updated");
        mockMvc.perform(put("/api/v1/libros/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Integracion Updated"));

        // Delete
        mockMvc.perform(delete("/api/v1/libros/" + id)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());

        // Ensure not found
        mockMvc.perform(get("/api/v1/libros/" + id)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());
    }
}
