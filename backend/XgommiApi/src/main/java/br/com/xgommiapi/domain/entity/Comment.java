package br.com.xgommiapi.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment extends Post {
    @ManyToOne
    private Post originalPost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(originalPost, comment.originalPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), originalPost);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "originalPost=" + originalPost +
                '}';
    }
}
