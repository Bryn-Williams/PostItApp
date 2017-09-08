package elpackage.postmee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@SpringBootApplication
public class PostmeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostmeeApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepo, PostItNoteRepository postItRepo) {
		return (args) -> {

			AUser AUserOne = new AUser("Bryn", "bryn");
			AUser AUserTwo = new AUser("Emi", "emi");
			userRepo.save(AUserOne);
			userRepo.save(AUserTwo);

			PostItNote noteOne = new PostItNote("TRIAL TRIAL TRIAL", "toDo");
			PostItNote noteTwo = new PostItNote("SECOND TRIAL SECOND TRIAL", "toDo");

			PostItNote emiNoteOne = new PostItNote("EMI TRIAL ONE", "inProcess");
			PostItNote emiNoteTwo = new PostItNote("EMI TWO TWO TWO TRIAL", "done");

			AUserOne.addPostItNote(noteOne);
			AUserOne.addPostItNote(noteTwo);

			AUserTwo.addPostItNote(emiNoteOne);
			AUserTwo.addPostItNote(emiNoteTwo);

			postItRepo.save(noteOne);
			postItRepo.save(noteTwo);

			postItRepo.save(emiNoteOne);
			postItRepo.save(emiNoteTwo);
		};
	}
}


@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	UserRepository userRepo;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

				AUser loggedInAUser = userRepo.findByUserName(userName);

				if (loggedInAUser != null) {

					return new User(loggedInAUser.getUserName(), loggedInAUser.getThePassword(),
							AuthorityUtils.createAuthorityList("USER"));

					//add if statement for admin

				} else {
					throw new UsernameNotFoundException("Unknown user: " + userName);
				}
			}
		};
	}
}

@Configuration
class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/web/theMainPage.html");
	}
}


@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/web/theMainPage.html").permitAll()
				.antMatchers("/web/scripts/**").permitAll()
				.antMatchers("/web/styles/**").permitAll()
				.antMatchers("/scripts/*").permitAll()
				.antMatchers("/styles/*").permitAll()
				.antMatchers("/api/createUser").permitAll()
				.antMatchers("/appp/login").permitAll()
				.antMatchers("/rest/**").denyAll()
				.anyRequest().fullyAuthenticated();
				//.and()
				//.formLogin();

		http.formLogin()
		    	.usernameParameter("userName")
				.passwordParameter("thePassword")
				.loginPage("/appp/login");

		http.logout().logoutUrl("/appp/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}

// TODO: 31/08/2017 NEXT IS OPTION TO CREATE A POST IT AND ADD IT TO DO IT SECTION!!! 
// TODO: 31/08/2017 SPEAK TO RAUL ABOUT EVERYONE SEEING REST REPO!!! 
