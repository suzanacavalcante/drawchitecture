package br.com.suzanacavalcante.drawchitecture.controller;

import br.com.suzanacavalcante.drawchitecture.dto.DiagramRequestDTO;
import br.com.suzanacavalcante.drawchitecture.model.Diagram;
import br.com.suzanacavalcante.drawchitecture.service.DiagramService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource; // Importação correta
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diagrams")
public class DiagramController {

    private final DiagramService diagramService;

    public DiagramController(DiagramService diagramService) {
        this.diagramService = diagramService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody @Valid DiagramRequestDTO request) {
        try {
            Diagram saved = diagramService.processAndSave(request.getContent());
            return ResponseEntity.ok("Diagrama gerado e salvo com ID: " + saved.getId() + " | Arquivo: " + saved.getFilePath());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro na orquestração do diagrama: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Resource> viewDiagram(@PathVariable UUID id) {
        // Busca o diagrama no banco
        Diagram diagram = diagramService.findById(id);
        
        // No seu modelo Diagram, verifique se o método é getFilePath() ou getImagePath()
        // Usei getFilePath() pois vi no seu POST acima
        Path path = Paths.get(diagram.getFilePath());

        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Diagram>> listAll() {
        List<Diagram> diagrams = diagramService.findAll();
        return ResponseEntity.ok(diagrams);
    }
}