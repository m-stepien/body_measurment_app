package unit;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;
import com.body.measurment.dto.responses.CircumferenceDataSaveResponse;
import com.body.measurment.repositories.AdditionalCircumferenceRepository;
import com.body.measurment.repositories.BasicCircumferenceRepository;
import com.body.measurment.repositories.CircumferenceDataRepository;
import com.body.measurment.services.CircumferenceMeasurementService;
import com.body.measurment.utils.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CircumferenceMeasurementServiceTest {

    @Mock
    Validator validator;
    @Mock
    BasicCircumferenceRepository basicCircumferenceRepository;
    @Mock
    AdditionalCircumferenceRepository additionalCircumferenceRepository;
    @Mock
    CircumferenceDataRepository circumferenceDataRepository;
    @InjectMocks
    CircumferenceMeasurementService circumferenceMeasurementService;

    @Test
    public void saveCircumferenceMeasurementSuccessfulSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        circumferenceData.setAdditionalCircumference(new AdditionalCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        when(additionalCircumferenceRepository.save(any(AdditionalCircumference.class))).thenReturn(circumferenceData.getAdditionalCircumference());
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
        verify(basicCircumferenceRepository).save(any(BasicCircumference.class));
        verify(circumferenceDataRepository).save(any(CircumferenceData.class));
    }

    @Test
    public void saveCircumferenceMeasurementSuccessfulWithoutAdditionalCircumferenceSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
        verify(basicCircumferenceRepository).save(any(BasicCircumference.class));
        verify(circumferenceDataRepository).save(any(CircumferenceData.class));
    }

    @Test
    public void saveCircumferenceMeasurementFailedWithoutBasicCircumferenceSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        when(validator.validateCircumferenceData(circumferenceData)).thenThrow(new MissingRequiredDataException(BasicCircumference.class.getName()));
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("Required data is missing in object com.body.measurment.dto.BasicCircumference", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
    }

    @Test
    public void saveCircumferenceMeasurementFailedWithInvalidDataSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        when(validator.validateCircumferenceData(circumferenceData)).thenThrow(new InvalidDataException(BasicCircumference.class.getName(), "chest"));
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("There is invalid data value in com.body.measurment.dto.BasicCircumference in field chest", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
    }

    @Test
    public void saveCircumferenceMeasurementFailedDatabaseErrorSaveTest() throws RuntimeException, MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenThrow(new RuntimeException("Error in database"));
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("Failed save to database", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
    }

    @Test
    public void saveCircumferenceMeasurementSuccessfulWithoutDateSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertEquals(LocalDate.now(), response.getCircumferenceData().getMeasurmentDate());
    }

    @Test
    public void saveCircumferenceMeasurementSuccessfulWithDateSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        circumferenceData.setMeasurmentDate(LocalDate.of(2023, 12, 11));
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        assertEquals(LocalDate.of(2023, 12, 11), response.getCircumferenceData().getMeasurmentDate());
    }


    @Test
    public void updateCircumferenceMeasurementFailedWithoutIdTest(){
        CircumferenceData circumferenceData = new CircumferenceData();
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("Id is required for update",response.getMessage());
        assertEquals(response.getCircumferenceData(), circumferenceData);
    }

    @Test
    public void updateCircumferenceMeasurementFailedNoObjectWithIdInDatabaseTest(){
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setId(11L);
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("There is no CircumferenceData object with this id in database",response.getMessage());
        assertEquals(response.getCircumferenceData(), circumferenceData);
    }

    @Test
    public void updateCircumferenceMeasurementFullSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getFullCircumferenceDataInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
           Object[] args = invocation.getArguments();
           return args[0];
        });
        when(additionalCircumferenceRepository.save(any(AdditionalCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(getCircumferenceDataFullUpdated());
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(response.getCircumferenceData(), getCircumferenceDataFullUpdated());
    }

    @Test
    public void updateCircumferenceMeasurementOnlyBCFullSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        circumferenceDataUpdate.setBasicCircumference(setSameValueToEveryFieldInBasicCircumference(basicCircumferenceUpdate,33.0));
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(getCircumferenceDataBCUpdate(), response.getCircumferenceData());
    }

    @Test
    public void updateCircumferenceMeasurementOnlyBCOnlyChestSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        basicCircumferenceUpdate.setChest(99.0);
        circumferenceDataUpdate.setBasicCircumference(basicCircumferenceUpdate);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(getCircumferenceDataBCChestUpdate(), response.getCircumferenceData());
    }
    @Test
    public void updateCircumferenceMeasurementOnlyBCOnlyAbdominalSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        basicCircumferenceUpdate.setAbdominal(99.0);
        circumferenceDataUpdate.setBasicCircumference(basicCircumferenceUpdate);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(getCircumferenceDataBCAbdominalUpdate(), response.getCircumferenceData());
    }

    @Test
    public void updateCircumferenceMeasurementOnlyBCOnlyHiplSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        basicCircumferenceUpdate.setHip(99.0);
        circumferenceDataUpdate.setBasicCircumference(basicCircumferenceUpdate);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(getCircumferenceDataBCHipUpdate(), response.getCircumferenceData());
    }

    @Test
    public void updateCircumferenceMeasurementOnlyBCOnlyWaistlSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        basicCircumferenceUpdate.setWaist(99.0);
        circumferenceDataUpdate.setBasicCircumference(basicCircumferenceUpdate);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(getCircumferenceDataBCWaistUpdate(), response.getCircumferenceData());
    }

    @Test
    public void updateCircumferenceMeasurementOnlyACACNullInDatabaseSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(additionalCircumferenceRepository.save(any(AdditionalCircumference.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        AdditionalCircumference additionalCircumferenceUpdate = new AdditionalCircumference();
        additionalCircumferenceUpdate.setNeck(45.0);
        circumferenceDataUpdate.setAdditionalCircumference(additionalCircumferenceUpdate);
        circumferenceDataUpdate.setId(11L);
        CircumferenceDataSaveResponse response = circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(getCircumferenceDataUpdateAcWhenWasNullInDb(), response.getCircumferenceData());
    }

    private CircumferenceData getCircumferenceDataUpdateAcWhenWasNullInDb(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(45.0);
        circumferenceDataUpdate.setAdditionalCircumference(additionalCircumference);
        circumferenceDataUpdate.setMeasurmentDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCWaistUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setWaist(99.0);
        circumferenceDataUpdate.setMeasurmentDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCHipUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setHip(99.0);
        circumferenceDataUpdate.setMeasurmentDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCAbdominalUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setAbdominal(99.0);
        circumferenceDataUpdate.setMeasurmentDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCChestUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setChest(99.0);
        circumferenceDataUpdate.setMeasurmentDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCUpdate(){
        CircumferenceData circumferenceData = new CircumferenceData();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference = setSameValueToEveryFieldInBasicCircumference(basicCircumference, 33.0);
        basicCircumference.setId(12L);
        circumferenceData.setId(11L);
        circumferenceData.setBasicCircumference(basicCircumference);
        circumferenceData.setMeasurmentDate(LocalDate.now());
        return circumferenceData;
    }


    private CircumferenceData getCircumferenceDataOnlyBCInDatabase(){
        CircumferenceData circumferenceDataInDatabase = new CircumferenceData();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference = setSameValueToEveryFieldInBasicCircumference(basicCircumference, 22.0);
        basicCircumference.setId(12L);
        circumferenceDataInDatabase.setBasicCircumference(basicCircumference);
        circumferenceDataInDatabase.setId(11L);
        return circumferenceDataInDatabase;
    }
    private CircumferenceData getFullCircumferenceDataInDatabase(){
        CircumferenceData circumferenceDataInDatabase = new CircumferenceData();
        circumferenceDataInDatabase.setMeasurmentDate(LocalDate.of(2023,11,28));
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference = setSameValueToEveryFieldInBasicCircumference(basicCircumference, 22.0);
        basicCircumference.setId(12L);
        circumferenceDataInDatabase.setBasicCircumference(basicCircumference);
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference = setSameValueToEveryFieldAdditionalCircumference(additionalCircumference, 22.0);
        additionalCircumference.setId(14L);
        circumferenceDataInDatabase.setAdditionalCircumference(additionalCircumference);
        circumferenceDataInDatabase.setId(11L);
        return circumferenceDataInDatabase;
    }

    private CircumferenceData getCircumferenceDataFullUpdated(){
        CircumferenceData circumferenceDataUpdated = new CircumferenceData();
        circumferenceDataUpdated.setMeasurmentDate(LocalDate.of(2022,12,1));
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference = setSameValueToEveryFieldInBasicCircumference(basicCircumference, 45.0);
        basicCircumference.setId(12L);
        circumferenceDataUpdated.setBasicCircumference(basicCircumference);
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setId(14L);
        additionalCircumference = setSameValueToEveryFieldAdditionalCircumference(additionalCircumference, 45.0);
        circumferenceDataUpdated.setAdditionalCircumference(additionalCircumference);
        circumferenceDataUpdated.setId(11L);
        return circumferenceDataUpdated;
    }

    private BasicCircumference setSameValueToEveryFieldInBasicCircumference(BasicCircumference basicCircumference, Double value){
        basicCircumference.setWaist(value);
        basicCircumference.setChest(value);
        basicCircumference.setHip(value);
        basicCircumference.setAbdominal(value);
        return basicCircumference;
    }

    private AdditionalCircumference setSameValueToEveryFieldAdditionalCircumference(AdditionalCircumference additionalCircumference, Double value){
        additionalCircumference.setThighL(value);
        additionalCircumference.setThighR(value);
        additionalCircumference.setForarmL(value);
        additionalCircumference.setForarmR(value);
        additionalCircumference.setArmR(value);
        additionalCircumference.setArmL(value);
        additionalCircumference.setCalfL(value);
        additionalCircumference.setCalfR(value);
        additionalCircumference.setNeck(value);
        return additionalCircumference;
    }
}
