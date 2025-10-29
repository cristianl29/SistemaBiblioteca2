package com.cristianlopera.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String titulo;

    @Column(nullable=false)
    private String autor;

    @Column(nullable=false)
    private Integer anio;
}
