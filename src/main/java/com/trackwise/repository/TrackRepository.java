package com.trackwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackwise.entity.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
	Optional<Track> findByName(String name);
}
