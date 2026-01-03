package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.TournamentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<TournamentApplication, Long> {

}