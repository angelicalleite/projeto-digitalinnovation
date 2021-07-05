package bio.especies.ameaca.service;

import bio.especies.ameaca.builder.AmeacaDTOBuilder;
import bio.especies.ameaca.dto.mapper.AmeacaMapper;
import bio.especies.ameaca.dto.request.AmeacaDTO;
import bio.especies.ameaca.entities.Ameaca;
import bio.especies.ameaca.exception.AmeacaAlreadyRegisteredException;
import bio.especies.ameaca.exception.AmeacaNotFoundException;
import bio.especies.ameaca.repositories.AmeacaRepository;
import bio.especies.ameaca.services.AmeacaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmeacaServiceTest {

    private static final long INVALID_AMEACA_ID = 1L;

    @Mock
    private AmeacaRepository ameacaRepository;

    private AmeacaMapper ameacaMapper = AmeacaMapper.INSTANCE;

    @InjectMocks
    private AmeacaService ameacaService;

    @Test
    void whenAmeacaInformedThenItShouldBeCreated() throws AmeacaAlreadyRegisteredException {
        // given
        AmeacaDTO expectedAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedSavedAmeaca = ameacaMapper.toModel(expectedAmeacaDTO);

        // when
        when(ameacaRepository.findByEspecie(expectedAmeacaDTO.getEspecie())).thenReturn(Optional.empty());
        when(ameacaRepository.save(expectedSavedAmeaca)).thenReturn(expectedSavedAmeaca);

        //then
        AmeacaDTO createdAmeacaDTO = ameacaService.create(expectedAmeacaDTO);

        assertThat(createdAmeacaDTO.getId(), is(equalTo(expectedAmeacaDTO.getId())));
        assertThat(createdAmeacaDTO.getEspecie(), is(equalTo(expectedAmeacaDTO.getEspecie())));
        assertThat(createdAmeacaDTO.getLocal(), is(equalTo(expectedAmeacaDTO.getLocal())));
    }

    @Test
    void whenAlreadyRegisteredAmeacaInformedThenAnExceptionShouldBeThrown() {
        // given
        AmeacaDTO expectedAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca duplicatedAmeaca = ameacaMapper.toModel(expectedAmeacaDTO);

        // when
        when(ameacaRepository.findByEspecie(expectedAmeacaDTO.getEspecie())).thenReturn(Optional.of(duplicatedAmeaca));

        // then
        assertThrows(AmeacaAlreadyRegisteredException.class, () -> ameacaService.create(expectedAmeacaDTO));
    }

    @Test
    void whenValidAmeacaNameIsGivenThenReturnAAmeaca() throws AmeacaNotFoundException {
        // given
        AmeacaDTO expectedFoundAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedFoundAmeaca = ameacaMapper.toModel(expectedFoundAmeacaDTO);

        // when
        when(ameacaRepository.findByEspecie(expectedFoundAmeaca.getEspecie())).thenReturn(Optional.of(expectedFoundAmeaca));

        // then
        AmeacaDTO foundAmeacaDTO = ameacaService.findByEspecie(expectedFoundAmeacaDTO.getEspecie());

        assertThat(foundAmeacaDTO, is(equalTo(expectedFoundAmeacaDTO)));
    }

    @Test
    void whenNotRegisteredAmeacaNameIsGivenThenThrowAnException() {
        // given
        AmeacaDTO expectedFoundAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();

        // when
        when(ameacaRepository.findByEspecie(expectedFoundAmeacaDTO.getEspecie())).thenReturn(Optional.empty());

        // then
        assertThrows(AmeacaNotFoundException.class, () -> ameacaService.findByEspecie(expectedFoundAmeacaDTO.getEspecie()));
    }

    @Test
    void whenListAmeacaIsCalledThenReturnAListOfAmeacas() {
        // given
        AmeacaDTO expectedFoundAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedFoundAmeaca = ameacaMapper.toModel(expectedFoundAmeacaDTO);

        //when
        when(ameacaRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundAmeaca));

        //then
        List<AmeacaDTO> foundListAmeacasDTO = ameacaService.listAll();

        assertThat(foundListAmeacasDTO, is(not(empty())));
        assertThat(foundListAmeacasDTO.get(0), is(equalTo(expectedFoundAmeacaDTO)));
    }

    @Test
    void whenListAmeacaIsCalledThenReturnAnEmptyListOfAmeacas() {
        //when
        when(ameacaRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<AmeacaDTO> foundListAmeacasDTO = ameacaService.listAll();

        assertThat(foundListAmeacasDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAAmeacaShouldBeDeleted() throws AmeacaNotFoundException {
        // given
        AmeacaDTO expectedDeletedAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedDeletedAmeaca = ameacaMapper.toModel(expectedDeletedAmeacaDTO);

        // when
        when(ameacaRepository.findById(expectedDeletedAmeacaDTO.getId())).thenReturn(Optional.of(expectedDeletedAmeaca));
        doNothing().when(ameacaRepository).deleteById(expectedDeletedAmeacaDTO.getId());

        // then
        ameacaService.delete(expectedDeletedAmeacaDTO.getId());

        verify(ameacaRepository, times(1)).findById(expectedDeletedAmeacaDTO.getId());
        verify(ameacaRepository, times(1)).deleteById(expectedDeletedAmeacaDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementAmeacaStock() throws AmeacaNotFoundException, AmeacaStockExceededException {
        //given
        AmeacaDTO expectedAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedAmeaca = ameacaMapper.toModel(expectedAmeacaDTO);

        //when
        when(ameacaRepository.findById(expectedAmeacaDTO.getId())).thenReturn(Optional.of(expectedAmeaca));
        when(ameacaRepository.save(expectedAmeaca)).thenReturn(expectedAmeaca);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedAmeacaDTO.getQuantity() + quantityToIncrement;

        // then
        AmeacaDTO incrementedAmeacaDTO = ameacaService.increment(expectedAmeacaDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedAmeacaDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedAmeacaDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        AmeacaDTO expectedAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedAmeaca = ameacaMapper.toModel(expectedAmeacaDTO);

        when(ameacaRepository.findById(expectedAmeacaDTO.getId())).thenReturn(Optional.of(expectedAmeaca));

        int quantityToIncrement = 80;
        assertThrows(AmeacaStockExceededException.class, () -> ameacaService.increment(expectedAmeacaDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        AmeacaDTO expectedAmeacaDTO = AmeacaDTOBuilder.builder().build().toAmeacaDTO();
        Ameaca expectedAmeaca = ameacaMapper.toModel(expectedAmeacaDTO);

        when(ameacaRepository.findById(expectedAmeacaDTO.getId())).thenReturn(Optional.of(expectedAmeaca));

        int quantityToIncrement = 45;
        assertThrows(AmeacaStockExceededException.class, () -> ameacaService.increment(expectedAmeacaDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(ameacaRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        assertThrows(AmeacaNotFoundException.class, () -> ameacaService.increment(INVALID_BEER_ID, quantityToIncrement));
    }

}
