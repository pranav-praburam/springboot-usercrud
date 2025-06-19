package com.example.userCrud;

import org.springframework.data.jpa.repository.JpaRepository;
/*
 * JpaRepository<Post, Long> gives you built-in methods like:
 * findAll()
 * save()
 * deleteById()
 * findById()
 * You don’t have to manually write SQL — Spring does it for you.
 */

public interface PostRepository extends JpaRepository<Post, Long> {
    // No need to write anything – JPA gives you CRUD operations automatically!
}


