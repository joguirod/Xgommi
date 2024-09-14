package br.com.xgommiapi.domain.repository;

import br.com.xgommiapi.domain.entity.FollowerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRelationRepository extends JpaRepository<FollowerRelation, Long> {
}
