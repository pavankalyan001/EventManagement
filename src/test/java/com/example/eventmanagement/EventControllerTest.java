package com.example.eventmanagement;

import com.example.eventmanagement.model.EventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEvent_returnsId() throws Exception {
        EventRequest request = new EventRequest();
        request.setTitle("Conference");
        request.setDescription("Annual conference");
        request.setLocation("Chicago");
        request.setStartTime(Instant.parse("2026-03-10T10:00:00Z"));
        request.setEndTime(Instant.parse("2026-03-10T12:00:00Z"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void getEvent_notFound() throws Exception {
        mockMvc.perform(get("/api/events/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEvent_invalidInput() throws Exception {
        EventRequest request = new EventRequest();
        request.setTitle("");
        request.setLocation("");
        request.setStartTime(Instant.parse("2026-03-10T10:00:00Z"));
        request.setEndTime(Instant.parse("2026-03-10T09:00:00Z"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
