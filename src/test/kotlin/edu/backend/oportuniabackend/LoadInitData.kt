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
        "DELETE FROM public.companies_reviews",
        "DELETE FROM public.experiences",
        "DELETE FROM public.student_progress",
        "DELETE FROM public.ia_analysis",
        "DELETE FROM public.interview",
        "DELETE FROM public.curriculums",
        "DELETE FROM public.ability",
        "DELETE FROM public.education",
        "DELETE FROM public.notification",
        "DELETE FROM public.streaks",
        "DELETE FROM public.companies",
        "DELETE FROM public.student",
        "DELETE FROM public.admin",
        "DELETE FROM public.advices",
        "DELETE FROM public.admin",
        "DELETE FROM public.companies",
        "DELETE FROM public.user_role",
        "DELETE FROM public.users",
        "DELETE FROM public.role_privilege",
        "DELETE FROM public.role",
        "DELETE FROM public.privilege",
        "DELETE FROM public.role"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-users.sql", "/import-companies.sql", "/import-admins.sql" ,"/import-student.sql", "/import-student_progress.sql", "/import-experiences.sql" , "/import-interviews.sql","/import-companies_reviews.sql", "/import-advices.sql", "/import-curriculums.sql",
        "/import-abilities.sql","/import-ia_analysis.sql", "/import-education.sql", "/import-notifications.sql", "/import-streaks.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class LoadInitData (
    @Autowired
    val userRepository: UserRepository,

    @Autowired
    val companyRepository: CompanyRepository,

    @Autowired
    val studentRepository: StudentRepository,

    @Autowired
    val companyReviewRepository: CompanyReviewRepository,

    @Autowired
    val adviceRepository: AdviceRepository,

    @Autowired
    val adminRepository: AdminRepository,

    @Autowired
    val curriculumRepository: CurriculumRepository,

    @Autowired
    val abilityRepository: AbilityRepository,

    @Autowired
    val educationRepository: EducationRepository,

    @Autowired
    val notificationRepository: NotificationRepository,

    @Autowired
    val streakRepository: StreakRepository,

    @Autowired
    val interviewRepository: InterviewRepository,

    @Autowired
    val experienceRepository: ExperienceRepository,

    @Autowired
    val iaAnalysisRepository: IAAnalysisRepository,

    @Autowired
    val studentProgressRepository: StudentProgressRepository

    )
{
    // Users
    @Test
    fun testUserFindAll(){
        val userList: List<User> = userRepository.findAll()
        Assertions.assertTrue(userList.size == 6)
    }

    @Test
    fun testUserFindById() {
        val user: User = userRepository.findById(1).get()
        Assertions.assertTrue(user.id?.toInt() == 1)
    }

    @Test
    fun testUserFindByEmail() {
        val user: User = userRepository.findByEmail("user4@example.com").get()
        Assertions.assertTrue(user.id?.toInt() == 4)
    }

    // Companies
    @Test
    fun testCompanyFindAll(){
        val companyList: List<Company> = companyRepository.findAll()
        Assertions.assertTrue(companyList.size == 3)
    }

    @Test
    fun testCompanyFindById() {
        val company: Company = companyRepository.findById(1).get()
        Assertions.assertTrue(company.id?.toInt() == 1)
    }

    // Companies Reviews
    @Test
    fun testCompanyReviewFindAll(){
        val companyReviewList: List<CompanyReview> = companyReviewRepository.findAll()
        Assertions.assertTrue(companyReviewList.size == 5)
    }

    @Test
    fun testCompanyReviewFindById() {
        val companyReview: CompanyReview = companyReviewRepository.findById(1).get()
        Assertions.assertTrue(companyReview.id?.toInt() == 1)
    }

    @Test
    fun testCompanyReviewFindByCompanyId() {
        val companyReviewList: List<CompanyReview> = companyReviewRepository.findByCompany(1)
        Assertions.assertTrue(companyReviewList.size == 2)
    }

    // Students

    @Test
    fun testStudentFindAll(){
        val studentList: List<Student> = studentRepository.findAll()
        Assertions.assertTrue(studentList.size == 4)
    }

    @Test
    fun testStudentFindById() {
        val student: Student = studentRepository.findById(1).get()
        Assertions.assertTrue(student.id?.toInt() == 1)
    }

    // Advices

    @Test
    fun testAdviceFindAll(){
        val adviceList: List<Advice> = adviceRepository.findAll()
        Assertions.assertTrue(adviceList.size == 6)
    }

    @Test
    fun testAdviceFindById() {
        val advice: Advice = adviceRepository.findById(3).get()
        Assertions.assertTrue(advice.id.toInt() == 3)
    }

    // Admin
    @Test
    fun testAdminFindAll(){
        val adminList: List<Admin> = adminRepository.findAll()
        Assertions.assertTrue(adminList.size == 2)
    }
    @Test
    fun testAdminFindById() {
        val admin: Admin = adminRepository.findById(4).get()
        Assertions.assertTrue(admin.id.toInt() == 4)
    }

    // Curriculums

    @Test
    fun testCurriculumFindAll(){
        val curriculumList: List<Curriculum> = curriculumRepository.findAll()
        Assertions.assertTrue(curriculumList.size == 3)
    }

    @Test
    fun testCurriculumFindById() {
        val curriculum: Curriculum = curriculumRepository.findById(1).get()
        Assertions.assertTrue(curriculum.id?.toInt() == 1)
    }

    // Abilities
    @Test
    fun testAbilityFindAll(){
        val abilityList: List<Ability> = abilityRepository.findAll()
        Assertions.assertTrue(abilityList.size == 5)
    }

    @Test
    fun testAbilityFindById() {
        val ability: Ability = abilityRepository.findById(1).get()
        Assertions.assertTrue(ability.id?.toInt() == 1)
    }

    // Education
    @Test
    fun testEducationFindAll(){
        val educationList: List<Education> = educationRepository.findAll()
        Assertions.assertTrue(educationList.size == 5)
    }

    @Test
    fun testEducationFindById() {
        val education: Education = educationRepository.findById(1).get()
        Assertions.assertTrue(education.id?.toInt() == 1)
    }

    // Notifications
    @Test
    fun testNotificationFindAll(){
        val notificationList: List<Notification> = notificationRepository.findAll()
        Assertions.assertTrue(notificationList.size == 5)
    }

    @Test
    fun testNotificationFindById() {
        val notification: Notification = notificationRepository.findById(1).get()
        Assertions.assertTrue(notification.id?.toInt() == 1)
    }

    // Streaks
    @Test
    fun testStreakFindAll(){
        val streakList: List<Streak> = streakRepository.findAll()
        Assertions.assertTrue(streakList.size == 3)
    }

    @Test
    fun testStreakFindById() {
        val streak: Streak = streakRepository.findById(1).get()
        Assertions.assertTrue(streak.id?.toInt() == 1)
    }

    // interviews
    @Test
    fun testInterviewFindAll(){
        val interviewList: List<Interview> = interviewRepository.findAll()
        Assertions.assertTrue(interviewList.size == 5)
    }
    @Test
    fun testInterviewFindById() {
        val interview: Interview = interviewRepository.findById(1).get()
        Assertions.assertTrue(interview.id?.toInt() == 1)
    }
    // experiences
    @Test
    fun testExperienceFindAll(){
        val experienceList: List<Experience> = experienceRepository.findAll()
        Assertions.assertTrue(experienceList.size == 5)
    }
    @Test
    fun testExperienceFindById() {
        val experience: Experience = experienceRepository.findById(1).get()
        Assertions.assertTrue(experience.id?.toInt() == 1)
    }

    @Test
    fun testIAAnalysisFindAll(){
        val iaAnalysisList: List<IAAnalysis> = iaAnalysisRepository.findAll()
        Assertions.assertTrue(iaAnalysisList.size == 3)
    }
    @Test
    fun testIAAnalysisFindById() {
        val iaAnalysis: IAAnalysis = iaAnalysisRepository.findById(1).get()
        Assertions.assertTrue(iaAnalysis.id?.toInt() == 1)
    }

    @Test
    fun testStudentProgressFindAll(){
        val studentProgressList: List<StudentProgress> = studentProgressRepository.findAll()
        Assertions.assertTrue(studentProgressList.size == 3)
    }

    @Test
    fun testStudentProgressFindById() {
        val studentProgress: StudentProgress = studentProgressRepository.findById(1).get()
        Assertions.assertTrue(studentProgress.id?.toInt() == 1)
    }

}