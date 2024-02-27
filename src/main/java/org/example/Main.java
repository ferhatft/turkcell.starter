package org.example;

public class Main
{
    public static void main(String[] args)
    {
        CorporateCustomer corporateCustomer = new CorporateCustomer();
        IndividualCustomer individualCustomer = new IndividualCustomer();

        corporateCustomer.setCustomerNo("ABC");
        corporateCustomer.setTaxNo("123");

        individualCustomer.setCustomerNo("GHJ");
        individualCustomer.setNationalityId("456");

        //Customer individualCustomer2 = new IndividualCustomer();
        CustomerServiceInterface customerService = new CustomerService();
        customerService.add();
        customerService.update();
        customerService.add();
        customerService.add();
        customerService.add();
        customerService.add();
    }
}
// 15:05