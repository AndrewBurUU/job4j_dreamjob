package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.*;
import org.springframework.mock.web.*;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.*;
import ru.job4j.dreamjob.service.*;

import javax.servlet.http.*;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;

    private UserController userController;

    private HttpServletRequest httpServletRequest;

    private HttpSession httpSession;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        httpServletRequest = new MockHttpServletRequest();
        httpSession = new MockHttpSession();
    }

    @Test
    public void whenGetRegisterUser() {
        var view = userController.getRegistrationPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenPostRegisterUser() {
        var user = new User(1, "user1@mail.ru", "user1", "111");
        when(userService.save(any())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);

        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenPostRegisterUserError() {
        when(userService.save(any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, new User());

        assertThat(view).isEqualTo("errors/404");
    }

    @Test
    public void whenGetLoginPage() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenPostLoginPage() {
        var user = new User(1, "user1@mail.ru", "user1", "111");
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);
        var actualUser = httpServletRequest.getSession().getAttribute("user");

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(user).isEqualTo(actualUser);
    }

    @Test
    public void whenPostLoginPageError() {
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(new User(), model, httpServletRequest);

        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenGetLogout() {
        var view = userController.logout(httpSession);

        assertThat(view).isEqualTo("redirect:/users/login");
    }
}