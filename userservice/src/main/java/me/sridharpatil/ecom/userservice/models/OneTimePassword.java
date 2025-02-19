package me.sridharpatil.ecom.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class OneTimePassword extends BaseModel{
    private Integer value;
    private LocalDateTime expiryDateTime;

    @OneToOne
    private User user;
}
