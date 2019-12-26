package com.apatro.landon.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.apatro.landon.data.entity.Guest;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {

}