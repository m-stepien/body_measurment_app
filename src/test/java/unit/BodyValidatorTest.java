package unit;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.utils.BodyDataValidator;
import org.junit.Test;
import org.junit.Assert;
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

    private BasicBodyData getValidBasicBodyData(){
        BasicBodyData basicBodyData = new BasicBodyData();
        basicBodyData.setAge(22);
        basicBodyData.setGander("M");
        basicBodyData.setHeightInCm(192.0);
        basicBodyData.setId(1);
        return basicBodyData;
    }
}
