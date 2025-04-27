package edu.backend.oportuniabackend

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.users",
        "DELETE FROM public.company"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-users.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class LoadInitData (
    @Autowired
    val userRepository: UserRepository
)
{
    @Test
    fun testUserFindAll(){
        val userList: List<User> = userRepository.findAll()
        Assertions.assertTrue(userList.size == 5)
    }

    @Test
    fun testTaskFindById() {
        val user: User = userRepository.findById(1).get()
        Assertions.assertTrue(user.id?.toInt() == 1)
    }

    @Test
    fun testTaskFindByEmail() {
        val user: User = userRepository.findByEmail("user4@example.com").get()
        Assertions.assertTrue(user.id?.toInt() == 4)
    }
}