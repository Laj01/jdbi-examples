package user;


import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        User user1 = User.builder()
                .username("Laj01")
                .password("jukasbagojtolyás")
                .name("Nagy Lajos")
                .email("Laj01@gmail.com")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1990-08-18"))
                .enabled(true)
                .build();

        User user2 = User.builder()
                .username("007")
                .password("jb")
                .name("James Bond")
                .email("jamesbond@secretmail.com")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1920-11-11"))
                .enabled(true)
                .build();

        User user3 = User.builder()
                .username("Márklar")
                .password("elmaromowsky")
                .name("Laar Mark")
                .email("marklaaaar@idontknowwhatimdoing.co.uk.eu.com.tw.gif.exe")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1970-01-01"))
                .enabled(true)
                .build();


        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);

            Logger.trace("CREATING TABLE");
            dao.createTable();

            Logger.trace("INSERTING DATA");
            dao.insert(user1);
            dao.insert(user2);
            dao.insert(user3);

            Logger.trace("LISTING DATA");
            dao.list().stream().forEach(System.out::println);

            Logger.trace("FIND BY ID");
            Logger.info(dao.findById(3).get().toString());

            Logger.trace("FIND BY USERNAME");
            Logger.info(dao.findByUsername("Laj01").get().toString());

            Logger.trace("DELETING USER");
            dao.delete(dao.findById(3).get());

            Logger.trace("LISTING DATA");
            dao.list().stream().forEach(System.out::println);

        }

    }

}
