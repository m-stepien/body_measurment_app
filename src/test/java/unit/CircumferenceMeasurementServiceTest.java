package unit;

import com.body.measurement.custom.exception.DatabaseException;
import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurement.dto.AdditionalCircumference;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.CircumferenceData;
import com.body.measurement.repositories.AdditionalCircumferenceRepository;
import com.body.measurement.repositories.BasicCircumferenceRepository;
import com.body.measurement.repositories.CircumferenceDataRepository;
import com.body.measurement.services.CircumferenceMeasurementService;
import com.body.measurement.utils.Validator;
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
    public void saveCircumferenceMeasurementSuccessfulSaveTest() throws MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        circumferenceData.setAdditionalCircumference(new AdditionalCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        when(additionalCircumferenceRepository.save(any(AdditionalCircumference.class))).thenReturn(circumferenceData.getAdditionalCircumference());
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        verify(basicCircumferenceRepository).save(any(BasicCircumference.class));
        verify(circumferenceDataRepository).save(any(CircumferenceData.class));
    }

    @Test
    public void saveCircumferenceMeasurementSuccessfulWithoutAdditionalCircumferenceSaveTest() throws MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        verify(basicCircumferenceRepository).save(any(BasicCircumference.class));
        verify(circumferenceDataRepository).save(any(CircumferenceData.class));
    }

    @Test(expected = MissingRequiredDataException.class)
    public void saveCircumferenceMeasurementFailedWithoutBasicCircumferenceSaveTest() throws MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        when(validator.validateCircumferenceData(circumferenceData)).thenThrow(new MissingRequiredDataException(BasicCircumference.class.getName()));
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }

    @Test(expected = InvalidDataException.class)
    public void saveCircumferenceMeasurementFailedWithInvalidDataSaveTest() throws MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        when(validator.validateCircumferenceData(circumferenceData)).thenThrow(new InvalidDataException(BasicCircumference.class.getName(), "chest"));
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }

    @Test(expected = DatabaseException.class)
    public void saveCircumferenceMeasurementFailedDatabaseErrorSaveTest() throws RuntimeException, MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenThrow(new RuntimeException("Error in database"));
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }

    @Test
    public void saveCircumferenceMeasurementSuccessfulWithoutDateSaveTest() throws MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }

    @Test
    public void saveCircumferenceMeasurementSuccessfulWithDateSaveTest() throws MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        circumferenceData.setMeasurementDate(LocalDate.of(2023, 12, 11));
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }


    @Test
    public void updateCircumferenceMeasurementFailedWithoutIdTest() throws NoSuchObjectInDatabaseException, MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceMeasurementService.updateCircumference(circumferenceData);
    }

    @Test(expected = NoSuchObjectInDatabaseException.class)
    public void updateCircumferenceMeasurementFailedNoObjectWithIdInDatabaseTest() throws NoSuchObjectInDatabaseException, MissingRequiredDataException, InvalidDataException, DatabaseException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setId(11L);
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        circumferenceMeasurementService.updateCircumference(circumferenceData);
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
        circumferenceMeasurementService.updateCircumference(getCircumferenceDataFullUpdated());
    }

    @Test
    public void updateCircumferenceMeasurementOnlyBCFullSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        circumferenceDataUpdate.setBasicCircumference(setSameValueToEveryFieldInBasicCircumference(basicCircumferenceUpdate,33.0));
        circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
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
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        basicCircumferenceUpdate.setChest(99.0);
        circumferenceDataUpdate.setBasicCircumference(basicCircumferenceUpdate);
        circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
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
        circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
    }

    @Test
    public void updateCircumferenceMeasurementOnlyBCOnlyHiplSuccessfulTest() throws Exception{
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.of(getCircumferenceDataOnlyBCInDatabase()));
        when(validator.validateCircumferenceData(any(CircumferenceData.class))).thenReturn(true);
        CircumferenceData circumferenceDataUpdate = new CircumferenceData();
        BasicCircumference basicCircumferenceUpdate = new BasicCircumference();
        basicCircumferenceUpdate.setId(12L);
        circumferenceDataUpdate.setId(11L);
        basicCircumferenceUpdate.setHip(99.0);
        circumferenceDataUpdate.setBasicCircumference(basicCircumferenceUpdate);
        circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
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
        circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
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
        circumferenceMeasurementService.updateCircumference(circumferenceDataUpdate);
    }

    private CircumferenceData getCircumferenceDataUpdateAcWhenWasNullInDb(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        AdditionalCircumference additionalCircumference = new AdditionalCircumference();
        additionalCircumference.setNeck(45.0);
        circumferenceDataUpdate.setAdditionalCircumference(additionalCircumference);
        circumferenceDataUpdate.setMeasurementDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCWaistUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setWaist(99.0);
        circumferenceDataUpdate.setMeasurementDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCHipUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setHip(99.0);
        circumferenceDataUpdate.setMeasurementDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCAbdominalUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setAbdominal(99.0);
        circumferenceDataUpdate.setMeasurementDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCChestUpdate(){
        CircumferenceData circumferenceDataUpdate = getCircumferenceDataOnlyBCInDatabase();
        circumferenceDataUpdate.getBasicCircumference().setChest(99.0);
        circumferenceDataUpdate.setMeasurementDate(LocalDate.now());
        return circumferenceDataUpdate;
    }

    private CircumferenceData getCircumferenceDataBCUpdate(){
        CircumferenceData circumferenceData = new CircumferenceData();
        BasicCircumference basicCircumference = new BasicCircumference();
        basicCircumference = setSameValueToEveryFieldInBasicCircumference(basicCircumference, 33.0);
        basicCircumference.setId(12L);
        circumferenceData.setId(11L);
        circumferenceData.setBasicCircumference(basicCircumference);
        circumferenceData.setMeasurementDate(LocalDate.now());
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
        circumferenceDataInDatabase.setMeasurementDate(LocalDate.of(2023,11,28));
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
        circumferenceDataUpdated.setMeasurementDate(LocalDate.of(2022,12,1));
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
