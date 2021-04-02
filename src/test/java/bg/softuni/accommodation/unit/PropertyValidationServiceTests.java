//package bg.softuni.accommodation.unit;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//public class PropertyValidationServiceTests {
//
//    private PropertyValidationService propertyValidationService;
//
//    @Before
//    public void setUpTest(){
//        propertyValidationService = new PropertyValidationServiceImpl();
//    }
//
//    @Test
//    public void isValid_whenNull_returnFalse(){
//        boolean result = propertyValidationService.isValid(null);
//        Assert.assertFalse(result);
//    }
//
//    @Test
//    public void isValid_whenValid_returnTrue(){
//        boolean result = propertyValidationService
//                .isValid(new Product(){{
//                    setCategories(new ArrayList<>());
//                }});
//        Assert.assertTrue(result);
//    }
//}
