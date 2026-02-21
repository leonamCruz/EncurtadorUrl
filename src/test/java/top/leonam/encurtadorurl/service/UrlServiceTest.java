package top.leonam.encurtadorurl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import top.leonam.encurtadorurl.model.Url;
import top.leonam.encurtadorurl.model.UrlDTO;
import top.leonam.encurtadorurl.model.UrlEntrada;
import top.leonam.encurtadorurl.repository.UrlRepository;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void deveSalvarNovaUrlGerandoBase62() throws Exception {
        UrlEntrada entrada = new UrlEntrada("https://google.com");

        Url salvoSemEncurtada = Url.builder()
                .id(62L)
                .urlOriginal(entrada.getUrl())
                .dataDeCriacao(Instant.now())
                .quantasVezesEntraram(0L)
                .build();

        Url salvoFinal = Url.builder()
                .id(62L)
                .urlOriginal(entrada.getUrl())
                .urlEncurtada("10")
                .dataDeCriacao(salvoSemEncurtada.getDataDeCriacao())
                .quantasVezesEntraram(0L)
                .build();

        when(urlRepository.existsByUrlOriginal(entrada.getUrl())).thenReturn(false);
        when(urlRepository.save(any(Url.class)))
                .thenReturn(salvoSemEncurtada)
                .thenReturn(salvoFinal);

        UrlDTO dto = urlService.salvar(entrada);

        assertEquals("https://google.com", dto.urlOriginal());
        assertEquals("10", dto.urlEncurtada());
        assertEquals(0L, dto.quantasVezesEntraram());

        verify(urlRepository, times(2)).save(any());
    }

    @Test
    void deveRetornarUrlExistenteSemSalvarNova() throws Exception {
        UrlEntrada entrada = new UrlEntrada("https://google.com");

        Url existente = Url.builder()
                .id(5L)
                .urlOriginal(entrada.getUrl())
                .urlEncurtada("5")
                .dataDeCriacao(Instant.now())
                .quantasVezesEntraram(3L)
                .build();

        when(urlRepository.existsByUrlOriginal(entrada.getUrl())).thenReturn(true);
        when(urlRepository.findByUrlOriginal(entrada.getUrl())).thenReturn(existente);

        UrlDTO dto = urlService.salvar(entrada);

        assertEquals("5", dto.urlEncurtada());
        assertEquals(3L, dto.quantasVezesEntraram());

        verify(urlRepository, never()).save(any());
    }

    @Test
    void deveFalharComUrlInvalida() {
        UrlEntrada entrada = new UrlEntrada("nao-e-url");

        Exception ex = assertThrows(Exception.class,
                () -> urlService.salvar(entrada));

        assertEquals("A Url não é válida", ex.getMessage());
    }

    @Test
    void deveIncrementarContadorAoBuscar() {
        Url url = Url.builder()
                .id(1L)
                .urlOriginal("https://google.com")
                .urlEncurtada("1")
                .quantasVezesEntraram(2L)
                .build();

        when(urlRepository.findByUrlEncurtada("1")).thenReturn(url);
        when(urlRepository.save(any())).thenReturn(url);

        String original = urlService.buscaPorStringOriginal("1");

        assertEquals("https://google.com", original);
        assertEquals(3L, url.getQuantasVezesEntraram());
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrar() {
        when(urlRepository.findByUrlEncurtada("x")).thenReturn(null);

        String resultado = urlService.buscaPorStringOriginal("x");

        assertEquals("", resultado);
        verify(urlRepository, never()).save(any());
    }
}