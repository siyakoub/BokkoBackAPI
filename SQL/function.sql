use BokkoDB;
DELIMITER //
drop function if exists createUser;
create function createUser(
    p_name varchar(255),
    p_prenom varchar(255),
    p_email varchar(254),
    p_motDePasse varchar(255),
    p_telephone varchar(20),
    p_dateInscription datetime,
    p_statut enum('A', 'I', 'B')
)
returns int
deterministic
BEGIN
    declare user_id int;
    SELECT user_id INTO user_id FROM utilisateur WHERE email = p_email;
    IF user_id IS NOT NULL THEN
        RETURN -1;
    ELSE
        insert into utilisateur(nom, prenom, email, mot_de_passe, telephone, statut, date_inscription)
            values (p_name, p_prenom, p_email, p_motDePasse, p_telephone, p_statut, p_dateInscription);
        SELECT LAST_INSERT_ID() INTO user_id;
    end if;
    return user_id;
end;
//
DELIMITER ;

