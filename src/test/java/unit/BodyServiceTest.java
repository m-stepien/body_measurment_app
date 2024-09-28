package unit;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.BasicBodyData;
import com.body.measurement.dto.responses.BodySaveResponse;
import com.body.measurement.repositories.BasicBodyDataRepository;
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
    BasicBodyDataRepository basicBodyDataRepository;
    @Mock
    BodyDataValidator validator;
    @InjectMocks
    BodyService bodyService;

    @Test
    public void saveBasicBodyDataSuccessfulTest() throws MissingRequiredDataException, InvalidDataException {
        BasicBodyData basicBodyData = getCorrectBasicBodyData();

        when(validator.isBodyDataValid(basicBodyData)).thenReturn(true);
        when(basicBodyDataRepository.save(any(BasicBodyData.class))).thenReturn(getCorrectBasicBodyData());

        BodySaveResponse response = bodyService.saveBasicBodyData(basicBodyData);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("Basic body data save successful", response.getMessage());
        Assert.assertEquals(getCorrectBasicBodyData(), response.getBasicBodyData());

        verify(basicBodyDataRepository).save(any(BasicBodyData.class));
        verify(validator).isBodyDataValid(any(BasicBodyData.class));
    }

    @Test
    public void saveBasicBodyDataSuccessfulWhenAlreadyExistInDbTest() throws MissingRequiredDataException, InvalidDataException {
        BasicBodyData basicBodyData = getCorrectBasicBodyData();

        when(validator.isBodyDataValid(basicBodyData)).thenReturn(true);
        when(basicBodyDataRepository.save(any(BasicBodyData.class))).thenReturn(getCorrectBasicBodyData());

        BodySaveResponse response = bodyService.saveBasicBodyData(basicBodyData);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("Basic body data save successful", response.getMessage());
        Assert.assertEquals(basicBodyData, response.getBasicBodyData());

        verify(basicBodyDataRepository).save(any(BasicBodyData.class));
        verify(validator).isBodyDataValid(any(BasicBodyData.class));
    }

    @Test
    public void saveBasicBodyDataFailedMissingHeightInCmValueTest() throws MissingRequiredDataException, InvalidDataException {
        BasicBodyData basicBodyData = getCorrectBasicBodyData();
        basicBodyData.setHeightInCm(null);

        when(validator.isBodyDataValid(basicBodyData)).thenThrow(MissingRequiredDataException.class);

        BodySaveResponse response = bodyService.saveBasicBodyData(basicBodyData);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(basicBodyData, response.getBasicBodyData());

        verify(validator).isBodyDataValid(any(BasicBodyData.class));
    }

    @Test
    public void saveBasicBodyDataFailedMissingAgeValueTest() throws MissingRequiredDataException, InvalidDataException {
        BasicBodyData basicBodyData = getCorrectBasicBodyData();
        basicBodyData.setAge(null);

        when(validator.isBodyDataValid(basicBodyData)).thenThrow(MissingRequiredDataException.class);

        BodySaveResponse response = bodyService.saveBasicBodyData(basicBodyData);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(basicBodyData, response.getBasicBodyData());

        verify(validator).isBodyDataValid(any(BasicBodyData.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveBasicBodyDataFailedInvalidDataAgeTest() throws MissingRequiredDataException, InvalidDataException {
        BasicBodyData basicBodyData = getCorrectBasicBodyData();
        basicBodyData.setAge(-43);

        when(validator.isBodyDataValid(basicBodyData)).thenThrow(IllegalArgumentException.class);

        BodySaveResponse response = bodyService.saveBasicBodyData(basicBodyData);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(getCorrectBasicBodyDataOverwrite(), response.getBasicBodyData());

        verify(validator.isBodyDataValid(any(BasicBodyData.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveBasicBodyDataFailedInvalidHeightInCm() throws MissingRequiredDataException, InvalidDataException {
        BasicBodyData basicBodyData = getCorrectBasicBodyData();
        basicBodyData.setHeightInCm(-43.2);

        when(validator.isBodyDataValid(basicBodyData)).thenThrow(IllegalArgumentException.class);

        BodySaveResponse response = bodyService.saveBasicBodyData(basicBodyData);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(getCorrectBasicBodyDataOverwrite(), response.getBasicBodyData());

        verify(validator.isBodyDataValid(any(BasicBodyData.class)));
    }

    private BasicBodyData getCorrectBasicBodyData() {
        BasicBodyData basicBodyData = new BasicBodyData();
        basicBodyData.setId(11L);
        basicBodyData.setAge(22);
        basicBodyData.setHeightInCm(189.3);
        basicBodyData.setGander("m");
        return basicBodyData;
    }

    private BasicBodyData getCorrectBasicBodyDataOverwrite() {
        BasicBodyData basicBodyData = new BasicBodyData();
        basicBodyData.setId(11L);
        basicBodyData.setAge(25);
        basicBodyData.setHeightInCm(192.3);
        basicBodyData.setGander("m");
        return basicBodyData;
    }
}
