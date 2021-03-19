package dhbw.server;

import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    NutzerRepository nutzerRepository;

    @Transactional
    public Nutzer registerNewUserAccount(Nutzer userDto) throws Exception {

        if (emailExist(userDto.getNut_email())) {
            throw new Exception(
                    "There is an account with that email address: "
                            + userDto.getNut_email());
        }
        return userDto;
    }

    private boolean emailExist(String email) {
        return nutzerRepository.findByEmail(email) != null;
    }

}
