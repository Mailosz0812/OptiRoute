package org.locations.optiroute.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String NAME;
    private String TEXT;
    private Double LAT;
    private Double LON;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID",referencedColumnName = "ID")
    private UserEntity userEntity;
}
