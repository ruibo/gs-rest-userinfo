package users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void userList() throws Exception {
        //
        // Verify access to list of users.
        //
        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createUser() throws Exception {
        //
        // Verify that a user can be created.
        //
        mockMvc.perform(post("/users/")
                  .content("{ \"userName\" : \"foo\" , \"password\" : \"password\" }")
                  .contentType("application/json"))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/users/foo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.userName", is("foo")));
    }

    @Test
    public void deleteUser() throws Exception {
        //
        // Verify that a user can be deleted.
        //
        mockMvc.perform(post("/users/")
                  .content("{ \"userName\" : \"bar\" , \"password\" : \"password\" }")
                  .contentType("application/json"))
                .andExpect(status().isCreated());
        mockMvc.perform(delete("/users/bar/"))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/users/bar/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/users/johndoe/"))
                .andExpect(status().isNotFound());
    }

}
