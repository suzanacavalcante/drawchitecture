package br.com.suzanacavalcante.drawchitecture.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_diagrams")
@Data
public class Diagram {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mermaidCode;

    @Column(nullable = false)
    private String filePath;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}