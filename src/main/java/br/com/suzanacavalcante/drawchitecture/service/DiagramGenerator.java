package br.com.suzanacavalcante.drawchitecture.service;

public interface DiagramGenerator {
    byte[] generate(String content);

    String getSupportedType();
}
