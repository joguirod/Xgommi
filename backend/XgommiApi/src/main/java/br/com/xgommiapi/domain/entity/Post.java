package br.com.xgommiapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPost;

    @Column(nullable = false)
    private String text;

    @JsonIgnore
    @ManyToOne
    private GommiUser author;

    @OneToMany(mappedBy = "originalPost")
    private List<Comment> commentsList;

    @ManyToOne
    private Community comunity;

    private int upvotes;
    private int downvotes;
    private boolean isRepost;

    public Post(GommiUser author, String text) {
        this.author = author;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(idPost, post.idPost) && Objects.equals(author, post.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPost, author);
    }

    @Override
    public String toString() {
        return "Post{" +
                "idPost=" + idPost +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", commentsList=" + commentsList +
                ", comunity=" + comunity +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", isRepost=" + isRepost +
                '}';
    }
}
