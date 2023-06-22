package tgbot.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "user_")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private String fullName;

    @NotNull
    @Column(unique = true)
    private String telegramId;

    @Setter
    private String currentStateName;

    @Setter
    private String currentStateData;

    public User(String telegramId) {
        this.telegramId = telegramId;
    }

    public boolean isRegistered() {
        return fullName != null;
    }
}
