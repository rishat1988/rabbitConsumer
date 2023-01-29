package com.example.rabbitconsumer.entities

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "activation_code")
data class ActivationCode(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val activation_code_id: Long = 0,
    val name: String? = null,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm['Z']")
    @CreationTimestamp
    @Column(name = "create_datetime")
    val createDateTime: LocalDateTime? = null,

    @Column(name = "tariff_id")
    val tariffId: Long = 0,

    @Column(name = "is_active")
    val isActive: Boolean? = null
)