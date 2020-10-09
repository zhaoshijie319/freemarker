package com.example.freemarker.controller;

import com.example.freemarker.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/json")
public class JsonController {

    @Qualifier("ftlConfiguraion")
    @Resource
    private Configuration configuration;

    @PostMapping("/users")
    public String users(@RequestBody List<User> users) throws IOException, TemplateException {
        Template template = configuration.getTemplate("user.ftl");
        StringWriter writer = new StringWriter();
        Map<String, List<User>> map = Collections.singletonMap("users", users);
        template.process(map, writer);
        return writer.toString();
    }

    @GetMapping("/excelToJson")
    public String excelToJson() throws IOException, TemplateException {
        File file = new File(System.getProperty("user.dir"),"excel/user.xlsx");
        if (!file.exists() || !file.isFile()) {
            throw new NullPointerException("the file not exist or the path is not a file with path: " + file.getPath());
        }
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheet("User");

        List<User> users = new ArrayList<User>();
        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            User user = new User();
            user.setName(row.getCell(0).getStringCellValue());
            user.setAge((int) row.getCell(1).getNumericCellValue());
            users.add(user);
        }

        Template template = configuration.getTemplate("user.ftl");
        StringWriter writer = new StringWriter();
        Map<String, List<User>> map = Collections.singletonMap("users", users);
        template.process(map, writer);
        return writer.toString();
    }
}
