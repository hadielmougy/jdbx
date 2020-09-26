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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMapperTest {

    @Mock
    ResultSet resultSet;

    @Test
    public void testNull() throws SQLException {
        DefaultMapper m = new DefaultMapper("PARAM", String.class);
        Assert.assertNull(m.getObject(resultSet));
    }

    @Test
    public void testString() throws SQLException {
        DefaultMapper m = new DefaultMapper("PARAM", String.class);
        when(resultSet.findColumn("PARAM")).thenReturn(1);
        when(resultSet.getString(1)).thenReturn("MY_VALUE");
        Assert.assertEquals("MY_VALUE", m.getObject(resultSet));
    }
}
