package tech.leonam.encurtadorurl.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.leonam.encurtadorurl.model.Url;
import tech.leonam.encurtadorurl.model.UrlEntrada;
import tech.leonam.encurtadorurl.service.UrlService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/gerar")
    public ResponseEntity<Url> gerar(@RequestBody UrlEntrada urlEntrada) {
        var urlGerada = urlService.salvar(urlEntrada);
        return ResponseEntity.ok(urlGerada);
    }

}
