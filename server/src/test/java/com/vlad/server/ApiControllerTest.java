package com.vlad.server;

import com.vlad.server.dto.ChatDTO;
import com.vlad.server.dto.UserDTO;
import com.vlad.server.entity.Chat;
import com.vlad.server.entity.User;
import com.vlad.server.service.chat.ChatService;
import com.vlad.server.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ChatService chatService;

    @Test
    public void testRegisterUser() throws Exception {
        // Создание объекта User, который будет передан в метод registerUser
        User user = new User("John", "Doe", "johndoe", "password");

        // Ожидаемый объект UserDTO, который будет возвращен из метода userService.createUser
        UserDTO expectedUserDTO = new UserDTO(user);

        // Мокирование поведения userService.createUser
        when(userService.createUser(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(expectedUserDTO);

        // Выполнение POST-запроса на /registration и передача JSON-представления объекта User
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"surname\": \"Doe\", \"login\": \"johndoe\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Ожидаемый статус ответа 201 CREATED
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("John Doe")) // Проверка значений возвращенного JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true));

        // Проверка, что метод userService.createUser был вызван с корректными аргументами
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> surnameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> loginCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(userService).createUser(nameCaptor.capture(), surnameCaptor.capture(), loginCaptor.capture(), passwordCaptor.capture());
        assertEquals("John", nameCaptor.getValue());
        assertEquals("Doe", surnameCaptor.getValue());
        assertEquals("johndoe", loginCaptor.getValue());
        assertEquals("password", passwordCaptor.getValue());
    }
    @Test
    public void testCreateChat() throws Exception {
        User testUser1 = new User("John", "Doe", "john_doe", "password1");
        User testUser2 = new User("Doe", "John", "doe_john", "password2");
        testUser1.setId(1L);
        testUser2.setId(2L);
        List<Long> userIds = new ArrayList<>();
        userIds.add(testUser1.getId());
        userIds.add(testUser2.getId());

        Chat chat = new Chat("testChat", false);

        when(chatService.createChat(userIds , false)).thenReturn(new ChatDTO(chat));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/createChat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userIds\": [1, 2, 3], \"isGroup\": true}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}
