package dhbw.server.services;

import dhbw.server.entities.Kurs_Von_Nutzer;
import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Nutzer_Role;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.exceptions.UserAlreadyExistsException;
import dhbw.server.helper.KVS;
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

    @Transactional
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

    @Transactional
    public void registerNewDozentAccount(Nutzer userDto, ArrayList<KVS> kvs) throws UserAlreadyExistsException {

        if (emailExist(userDto.getNut_email())) {
            throw new UserAlreadyExistsException(
                    "Diese E-Mail wird bereits verwendet: "
                            + userDto.getNut_email());
        } else {
            Nutzer_Role nutzer_role = saveNutzer(userDto, 1);

            ArrayList<Integer> kursIds = new ArrayList<>();
            for (KVS obj : kvs) {
                if (!kursIds.contains(obj.getKursId())) {
                    kursIds.add(obj.getKursId());
                    Kurs_Von_Nutzer kursVonNutzer = new Kurs_Von_Nutzer(nutzer_role.getNut_id(), obj.getKursId());
                    kursVonNutzerRepository.save(kursVonNutzer);
                }
                Vorlesung_Von_Nutzer vorlesungVonNutzer = new Vorlesung_Von_Nutzer(nutzer_role.getNut_id(), obj.getVorlesungId(),
                        obj.getStunden(), obj.getKursId());
                vorlesungVonNutzerRepository.save(vorlesungVonNutzer);
            }
        }
    }

    @Transactional
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
