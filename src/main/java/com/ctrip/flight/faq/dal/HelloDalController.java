package com.ctrip.flight.faq.dal;

import com.ctrip.flight.faq.dal.entity.DalUser;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.framework.spring.boot.dal.DalDatabase;
import com.ctrip.framework.spring.boot.dal.DalTable;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.base.DalDatabaseOperations;
import com.ctrip.platform.dal.dao.base.DalTableOperations;
import com.ctrip.platform.dal.dao.base.SQLResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping(path = "/dal")
public class HelloDalController {

    @DalDatabase
    private DalDatabaseOperations dalDatabaseOperations;

    @DalTable
    private DalTableOperations<DalUser> dalTableOperations;

    @RequestMapping
    public String demo() {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>DAL Demo</title></head>");
        responseBuilder.append("<body>");

        responseBuilder.append("<h1>DAL Demo</h1>");

        final String ip = Foundation.net().getHostAddress();

        responseBuilder.append("<h3>DalDatabaseOperations Demo</h3>");
        try {
            final String name = "admin_" + ip;
            final int age = 20;
            dalDatabaseOperations.update("insert into `user` (`name`, `age`) values (?, ?)", new DalHints(), name, age);
            responseBuilder.append("UserInserted: name=").append(name).append(", age=").append(age);
            responseBuilder.append("<br/>");
            DalUser res = dalDatabaseOperations.queryForObject("select * from `user` where name = ? limit 1", new DalHints(), SQLResult.type(DalUser.class), name);
            responseBuilder.append("UserQueried").append(res);
            responseBuilder.append("<br/>");
            dalDatabaseOperations.update("delete from `user` where name = ?", new DalHints(), name);
            responseBuilder.append("User with name '").append(name).append("' is deleted.");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw)) {
                e.printStackTrace(pw);
                pw.flush();
                responseBuilder.append("Error: ").append(sw);
            }
        }

        responseBuilder.append("<h3>DalTableOperations Demo</h3>");
        try {
            final String name = "admin1_" + ip;
            DalUser user = new DalUser();
            user.setName(name);
            user.setAge(21);
            dalTableOperations.insert(new DalHints(), user);
            responseBuilder.append("UserInserted: ").append(user);
            responseBuilder.append("<br/>");
            List<DalUser> users = dalTableOperations.queryBy(user, new DalHints());
            responseBuilder.append("UserQueried: ").append(users.getFirst());
            responseBuilder.append("<br/>");
            dalTableOperations.delete(new DalHints(), users);
            responseBuilder.append("User with name '").append(name).append("' is deleted.");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw)) {
                e.printStackTrace(pw);
                pw.flush();
                responseBuilder.append("Error: ").append(sw);
            }
        }

        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }
}