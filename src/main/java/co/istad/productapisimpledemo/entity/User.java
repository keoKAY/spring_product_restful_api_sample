package co.istad.productapisimpledemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jboss.resteasy.annotations.ClientURI;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;

    // might not need to store the password since keycloak manage the pass
    //private String password;
    @Column(nullable = false, unique = true)
    private String keycloakId;

    @OneToOne(mappedBy ="user", cascade = CascadeType.ALL)
    private Profile profile;
}

// Login with email ON
// Email as username OFF