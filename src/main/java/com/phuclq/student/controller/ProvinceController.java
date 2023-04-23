package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.Province;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.service.CategoryService;
import com.phuclq.student.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private RestEntityResponse restEntityRes;

    @GetMapping("/province")
    public ResponseEntity<?> getAllProvince() {

        List<Province> result = provinceService.findAll();
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
    }

    @PostMapping("/province/create")
    public ResponseEntity<?> createProvince(@RequestBody Province province) {
        Province provinceCreate = provinceService.save(province);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(provinceCreate).getResponse();
    }

    @PutMapping("/province/update")
    public ResponseEntity<?> updateProvince(@RequestBody Province categoryDTO) {

        Province province = provinceService.update(categoryDTO);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(province).getResponse();
    }

    @DeleteMapping("/province/delete/{Id}")
    public ResponseEntity<?> deleteProvince(@PathVariable int Id) {

        provinceService.deleteById(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }

    @GetMapping("/province/id")
    public ResponseEntity<?> findByIdProvince(@PathVariable int Id) {
        Optional<Province> province = provinceService.findAllById(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(province).getResponse();
    }



}
