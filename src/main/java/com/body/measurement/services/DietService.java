package com.body.measurement.services;


import com.body.measurement.dto.BodyData;
import com.body.measurement.dto.UserDemand;
import com.body.measurement.repositories.UserDemandRepository;
import com.body.measurement.utils.MacroDemandCalc;
import com.body.measurement.utils.MacroDemandCalcBasic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DietService {
    private static final Logger log = LoggerFactory.getLogger(DietService.class);
    private final UserDemandRepository userDemandRepository;
    private final BodyDataProvider bodyDataProvider;


    @Autowired
    public DietService(UserDemandRepository userDemandRepository, BodyDataProvider bodyDataProvider) {
        this.userDemandRepository = userDemandRepository;
        this.bodyDataProvider = bodyDataProvider;
    }


    public Optional<UserDemand> getUserDemand() {
        return this.userDemandRepository.findById(1L);
    }

    public void saveUserDemand(Long userId) {
        BodyData bodyData = this.bodyDataProvider.getBodyDataByUserId(userId);

    }


    private UserDemand createUserDemand(BodyData bodyData) {
        MacroDemandCalc macroDemandCalc = new MacroDemandCalcBasic();
        UserDemand userDemand = new UserDemand();
        double ppm = macroDemandCalc.calcPPM(bodyData.age(), bodyData.weightKg(),
                bodyData.heightInCm(), bodyData.gender());
        double protein = macroDemandCalc.protein(bodyData.weightKg());
        double fat = macroDemandCalc.fat(bodyData.weightKg());

        userDemand.setPpm(ppm);
        userDemand.setFat(fat);
        userDemand.setProtein(protein);
        return userDemand;
    }
}
