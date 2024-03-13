package ru.astondevs.astlink.aop.logging.spring.boot.starter.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import ru.astondevs.astlink.aop.logging.spring.boot.starter.configuration.LoggingAutoConfiguration.Companion.logger

@Aspect
class LoggingAspect() {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    fun loggingServiceMethod() {
    }

    @Pointcut("@within(ru.astondevs.astlink.aop.logging.spring.boot.starter.annotation.Loggable)")
    fun customLoggingServiceMethod() {
    }

    @Before("loggingServiceMethod()|| customLoggingServiceMethod()")
    fun logBeforeMethodCall(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        logger.info { "Вызван метод: $methodName из класса: $className" }
    }

    @AfterReturning("loggingServiceMethod()|| customLoggingServiceMethod()")
    fun logAfterReturningMethodCall(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        logger.info { "Метод $methodName из класса: $className был завершен" }
    }

    @AfterThrowing(
        pointcut = "loggingServiceMethod()|| customLoggingServiceMethod()", throwing = "exception"
    )
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Exception) {
        val methodName = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        logger.error {
            "В методе $methodName класса $className выброшено исключение: [${exception::class}] ${exception.message}"
        }
    }
}