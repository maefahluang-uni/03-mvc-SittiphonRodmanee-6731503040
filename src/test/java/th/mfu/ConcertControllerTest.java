package th.mfu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
    @BeforeEach
    public void clearConcerts() throws Exception {
        mockMvc.perform(get("/delete-concert"));
    }

    @Test
    public void testAddAndDeleteConcerts() throws Exception {
        // add 1 concert
        mockMvc.perform(post("/concerts")
                .param("title", "Test Concert")
                .param("performer", "Test Performer")
                .param("date", "2025-08-25")
                .param("description", "Test Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/concerts"));

        
        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-concert"))
                .andExpect(model().attribute("concerts", hasSize(1)));

        
        mockMvc.perform(get("/delete-concert"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/concerts"));

       
        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-concert"))
                .andExpect(model().attribute("concerts", hasSize(0)));
    }
}