package br.com.suzanacavalcante.drawchitecture.service;

import br.com.suzanacavalcante.drawchitecture.model.Diagram;
import br.com.suzanacavalcante.drawchitecture.repository.DiagramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.List;

@Service
public class DiagramService {
    // Logger oficial do Spring (SLF4J)
    private static final Logger log = LoggerFactory.getLogger(DiagramService.class);
    private final DiagramRepository repository;

    public DiagramService(DiagramRepository repository) {
        this.repository = repository;
    }

    public Diagram processAndSave(String mermaidCode) {
        log.info("Iniciando processamento de novo diagrama. Tamanho do código: {} caracteres", mermaidCode.length());
        long startTime = System.currentTimeMillis();

        try {
            String fileName = UUID.randomUUID().toString();
            String filePath = generateDiagramImage(mermaidCode, fileName);

            Diagram diagram = new Diagram();
            diagram.setMermaidCode(mermaidCode);
            diagram.setFilePath(filePath);
            
            Diagram saved = repository.save(diagram);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("Diagrama processado com sucesso em {}ms. ID: {}", duration, saved.getId());
            
            return saved;
        } catch (Exception e) {
            log.error("Erro crítico ao processar diagrama: {}", e.getMessage());
            throw new RuntimeException("Falha no processamento: " + e.getMessage(), e);
        }
    }

    private String generateDiagramImage(String mermaidCode, String fileName) throws Exception {
        String outputPath = "outputs/" + fileName + ".png";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get("outputs"));

        String encoded = java.util.Base64.getEncoder().encodeToString(mermaidCode.getBytes());
        String urlString = "https://mermaid.ink/img/" + encoded;
        
        log.debug("Conectando à API externa: {}", urlString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000); // 5 segundos de limite para conectar
        connection.setReadTimeout(10000);    // 10 segundos de limite para baixar

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            log.warn("API Mermaid retornou erro HTTP: {}", responseCode);
            throw new RuntimeException("A API externa não conseguiu renderizar o diagrama (HTTP " + responseCode + ")");
        }

        try (java.io.InputStream in = connection.getInputStream()) {
            java.nio.file.Files.copy(in, java.nio.file.Paths.get(outputPath),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        return outputPath;
    }

    public Diagram findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> {
                log.error("Tentativa de busca por ID inexistente: {}", id);
                return new RuntimeException("Diagrama não encontrado.");
            });
    }

    public List<Diagram> findAll() {
        log.info("Consultando histórico de todos os diagramas no banco de dados.");
        return repository.findAll();
    }
}