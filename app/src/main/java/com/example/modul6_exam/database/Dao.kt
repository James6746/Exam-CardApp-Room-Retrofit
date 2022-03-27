package com.example.modul6_exam.database
import androidx.room.*
import androidx.room.Dao
import com.example.modul6_exam.model.Card

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPost(post: Card)

    @Query("UPDATE cards SET is_exsist = :is_exsist WHERE id = :id")
    fun updateCard(id: Int, is_exsist: Boolean): Int

    @Query("select * from cards")
    fun getPosts(): List<Card>

    @Query("Select * from cards where id =:id")
    fun getPost(id: Int): Card

    @Query("Delete from cards")
    fun clearCards()

    @Query("select * from cards where is_exsist=0")
    fun getOfflinePosts(): List<Card>

}
