package top.leonam.encurtadorurl.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String urlOriginal;
    @Column(unique = true)
    private String urlEncurtada;
    private Instant dataDeCriacao;
    private Long quantasVezesEntraram;

    @PrePersist
    public void init(){
        quantasVezesEntraram = 0L;
    }

}
