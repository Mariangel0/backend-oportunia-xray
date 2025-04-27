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
        "DELETE FROM public.student",
        "DELETE FROM public.advices",
        "DELETE FROM public.admin",
        "DELETE FROM public.companies",
        "DELETE FROM public.users",
        "DELETE FROM public.role",

    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-users.sql", "/import-companies.sql", "/import-admins.sql" ,"/import-student.sql", "/import-companies_reviews.sql", "/import-advices.sql"],
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


)
{
    // Users
    @Test
    fun testUserFindAll(){
        val userList: List<User> = userRepository.findAll()
        Assertions.assertTrue(userList.size == 5)
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
        val companyReviewList: List<CompanyReview> = companyReviewRepository.findByCompanyId(1)
        Assertions.assertTrue(companyReviewList.size == 2)
    }

    // Students

    @Test
    fun testStudentFindAll(){
        val studentList: List<Student> = studentRepository.findAll()
        Assertions.assertTrue(studentList.size == 3)
    }

    @Test
    fun testStudentFindById() {
        val student: Student = studentRepository.findById(1).get()
        Assertions.assertTrue(student.id.toInt() == 1)
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
        val admin: Admin = adminRepository.findById(1).get()
        Assertions.assertTrue(admin.id.toInt() == 1)
    }

    // Curriculums

    @Test
    fun testCurriculumFindAll(){
        val curriculumList: List<Curriculum> = curriculumRepository.findAll()
        Assertions.assertTrue(curriculumList.size == 5)
    }

    @Test
    fun testCurriculumFindById() {
        val curriculum: Curriculum = curriculumRepository.findById(1).get()
        Assertions.assertTrue(curriculum.id?.toInt() == 1)
    }


}