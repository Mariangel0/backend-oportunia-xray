package edu.backend.oportuniabackend

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(@Param("email") email: String): Optional<User>
}

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(@Param("name") name: String): Optional<Role>
}

@Repository
interface PrivilegeRepository : JpaRepository<Privilege, Long>

@Repository
interface StudentRepository : JpaRepository<Student, Long>

@Repository
interface AdminRepository : JpaRepository<Admin, Long>

@Repository
interface AbilityRepository : JpaRepository<Ability, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Ability>
}

@Repository
interface AdviceRepository : JpaRepository<Advice, Long>

@Repository
interface CompanyRepository : JpaRepository<Company, Long>

@Repository
interface CompanyReviewRepository : JpaRepository<CompanyReview, Long>{
    fun findByCompany(@Param("company") company: Long): List<CompanyReview>
}

@Repository
interface CurriculumRepository : JpaRepository<Curriculum, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Curriculum>
}

@Repository
interface EducationRepository : JpaRepository<Education, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Education>
}

@Repository
interface ExperienceRepository : JpaRepository<Experience, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Experience>
}

@Repository
interface InterviewRepository : JpaRepository<Interview, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Interview>
}

@Repository
interface IAAnalysisRepository : JpaRepository<IAAnalysis, Long>{
    fun findByInterviewId(interviewId: Long): IAAnalysis?


}

@Repository
interface NotificationRepository : JpaRepository<Notification, Long>{
    fun findByUserId(@Param("userId") userId: Long): List<Notification>
}

@Repository
interface StreakRepository : JpaRepository<Streak, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): Streak?
}

@Repository
interface StudentProgressRepository : JpaRepository<StudentProgress, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): StudentProgress?
}


