package org.aksoftware.edu.udm.performance;

public class TestJPA {

    public static void main(String[] args) {
        try {
            TestResult result = CommonTest.JPA.equalsTest(100, 5, true); // количество мини-задач, количество нитей
            result.printDefaultLog();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
