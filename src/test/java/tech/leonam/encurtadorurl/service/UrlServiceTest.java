package tech.leonam.encurtadorurl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.leonam.encurtadorurl.model.Url;
import tech.leonam.encurtadorurl.model.UrlEntrada;
import tech.leonam.encurtadorurl.repository.UrlRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    private UrlEntrada urlEntrada;
    private Url url;
    private String urlEncurtada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        urlEntrada = new UrlEntrada("http://example.com");
        urlEncurtada = "abcd1234";

        url = new Url();
        url.setUrlOriginal(urlEntrada.getUrl());
        url.setUrlEncurtada(urlEncurtada);
        url.setDataDeCriacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    @Test
    void testSalvarUrlNaoExistente() {
        when(urlRepository.existsByUrlOriginal(urlEntrada.getUrl())).thenReturn(false);
        when(urlRepository.existsByUrlEncurtada(any())).thenReturn(false);
        when(urlRepository.save(any())).thenReturn(url);

        Url resultado = urlService.salvar(urlEntrada);

        assertNotNull(resultado);
        assertEquals(urlEntrada.getUrl(), resultado.getUrlOriginal());
        assertNotNull(resultado.getUrlEncurtada());
        assertEquals(url.getDataDeCriacao(), resultado.getDataDeCriacao());
        verify(urlRepository).save(any(Url.class));
    }

    @Test
    void testSalvarUrlExistente() {
        when(urlRepository.existsByUrlOriginal(urlEntrada.getUrl())).thenReturn(true);
        when(urlRepository.findByUrlOriginal(urlEntrada.getUrl())).thenReturn(url);

        Url resultado = urlService.salvar(urlEntrada);

        assertNotNull(resultado);
        assertEquals(urlEntrada.getUrl(), resultado.getUrlOriginal());
        assertEquals(url.getUrlEncurtada(), resultado.getUrlEncurtada());
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void testGeraAleatorio() {
        String resultado = urlService.geraAleatorio();
        assertNotNull(resultado);
        assertEquals(10, resultado.length());
        assertTrue(resultado.chars().allMatch(Character::isLetter));
    }

    @Test
    void testBuscaPorStringOriginal_ComUrlEncontrada() {
        // Inicializa o contador
        url.setQuantasVezesEntraram(0L);

        when(urlRepository.findByUrlEncurtada(urlEncurtada)).thenReturn(url);

        String result = urlService.buscaPorStringOriginal(urlEncurtada);

        assertEquals(urlEntrada.getUrl(), result);
        assertEquals(1L, url.getQuantasVezesEntraram());
        verify(urlRepository).save(url);
    }

    @Test
    void testBuscaPorStringOriginal_ComUrlNaoEncontrada() {
        when(urlRepository.findByUrlEncurtada("nonExistentUrl")).thenReturn(null);

        String result = urlService.buscaPorStringOriginal("nonExistentUrl");

        assertEquals("", result);
        verify(urlRepository, never()).save(any());
    }
}
