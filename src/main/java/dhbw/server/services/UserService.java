package dhbw.server.services;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Nutzer_Role;
import dhbw.server.repositories.NutzerRepository;
import dhbw.server.repositories.Nutzer_RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    NutzerRepository nutzerRepository;
    @Autowired
    Nutzer_RolesRepository nutzerRolesRepository;

    @Transactional
    public void registerNewUserAccount(Nutzer userDto) throws Exception {

        if (emailExist(userDto.getNut_email())) {
            throw new Exception(
                    "There is an account with that email address: "
                            + userDto.getNut_email());
        } else {
            nutzerRepository.save(userDto);
            Nutzer_Role nutzer_role = new Nutzer_Role(nutzerRepository
                    .findIdByEmail(userDto.getNut_email()), 1);
            nutzerRolesRepository.save(nutzer_role);
        }
    }

    private boolean emailExist(String email) {
        return nutzerRepository.findByEmail(email) != null;
    }

}
