//package bg.softuni.accommodation.unit;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//public class UserValidationServiceTests {
//
//    private UserValidationService userValidationService;
//
//    @Before
//    public void setUpTest() {
//        userValidationService = new UserValidationServiceImpl();
//    }
//
//    @Test
//    public void isValid_whenNull_returnFalse() {
//        boolean result = userValidationService.isValid(null);
//        Assert.assertFalse(result);
//    }
//
//    @Test
//    public void isValid_whenValid_returnTrue() {
//        boolean result = userValidationService.isValid(new User());
//        Assert.assertTrue(result);
//    }
//}
