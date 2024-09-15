package tech.leonam.encurtadorurl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Url {

    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String urlOriginal;
    @Column(nullable = false, unique = true)
    private String urlEncurtada;
    private LocalDateTime dataDeCriacao;

}
