package org.locations.optiroute.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String NAME;
    private String COMPANY;
    private String PASSWORD;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private Set<AddressEntity> addressEntity;


}
