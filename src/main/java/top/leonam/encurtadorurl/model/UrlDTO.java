package top.leonam.encurtadorurl.model;

import java.time.Instant;

public record UrlDTO(
        String urlOriginal,
        String urlEncurtada,
        Instant dataDeCriacao,
        Long quantasVezesEntraram
) {
}
