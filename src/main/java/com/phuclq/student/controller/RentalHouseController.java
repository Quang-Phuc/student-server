package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.RentalHouse;
import com.phuclq.student.service.RentalHouseService;
import com.phuclq.student.service.RentalHouseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RentalHouseController {
    @Autowired
    private RentalHouseService RentalHouseService;
    @Autowired
    private RestEntityResponse restEntityRes;

    @GetMapping("/rentalhouse")
    public ResponseEntity<?> getAllRentalHouse( Pageable pageable) {

        Page<RentalHouse> result = RentalHouseService.findAll(pageable);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
    }

    @PostMapping("/rentalhouse/create")
    public ResponseEntity<?> createRentalHouse(@RequestBody RentalHouse RentalHouse) {
        RentalHouse RentalHouseResult = RentalHouseService.save(RentalHouse);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(RentalHouseResult).getResponse();
    }

    @PutMapping("/rentalhouse/update")
    public ResponseEntity<?> updateRentalHouse(@RequestBody RentalHouse RentalHouse) {

        RentalHouse RentalHouseResult = RentalHouseService.update(RentalHouse);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(RentalHouseResult).getResponse();
    }

    @DeleteMapping("/rentalhouse/delete/{Id}")
    public ResponseEntity<?> deleteRentalHouse(@PathVariable int Id) {

        RentalHouseService.deleteById(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }

    @GetMapping("/rentalhouse/id")
    public ResponseEntity<?> findByIdRentalHouse(@PathVariable int Id) {
        RentalHouse RentalHouse = RentalHouseService.findAllById(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(RentalHouse).getResponse();
    }
}
