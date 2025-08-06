package com.trackwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackwise.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
	Optional<Language> findByName(String name);

	List<Language> findByNameIn(List<String> languages);
}
