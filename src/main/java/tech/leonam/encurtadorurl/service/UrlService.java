package tech.leonam.encurtadorurl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.leonam.encurtadorurl.model.Url;
import tech.leonam.encurtadorurl.model.UrlEntrada;
import tech.leonam.encurtadorurl.repository.UrlRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public Url salvar(UrlEntrada urlEntrada) {
        var url = new Url();
        if (urlRepository.existsByUrlOriginal(urlEntrada.getUrl())) {
            return buscarPorUrlOriginal(urlEntrada.getUrl());
        }

        var encurtada = geraEncurtada();

        url.setUrlOriginal(urlEntrada.getUrl());
        url.setUrlEncurtada(encurtada);
        url.setDataDeCriacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        return urlRepository.save(url);

    }

    public Url buscarPorUrlOriginal(String urlOriginal) {
        return urlRepository.findByUrlOriginal(urlOriginal);
    }

    public Url buscaPorUrlModificada(String urlModificada) {
        return urlRepository.findByUrlEncurtada(urlModificada);
    }

    public String geraAleatorio() {
        SecureRandom random = new SecureRandom();

        char[] caracteres = new char[10];

        for (var i = 0; i < caracteres.length; i++) {
            char c = (char) ('a' + random.nextInt(26));
            var ehMaiusculo = random.nextBoolean();

            if (ehMaiusculo) c = Character.toUpperCase(c);

            caracteres[i] = c;
        }

        return String.valueOf(caracteres);
    }

    public String geraEncurtada() {
        String encurtada;
        do {
            encurtada = geraAleatorio();
        } while (urlRepository.existsByUrlEncurtada(encurtada));
        return encurtada;
    }

    public String buscaPorStringOriginal(String urlModificada) {
        return urlRepository.findUrlOriginalByUrlEncurtada(urlModificada);
    }
}
