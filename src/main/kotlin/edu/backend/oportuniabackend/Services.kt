package edu.backend.oportuniabackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

interface UserService {
    fun findAll(): List<UserResult>?

    fun findById(id: Long): UserResult?

    fun findByEmail(email: String): UserResult?

    fun createUser(user: UserInput): UserResult?

    fun updateUser(id: Long, user: UserInput): UserResult?

    fun deleteUser(id: Long)
}

@Service
class AbstractUserService (
    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val userMapper: UserMapper,
): UserService {

    override fun findAll(): List<UserResult>? {
        return userMapper.userListToUserListResult(userRepository.findAll())
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): UserResult? {
        val user: Optional<User> = userRepository.findById(id)
        if (user.isEmpty) {
            throw NoSuchElementException(String.format("The user with the id: %s not found!", id))
        }
        return userMapper.userToUserResult(
            user.get(),
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findByEmail(email: String): UserResult? {
        val user: Optional<User> = userRepository.findByEmail(email)
        if (user.isEmpty) {
            throw NoSuchElementException(String.format("The user with the email: %s not found!", email))
        }
        return userMapper.userToUserResult(
            user.get(),
        )
    }

    override fun createUser(user: UserInput): UserResult? {
        return userMapper.userToUserResult(
            userRepository.save(userMapper.userInputToUser(user))
        )
    }

    @Throws(NoSuchElementException::class)
    override fun updateUser(id: Long, userInput: UserInput): UserResult? {
        val user: Optional<User> = userRepository.findById(userInput.id!!)
        if (user.isEmpty) {
            throw NoSuchElementException(String.format("The USER with the id: %s not found!", userInput.id))
        }
        val userUpdated: User = user.get()
        userMapper.updateUserFromInput(userInput, userUpdated)
        return userMapper.userToUserResult(userRepository.save(userUpdated))
    }

    @Throws(NoSuchElementException::class)
    override fun deleteUser(id: Long) {
        if (!userRepository.findById(id).isEmpty) {
            userRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The user with the id: %s not found!", id))
        }
    }

}

interface StudentService {

    fun findAll(): List<StudentResult>?

    fun findById(id: Long): StudentResult?

    fun createStudent(studentInput: StudentInput): StudentResult?

    fun updateStudent(id: Long, studentInput: StudentInput): StudentResult?

    fun deleteStudent(id: Long)
}

@Service
class AbstractStudentService (
    @Autowired
    private val studentRepository: StudentRepository,

    @Autowired
    private val studentMapper: StudentMapper,

    ): StudentService {

    override fun findAll(): List<StudentResult>? {
        return studentMapper.studentListToStudentListResult(
            (studentRepository.findAll())
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): StudentResult? {
        val student: Optional<Student> = studentRepository.findById(id)
        if (student.isEmpty) {
            throw NoSuchElementException(
                String.format(
                    "The student with the id: %s not found!",
                    id
                )
            )
        }
        return studentMapper.studentToStudentResult(
            student.get(),
        )
    }


    override fun createStudent(studentInput: StudentInput): StudentResult? {
        return studentMapper.studentToStudentResult(
            studentRepository.save(studentMapper.studentInputToStudent(studentInput))
        )
    }

    @Throws(NoSuchElementException::class)
    override fun updateStudent(id: Long, studentInput: StudentInput): StudentResult? {
        val student: Optional<Student> = studentRepository.findById(studentInput.id!!)
        if (student.isEmpty) {
            throw NoSuchElementException(
                String.format("The student with the id: %s not found!", studentInput.id)
            )
        }
        val studentUpdated: Student = student.get()
        studentMapper.updateStudentFromInput(studentInput, studentUpdated)
        return studentMapper.studentToStudentResult(
            studentRepository.save(studentUpdated)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteStudent(id: Long) {
        if (!studentRepository.findById(id).isEmpty) {
            studentRepository.deleteById(id)
        } else {
            throw NoSuchElementException(
                String.format("The student with the id: %s not found!", id)
            )
        }
    }


interface AdminService {
    fun findAll(): List<AdminInput>?
    fun findById(id: Long): AdminInput?
    fun createAdmin(adminInput: AdminInput): AdminInput?
    fun updateAdmin(id: Long, adminInput: AdminInput): AdminInput?
    fun deleteAdmin(id: Long)
}

@Service
class AbstractAdminService (
    @Autowired
    private val adminRepository: AdminRepository,
    @Autowired
    private val adminMapper: AdminMapper,
): AdminService {

    override fun findAll(): List<AdminInput>? {
        return adminMapper.adminListToAdminListResult(
            (adminRepository.findAll())
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): AdminInput? {
        val admin: Optional<Admin> = adminRepository.findById(id)
        if (admin.isEmpty) {
            throw NoSuchElementException(
                String.format("The admin with the id: %s not found!", id))}
        return adminMapper.adminToAdminInput(admin.get(),)
    }

    override fun createAdmin(adminInput: AdminInput): AdminInput? {
        return adminMapper.adminToAdminInput(
            adminRepository.save(adminMapper.adminInputToAdmin(adminInput))
        )
    }

    @Throws(NoSuchElementException::class)
    override fun updateAdmin(id: Long, adminInput: AdminInput): AdminInput? {
        val admin: Optional<Admin> = adminRepository.findById(adminInput.id!!)
        if (admin.isEmpty) {
            throw NoSuchElementException(
                String.format("The admin with the id: %s not found!", adminInput.id)
            )
        }
        val adminUpdated: Admin = admin.get()
        adminMapper.updateAdminFromInput(adminInput, adminUpdated)
        return adminMapper.adminToAdminInput(
            adminRepository.save(adminUpdated)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteAdmin(id: Long) {
        if (!adminRepository.findById(id).isEmpty) {
            adminRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The admin with the id: %s not found!", id))
        }
    }
}

interface CompanyService {
    fun findAll(): List<CompanyResult>?
    fun findAllInfo(): List<CompanyResultLarge>?
    fun findById(id: Long): CompanyResult?
    fun createCompany(companyInput: CompanyInput): CompanyResult?
    fun updateCompany(id: Long, companyInput: CompanyInput): CompanyResult?
    fun deleteCompany(id: Long)
}

@Service
class AbstractCompanyService (
    @Autowired
    private val companyRepository: CompanyRepository,
    @Autowired
    private val companyMapper: CompanyMapper,

    ): CompanyService {

        override fun findAll(): List<CompanyResult>? {
            return companyMapper.companyListToCompanyListResult(
                (companyRepository.findAll())
            )

        }

        override fun findAllInfo(): List<CompanyResultLarge>? {
            return companyMapper.companyListToCompanyListResultLarge(
                (companyRepository.findAll())
            )
        }

        @Throws(NoSuchElementException::class)
        override fun findById(id: Long): CompanyResult? {
            val company: Optional<Company> = companyRepository.findById(id)
            if (company.isEmpty) {
                throw NoSuchElementException(String.format("The company with the id: %s not found!", id))
            }
            return companyMapper.companyToCompanyResult(company.get())

        }

        override fun createCompany(companyInput: CompanyInput): CompanyResult? {
            return companyMapper.companyToCompanyResult(
                companyRepository.save(companyMapper.companyInputToCompany(companyInput))
            )
        }

        @Throws(NoSuchElementException::class)
        override fun updateCompany(id: Long, companyInput: CompanyInput): CompanyResult? {
            val company: Optional<Company> = companyRepository.findById(companyInput.id!!)
            if (company.isEmpty) {
                throw NoSuchElementException(String.format("The company with the id: %s not found!", companyInput.id))
            }
            val companyUpdated: Company = company.get()
            companyMapper.updateCompanyFromInput(companyInput, companyUpdated)
            return companyMapper.companyToCompanyResult(companyRepository.save(companyUpdated))
        }

        @Throws(NoSuchElementException::class)
        override fun deleteCompany(id: Long) {
            if (!companyRepository.findById(id).isEmpty) {
                companyRepository.deleteById(id)
            } else {
                throw NoSuchElementException(
                    String.format("The company with the id: %s not found!", id)
                )
            }

        }

    }

interface CompanyReviewService {
    fun findAll(): List<CompanyReviewResult>?
    fun findById(id: Long): CompanyReviewResult?
    fun createCompanyReview(companyReviewInput: CompanyReviewInput): CompanyReviewResult?
    fun updateCompanyReview(id: Long, companyReviewInput: CompanyReviewInput): CompanyReviewResult?
    fun deleteCompanyReview(id: Long)
}

@Service
class AbstractCompanyReviewService (
    @Autowired
    private val companyReviewRepository: CompanyReviewRepository,
    @Autowired
    private val companyReviewMapper: CompanyReviewMapper,
    ): CompanyReviewService {

    override fun findAll(): List<CompanyReviewResult>? {
        return companyReviewMapper.companyReviewListToCompanyReviewResultList(
            (companyReviewRepository.findAll())
        )
    }
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): CompanyReviewResult? {
        val companyReview: Optional<CompanyReview> = companyReviewRepository.findById(id)
        if (companyReview.isEmpty) {
            throw NoSuchElementException(String.format("The company review with the id: %s not found!", id))
        }
        return companyReviewMapper.companyReviewToCompanyReviewResult(companyReview.get())
    }

    override fun createCompanyReview(companyReviewInput: CompanyReviewInput): CompanyReviewResult? {
        return companyReviewMapper.companyReviewToCompanyReviewResult(
            companyReviewRepository.save(
                companyReviewMapper.companyReviewInputToCompanyReview(
                    companyReviewInput
                )
            )
        )
    }
    @Throws(NoSuchElementException::class)
    override fun updateCompanyReview(id: Long, companyReviewInput: CompanyReviewInput): CompanyReviewResult? {
        val companyReview: Optional<CompanyReview> = companyReviewRepository.findById(companyReviewInput.id!!)
        if (companyReview.isEmpty) {
            throw NoSuchElementException(String.format("The company review with the id: %s not found!", companyReviewInput.id))
        }
        val companyReviewUpdated: CompanyReview = companyReview.get()
        companyReviewMapper.updateCompanyReviewFromInput(companyReviewInput, companyReviewUpdated)
        return companyReviewMapper.companyReviewToCompanyReviewResult(companyReviewRepository.save(companyReviewUpdated))
    }

    @Throws(NoSuchElementException::class)
    override fun deleteCompanyReview(id: Long) {
        if (!companyReviewRepository.findById(id).isEmpty) {
            companyReviewRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The company review with the id: %s not found!", id))
        }
    }
}






}


