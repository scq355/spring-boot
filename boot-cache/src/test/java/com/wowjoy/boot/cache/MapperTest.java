package com.wowjoy.boot.cache;

import com.wowjoy.boot.cache.bean.Employee;
import com.wowjoy.boot.cache.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void testEmployeeFind() {
        Employee employee = employeeMapper.getEmpById(1);
        log.info(employee.toString());
    }

    @Test
    public void testEmployeeInsert() {
        Employee employee = new Employee().setLastName("孙悟空")
                .setGender(1)
                .setEmail("nwpuscq355@outlook.com")
                .setDId(1);
        employeeMapper.insertEmp(employee);
    }
}
