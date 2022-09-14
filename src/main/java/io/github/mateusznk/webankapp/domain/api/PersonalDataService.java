package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.personal.PersonalData;
import io.github.mateusznk.webankapp.domain.account.personal.PersonalDataDao;

public class PersonalDataService {
    private final PersonalDataDao personalDataDao = new PersonalDataDao();
    private final PersonalDataMapper personalDataMapper = new PersonalDataMapper();

    public void checkIfPersonalDataExists(PersonalDataBasicInfo personalDataBasicInfo,
                                          int id,
                                          int idOfCountry) {
        boolean isDataInDb = personalDataDao.isRecordInDb(id);
        if ( isDataInDb ) {
            replacePersonalData(personalDataBasicInfo,
                    id,
                    idOfCountry);
        } else {
            createNewPersonalData(personalDataBasicInfo,
                    id,
                    idOfCountry);
        }
    }

    private void replacePersonalData (PersonalDataBasicInfo personalDataBasicInfo,
                                      int id,
                                      Integer idOfCountry) {
        personalDataDao.replaceData(personalDataBasicInfo, id, idOfCountry);
    }
    private void createNewPersonalData (PersonalDataBasicInfo personalDataBasicInfo,
                                      int id,
                                      Integer idOfCountry) {
        PersonalData personalData = personalDataMapper.map(personalDataBasicInfo);
        personalDataDao.saveData(personalData, id, idOfCountry);
    }

    public String getStringCountryFromCountryID(int idOfCountry) {
        return personalDataDao.getCountryWithId(idOfCountry);
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

    public PersonalDataBasicInfo getPersonalData(int id) {
        return personalDataDao.readRecordWithId(id);
    }
}
