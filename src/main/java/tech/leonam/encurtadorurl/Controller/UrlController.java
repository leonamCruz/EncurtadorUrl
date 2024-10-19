package tech.leonam.encurtadorurl.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.leonam.encurtadorurl.model.Url;
import tech.leonam.encurtadorurl.model.UrlEntrada;
import tech.leonam.encurtadorurl.service.UrlService;

@RestController
@RequestMapping
@AllArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/gerar")
    public ResponseEntity<Url> gerar(@RequestBody UrlEntrada urlEntrada) {
        var urlGerada = urlService.salvar(urlEntrada);
        return ResponseEntity.ok(urlGerada);
    }

    @GetMapping("/{urlModificada}")
    public ResponseEntity<Void> redirect(@PathVariable("urlModificada") String urlModificada) {
        var url = urlService.buscaPorStringOriginal(urlModificada);

        if (url == null || url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, url);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
    }

}
