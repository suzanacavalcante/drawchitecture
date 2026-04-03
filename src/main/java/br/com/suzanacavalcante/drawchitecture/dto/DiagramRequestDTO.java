package br.com.suzanacavalcante.drawchitecture.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DiagramRequestDTO {
    @NotBlank(message = "O conteúdo do diagrama não pode estar vazio")
    private String content;

    private String type = "MERMAID";
}
