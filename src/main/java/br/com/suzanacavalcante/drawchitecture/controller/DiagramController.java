package br.com.suzanacavalcante.drawchitecture.controller;

import br.com.suzanacavalcante.drawchitecture.dto.DiagramRequestDTO;
import br.com.suzanacavalcante.drawchitecture.model.Diagram;
import br.com.suzanacavalcante.drawchitecture.service.DiagramService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diagrams")
public class DiagramController {

    private final DiagramService diagramService;

    // Injeção via construtor: Testável e limpo
    public DiagramController(DiagramService diagramService) {
        this.diagramService = diagramService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody @Valid DiagramRequestDTO request) {
        String generatedPath = "outputs/diagrama_provisorio.png"; 

        try {
            // Lógica de geração da imagem (Mermaid CLI) aqui...
            
            // 2. SALVAMENTO NO BANCO:
            // Assim que a geração termina, chamamos o service
            Diagram savedDiagram = diagramService.saveDiagram(
                request.getContent(),
                generatedPath
            );

            return ResponseEntity.ok("Diagrama gerado e salvo com ID: " + savedDiagram.getId());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar: " + e.getMessage());
        }
    }
}