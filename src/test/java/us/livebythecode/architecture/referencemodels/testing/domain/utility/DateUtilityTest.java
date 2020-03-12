package us.livebythecode.architecture.referencemodels.testing.domain.utility;

import static org.hamcrest.CoreMatchers.equalTo;
//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.common.base.Optional;

import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;
import us.livebythecode.architecture.referencemodels.testing.domain.utility.CurrentDate;
import us.livebythecode.architecture.referencemodels.testing.domain.utility.DateUtility;
import us.livebythecode.architecture.referencemodels.testing.exception.BadInputDateRangeException;

@ExtendWith(MockitoExtension.class)



class DateUtilityTest {
    @Mock
    private CurrentDate cd;

    @InjectMocks
    private DateUtility du;
    
    //Lidalia TestLogger instance of the DateUtility's logger
    TestLogger logger = TestLoggerFactory.getTestLogger(DateUtility.class);

   @Test
    //<Method Under Test>_<Scenario>_<Expected-Outcome>
    public void testDaysRemaining_StartDateAfterCurrentDate_17Days() {
        // Arrange
        doReturn(LocalDate.parse("2020-01-10")).when(cd).getLocalDate();
        // Act
        long result = du.daysRemaining(LocalDate.parse("2020-01-11"), LocalDate.parse("2020-01-28"));
        // Assert
        assertEquals(17l, result);
   }
   
   @Test
   public void testDaysRemaining_StartDateBeforeCurrentDate_18Days() {
       // Arrange
       doReturn(LocalDate.parse("2020-01-10")).when(cd).getLocalDate();
       // Act
       long result = du.daysRemaining(LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-28"));
       // Assert
       assertEquals(18l, result);
   }
   @Test
   public void testDaysRemaining_CurrentDateAfterEndDate_0Days() {
       // Arrange
       doReturn(LocalDate.parse("2020-01-10")).when(cd).getLocalDate();
       // Act
       long result = du.daysRemaining(LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-09"));
       // Assert
       assertEquals(0l, result);
   }
   @Test
   public void testDaysRemaining_StartDateNull_ExceptionThrown() {

       // Arrange
       initLogger();
       doReturn(LocalDate.parse("2020-01-10")).when(cd).getLocalDate();

       // Act / Assert
       assertThrows(BadInputDateRangeException.class, () -> {
           du.daysRemaining(null, LocalDate.parse("2020-01-28"));
       });
       
       /*
        * Log testing doesn't isn't currently supported in quarkus
        * See: https://github.com/quarkusio/quarkus/issues/7696
        * 

       // check to see that the error was logged
       assertEquals("End date ({}) can not be before Start date ({}) (and neither date may be null)",logger.getAllLoggingEvents().get(0).getMessage());
       // make sure it's logged at ERROR level
       assertEquals(Level.ERROR, logger.getAllLoggingEvents().get(0).getLevel());
       // confirm the first logger parm is as expected
       assertEquals(LocalDate.parse("2020-01-28"), logger.getAllLoggingEvents().get(0).getArguments().get(0));
       // the second logger parm should not be set
       //assertThat(logger.getAllLoggingEvents().get(0).getArguments().get(1), equalTo(Optional.absent()));
        * 
        */
   }

   @Test
   public void testDaysRemaining_EndDateNull_ExceptionThrown() {

       // Arrange
       initLogger();
       doReturn(LocalDate.parse("2020-01-10")).when(cd).getLocalDate();

       // Act / Assert
       assertThrows(BadInputDateRangeException.class, () -> {
           du.daysRemaining(LocalDate.parse("2020-01-11"), null);
       });
       /*
        * Log testing doesn't isn't currently supported in quarkus
        * See: https://github.com/quarkusio/quarkus/issues/7696
        * 

       // check to see that the error was logged
       assertEquals("End date ({}) can not be before Start date ({}) (and neither date may be null)",logger.getAllLoggingEvents().get(0).getMessage());
       // make sure it's logged at ERROR level
       assertEquals(Level.ERROR, logger.getAllLoggingEvents().get(0).getLevel());
       // the first logger parm should not be set
//       assertThat(logger.getAllLoggingEvents().get(0).getArguments().get(0), equalTo(Optional.absent()));
       // confirm the second logger parm is as expected
       assertEquals(LocalDate.parse("2020-01-11"), logger.getAllLoggingEvents().get(0).getArguments().get(1));
       **/
   }

   @Test
   public void testDaysRemaining_EndDateBeforeStartDate_ExceptionThrown() {

       // Arrange
       initLogger();
       doReturn(LocalDate.parse("2020-01-10")).when(cd).getLocalDate();

       // Act / Assert
       assertThrows(BadInputDateRangeException.class, () -> {
           du.daysRemaining(LocalDate.parse("2020-01-11"), LocalDate.parse("2020-01-10"));
       });

       /*
        * Log testing doesn't isn't currently supported in quarkus
        * See: https://github.com/quarkusio/quarkus/issues/7696
        * 
       // check to see that the error was logged
       assertEquals("End date ({}) can not be before Start date ({}) (and neither date may be null)",logger.getAllLoggingEvents().get(0).getMessage());
       // make sure it's logged at ERROR level
       assertEquals(Level.ERROR, logger.getAllLoggingEvents().get(0).getLevel());
       // the first logger parm should not be set
       assertEquals(LocalDate.parse("2020-01-10"), logger.getAllLoggingEvents().get(0).getArguments().get(0));
       // confirm the second logger parm is as expected
       assertEquals(LocalDate.parse("2020-01-11"), logger.getAllLoggingEvents().get(0).getArguments().get(1));
       **/
   }

   private void initLogger() {
       logger.clearAll();
       logger.setEnabledLevels(Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR);
   }


}
