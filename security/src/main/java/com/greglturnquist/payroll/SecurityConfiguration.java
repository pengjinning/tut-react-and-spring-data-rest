/*
 * @Author: jack ning github@bytedesk.com
 * @Date: 2024-01-25 23:53:50
 * @LastEditors: jack ning github@bytedesk.com
 * @LastEditTime: 2024-01-31 12:44:00
 * @FilePath: /tut-react-and-spring-data-rest/security/src/main/java/com/greglturnquist/payroll/SecurityConfiguration.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Configuration
@EnableWebSecurity // <1>
@EnableGlobalMethodSecurity(prePostEnabled = true) // <2>
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // <3>

	@Autowired
	private SpringDataJpaUserDetailsService userDetailsService; // <4>

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(this.userDetailsService)
				.passwordEncoder(Manager.PASSWORD_ENCODER);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // <5>
		http
				.authorizeRequests()
				.antMatchers("/built/**", "/main.css").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin().disable()))
				.logout()
				.logoutSuccessUrl("/");
	}

}
// end::code[]
