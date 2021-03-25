package dhbw.server.services;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Nutzer_Role;
import dhbw.server.exceptions.UserAlreadyExistsException;
import dhbw.server.repositories.NutzerRepository;
import dhbw.server.repositories.Nutzer_RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    NutzerRepository nutzerRepository;
    @Autowired
    Nutzer_RolesRepository nutzerRolesRepository;

    @Transactional
    public void registerNewUserAccount(Nutzer userDto) throws UserAlreadyExistsException {

        if (emailExist(userDto.getNut_email())) {
            throw new UserAlreadyExistsException(
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

    public int getUserId(String email) {
        Nutzer nutzer = nutzerRepository.findByEmail(email);
        return nutzer.getNut_id();
    }

    public ArrayList<Nutzer> getAllNutzer() {
        return (ArrayList<Nutzer>) nutzerRepository.findAll();
    }

    public void addEditorRole(Integer nutzerId) {
        Nutzer_Role nutzer_role = new Nutzer_Role(nutzerId, 3);
        nutzerRolesRepository.save(nutzer_role);
    }

    public void removeEditorRole(Integer nutzerId) {
        Nutzer_Role nutzer_role = nutzerRolesRepository.findEditorByNutzerId(nutzerId);
        nutzerRolesRepository.delete(nutzer_role);
    }

    public ArrayList<Integer> getRoles(Integer id) {
        ArrayList<Integer> nutzer_roles = nutzerRolesRepository.findRolesByNutzerId(id);
        return nutzer_roles;
    }

}
