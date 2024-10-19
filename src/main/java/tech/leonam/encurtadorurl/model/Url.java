package tech.leonam.encurtadorurl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String urlOriginal;
    @Column(nullable = false, unique = true)
    private String urlEncurtada;
    private String dataDeCriacao;
    private Long quantasVezesEntraram;

    @PrePersist
    public void init(){
        quantasVezesEntraram = 0L;
    }

}
