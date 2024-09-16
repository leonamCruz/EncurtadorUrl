package tech.leonam.encurtadorurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.leonam.encurtadorurl.model.Url;
import tech.leonam.encurtadorurl.model.UrlEntrada;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Url findByUrlOriginal(String urlOriginal);
    Url findByUrlEncurtada(String urlEncurtada);
    Boolean existsByUrlEncurtada(String urlEncurtada);
    Boolean existsByUrlOriginal(String urlOriginal);
    @Query("SELECT e.urlOriginal FROM Url e WHERE e.urlEncurtada = :urlEncurtada")
    String findUrlOriginalByUrlEncurtada(@Param("urlEncurtada") String urlEncurtada);}
