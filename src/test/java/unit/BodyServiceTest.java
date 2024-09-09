package unit;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.repositories.BasicBodyDataRepository;
import com.body.measurment.repositories.WeightRepository;
import com.body.measurment.services.BodyService;
import com.body.measurment.utils.BodyDataValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        BasicBodyData basicBodyData = new BasicBodyData();
        when(validator.isBodyDataValid(basicBodyData)).thenReturn(true);
    }

    @Test
    public void saveBasicBodyDataSuccessfulWhenAlreadyExistInDbTest(){

    }

    @Test
    public void saveBasicBodyDataFailedMissingHeightInCmValueTest(){

    }

    @Test
    public void saveBasicBodyDataFailedMissingAgeValueTest(){

    }

    @Test
    public void saveBasicBodyDataFailedInvalidDataAgeTest(){

    }

    @Test
    public void saveBasicBodyDataFailedInvalidHeightInCm(){

    }
}
