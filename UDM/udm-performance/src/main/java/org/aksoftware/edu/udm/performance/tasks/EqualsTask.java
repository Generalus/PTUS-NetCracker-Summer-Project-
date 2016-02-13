package org.aksoftware.edu.udm.performance.tasks;

import org.aksoftware.edu.udm.UdmManager;
import org.aksoftware.edu.udm.User;
import org.aksoftware.edu.udm.jdbc.UdmManagerJDBC;
import org.aksoftware.edu.udm.jdbc.UserJDBC;
import org.aksoftware.edu.udm.jpa.UdmManagerJPA;
import org.aksoftware.edu.udm.jpa.UserJPA;
import org.aksoftware.edu.udm.performance.ThreadResult;
import org.aksoftware.edu.udm.performance.exceptions.FailedTestException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EqualsTask extends AbstractTask  {

    public EqualsTask(UdmManager manager, Set<String> randomStrings, boolean showProgress) {
        setManager(manager);
        setRandomStrings(randomStrings);
        setShowProgress(showProgress);
    }

    @Override
    public ThreadResult call() throws Exception {
        List<String> strings = new ArrayList<>();
        Date beforeThread = new Date();
        try {

            for(int i = 0; i < 3; i++)
                strings.add(getRandomString());

            String login = strings.get(0);
            String password = strings.get(1);
            String abbr = strings.get(2);

            User user;
            User user2;

            if (getManager() instanceof UdmManagerJPA) {
                user = new UserJPA();
            } else if (getManager() instanceof UdmManagerJDBC) {
                user = new UserJDBC();
            } else
                throw new IllegalArgumentException();

            user.setLogin(login);
            user.setPassword(password);
            user.setAbbr(abbr);
            user.setUserCategory(1);
            getManager().save(user);
            user2 = getManager().getUser(login);
            //JDBC-реализация не смогла в user.equals(user2), потому тут такой говнокод
            if (!user.getLogin().equals(user2.getLogin()) || !user.getAbbr().equals(user2.getAbbr()) || !user.getPassword().equals(user2.getPassword()))
                throw new FailedTestException("Содержимое объектов должно быть одинаковым!");
            long time = new Date().getTime() - beforeThread.getTime();
            return new ThreadResult(true, time, null, strings);
        }catch (Exception e){
            long time = new Date().getTime() - beforeThread.getTime();
            return new ThreadResult(false, time, e, strings);
        } finally {
            if (isShowProgress())
                System.out.print("|");
            getCountDownLatch().countDown();
        }
    }

}
