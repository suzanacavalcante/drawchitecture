package br.com.suzanacavalcante.drawchitecture.service;

import br.com.suzanacavalcante.drawchitecture.model.Diagram;
import br.com.suzanacavalcante.drawchitecture.repository.DiagramRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
public class DiagramService {
    private final DiagramRepository repository;

    public DiagramService(DiagramRepository repository) {
        this.repository = repository;
    }

    public Diagram saveDiagram(String code, String path){
        Diagram diagram = new Diagram();
        diagram.setMermaidCode(code);
        diagram.setFilePath(path);
        return repository.save(diagram);
    }
}
