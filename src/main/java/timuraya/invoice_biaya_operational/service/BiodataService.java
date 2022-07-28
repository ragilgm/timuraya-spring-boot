package timuraya.invoice_biaya_operational.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import timuraya.invoice_biaya_operational.entity.Biodata;
import timuraya.invoice_biaya_operational.repository.BiodataRepository;

import java.util.concurrent.TimeUnit;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class BiodataService {

    private final RedisTemplate redisTemplate;
    private final BiodataRepository biodataRepository;

    public Biodata createBiodata(Biodata biodata) {
        return biodataRepository.save(biodata);
    }

    public Object getBiodata(String request){
         return isAllowedRequest(request);
    }


    public boolean isAllowedRequest(String idempotentKey) {

//        var isAllowed = false;
//
//        long count = redisTemplate.opsForValue().increment(idempotentKey, 1);
//        log.debug("count : {}", count);
//
//        if (count == 1) {
//            redisTemplate.expire(idempotentKey, 10, TimeUnit.SECONDS);
//            isAllowed=true;
//        }
//
//        return isAllowed;
        return true;
    }
}
