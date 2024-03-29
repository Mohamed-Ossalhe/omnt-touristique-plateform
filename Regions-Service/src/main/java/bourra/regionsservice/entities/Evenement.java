package bourra.regionsservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity @Table(name = "evenements")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String image;
    private String type;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;
    private String webSite;
    private String telephone;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}