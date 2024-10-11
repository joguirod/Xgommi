package br.com.xgommiapi.domain.repository;

import br.com.xgommiapi.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByAuthorIdGommiUser(Long authorId);

    @Query("SELECT p FROM Post p WHERE p.author.login = :login")
    List<Post> findByAuthorLogin(@Param("login") String login);
}
