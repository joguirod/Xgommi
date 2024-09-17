package br.com.xgommiapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FollowerRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRelation;

    @ManyToOne
    private GommiUser follower;

    @ManyToOne
    private GommiUser followed;
}
