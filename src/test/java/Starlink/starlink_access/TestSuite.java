package Starlink.starlink_access;

import Starlink.starlink_access.controller.*;
import Starlink.starlink_access.service.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ProductCategoryControllerTest.class,
        ProductControllerTest.class,
        DiscountServiceTest.class,
        ProductCategoryServiceTest.class,
        ProductServiceTest.class,
        ProductListServiceTest.class,
        TransactionServiceTest.class,
        UserServiceTest.class,
        UserControllerTest.class,
        AuthServiceTest.class,
        AuthControllerTest.class

})
public class TestSuite {

}
