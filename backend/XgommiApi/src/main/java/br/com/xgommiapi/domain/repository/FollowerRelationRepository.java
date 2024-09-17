package br.com.xgommiapi.domain.repository;

import br.com.xgommiapi.domain.entity.FollowerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRelationRepository extends JpaRepository<FollowerRelation, Long> {
    @Query("SELECT COUNT(f) > 0 FROM FollowerRelation f " +
            "WHERE f.follower.idGommiUser = :followerId AND f.followed.idGommiUser = :followedId")
    boolean existsByFollowerIdAndFollowedId(@Param("followerId") Long followerId, @Param("followedId") Long followedId);
}
