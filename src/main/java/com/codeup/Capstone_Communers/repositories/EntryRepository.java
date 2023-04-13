package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Entry;
import com.codeup.Capstone_Communers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findByTitle(String title);
    Entry findById(long id);
    List<Entry> findAllByUser(User user);

    @Query("from Entry j where j.title like %:name%")
    List<Entry> findLikeName(@Param("name") String name);

}
