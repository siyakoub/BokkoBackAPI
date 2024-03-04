package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.msyconseil.bokkobackapi.utils.Utils;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "session")
public class SessionModel extends AbstractModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email",  foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "token")
    private String token;

    @Getter
    @Setter
    @Column(name = "date_debut_session")
    private LocalDateTime dateDebutSession;

    @Getter
    @Setter
    @Column(name = "date_fin_session")
    private LocalDateTime dateFinSession;

    @Getter
    @Setter
    @Column(name = "actif")
    private String actif;

    public SessionModel() {
        super();
    }

    public SessionModel(UserModel userModel) {
        this.userModel = userModel;
        createCode();
        this.dateDebutSession = LocalDateTime.now();
        this.dateFinSession = this.dateDebutSession.plusHours(24);
        this.actif = "E";
    }

    private void createCode() {
        this.setToken(Utils.generateUUID());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(token);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SessionModel other = (SessionModel) obj;
        return Objects.equals(token, other.token);
    }

    @Override
    public String toString() {
        return "Session [utilisateur="+ userModel +", token="+ token +", dateDebutSession="+ dateDebutSession +", dateFinSession="+ dateFinSession +", actif="+ actif +"]";
    }

}
