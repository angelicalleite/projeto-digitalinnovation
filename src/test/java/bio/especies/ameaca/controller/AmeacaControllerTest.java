package bio.especies.ameaca.controller;

import bio.especies.ameaca.builder.AmeacaDTOBuilder;
import bio.especies.ameaca.dto.request.AmeacaDTO;
import bio.especies.ameaca.exception.AmeacaNotFoundException;
import bio.especies.ameaca.services.AmeacaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static bio.especies.ameaca.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class AmeacaControllerTest {

    private static final String AMEACA_API_URL_PATH = "/api/v1/ameacas";
    private static final long VALID_AMEACA_ID = 1L;
    private static final long INVALID_AMEACA_ID = 2l;
    private static final String AMEACA_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String AMEACA_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private AmeacaService ameacaService;

    @InjectMocks
    private AmeacaController ameacaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ameacaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAAmeacaIsCreated() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        // when
        when(ameacaService.create(ameacaDTO)).thenReturn(ameacaDTO);

        // then
        mockMvc.perform(post(AMEACA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ameacaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(ameacaDTO.getEspecie())))
                .andExpect(jsonPath("$.brand", is(ameacaDTO.getAno())))
                .andExpect(jsonPath("$.type", is(ameacaDTO.getData().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        ameacaDTO.setAno(null);

        // then
        mockMvc.perform(post(AMEACA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ameacaDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        //when
        when(ameacaService.findByEspecie(ameacaDTO.getEspecie())).thenReturn(ameacaDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AMEACA_API_URL_PATH + "/" + ameacaDTO.getEspecie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(ameacaDTO.getEspecie())))
                .andExpect(jsonPath("$.brand", is(ameacaDTO.getAno())))
                .andExpect(jsonPath("$.type", is(ameacaDTO.getData().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        //when
        when(ameacaService.findByEspecie(ameacaDTO.getEspecie())).thenThrow(AmeacaNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AMEACA_API_URL_PATH + "/" + ameacaDTO.getEspecie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithAmeacasIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        //when
        when(ameacaService.listAll()).thenReturn(Collections.singletonList(ameacaDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AMEACA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(ameacaDTO.getEspecie())))
                .andExpect(jsonPath("$[0].brand", is(ameacaDTO.getAno())))
                .andExpect(jsonPath("$[0].type", is(ameacaDTO.getData().toString())));
    }

    @Test
    void whenGETListWithoutAmeacasIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        //when
        when(ameacaService.listAll()).thenReturn(Collections.singletonList(ameacaDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(AMEACA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        AmeacaDTO ameacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        //when
        doNothing().when(ameacaService).delete(ameacaDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(AMEACA_API_URL_PATH + "/" + ameacaDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(AmeacaNotFoundException.class).when(ameacaService).delete(INVALID_AMEACA_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(AMEACA_API_URL_PATH + "/" + INVALID_AMEACA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
