package com.ivanpuzyrev.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivanpuzyrev.data.db_model.GameResultDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultsDao {

    @Query("SELECT * FROM game_results")
    fun getGameResults(): Flow<List<GameResultDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameResult(gameResultDbModel: GameResultDbModel)

    @Query("DELETE FROM game_results WHERE id=:gameResultId")
    suspend fun deleteGameResult(gameResultId: Int): Int
}