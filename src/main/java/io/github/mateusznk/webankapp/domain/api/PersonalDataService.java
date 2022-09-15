package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.personal.PersonalData;
import io.github.mateusznk.webankapp.domain.account.personal.PersonalDataDao;

public class PersonalDataService {
    private final PersonalDataDao personalDataDao = new PersonalDataDao();
    private final PersonalDataMapper personalDataMapper = new PersonalDataMapper();

    public PersonalDataBasicInfo getPersonalData(int id) {
        return personalDataDao.readRecordWithId(id);
    }

    public void checkIfPersonalDataExists(PersonalDataBasicInfo personalDataBasicInfo, int id) {
        if (personalDataDao.isRecordInDb(id)) {
            replacePersonalData(personalDataBasicInfo, id);
        } else {
            createNewPersonalData(personalDataBasicInfo, id);
        }
    }

    private void replacePersonalData (PersonalDataBasicInfo personalDataBasicInfo, int id) {
        personalDataDao.replaceData(personalDataBasicInfo, id);
    }
    private void createNewPersonalData (PersonalDataBasicInfo personalDataBasicInfo, int id) {
        PersonalData personalData = personalDataMapper.map(personalDataBasicInfo);
        personalDataDao.saveData(personalData, id);
    }

    private static class PersonalDataMapper {

        PersonalData map(PersonalDataBasicInfo pdbi) {
            return new PersonalData(
                    pdbi.getName(),
                    pdbi.getSurname(),
                    pdbi.getBirthDate(),
                    pdbi.getCity(),
                    pdbi.getPostalCode(),
                    pdbi.getAddress(),
                    pdbi.getCountry()
            );
        }
    }
}