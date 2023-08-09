package com.example.bff;

import com.example.bff.api.operations.auth.login.LoginUserRequest;
import com.example.bff.api.operations.auth.register.RegisterUserRequest;
import com.example.bff.persistence.entities.Role;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.bff.rest.BffApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BffApplication.class)
//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@RequiredArgsConstructor
@PropertySource("classpath:tests.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    private User user;

//    @Autowired
//    public UserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository) {
//        this.mockMvc = mockMvc;
//        this.objectMapper = objectMapper;
//        this.userRepository = userRepository;
//    }

    @BeforeEach
    void setUp() {
        this.user = User
                .builder()
                .phoneNumber("0895070092")
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .role(Role.USER)
                .password("test")
                .build();
    }

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }

    @Test
    void test_UserRegister_Successfully() throws Exception {
        RegisterUserRequest userRegisterRequest = RegisterUserRequest
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .build();

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegisterRequest))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['email']").value(user.getEmail()))
                .andExpect(jsonPath("$['firstName']").value(user.getFirstName()))
                .andExpect(jsonPath("$['lastName']").value(user.getLastName()))
                .andExpect(jsonPath("$['phoneNumber']").value(user.getPhoneNumber()));
    }

    @Test
    void test_UserRegister_FailedRequestValidation_Returns_BadRequest() throws Exception {
        RegisterUserRequest userWithInvalidFields = RegisterUserRequest
                .builder()
                .firstName("  ")
                .lastName("  ")
                .email("test@test")
                .phoneNumber("0895070092222")
                .password("  ")
                .build();

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userWithInvalidFields))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void test_UserRegister_ExistingUser_Returns_BadRequest() throws Exception {
        userRepository.save(user);

        RegisterUserRequest existingUser = RegisterUserRequest
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .build();

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(existingUser))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void test_UserLogin_Successfully() throws Exception {
        mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .accept(MediaType.APPLICATION_JSON)
        );

        LoginUserRequest userLoginRequest = LoginUserRequest
                .builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginRequest))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void test_UserLogin_Fail_Returns_Forbidden() throws Exception {
        LoginUserRequest userLoginRequest = LoginUserRequest
                .builder()
                .email("notexisting@abv.bg")
                .password(user.getPassword())
                .build();

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginRequest))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

//    @Test
//    @WithMockUser(username = "test@test.com")
//    void test_UserChangePassword_Successfully() throws Exception {
//        userRepository.save(user);
//
//        ChangeUserPasswordRequest userRequest = ChangeUserPasswordRequest
//                .builder()
//                .newPassword("new_password")
//                .build();
//
//        mockMvc.perform(
//                        put("/api/bff/users/changePassword")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(userRequest))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    void test_UserChangePassword_Unsuccessfully_Returns_Forbidden() throws Exception {
//        ChangeUserPasswordRequest userRequest = ChangeUserPasswordRequest
//                .builder()
//                .newPassword("new_password")
//                .build();
//
//        mockMvc.perform(
//                        put("/api/bff/users/changePassword")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(userRequest))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isForbidden());
//    }
}
