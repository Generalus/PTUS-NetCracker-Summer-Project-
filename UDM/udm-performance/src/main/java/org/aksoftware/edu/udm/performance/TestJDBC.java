package org.aksoftware.edu.udm.performance;

public class TestJDBC {
    public static void main(String[] args) {
        try {
            TestResult result = CommonTest.JDBC.equalsTest(100, 20, true); // количество мини-задач, количество нитей
            result.printDefaultLog();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
