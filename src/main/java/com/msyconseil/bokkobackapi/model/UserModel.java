package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.msyconseil.bokkobackapi.service.enumerator.UserStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Utilisateur")
public class UserModel extends AbstractModel implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    @Column(name = "nom")
    private String name;

    @Getter @Setter
    @Column(name = "prenom")
    private String firstName;

    @Getter @Setter
    @Column(name = "email")
    private String email;

    @Getter @Setter
    @Column(name = "mot_de_Passe")
    private String password;

    @Getter @Setter
    @Column(name = "telephone")
    private String phoneNumber;

    @Getter @Setter
    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;

    @Getter @Setter
    @Column(name = "statut")
    private String statut;

    public UserModel(){
        super();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        UserModel other = (UserModel) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "User [Name="+name+", FirstName="+firstName+", Email="+email+", Password="+password+", Téléphone="+phoneNumber+", statut="+statut+"]";
    }

}
