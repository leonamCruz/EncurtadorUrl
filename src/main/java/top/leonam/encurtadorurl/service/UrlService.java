package top.leonam.encurtadorurl.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import top.leonam.encurtadorurl.model.Url;
import top.leonam.encurtadorurl.model.UrlDTO;
import top.leonam.encurtadorurl.model.UrlEntrada;
import top.leonam.encurtadorurl.repository.UrlRepository;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private static final String BASE62 =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Transactional
    public UrlDTO salvar(UrlEntrada urlEntrada) throws Exception {
        UrlValidator validator = new UrlValidator();

        if (!validator.isValid(urlEntrada.getUrl())) throw new Exception("A Url não é válida");

        if (urlRepository.existsByUrlOriginal(urlEntrada.getUrl())) {
            Url original = buscarPorUrlOriginal(urlEntrada.getUrl());
            return toDTO(original);
        }

        // Cria sem encurtada
        Url url = Url.builder()
                .urlOriginal(urlEntrada.getUrl())
                .dataDeCriacao(Instant.now())
                .quantasVezesEntraram(0L)
                .build();

        // Salva para gerar ID
        url = urlRepository.save(url);

        // Converte ID para Base62 (cresce conforme necessário)
        url.setUrlEncurtada(toBase62(url.getId()));

        url = urlRepository.save(url);

        return toDTO(url);
    }

    public Url buscarPorUrlOriginal(String urlOriginal) {
        return urlRepository.findByUrlOriginal(urlOriginal);
    }

    public String buscaPorStringOriginal(String urlModificada) {
        Url url = urlRepository.findByUrlEncurtada(urlModificada);
        if (url == null) return "";

        url.setQuantasVezesEntraram(url.getQuantasVezesEntraram() + 1);
        url = urlRepository.save(url);

        return url.getUrlOriginal();
    }

    private String toBase62(long value) {
        if (value <= 0) throw new IllegalArgumentException("ID inválido para Base62");

        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return sb.reverse().toString();
    }

    private UrlDTO toDTO(Url url) {
        return new UrlDTO(
                url.getUrlOriginal(),
                url.getUrlEncurtada(),
                url.getDataDeCriacao(),
                url.getQuantasVezesEntraram()
        );
    }
}