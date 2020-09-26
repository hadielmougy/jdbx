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
package io.github.jdbx;

import io.github.jdbx.query.QueryRepo;
import io.github.jdbx.query.deeper.QueryRepo2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJdbcConfiguration.class, QueryScannerConfiguration.class})
public class QueryRepositoryScannerTest {

    @Autowired
    QueryRepo queryRepo;

    @Autowired
    QueryRepo2 queryRepo2;

    @Test
    public void checkInjection() {
        Assert.assertNotNull(queryRepo);
        Assert.assertNotNull(queryRepo2);
    }

}
