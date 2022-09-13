package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.personal.PersonalData;
import io.github.mateusznk.webankapp.domain.account.personal.PersonalDataDao;

public class PersonalDataService {
    private final PersonalDataDao personalDataDao = new PersonalDataDao();
    private final PersonalDataMapper personalDataMapper = new PersonalDataMapper();

    public void createNewPersonalData(PersonalDataBasicInfo personalDataBasicInfo) {
        PersonalData personalData = personalDataMapper.map(personalDataBasicInfo);
        personalDataDao.saveDataToDatabase(personalData);
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
