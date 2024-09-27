package unit;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.BasicBodyData;
import com.body.measurement.dto.Weight;
import com.body.measurement.utils.BodyDataValidator;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;

public class BodyValidatorTest {

    @Test
    public void isBodyDataValidSuccessfulTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        Assert.assertTrue(bodyDataValidator.isBodyDataValid(basicBodyData));
    }

    @Test(expected = InvalidDataException.class)
    public void isBodyDataValidFailedNegativeAgeValueTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setAge(-22);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = InvalidDataException.class)
    public void isBodyDataValidFailedToHighAgeValueTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setAge(200);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = InvalidDataException.class)
    public void isBodyDataValidFailedToHighHeightValueTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setHeightInCm(400D);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = InvalidDataException.class)
    public void isBodyDataValidFailedNegativeHeightValueTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setHeightInCm(-3d);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = InvalidDataException.class)
    public void isBodyDataValidSuccessfulWomanAsGenderValueTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setGander("W");
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void isBodyDataValidNoValueSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = new BasicBodyData();
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void isBodyDataValidMissingAgeValueFailedTest() throws Exception {
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setAge(null);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void isBodyDataValidMissingGenderValueFailedTest() throws Exception {
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setGander(null);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void isBodyDataValidMissingHeightValueFailedTest() throws Exception {
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        BasicBodyData basicBodyData = getValidBasicBodyData();
        basicBodyData.setHeightInCm(null);
        bodyDataValidator.isBodyDataValid(basicBodyData);
    }

    @Test
    public void checkIsWeightValidSuccessfulTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setDate(LocalDate.now());
        weight.setWeightInKg(88.3);
        Assert.assertTrue(bodyDataValidator.isWeightValid(weight));
    }

    @Test(expected = MissingRequiredDataException.class)
    public void checkIsWeightValidNoWeightSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setDate(LocalDate.now());
        bodyDataValidator.isWeightValid(weight);
    }

    @Test(expected = InvalidDataException.class)
    public void checkIsWeightValidNoDateSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setWeightInKg(-88.3);
        weight.setDate(LocalDate.now());
        bodyDataValidator.isWeightValid(weight);
    }

    @Test(expected = InvalidDataException.class)
    public void checkIsWeightValidFutureDateSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setWeightInKg(-88.3);
        weight.setDate(LocalDate.of(2055, 12, 11));
        bodyDataValidator.isWeightValid(weight);
    }

    @Test(expected = InvalidDataException.class)
    public void checkIsWeightValidToOldDateSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setWeightInKg(-88.3);
        weight.setDate(LocalDate.of(1755, 12, 11));
        bodyDataValidator.isWeightValid(weight);
    }

    @Test(expected = InvalidDataException.class)
    public void checkIsWeightValidNegativeWeightSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setDate(LocalDate.now());
        weight.setWeightInKg(-88.3);
        bodyDataValidator.isWeightValid(weight);
    }

    @Test(expected = InvalidDataException.class)
    public void checkIsWeightValidToBigWeightSetFailedTest() throws Exception{
        BodyDataValidator bodyDataValidator = new BodyDataValidator();
        Weight weight = new Weight();
        weight.setDate(LocalDate.now());
        weight.setWeightInKg(1000.3);
        bodyDataValidator.isWeightValid(weight);
    }

    private BasicBodyData getValidBasicBodyData(){
        BasicBodyData basicBodyData = new BasicBodyData();
        basicBodyData.setAge(22);
        basicBodyData.setGander("M");
        basicBodyData.setHeightInCm(192.0);
        basicBodyData.setId(1);
        return basicBodyData;
    }
}
