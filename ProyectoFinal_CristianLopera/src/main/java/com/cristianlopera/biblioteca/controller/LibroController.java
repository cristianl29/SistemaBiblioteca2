package com.cristianlopera.biblioteca.controller;

import com.cristianlopera.biblioteca.dto.LibroDTO;
import com.cristianlopera.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/libros")
@Tag(name="Libros", description="API para gestionar libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los libros")
    @GetMapping
    public ResponseEntity<List<LibroDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener libro por id")
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtener(@PathVariable Long id){
        LibroDTO dto = service.obtener(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un libro")
    @PostMapping
    public ResponseEntity<LibroDTO> crear(@Valid @RequestBody LibroDTO dto){
        LibroDTO created = service.crear(dto);
        return ResponseEntity.created(URI.create("/api/v1/libros/" + created.getId())).body(created);
    }

    @Operation(summary = "Actualizar un libro")
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LibroDTO dto){
        LibroDTO updated = service.actualizar(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un libro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        boolean deleted = service.eliminar(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
