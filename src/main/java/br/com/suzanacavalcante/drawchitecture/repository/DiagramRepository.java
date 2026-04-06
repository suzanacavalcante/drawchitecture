package br.com.suzanacavalcante.drawchitecture.repository;

import br.com.suzanacavalcante.drawchitecture.model.Diagram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DiagramRepository extends JpaRepository<Diagram, UUID> {
    
}
