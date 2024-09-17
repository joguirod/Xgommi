package br.com.xgommiapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @ManyToOne
    private GommiUser userNotified;

    private String text;
    private LocalDateTime creationDate;
    private boolean unreaded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(idNotification, that.idNotification);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idNotification);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idNotification=" + idNotification +
                ", userNotified=" + userNotified +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
