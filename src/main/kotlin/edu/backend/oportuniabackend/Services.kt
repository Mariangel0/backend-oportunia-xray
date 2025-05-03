package edu.backend.oportuniabackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserService {
    fun findAll(): List<UserResult>?

    fun findById(id: Long): UserResult?
}

@Service
class AbstractUserService (
    @Autowired
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
): UserService {
    override fun findAll(): List<UserResult>? {
        return userRepository.findAll().map { userMapper.userToUserResult(it) }
    }
    override fun findById(id: Long): UserResult? {
        return userRepository.findById(id).map { userMapper.userToUserResult(it) }.orElse(null)
    }
}
