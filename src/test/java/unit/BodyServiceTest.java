package unit;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.BodyDetails;
import com.body.measurement.repositories.BodyDetailsRepository;
import com.body.measurement.repositories.WeightRepository;
import com.body.measurement.services.BodyService;
import com.body.measurement.utils.BodyDataValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BodyServiceTest {
    @Mock
    WeightRepository weightRepository;
    @Mock
    BodyDetailsRepository bodyDetailsRepository;
    @Mock
    BodyDataValidator validator;
    @InjectMocks
    BodyService bodyService;

    @Test
    public void saveBodyDetailsSuccessfulTest() throws MissingRequiredDataException, InvalidDataException {
        BodyDetails bodyDetails = getCorrectBasicBodyData();

        when(validator.isBodyDetailsValid(bodyDetails)).thenReturn(true);
        when(bodyDetailsRepository.save(any(BodyDetails.class))).thenReturn(getCorrectBasicBodyData());
        bodyService.saveBodyDetails(bodyDetails);
        verify(bodyDetailsRepository).save(any(BodyDetails.class));
        verify(validator).isBodyDetailsValid(any(BodyDetails.class));
    }

    @Test
    public void saveBodyDetailsSuccessfulWhenAlreadyExistInDbTest() throws MissingRequiredDataException, InvalidDataException {
        BodyDetails bodyDetails = getCorrectBasicBodyData();

        when(validator.isBodyDetailsValid(bodyDetails)).thenReturn(true);
        when(bodyDetailsRepository.save(any(BodyDetails.class))).thenReturn(getCorrectBasicBodyData());

       bodyService.saveBodyDetails(bodyDetails);

        verify(bodyDetailsRepository).save(any(BodyDetails.class));
        verify(validator).isBodyDetailsValid(any(BodyDetails.class));
    }

    @Test
    public void saveBodyDetailsFailedMissingHeightInCmValueTest() throws MissingRequiredDataException, InvalidDataException {
        BodyDetails bodyDetails = getCorrectBasicBodyData();
        bodyDetails.setHeightInCm(null);

        when(validator.isBodyDetailsValid(bodyDetails)).thenThrow(MissingRequiredDataException.class);
        bodyService.saveBodyDetails(bodyDetails);

        verify(validator).isBodyDetailsValid(any(BodyDetails.class));
    }

    @Test
    public void saveBodyDetailsFailedMissingAgeValueTest() throws MissingRequiredDataException, InvalidDataException {
        BodyDetails bodyDetails = getCorrectBasicBodyData();
        bodyDetails.setAge(null);

        when(validator.isBodyDetailsValid(bodyDetails)).thenThrow(MissingRequiredDataException.class);
        bodyService.saveBodyDetails(bodyDetails);

        verify(validator).isBodyDetailsValid(any(BodyDetails.class));
    }

    private BodyDetails getCorrectBasicBodyData() {
        BodyDetails bodyDetails = new BodyDetails();
        bodyDetails.setId(11L);
        bodyDetails.setAge(22);
        bodyDetails.setHeightInCm(189.3);
        bodyDetails.setGender("m");
        return bodyDetails;
    }

    private BodyDetails getCorrectBasicBodyDataOverwrite() {
        BodyDetails bodyDetails = new BodyDetails();
        bodyDetails.setId(11L);
        bodyDetails.setAge(25);
        bodyDetails.setHeightInCm(192.3);
        bodyDetails.setGender("m");
        return bodyDetails;
    }
}
