package unit;

import com.body.measurment.repositories.AdditionalCircumferenceRepository;
import com.body.measurment.repositories.BasicCircumferenceRepository;
import com.body.measurment.repositories.CircumferenceDataRepository;
import com.body.measurment.services.MeasurementService;
import com.body.measurment.utils.Validator;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
}
