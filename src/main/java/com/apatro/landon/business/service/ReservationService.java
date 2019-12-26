package com.apatro.landon.business.service;

import com.apatro.landon.business.domain.RoomReservation;
import com.apatro.landon.data.entity.Guest;
import com.apatro.landon.data.entity.Reservation;
import com.apatro.landon.data.entity.Room;
import com.apatro.landon.data.repository.GuestRepository;
import com.apatro.landon.data.repository.ReservationRepository;
import com.apatro.landon.data.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(String dateString){
            Date date = this.createDateFromDateString(dateString);
            Iterable<Room> rooms = this.roomRepository.findAll();
            Map<Long,RoomReservation > roomReservationMap = new HashMap<>();
            rooms.forEach( room -> {
                RoomReservation roomReservation = new RoomReservation();
                roomReservation.setRoomId(room.getId());
                roomReservation.setRoomName(room.getName());
                roomReservation.setRoomNumber(room.getNumber());
                roomReservationMap.put(room.getId(),roomReservation);
            });
            Iterable<Reservation> reservations = this.reservationRepository.findByReservationDate(new java.sql.Date(date.getTime()));
            reservations.forEach( reservation -> {
                Optional<Guest> guest = this.guestRepository.findById(reservation.getGuestID());
                if(guest.isPresent()){
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
                    roomReservation.setDate(date);
                    roomReservation.setfName(guest.get().getfName());
                    roomReservation.setlName(guest.get().getlName());
                    roomReservation.setGuestId(guest.get().getId());
                }
            });
             List<RoomReservation> roomReservations = new ArrayList<>(roomReservationMap.values());
        /*for(Long roomId:roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(roomId));
        }*/
        return roomReservations;
     }

    private Date createDateFromDateString(String dateString){
        Date date = null;
        if(null!=dateString) {
            try {
                date = DATE_FORMAT.parse(dateString);
            }catch(ParseException pe){
                date = new Date();
            }
        }else{
            date = new Date();
        }
        return date;
    }

    public List<Room> getAllRoomsInfo(){
        return (List<Room>) roomRepository.findAll();
    }

}
