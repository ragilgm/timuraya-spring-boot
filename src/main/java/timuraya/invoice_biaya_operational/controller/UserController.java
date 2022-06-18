package timuraya.invoice_biaya_operational.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import timuraya.invoice_biaya_operational.dto.LoginDto;
import timuraya.invoice_biaya_operational.dto.LoginRequestDto;
import timuraya.invoice_biaya_operational.dto.UserDto;
import timuraya.invoice_biaya_operational.dto.UserRequestDto;
import timuraya.invoice_biaya_operational.service.UserService;

import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody UserRequestDto userRequestDto) throws NotFoundException {
        return  userService.createUser(userRequestDto);
    }

    @GetMapping
    public List<UserDto> getUser() {
        return  userService.getUser();
    }

    @PostMapping("/login")
    public LoginDto userLogin(@RequestBody LoginRequestDto loginRequestDto) throws NotFoundException {
        return userService.login(loginRequestDto);
    }


}
