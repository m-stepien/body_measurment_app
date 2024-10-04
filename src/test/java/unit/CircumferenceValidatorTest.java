package unit;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.AdditionalCircumference;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.utils.CircumferenceValidator;
import com.body.measurement.utils.Validator;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircumferenceValidatorTest {

    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestNoParamSet() throws MissingRequiredDataException {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        validator.checkRequiredField(basicCircumference);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestWaistParamSet() throws MissingRequiredDataException {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestChestParamSet() throws MissingRequiredDataException {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setChest(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestHipParamSet() throws MissingRequiredDataException {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setHip(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestAbdominalParamSet() throws MissingRequiredDataException {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    //Abdominal Waist Hip
    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestAWHParamSet() throws MissingRequiredDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setWaist(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    //Abdominal Waist Chest
    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestAWHCaramSet() throws MissingRequiredDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    //Hip Waist Chest
    @Test(expected = MissingRequiredDataException.class)
    public void checkRequiredFieldBCTestCWHCaramSet() throws MissingRequiredDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setChest(22.0);
        validator.checkRequiredField(basicCircumference);
    }

    @Test
    public void checkRequiredFieldBCTestAllParamSet() throws MissingRequiredDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(22.0);
        assertTrue(validator.checkRequiredField(basicCircumference));
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsBCAllWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setChest(-22.0);
        basicCircumference.setHip(-22.0);
        basicCircumference.setAbdominal(-22.0);
        basicCircumference.setWaist(-22.0);
        validator.checkSignOnFields(basicCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsBCChestWrongTest() throws InvalidDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(22.0);
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setChest(-22.0);
        validator.checkSignOnFields(basicCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsBCHipWrongTest() throws InvalidDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(22.0);
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(-22.0);
        basicCircumference.setChest(22.0);
        validator.checkSignOnFields(basicCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsBCWaistWrongTest() throws InvalidDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(-22.0);
        basicCircumference.setAbdominal(22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setChest(22.0);
        validator.checkSignOnFields(basicCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsBCAbdominalWrongTest() throws InvalidDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setWaist(22.0);
        basicCircumference.setAbdominal(-22.0);
        basicCircumference.setHip(22.0);
        basicCircumference.setChest(22.0);
        validator.checkSignOnFields(basicCircumference);
    }

    @Test
    public void checkSignOnFieldsBCRightTest() throws InvalidDataException{
        Validator validator = new CircumferenceValidator();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference.setHip(22.3);
        basicCircumference.setAbdominal(24.3);
        basicCircumference.setWaist(22.0);
        basicCircumference.setChest(123.0);
        assertTrue(validator.checkSignOnFields(basicCircumference));
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACAllWrongTest() throws InvalidDataException{
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
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACNeckWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACCalfRWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setCalfR(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACCalfLWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setCalfL(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACArmLWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setArmL(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACArmRWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setArmR(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACForarmRWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setForarmR(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACForarmLWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setForarmL(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACThighRWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setThighR(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test(expected = InvalidDataException.class)
    public void checkSignOnFieldsACThighLWrongTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setThighL(-22.0);
        validator.checkSignOnFields(additionalCircumference);
    }

    @Test
    public void checkSignOnFieldsACAllRightTest() throws InvalidDataException{
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
    public void checkSignOnFieldsACNeckRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACCalfRRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setCalfR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACCalfLRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setCalfL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACArmLRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setArmL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACArmRRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setArmR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACForarmRRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setForarmR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACForarmLRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setForarmL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACThighLRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setThighL(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }

    @Test
    public void checkSignOnFieldsACThighRRightTest() throws InvalidDataException {
        Validator validator = new CircumferenceValidator();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setThighR(22.0);
        assertTrue(validator.checkSignOnFields(additionalCircumference));
    }
}
