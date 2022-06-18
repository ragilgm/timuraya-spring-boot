package timuraya.invoice_biaya_operational.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import timuraya.invoice_biaya_operational.dto.LoginDto;
import timuraya.invoice_biaya_operational.dto.LoginRequestDto;
import timuraya.invoice_biaya_operational.dto.UserDto;
import timuraya.invoice_biaya_operational.dto.UserRequestDto;
import timuraya.invoice_biaya_operational.entity.User;
import timuraya.invoice_biaya_operational.repository.BiodataRepository;
import timuraya.invoice_biaya_operational.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Service
@RequiredArgsConstructor
public class UserService {

    private final MapperFacade mapperFacade;
    private final UserRepository userRepository;
    private final BiodataRepository biodataRepository;


    public UserDto createUser(UserRequestDto userRequestDto) throws NotFoundException {
       return biodataRepository.findById(userRequestDto.getBiodataId()).map(data-> {
            User user = mapperFacade.map(userRequestDto,User.class);
            user.setBiodata(data);
            return mapperFacade.map(userRepository.save(user),UserDto.class);
        }).orElseThrow(()-> new NotFoundException("Biodata tidak ditemukan"));

    }

    public List<UserDto> getUser() {
      return userRepository.findAll().stream().map(data-> mapperFacade.map(data,UserDto.class)).collect(Collectors.toList());
    }

    public LoginDto login(LoginRequestDto loginRequestDto) throws NotFoundException {
        return userRepository.findByUsernameAndPassword(loginRequestDto.getUsername(),loginRequestDto.getPassword())
                .map(data-> mapperFacade.map(data,LoginDto.class))
                .orElseThrow(()->new NotFoundException("Login Gagal"));
    }
}
