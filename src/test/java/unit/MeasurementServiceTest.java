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
import com.body.measurment.services.MeasurementService;
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
public class MeasurementServiceTest {

    @Mock
    Validator validator;
    @Mock
    BasicCircumferenceRepository basicCircumferenceRepository;
    @Mock
    AdditionalCircumferenceRepository additionalCircumferenceRepository;
    @Mock
    CircumferenceDataRepository circumferenceDataRepository;
    @InjectMocks
    MeasurementService measurementService;

    @Test
    public void saveMeasurementSuccessfulSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        circumferenceData.setAdditionalCircumference(new AdditionalCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        when(additionalCircumferenceRepository.save(any(AdditionalCircumference.class))).thenReturn(circumferenceData.getAdditionalCircumference());
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
        verify(basicCircumferenceRepository).save(any(BasicCircumference.class));
        verify(circumferenceDataRepository).save(any(CircumferenceData.class));
    }

    @Test
    public void saveMeasurementSuccessfulWithoutAdditionalCircumferenceSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
        verify(basicCircumferenceRepository).save(any(BasicCircumference.class));
        verify(circumferenceDataRepository).save(any(CircumferenceData.class));
    }

    @Test
    public void saveMeasurementFailedWithoutBasicCircumferenceSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        when(validator.validateCircumferenceData(circumferenceData)).thenThrow(new MissingRequiredDataException(BasicCircumference.class.getName()));
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("Required data is missing in object com.body.measurment.dto.BasicCircumference", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
    }

    @Test
    public void saveMeasurementFailedWithInvalidDataSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        when(validator.validateCircumferenceData(circumferenceData)).thenThrow(new InvalidDataException(BasicCircumference.class.getName(), "chest"));
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("There is invalid data value in com.body.measurment.dto.BasicCircumference in field chest", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
    }

    @Test
    public void saveMeasurementFailedDatabaseErrorSaveTest() throws RuntimeException, MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenThrow(new RuntimeException("Error in database"));
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("Failed save to database", response.getMessage());
        assertEquals(circumferenceData, response.getCircumferenceData());
    }

    @Test
    public void saveMeasurementSuccessfulWithoutDateSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertEquals(LocalDate.now(), response.getCircumferenceData().getMeasurmentDate());
    }

    @Test
    public void saveMeasurementSuccessfulWithDateSaveTest() throws MissingRequiredDataException, InvalidDataException {
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setBasicCircumference(new BasicCircumference());
        circumferenceData.setMeasurmentDate(LocalDate.of(2023, 12, 11));
        when(validator.validateCircumferenceData(circumferenceData)).thenReturn(true);
        when(basicCircumferenceRepository.save(any(BasicCircumference.class))).thenReturn(circumferenceData.getBasicCircumference());
        when(circumferenceDataRepository.save(any(CircumferenceData.class))).thenReturn(circumferenceData);
        CircumferenceDataSaveResponse response = measurementService.saveMeasurement(circumferenceData);
        assertEquals(LocalDate.of(2023, 12, 11), response.getCircumferenceData().getMeasurmentDate());
    }


    @Test
    public void updateMeasurementFailedWithoutIdTest(){
        CircumferenceData circumferenceData = new CircumferenceData();
        CircumferenceDataSaveResponse response = measurementService.updateCircumference(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("Id is required for update",response.getMessage());
        assertEquals(response.getCircumferenceData(), circumferenceData);
    }

    @Test
    public void updateMeasurementFailedNoObjectWithIdInDatabaseTest(){
        CircumferenceData circumferenceData = new CircumferenceData();
        circumferenceData.setId(11L);
        when(circumferenceDataRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        CircumferenceDataSaveResponse response = measurementService.updateCircumference(circumferenceData);
        assertFalse(response.isSuccess());
        assertEquals("There is no CircumferenceData object with this id in database",response.getMessage());
        assertEquals(response.getCircumferenceData(), circumferenceData);
    }

    @Test
    public void updateMeasurementSuccessfulTest() throws Exception{
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
        CircumferenceDataSaveResponse response = measurementService.updateCircumference(getCircumferenceDataFullUpdated());
        assertTrue(response.isSuccess());
        assertEquals("Save CircumferenceData successful", response.getMessage());
        assertEquals(response.getCircumferenceData(), getCircumferenceDataFullUpdated());
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
