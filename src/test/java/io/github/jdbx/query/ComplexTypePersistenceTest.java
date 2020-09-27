package io.github.jdbx.query;


import io.github.jdbx.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJdbcConfiguration.class, QueryScannerConfiguration.class})
public class ComplexTypePersistenceTest {


    @Autowired
    DataSource dataSource;


    @Autowired
    ComplexRepository cr;


    @Test
    public void ComplexTypeTest() {

        cr.createTable();

        Assert.assertTrue(cr.findAll().isEmpty());
        Assert.assertEquals(1, cr.insertValue(new Complex("KEY", new ValueObject("VALUE"))));

        Complex complex = cr.findByKey("KEY");

        Assert.assertEquals("KEY", complex.key);
        Assert.assertEquals("VALUE", complex.valueObject.value);

        Assert.assertFalse(cr.findAll().isEmpty());
    }




    @QueryRepository
    public interface ComplexRepository {

        @Query("CREATE TABLE CL_CONF (CONF_KEY VARCHAR(64) PRIMARY KEY NOT NULL, CONF_VALUE VARCHAR(64) NOT NULL)")
        void createTable();


        @Query("INSERT INTO CL_CONF(CONF_KEY, CONF_VALUE) VALUES(:key, :valueObject.value)")
        int insertValue(Complex complex);

        @Query(value = "SELECT * FROM CL_CONF WHERE CONF_KEY = :key", mapper = ComplexMapper.class)
        Complex findByKey(@Bind("key") String key);

        @Query(value = "SELECT * FROM CL_CONF" , mapper = ComplexMapper.class)
        List<Complex> findAll();
    }

    public static class Complex {
        public String key;
        public ValueObject valueObject;

        public Complex(String key, ValueObject valueObject) {
            this.key = key;
            this.valueObject = valueObject;
        }

    }

    public static class ValueObject {
        public String value;

        public ValueObject(String value) {
            this.value = value;
        }

    }

    public static class ComplexMapper implements RowMapper<Complex> {

        public ComplexMapper() {
        }

        @Override
        public Complex mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Complex(rs.getString(1), new ValueObject(rs.getString(2)));
        }
    }
}
