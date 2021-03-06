/**
 * Copyright © 2015 digitalfondue (info@digitalfondue.ch)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jdbx.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.JdbcUtils;

import java.lang.annotation.Annotation;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultMapper extends ColumnMapper {

    private static final int ORDER = Integer.MAX_VALUE;

    public DefaultMapper(String name, Class<?> paramType) {
        super(name, paramType);
    }

    public Object getObject(ResultSet rs) throws SQLException {
        int columnIdx = rs.findColumn(name);
        return JdbcUtils.getResultSetValue(rs, columnIdx, paramType);
    }

    public static class Converter implements ParameterConverter {

        @Override
        public boolean accept(Class<?> parameterType, Annotation[] annotations) {
            return true;
        }

        @Override
        public void processParameter(String parameterName, Object arg,
                                     Class<?> parameterType, MapSqlParameterSource ps) {
            ps.addValue(parameterName, arg, StatementCreatorUtils.javaTypeToSqlParameterType(parameterType));
        }

        @Override
        public int order() {
            return ORDER;
        }

    }


    public static class Factory implements ColumnMapperFactory {

        @Override
        public ColumnMapper build(String name, Class<?> paramType) {
            return new DefaultMapper(name, paramType);
        }

        @Override
        public int order() {
            return ORDER;
        }

        @Override
        public boolean accept(Class<?> paramType, Annotation[] annotations) {
            return true;
        }

        @Override
        public RowMapper<Object> getSingleColumnRowMapper(Class<Object> clzz) {
            return new SingleColumnRowMapper<>(clzz);
        }

    }
}
