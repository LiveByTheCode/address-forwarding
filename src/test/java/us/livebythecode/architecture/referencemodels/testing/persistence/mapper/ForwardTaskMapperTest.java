package us.livebythecode.architecture.referencemodels.testing.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import us.livebythecode.architecture.referencemodels.testing.domain.Address;
import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;
import us.livebythecode.architecture.referencemodels.testing.domain.utility.DateUtility;
import us.livebythecode.architecture.referencemodels.testing.persistence.entity.ForwardTaskEntity;
import us.livebythecode.architecture.referencemodels.testing.persistence.mapper.ForwardTaskMapper;

@ExtendWith(MockitoExtension.class)
public class ForwardTaskMapperTest {
    
    @InjectMocks
    private ForwardTaskMapper ftm;

    @Mock
    private DateUtility du;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testMapToDomain() throws Exception {
        //Arrange
        doReturn(730l).when(du).daysRemaining(LocalDate.parse("2020-03-05"), LocalDate.parse("2022-03-05"));
        //Act
        ForwardTask result = ftm.mapToDomain(
                getForwardTaskEntity(
                    123l, 
                    "500 Old Dominion Way", 
                    "Thomasville", 
                    "NC", 
                    "27360", 
                    "PointAddress", 
                    100, 
                    -80.060606, 35.90805, 
                    "90 Piedmont Industrial Dr", 
                    "Winston-Salem", 
                    "NC", 
                    "27107", 
                    "PointAddress", 
                    100, 
                    -80.235872, 36.020324, 
                    getDateFromString("2020-02-25"), 
                    getDateFromString("2020-03-05"), 
                    getDateFromString("2022-03-05")
                )
            );
        //Assert
        assertEquals(new Address("500 Old Dominion Way", "Thomasville", "NC", "27360", -80.060606, 35.90805, "PointAddress", 100),result.getFromAddress());
        assertEquals(new Address("90 Piedmont Industrial Dr", "Winston-Salem", "NC", "27107", -80.235872, 36.020324, "PointAddress", 100), result.getToAddress());
        assertEquals(123l, result.getId());
        assertEquals(730l, result.getForwardDaysRemaining());
        assertEquals(LocalDate.parse("2020-02-25"), result.getCreated());
        assertEquals(LocalDate.parse("2020-03-05"), result.getStart());
        assertEquals(LocalDate.parse("2022-03-05"), result.getEnd());
    }

    @Test
    public void testMapListToDomain() throws Exception {
        //Arrange
        doReturn(730l).when(du).daysRemaining(LocalDate.parse("2020-03-05"), LocalDate.parse("2022-03-05"));
        //Act
        List <ForwardTask> result = ftm.mapListToDomain(
                convertToArray(getForwardTaskEntity(
                    123l, 
                    "500 Old Dominion Way", 
                    "Thomasville", 
                    "NC", 
                    "27360", 
                    "PointAddress", 
                    100, 
                    -80.060606, 35.90805, 
                    "90 Piedmont Industrial Dr", 
                    "Winston-Salem", 
                    "NC", 
                    "27107", 
                    "PointAddress", 
                    100, 
                    -80.235872, 36.020324, 
                    getDateFromString("2020-02-25"), 
                    getDateFromString("2020-03-05"), 
                    getDateFromString("2022-03-05")
                ))
            );
        //Assert
        assertEquals(new Address("500 Old Dominion Way", "Thomasville", "NC", "27360", -80.060606, 35.90805, "PointAddress", 100),result.get(0).getFromAddress());
        assertEquals(new Address("90 Piedmont Industrial Dr", "Winston-Salem", "NC", "27107", -80.235872, 36.020324, "PointAddress", 100), result.get(0).getToAddress());
        assertEquals(123l, result.get(0).getId());
        assertEquals(730l, result.get(0).getForwardDaysRemaining());
        assertEquals(LocalDate.parse("2020-02-25"), result.get(0).getCreated());
        assertEquals(LocalDate.parse("2020-03-05"), result.get(0).getStart());
        assertEquals(LocalDate.parse("2022-03-05"), result.get(0).getEnd());
    }
    
    @Test
    public void testMapFromDomain() throws Exception {
        //Act
        ForwardTaskEntity result = ftm.mapFromDomain(
                new ForwardTask(
                        new Address("500 Old Dominion Way", "Thomasville", "NC", "27360", -80.060606, 35.90805, "PointAddress", 100),
                        new Address("90 Piedmont Industrial Dr", "Winston-Salem", "NC", "27107", -80.235872, 36.020324, "PointAddress", 100),
                        LocalDate.parse("2020-03-05"),
                        LocalDate.parse("2022-03-05")
                )
        );
        //Assert
        assertEquals("500 Old Dominion Way", result.fromStreetAddress);
        assertEquals("Thomasville", result.fromCity);
        assertEquals("NC", result.fromState);
        assertEquals("27360", result.fromPostalCode);
        assertEquals(100, result.fromAddressScore);
        assertEquals(-80.060606, result.fromAddressX);
        assertEquals(35.90805, result.fromAddressY);
        assertEquals("90 Piedmont Industrial Dr", result.toStreetAddress);
        assertEquals("Winston-Salem", result.toCity);
        assertEquals("NC", result.toState);
        assertEquals("27107", result.toPostalCode);
        assertEquals(100, result.toAddressScore);
        assertEquals(-80.235872, result.toAddressX);
        assertEquals(36.020324, result.toAddressY);
    }
    
    @Test
    public void testDateToLocalDate_NullDate_ReturnsNull() throws Exception {
        //Act (use reflection (via PowerMock) to exercise private dateToLocalDate() method
        LocalDate result = Whitebox.invokeMethod(ftm, "dateToLocalDate", null);
        //Assert
        assertNull(result);
    }
    
    @Test
    public void testDateToLocalDate() throws Exception {
        //Act (use reflection (via PowerMock) to exercise private dateToLocalDate() method
        LocalDate result = Whitebox.invokeMethod(ftm, "dateToLocalDate", getDateFromString("2020-02-25"));
        //Assert
        assertEquals(LocalDate.parse("2020-02-25"), result);
    }
    
    
    //Helper methods
    
    
    private List<ForwardTaskEntity> convertToArray(ForwardTaskEntity fte){
        List <ForwardTaskEntity> returnList = new ArrayList<>();
        returnList.add(fte);
        return returnList;
    }
    
    private Date getDateFromString(String dateString) throws ParseException {
        return sdf.parse(dateString);
    }
    
    private ForwardTaskEntity getForwardTaskEntity(long id, String fromStreetAddress, 
            String fromCity, String fromState, String fromPostalCode, String fromAddressType,
            int fromAddressScore, double fromAddressX, double fromAddressY, String toStreetAddress, String toCity, 
            String toState, String toPostalCode, String toAddressType, int toAddressScore,
            double toAddressX, double toAddressY, Date created, Date start, Date end) {
        ForwardTaskEntity fte = new ForwardTaskEntity();
        fte.id = id;
        
        fte.fromStreetAddress = fromStreetAddress;
        fte.fromCity = fromCity;
        fte.fromState = fromState;
        fte.fromPostalCode = fromPostalCode;
        fte.fromAddressType = fromAddressType;
        fte.fromAddressScore = fromAddressScore;
        fte.fromAddressX = fromAddressX;
        fte.fromAddressY = fromAddressY;
        
        fte.toStreetAddress = toStreetAddress;
        fte.toCity = toCity;
        fte.toState = toState;
        fte.toPostalCode = toPostalCode;
        fte.toAddressType = toAddressType;
        fte.toAddressScore = toAddressScore;
        fte.toAddressX = toAddressX;
        fte.toAddressY = toAddressY;
        
        fte.created = created;
        fte.startDate = start;
        fte.endDate = end;
        return fte;
    }
}
