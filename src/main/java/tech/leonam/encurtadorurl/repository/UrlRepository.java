package tech.leonam.encurtadorurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.leonam.encurtadorurl.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Url findByUrlOriginal(String urlOriginal);
    Url findByUrlEncurtada(String urlEncurtada);
    Boolean existsByUrlEncurtada(String urlEncurtada);
    Boolean existsByUrlOriginal(String urlOriginal);
}
