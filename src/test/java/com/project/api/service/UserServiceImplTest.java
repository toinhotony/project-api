package com.project.api.service;

import com.project.api.model.User;
import com.project.api.repository.UserRepository;
import com.project.api.service.dto.UserDTO;
import com.project.api.service.dto.UserIDTO;
import com.project.api.service.exception.CpfInvalidException;
import com.project.api.service.exception.CpfNotNumberEvenException;
import com.project.api.service.exception.ObjectNotFoundException;
import com.project.api.util.FileUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private static MockedStatic<FileUtil> fileUtilsMockedStatic;

    private User user;

    private UserDTO userDTO;

    private UserIDTO userIDTO;

    @BeforeAll
    public static void setupBeforeClass() {
        fileUtilsMockedStatic = mockStatic(FileUtil.class);
    }

    @AfterAll
    public static void setupAfterClass() {
        fileUtilsMockedStatic.close();
    }

    @BeforeEach
    void setUp() {
        user = prepareUser();
        userDTO = prepareUserDTO();
        userIDTO = prepareUserIDTO();
    }

    @Test
    @DisplayName("Deve retornar todos usuários cadastros")
    void shouldReturnUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> userDTOList = userService.findAll();

        assertThat(userDTOList.get(0).getId()).isEqualTo("12345");
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar inserir um usuario cpf com caracteres diferantes de numeros")
    void shouldReturnExceptionInsertWithCpfNotNumber() {
        userIDTO.setCpf("12345678t933");

        assertThrows(CpfInvalidException.class, () -> userService.insert(userIDTO));
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar inserir um usuario cpf com total de digitos diferente de 11")
    void shouldReturnExceptionInsertWithCpfInvalid() {
        userIDTO.setCpf("123456789388");

        assertThrows(CpfInvalidException.class, () -> userService.insert(userIDTO));
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar inserir um usuario cpf impar")
    void shouldReturnExceptionInsertWithCpfOdd() {
        userIDTO.setCpf("12345678933");

        assertThrows(CpfNotNumberEvenException.class, () -> userService.insert(userIDTO));
    }

    @Test
    @DisplayName("Deve inserir usuário")
    void shouldInsert() {
        when(userRepository.insert(any(User.class))).thenReturn(user);

        UserDTO userNewDTO = userService.insert(userIDTO);

        assertThat(userNewDTO).isNotNull();
        assertThat(userNewDTO.getCpf()).isEqualTo("22255588898");
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar atualizar um usuario cpf com caracteres diferantes de numeros")
    void shouldReturnExceptionUpdateWithCpfNotNumber() {
        String id = userDTO.getId();
        userDTO.setCpf("123456t78933");

        assertThrows(CpfInvalidException.class, () -> userService.update(id, userDTO));
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar update um usuario cpf com total de digitos diferente de 11")
    void shouldReturnExceptionUpdateWithCpfInvalid() {
        userIDTO.setCpf("123456789388");

        assertThrows(CpfInvalidException.class, () -> userService.insert(userIDTO));
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar atualizar um usuario com cpf impar")
    void shouldReturnExceptionUpdateWithCpfOdd() {
        String id = userDTO.getId();
        userDTO.setCpf("12345678933");

        assertThrows(CpfNotNumberEvenException.class, () -> userService.update(id, userDTO));
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar atualizar um usuario que não existe na base")
    void shouldReturnExceptionUpdateUserNotExist() {
        String id = "19547";

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.update(id, userDTO));
    }

    @Test
    @DisplayName("Deve atualizar um usuario")
    void shouldUpdateUser() {
        String id = userDTO.getId();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userNewDTO = userService.update(id, userDTO);

        assertThat(userNewDTO).isNotNull();
        assertThat(userNewDTO.getId()).isEqualTo(userDTO.getId());
    }

    @Test
    @DisplayName("Deve retornar Exception ao tentar deletar um usuario que não existe na base")
    void shouldReturnExceptionDeleteUserNotExist() {
        String id = "19547";

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.delete(id));
    }

    @Test
    @DisplayName("Deve deletar um usuario")
    void shouldDeleteUser() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        userService.delete(user.getId());

        verify(userRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Deve retornar User Properties para usar no Job")
    void shouldReturnUserPropertiesForJob() throws IOException {
        MultipartFile file = prepareMultipartFile();

        Properties properties = userService.userJobProperties(file);

        assertThat(properties).isNotNull();
    }

    private User prepareUser() {
        User user = new User();

        user.setId("12345");
        user.setName("Name Test");
        user.setCpf("22255588898");

        return user;
    }

    private UserIDTO prepareUserIDTO() {
        UserIDTO userIDTO = new UserIDTO();

        userIDTO.setName("Name Test");
        userIDTO.setCpf("22255588898");

        return userIDTO;
    }

    private UserDTO prepareUserDTO() {
        UserDTO userDTO = new UserDTO();

        userDTO.setId("12345");
        userDTO.setName("Name Test");
        userDTO.setCpf("45612378988");

        return userDTO;
    }

    private MultipartFile prepareMultipartFile() {
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "fileTest";
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File file) throws IllegalStateException {

            }
        };

        return multipartFile;
    }
}