package com.apatro.landon.web.services;

import com.apatro.landon.business.domain.RoomReservation;
import com.apatro.landon.business.service.ReservationService;
import com.apatro.landon.data.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationServiceController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method= RequestMethod.GET, value="/reservations/{date}")
    public List<RoomReservation> getAllReservationsForDate(@PathVariable(value="date")String dateString){
        return this.reservationService.getRoomReservationsForDate(dateString);
    }

    @GetMapping("/rooms")
    List<Room> contacts() {
        return this.reservationService.getAllRoomsInfo();
    }

}
