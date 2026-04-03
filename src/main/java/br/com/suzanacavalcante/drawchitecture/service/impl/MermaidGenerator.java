package br.com.suzanacavalcante.drawchitecture.service.impl;

import br.com.suzanacavalcante.drawchitecture.service.DiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class MermaidGenerator implements DiagramGenerator {
    @Override
    public byte[] generate(String content) {
        String url = "https://kroki.io/mermaid/png" + Base64.getEncoder().encodeToString(content.getBytes());

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, byte[].class);
    }

    @Override
    public String getSupportedType() {
        return "MERMAID";
    }
}
