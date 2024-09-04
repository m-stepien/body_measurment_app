package unit;

import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.utils.CircumferenceValidator;
import com.body.measurment.utils.Validator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {
    @Test
    public void checkRequiredFieldBCTestOneParamSet(){
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference.setWaist(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference.setWaist(null);
        basicCircumference.setHip(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference.setHip(null);
        basicCircumference.setChest(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference.setChest(null);
        basicCircumference.setAbdominal(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
    }

    @Test
    public void checkRequiredFieldBCTestThreeParamSet(){
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setWaist(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setChest(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));
        basicCircumference = new BasicCircumference();
        basicCircumference.setHip(22.0);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(22.0);
        assertFalse(validator.checkRequiredField(basicCircumference));

    }

    @Test
    public void checkRequiredFieldBCTestAllParamSet(){
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(22.0);
        assertTrue(validator.checkRequiredField(basicCircumference));
    }

    @Test
    public void checkSignOnFieldsBCAllWrongTest(){
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setChest(-22.0);
        basicCircumference.setHip(-22.0);
        basicCircumference.setAbdominal(-22.0);
        basicCircumference.setWaist(-22.0);
        assertFalse(validator.checkSignOnFields(basicCircumference));
    }

    @Test
    public void checkSignOnFieldsBCOneWrongTest(){
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(22.0);
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setChest(-22.0);
        assertFalse(validator.checkSignOnFields(basicCircumference));
        basicCircumference.setChest(22.0);
        basicCircumference.setHip(-22.0);
        assertFalse(validator.checkSignOnFields(basicCircumference));
        basicCircumference.setHip(22.0);
        basicCircumference.setAbdominal(-22.0);
        assertFalse(validator.checkSignOnFields(basicCircumference));
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setWaist(-22.0);
        assertFalse(validator.checkSignOnFields(basicCircumference));
    }

    @Test
    public void checkSignOnFieldsBCRightTest() {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setHip(22.3);
        basicCircumference.setAbdominal(24.3);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(123.0);
        assertTrue(validator.checkSignOnFields(basicCircumference));
    }

    @Test
    public void checkSignOnFieldsACAllWrongTest(){
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(-22.0);
        additionalCircumference.setThighL(-22.0);
        additionalCircumference.setThighR(-22.0);
        additionalCircumference.setForarmR(-22.0);
        additionalCircumference.setForarmL(-22.0);
        additionalCircumference.setArmR(-22.0);
        additionalCircumference.setArmL(-22.0);
        additionalCircumference.setCalfL(-22.0);
        additionalCircumference.setCalfR(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACOneWrongTest(){
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setNeck(null);
        additionalCircumference.setCalfR(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setCalfR(null);
        additionalCircumference.setCalfL(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setCalfL(null);
        additionalCircumference.setArmL(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setArmL(null);
        additionalCircumference.setArmR(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setArmR(null);
        additionalCircumference.setForarmL(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setForarmL(null);
        additionalCircumference.setForarmR(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setForarmR(null);
        additionalCircumference.setThighR(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setThighR(null);
        additionalCircumference.setThighL(-22.0);
        assertFalse(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACAllRightTest(){
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(22.0);
        additionalCircumference.setThighL(22.0);
        additionalCircumference.setThighR(22.0);
        additionalCircumference.setForarmR(22.0);
        additionalCircumference.setForarmL(22.0);
        additionalCircumference.setArmR(22.0);
        additionalCircumference.setArmL(22.0);
        additionalCircumference.setCalfL(22.0);
        additionalCircumference.setCalfR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACOneRightTest(){
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setNeck(null);
        additionalCircumference.setCalfR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setCalfR(null);
        additionalCircumference.setCalfL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setCalfL(null);
        additionalCircumference.setArmL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setArmL(null);
        additionalCircumference.setArmR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setArmR(null);
        additionalCircumference.setForarmL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setForarmL(null);
        additionalCircumference.setForarmR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setForarmR(null);
        additionalCircumference.setThighR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
        additionalCircumference.setThighR(null);
        additionalCircumference.setThighL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }
}
