package com.example.rabbitconsumer.services

import com.example.rabbitconsumer.repositories.ActivationCodeRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
@Slf4j
class ActivationCodeRabbitReceiver(
    private val activationCodeRepository: ActivationCodeRepository
) {
    private val log = LoggerFactory.getLogger(ActivationCodeRabbitReceiver::class.java)

    @Bean
    fun receiver(): Function<Map<Long?, Long>, Map<Long?, Long?>> {
        return Function<Map<Long?, Long>, Map<Long?, Long?>> { subscriptionIdByTariffId: Map<Long?, Long> ->
            log.info("Process of getting codeId by subscriptionIdByTariffId: {}", subscriptionIdByTariffId)
            receivedMessage(subscriptionIdByTariffId)
        }
    }

    fun receivedMessage(subscriptionIdByTariffId: Map<Long?, Long>): Map<Long?, Long?> {
        log.info("Got from rabbit subscriptionIdByTariffId = {}", subscriptionIdByTariffId)
        val tariffId: Long
        var subscriptionId: Long? = 0L
        var codeId: Long = 0
        try {
            tariffId = subscriptionIdByTariffId.values.stream().findAny().orElseThrow { Exception() }
            subscriptionId = subscriptionIdByTariffId.keys.stream().findAny().orElseThrow { Exception() }
            val code = activationCodeRepository.findFirstByTariffIdAndIsActive(tariffId, true)?.get()
                ?: throw Exception()
            codeId = code.activation_code_id
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        log.info(
            "Successfully found active codeId = {} and sent to the rabbit with subscriptionId = {}",
            codeId, subscriptionId
        )

        return java.util.Map.of(subscriptionId, codeId)
    }
}