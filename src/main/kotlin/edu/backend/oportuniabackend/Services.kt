package edu.backend.oportuniabackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
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
}

interface AdminService {
    fun findAll(): List<AdminInput>?
    fun findById(id: Long): AdminInput?
    fun createAdmin(adminInput: AdminInput): AdminResult?
    fun updateAdmin(id: Long, adminInput: AdminInput): AdminResult?
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

    override fun createAdmin(adminInput: AdminInput): AdminResult? {
        val admin: Admin = adminMapper.adminInputToAdmin(adminInput)
        return adminMapper.adminToAdminResult(
            adminRepository.save(admin)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun updateAdmin(id: Long, adminInput: AdminInput): AdminResult? {
        val admin: Optional<Admin> = adminRepository.findById(adminInput.id!!)
        if (admin.isEmpty) {
            throw NoSuchElementException(
                String.format("The admin with the id: %s not found!", adminInput.id)
            )
        }
        val adminUpdated: Admin = admin.get()
        adminMapper.updateAdminFromInput(adminInput, adminUpdated)
        return adminMapper.adminToAdminResult(adminRepository.save(adminUpdated))
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
    @Autowired
    private var companyRepository: CompanyRepository,
    @Autowired
    private var studentRepository: StudentRepository

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
        val entity = companyReviewMapper.companyReviewInputToCompanyReview(companyReviewInput)
        companyReviewInput.company?.id?.let {
            entity.company = companyRepository.findById(it)
                .orElseThrow { NoSuchElementException("Company with id $it not found") }
        }
        companyReviewInput.student?.id?.let {
            entity.student = studentRepository.findById(it)
                .orElseThrow { NoSuchElementException("Student with id $it not found") }
        }
        return companyReviewMapper.companyReviewToCompanyReviewResult(companyReviewRepository.save(entity))
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

interface AbilityService{
    fun findAll(): List<AbilityResult>?
    fun findById(id: Long): AbilityResult?
    fun findByStudentId(studentId: Long): List<AbilityResult>?
    fun create(abilityInput: AbilityInput): AbilityResult?
    fun deleteById(id: Long)
}

@Service
class AbstractAbilityService (
    @Autowired
    val abilityRepository: AbilityRepository,
    @Autowired
    private val abilityMapper: AbilityMapper,
    @Autowired
    private val studentRepository: StudentRepository
): AbilityService {
    override fun findAll(): List<AbilityResult>? {
        return abilityMapper.abilityListToAbilityResultList(
            abilityRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): AbilityResult? {
        val ability: Optional<Ability> = abilityRepository.findById(id)
        if(ability.isEmpty){
            throw NoSuchElementException(String.format("The Ability with the id: %s not found!", id))
        }
        return abilityMapper.abilityToAbilityResult(
            ability.get()
        )
    }

    override fun findByStudentId(studentId: Long): List<AbilityResult>? {
        return abilityMapper.abilityListToAbilityResultList(
            abilityRepository.findByStudentId(studentId)
        )
    }

    override fun create(abilityInput: AbilityInput): AbilityResult? {
        val ability: Ability = abilityMapper.abilityInputToAbility(abilityInput)

        val studentId = abilityInput.student?.id
            ?: throw IllegalArgumentException("Student ID is required")

        val student = studentRepository.findById(studentId)
            .orElseThrow { NoSuchElementException("Student with id $studentId not found") }

        ability.student = student

        return abilityMapper.abilityToAbilityResult(
            abilityRepository.save(ability)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!abilityRepository.findById(id).isEmpty) {
            abilityRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Ability with the id: %s not found!", id))
        }
    }
}

interface CurriculumService{
    fun findAll(): List<CurriculumResult>?
    fun findById(id: Long): CurriculumResult?
    fun create(curriculumInput: CurriculumInput): CurriculumResult?
    fun update(curriculumInput: CurriculumInput): CurriculumResult?
    fun deleteById(id: Long)
}

@Service
class AbstractCurriculumService(
    @Autowired
    val curriculumRepository: CurriculumRepository,
    @Autowired
    private val curriculumMapper: CurriculumMapper,
    @Autowired
    private val studentRepository: StudentRepository
) : CurriculumService {

    override fun findAll(): List<CurriculumResult>? {
        return curriculumMapper.curriculumListToCurriculumResultList(
            curriculumRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): CurriculumResult? {
        val curriculum = curriculumRepository.findById(id)
            .orElseThrow { NoSuchElementException("The Curriculum with the id: $id not found!") }

        return curriculumMapper.curriculumToCurriculumResult(curriculum)
    }

    override fun create(curriculumInput: CurriculumInput): CurriculumResult? {
        val studentId = curriculumInput.student?.id
            ?: throw IllegalArgumentException("Student ID is required")

        val student = studentRepository.findById(studentId)
            .orElseThrow { NoSuchElementException("Student with ID $studentId not found") }

        val curriculum = curriculumMapper.curriculumInputToCurriculum(curriculumInput)
        curriculum.student = student

        return curriculumMapper.curriculumToCurriculumResult(
            curriculumRepository.save(curriculum)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun update(curriculumInput: CurriculumInput): CurriculumResult? {
        val existingCurriculum = curriculumRepository.findById(curriculumInput.id!!)
            .orElseThrow { NoSuchElementException("The Curriculum with the id: ${curriculumInput.id} not found!") }
        curriculumMapper.curriculumInputToCurriculum(curriculumInput, existingCurriculum)
        curriculumInput.student?.id?.let {
            val student = studentRepository.findById(it)
                .orElseThrow { NoSuchElementException("Student with id $it not found") }
            existingCurriculum.student = student
        }

        return curriculumMapper.curriculumToCurriculumResult(
            curriculumRepository.save(existingCurriculum)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!curriculumRepository.existsById(id)) {
            throw NoSuchElementException("The Curriculum with the id: $id not found!")
        }
        curriculumRepository.deleteById(id)
    }
}


interface EducationService{
    fun findAll(): List<EducationResult>?
    fun findById(id: Long): EducationResult?
    fun create(educationInput: EducationInput): EducationResult?
    fun update(educationInput: EducationInput): EducationResult?
    fun deleteById(id: Long)
}

@Service
class AbstractEducationService (
    @Autowired
    val educationRepository: EducationRepository,
    @Autowired
    private val educationMapper: EducationMapper,
    @Autowired
    private val studentRepository: StudentRepository
): EducationService {
    override fun findAll(): List<EducationResult>? {
        return educationMapper.educationListToEducationResultList(
            educationRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): EducationResult? {
        val education: Optional<Education> = educationRepository.findById(id)
        if (education.isEmpty) {
            throw NoSuchElementException(
                String.format(
                    "The education with the id: %s not found!", id))
        }
        return educationMapper.educationToEducationResult(
            education.get(),
        )
    }

    override fun create(educationInput: EducationInput): EducationResult? {
        val education: Education = educationMapper.educationInputToEducation(educationInput)

        val studentId = educationInput.student?.id
            ?: throw IllegalArgumentException("Student ID is required")

        val student = studentRepository.findById(studentId)
            .orElseThrow { NoSuchElementException("Student with id $studentId not found") }

        education.student = student

        return educationMapper.educationToEducationResult(
            educationRepository.save(education)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun update(educationInput: EducationInput): EducationResult? {
        val education = educationRepository.findById(educationInput.id!!)
            .orElseThrow { NoSuchElementException("The education with the id: ${educationInput.id} not found!") }

        educationMapper.educationInputToEducation(educationInput, education)

        educationInput.student?.id?.let { studentId ->
            val student = studentRepository.findById(studentId)
                .orElseThrow { NoSuchElementException("Student with id $studentId not found") }
            education.student = student
        }

        return educationMapper.educationToEducationResult(
            educationRepository.save(education)
        )
    }


    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if(!educationRepository.findById(id).isEmpty){
            educationRepository.deleteById(id)
        }else{
            throw NoSuchElementException(String.format("The education with the id: %s not found!", id))
        }
    }
}

interface ExperienceService{
    fun findAll(): List<ExperienceResult>?
    fun findById(id: Long): ExperienceResult?
    fun findByStudentId(studentId: Long): List<ExperienceResult>?
    fun create(experienceInput: ExperienceInput): ExperienceResult?
    fun update(experienceInput: ExperienceInput): ExperienceResult?
    fun deleteById(id: Long)
}

@Service
class AbstractExperienceService (
    @Autowired
    val experienceRepository: ExperienceRepository,
    @Autowired
    private val experienceMapper: ExperienceMapper,
    private val studentRepository: StudentRepository,
    private val companyRepository: CompanyRepository
): ExperienceService {
    override fun findAll(): List<ExperienceResult>? {
        return experienceMapper.experienceListToExperienceResultList(
            experienceRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): ExperienceResult? {
        val experience: Optional<Experience> = experienceRepository.findById(id)
        if(experience.isEmpty){
            throw NoSuchElementException(String.format("The Experience with the id: %s not found!", id))
        }
        return experienceMapper.experienceToExperienceResult(
            experience.get()
        )
    }

    override fun findByStudentId(studentId: Long): List<ExperienceResult>? {
        return experienceMapper.experienceListToExperienceResultList(
            experienceRepository.findByStudentId(studentId)
        )
    }

    override fun create(experienceInput: ExperienceInput): ExperienceResult? {
        val entity = experienceMapper.experienceInputToExperience(experienceInput)
        experienceInput.company?.id?.let {
            entity.company = companyRepository.findById(it)
                .orElseThrow { NoSuchElementException("Company with id $it not found") }
        }
        experienceInput.student?.id?.let {
            entity.student = studentRepository.findById(it)
                .orElseThrow { NoSuchElementException("Student with id $it not found") }
        }
        return experienceMapper.experienceToExperienceResult(experienceRepository.save(entity))
    }


    @Throws(NoSuchElementException::class)
    override fun update(experienceInput: ExperienceInput): ExperienceResult? {
        val experience: Optional<Experience> = experienceRepository.findById(experienceInput.id!!)
        if(experience.isEmpty){
            throw NoSuchElementException(String.format("The Experience with the id: %s not found!", experienceInput.id))
        }
        val experienceUpdated: Experience = experience.get()
        experienceMapper.experienceInputToExperience(experienceInput, experienceUpdated)
        return experienceMapper.experienceToExperienceResult(experienceRepository.save(experienceUpdated)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!experienceRepository.findById(id).isEmpty) {
            experienceRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Experience with the id: %s not found!", id))
        }
    }
}

interface IAAnalysisService{
    fun findAll(): List<IAAnalysisDetails>?
    fun findById(id: Long): IAAnalysisDetails?
    fun create(iaAnalysisInput: IAAnalysisInput): IAAnalysisDetails?
    fun deleteById(id: Long)
}

@Service
class AbstractIAAnalysisService (
    @Autowired
    val iaAnalysisRepository: IAAnalysisRepository,
    @Autowired
    private val iaAnalysisMapper: IAAnalysisMapper,
    @Autowired
    private val interviewRepository: InterviewRepository,
    @Autowired
    private val curriculumRepository: CurriculumRepository
): IAAnalysisService {
    override fun findAll(): List<IAAnalysisDetails>? {
        return iaAnalysisMapper.iaAnalysisListToIAAnalysisDetailsList(
            iaAnalysisRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): IAAnalysisDetails? {
        val iaAnalysis: Optional<IAAnalysis> = iaAnalysisRepository.findById(id)
        if (iaAnalysis.isEmpty) {
            throw NoSuchElementException(
                String.format(
                    "The IAAnalysis with the id: %s not found!", id))
        }
        return iaAnalysisMapper.iaAnalysisToIAAnalysisDetails(
            iaAnalysis.get(),
        )
    }

    override fun create(iaAnalysisInput: IAAnalysisInput): IAAnalysisDetails? {
        val iaAnalysis = iaAnalysisMapper.iaAnalysisInputToIAAnalysis(iaAnalysisInput)

        val interview = interviewRepository.findById(iaAnalysisInput.interview?.id!!)
            .orElseThrow { NoSuchElementException("Interview not found") }

        val curriculum = curriculumRepository.findById(iaAnalysisInput.curriculum?.id!!)
            .orElseThrow { NoSuchElementException("Curriculum not found") }

        iaAnalysis.interview = interview
        iaAnalysis.curriculum = curriculum

        return iaAnalysisMapper.iaAnalysisToIAAnalysisDetails(iaAnalysisRepository.save(iaAnalysis))
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if(!iaAnalysisRepository.findById(id).isEmpty){
            iaAnalysisRepository.deleteById(id)
        }else{
            throw NoSuchElementException(String.format("The IAAnalysis with the id: %s not found!", id))
        }
    }
}

interface InterviewService{
    fun findAll(): List<InterviewResult>?
    fun findById(id: Long): InterviewResult?
    fun create(interviewInput: InterviewInput): InterviewResult?
    fun deleteById(id: Long)
}

@Service
class AbstractInterviewService (
    @Autowired
    val interviewRepository: InterviewRepository,
    @Autowired
    private val interviewMapper: InterviewMapper,
    @Autowired
    private val studentRepository: StudentRepository
): InterviewService {
    override fun findAll(): List<InterviewResult>? {
        return interviewMapper.interviewListToInterviewResultList(
            interviewRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): InterviewResult? {
        val interview: Optional<Interview> = interviewRepository.findById(id)
        if(interview.isEmpty){
            throw NoSuchElementException(String.format("The Interview with the id: %s not found!", id))
        }
        return interviewMapper.interviewToInterviewResult(
            interview.get()
        )
    }

    override fun create(interviewInput: InterviewInput): InterviewResult? {
        val studentId = interviewInput.student?.id
            ?: throw IllegalArgumentException("Student ID is required for interview creation")

        val student = studentRepository.findById(studentId)
            .orElseThrow { NoSuchElementException("Student with id $studentId not found") }

        val interview = interviewMapper.interviewInputToInterview(interviewInput)
        interview.student = student

        return interviewMapper.interviewToInterviewResult(
            interviewRepository.save(interview)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!interviewRepository.findById(id).isEmpty) {
            interviewRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Interview with the id: %s not found!", id))
        }
    }
}

interface NotificationService{
    fun findAll(): List<NotificationResult>?
    fun findById(id: Long): NotificationResult?
    fun create(notificationInput: NotificationInput): NotificationResult?
    fun deleteById(id: Long)
}

@Service
class AbstractNotificationService (
    @Autowired
    val notificationRepository: NotificationRepository,
    @Autowired
    private val notificationMapper: NotificationMapper,
): NotificationService {
    override fun findAll(): List<NotificationResult>? {
        return notificationMapper.notificationListToNotificationResultList(
            notificationRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): NotificationResult? {
        val notification: Optional<Notification> = notificationRepository.findById(id)
        if(notification.isEmpty){
            throw NoSuchElementException(String.format("The Notification with the id: %s not found!", id))
        }
        return notificationMapper.notificationToNotificationResult(
            notification.get()
        )
    }

    override fun create(notificationInput: NotificationInput): NotificationResult? {
        val notification: Notification = notificationMapper.notificationInputToNotification(notificationInput)
        return notificationMapper.notificationToNotificationResult(
            notificationRepository.save(notification)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!notificationRepository.findById(id).isEmpty) {
            notificationRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Notification with the id: %s not found!", id))
        }
    }
}

interface StreakService{
    fun findAll(): List<StreakResult>?
    fun findById(id: Long): StreakResult?
    fun create(streakInput: StreakInput): StreakResult?
    fun update(streakInput: StreakInput): StreakResult?
    fun deleteById(id: Long)
}

@Service
class AbstractStreakService (
    @Autowired
    val streakRepository: StreakRepository,
    @Autowired
    private val streakMapper: StreakMapper,
): StreakService {
    override fun findAll(): List<StreakResult>? {
        return streakMapper.streakListToStreakResultList(
            streakRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): StreakResult? {
        val streak: Optional<Streak> = streakRepository.findById(id)
        if (streak.isEmpty) {
            throw NoSuchElementException(
                String.format(
                    "The Streak with the id: %s not found!", id))
        }
        return streakMapper.streakToStreakResult(
            streak.get(),
        )
    }

    override fun create(streakInput: StreakInput): StreakResult? {
        val streak: Streak = streakMapper.streakInputToStreak(streakInput)
        return streakMapper.streakToStreakResult(
            streakRepository.save(streak)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun update(streakInput: StreakInput): StreakResult? {
        val streak: Optional<Streak> = streakRepository.findById(streakInput.id!!)
        if(streak.isEmpty){
            throw NoSuchElementException(String.format("The Streak with the id: %s not found!", streakInput.id))
        }
        val streakUpdated: Streak = streak.get()
        streakMapper.streakInputToStreak(streakInput, streakUpdated)
        return streakMapper.streakToStreakResult(streakRepository.save(streakUpdated)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if(!streakRepository.findById(id).isEmpty){
            streakRepository.deleteById(id)
        }else{
            throw NoSuchElementException(String.format("The Streak with the id: %s not found!", id))
        }
    }
}

interface StudentProgressService{
    fun findAll(): List<StudentProgressResult>?
    fun findById(id: Long): StudentProgressResult?
    fun create(studentProgressInput: StudentProgressInput): StudentProgressResult?
    fun deleteById(id: Long)
}

@Service
class AbstractStudentProgressService (
    @Autowired
    val studentProgressRepository: StudentProgressRepository,
    @Autowired
    private val studentProgressMapper: StudentProgressMapper,
): StudentProgressService {
    override fun findAll(): List<StudentProgressResult>? {
        return studentProgressMapper.studentProgressListToStudentProgressResultList(
            studentProgressRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): StudentProgressResult? {
        val studentProgress: Optional<StudentProgress> = studentProgressRepository.findById(id)
        if(studentProgress.isEmpty){
            throw NoSuchElementException(String.format("The StudentProgress with the id: %s not found!", id))
        }
        return studentProgressMapper.studentProgressToStudentProgressResult(
            studentProgress.get()
        )
    }

    override fun create(studentProgressInput: StudentProgressInput): StudentProgressResult? {
        val studentProgress: StudentProgress = studentProgressMapper.studentProgressInputToStudentProgress(studentProgressInput)
        return studentProgressMapper.studentProgressToStudentProgressResult(
            studentProgressRepository.save(studentProgress)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!studentProgressRepository.findById(id).isEmpty) {
            studentProgressRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The StudentProgress with the id: %s not found!", id))
        }
    }
}



