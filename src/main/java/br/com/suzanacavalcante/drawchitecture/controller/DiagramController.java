package br.com.suzanacavalcante.drawchitecture.controller;

import br.com.suzanacavalcante.drawchitecture.dto.DiagramRequestDTO;
import br.com.suzanacavalcante.drawchitecture.service.DiagramGenerator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diagrams")
public class DiagramController {
    private final List<DiagramGenerator> generators;

    public DiagramController(List<DiagramGenerator> generators) {
        this.generators = generators;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generate (@RequestBody @Valid DiagramRequestDTO request) {
        DiagramGenerator selectedGenerator = generators.stream()
                .filter(g -> g.getSupportedType().equalsIgnoreCase(request.getType()))
                .findFirts()
                .orElseThrow(() -> new RuntimeException("Tipo de Diagrama não suportado!"));

        byte[] imageBytes = selectedGenerator.generate(request.getContent());

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(imageBytes);
    }
}
