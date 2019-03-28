package org.softuni.mbnm.repository;

import org.softuni.mbnm.domain.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, String> {

    Optional<Quote> findQuoteByTitle(String title);

    List<Quote> findQuotesByAuthor(String author);
}
