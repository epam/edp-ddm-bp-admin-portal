/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.bpms.admin.config;

import java.util.stream.Stream;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.DatabaseStartupValidator;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class GeneralConfig {

  @Bean
  public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource,
      @Value("${database-startup-validator.interval:10}") int interval,
      @Value("${database-startup-validator.timeout:100}") int timeout) {
    var dsv = new DatabaseStartupValidator();
    dsv.setInterval(interval);
    dsv.setTimeout(timeout);
    dsv.setDataSource(dataSource);
    dsv.setValidationQuery(DatabaseDriver.POSTGRESQL.getValidationQuery());
    return dsv;
  }

  @Bean
  public static BeanFactoryPostProcessor dependsOnPostProcessor() {
    return bf -> {
      String[] jpa = bf.getBeanNamesForType(JpaBaseConfiguration.class);
      Stream.of(jpa)
          .map(bf::getBeanDefinition)
          .forEach(it -> it.setDependsOn("databaseStartupValidator"));
    };
  }

  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }
}
