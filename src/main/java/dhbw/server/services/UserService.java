package dhbw.server.services;

import dhbw.server.entities.Kurs_Von_Nutzer;
import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Nutzer_Role;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.exceptions.UserAlreadyExistsException;
import dhbw.server.helper.Kurs_Vorlesung_Stunden;
import dhbw.server.helper.RegisterForm;
import dhbw.server.repositories.Kurs_Von_NutzerRepository;
import dhbw.server.repositories.NutzerRepository;
import dhbw.server.repositories.Nutzer_RolesRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
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
    @Autowired
    Kurs_Von_NutzerRepository kursVonNutzerRepository;
    @Autowired
    Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;

    @Transactional(rollbackFor = Exception.class)
    public void registerNewStudentAccount(Nutzer userDto, Integer kursId) throws UserAlreadyExistsException {

        if (emailExist(userDto.getNut_email())) {
            throw new UserAlreadyExistsException(
                    "Diese E-Mail wird bereits verwendet: "
                            + userDto.getNut_email());
        } else {
            saveNutzer(userDto, 2);
            kursVonNutzerRepository.save(new Kurs_Von_Nutzer(userDto.getNut_id(), kursId));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void registerNewDozentAccount(RegisterForm registerForm) throws UserAlreadyExistsException {

        if (emailExist(registerForm.getNut_email())) {
            throw new UserAlreadyExistsException(
                    "Diese E-Mail wird bereits verwendet: "
                            + registerForm.getNut_email());
        } else {
            Nutzer nutzer = new Nutzer(registerForm.getNut_vorname(), registerForm.getNut_nachname(),
                    registerForm.getNut_email(), registerForm.getNut_anrede(),
                    registerForm.getNut_titel(), registerForm.getNut_passwort());
            Nutzer_Role nutzer_role = saveNutzer(nutzer, 1);

            ArrayList<Integer> kursIds = new ArrayList<>();
            for (Kurs_Vorlesung_Stunden obj : registerForm.getKvs()) {
                if (!kursIds.contains(obj.getKvn_kurs_id())) {
                    kursIds.add(obj.getKvn_kurs_id());
                    Kurs_Von_Nutzer kursVonNutzer = new Kurs_Von_Nutzer(nutzer_role.getNut_id(), obj.getKvn_kurs_id());
                    kursVonNutzerRepository.save(kursVonNutzer);
                }
                System.out.println("Vor_id: " + obj.getVvn_vor_id());
                Vorlesung_Von_Nutzer vorlesungVonNutzer = new Vorlesung_Von_Nutzer(nutzer_role.getNut_id(), obj.getVvn_vor_id(),
                        obj.getVvn_stnd(), obj.getVvn_kurs_id());
                vorlesungVonNutzerRepository.save(vorlesungVonNutzer);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Nutzer_Role saveNutzer(Nutzer nutzer, Integer role_id) {
        nutzerRepository.save(nutzer);
        Nutzer_Role nutzer_role = new Nutzer_Role(nutzerRepository
                .findIdByEmail(nutzer.getNut_email()), role_id);
        nutzerRolesRepository.save(nutzer_role);

        return nutzer_role;
    }

    private boolean emailExist(String email) {
        return nutzerRepository.findByEmail(email) != null;
    }

    public int getUserId(String email) {
        Nutzer nutzer = nutzerRepository.findByEmail(email);
        return nutzer.getNut_id();
    }

    public ArrayList<Nutzer> getAllDozenten() {
        ArrayList<Integer> ids = nutzerRolesRepository.findAllDozenten();
        return (ArrayList<Nutzer>) nutzerRepository.findAllById(ids);
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
