package tech.leonam.encurtadorurl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.leonam.encurtadorurl.model.Url;
import tech.leonam.encurtadorurl.model.UrlEntrada;
import tech.leonam.encurtadorurl.repository.UrlRepository;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public Url salvar(UrlEntrada urlEntrada) {
        Url url = buscarPorUrlOriginal(urlEntrada.getUrl());

        return urlRepository.save(url);
    }

    public Url buscarPorUrlOriginal(String urlOriginal) {
        return urlRepository.findByUrlOriginal(urlOriginal);
    }

    public Url buscaPorUrlModificada(String urlModificada) {
        return urlRepository.findByUrlEncurtada(urlModificada);
    }

    public String geraEncurtada(){
        SecureRandom random = new SecureRandom();

        char[] caracteres = new char[10];

        for(var i = 0; i < caracteres.length; i++){
            char c = (char)('a' + random.nextInt(26));
            var ehMaiusculo = random.nextBoolean();

            if (ehMaiusculo) c = Character.toUpperCase(c);

            caracteres[i] = c;
        }

        return String.valueOf(caracteres);
    }

}
