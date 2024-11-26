package web.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ride_id")
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    @ManyToOne()
    @JoinColumn(name = "driver_id")
    private Drivers drivers;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime = null;
    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;


}
