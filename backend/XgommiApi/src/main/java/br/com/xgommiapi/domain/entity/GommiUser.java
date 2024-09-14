package br.com.xgommiapi.domain.entity;

import br.com.xgommiapi.dto.GommiUserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GommiUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGommiUser;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "follower")
    private List<FollowerRelation> following;

    @OneToMany(mappedBy = "followed")
    private List<FollowerRelation> followers;

    @ManyToOne
    private Community comunity;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @OneToMany(mappedBy = "userNotified")
    private List<Notification> unreadNotifications;

    private String biography;
    private LocalDateTime registrationDate;

    public GommiUser(GommiUserRequestDTO gommiUserRequestDTO) {
        this.login = gommiUserRequestDTO.login();
        this.password = gommiUserRequestDTO.password();
        this.email = gommiUserRequestDTO.email();
        this.name = gommiUserRequestDTO.name();
        this.biography = gommiUserRequestDTO.biography();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GommiUser gommiUser = (GommiUser) o;
        return Objects.equals(idGommiUser, gommiUser.idGommiUser) && Objects.equals(login, gommiUser.login) && Objects.equals(email, gommiUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGommiUser, login, email);
    }

    @Override
    public String toString() {
        return "GommiUser{" +
                "idGommiUser=" + idGommiUser +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", biography='" + biography + '\'' +
                ", registrationDate=" + registrationDate +
                ", comunity=" + comunity +
                ", posts=" + posts +
                ", unreadNotifications=" + unreadNotifications +
                '}';
    }
}
