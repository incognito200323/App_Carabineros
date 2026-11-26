package com.example.app_carabineros.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_carabineros.model.Documento
import com.example.app_carabineros.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE rut = :rut AND contrasena = :contrasena AND activo = 1 LIMIT 1")
    suspend fun validarLogin(rut: String, contrasena: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE rut = :rut LIMIT 1")
    suspend fun buscarPorRut(rut: String): Usuario?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(usuario: Usuario): Long
}

@Dao
interface DocumentoDao {
    @Query("SELECT * FROM documentos ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<Documento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(documento: Documento)

    @Delete
    suspend fun eliminar(documento: Documento)
}

@Database(entities = [Usuario::class, Documento::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun documentoDao(): DocumentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "esfocar_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
