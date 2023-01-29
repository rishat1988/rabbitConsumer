package com.example.rabbitconsumer.repositories

import com.example.rabbitconsumer.entities.ActivationCode
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ActivationCodeRepository : JpaRepository<ActivationCode?, Long?> {
    fun findFirstByTariffIdAndIsActive(tariffId: Long, isActive: Boolean?): Optional<ActivationCode?>?
}