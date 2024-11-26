package web.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "drivers")
public class Drivers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer id;
    private String name;
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DriverStatus status;
    @Column(name = "drivers_license")
    private String license;

}

