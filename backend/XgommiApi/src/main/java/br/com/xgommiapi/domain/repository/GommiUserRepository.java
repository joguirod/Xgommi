package br.com.xgommiapi.domain.repository;

import br.com.xgommiapi.domain.entity.GommiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GommiUserRepository extends JpaRepository<GommiUser, Long> {
    public GommiUser findByEmail(String email);

    public GommiUser findByLogin(String login);

    public boolean existsByLogin(String login);

    public boolean existsByEmail(String email);
}
