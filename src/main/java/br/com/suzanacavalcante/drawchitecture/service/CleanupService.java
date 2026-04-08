package br.com.suzanacavalcante.drawchitecture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class CleanupService {
    private static final Logger log = LoggerFactory.getLogger(CleanupService.class);
    private static final String OUTPUT_DIR = "outputs";

    // Roda todos os dias à meia-noite (padrão Cron: seg min hora dia mes sem)
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupOldFiles() {
        log.info("Iniciando faxina diária na pasta de outputs...");
        
        try (Stream<Path> files = Files.list(Paths.get(OUTPUT_DIR))) {
            files.filter(Files::isRegularFile)
                 .forEach(path -> {
                     log.debug("Verificando arquivo: {}", path.getFileName());
                 });
        } catch (Exception e) {
            log.error("Falha ao listar arquivos para limpeza: {}", e.getMessage());
        }
    }
}