package edu.backend.oportuniabackend

import jakarta.transaction.Transactional
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
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface AdviceRepository : JpaRepository<Advice, Long>

@Repository
interface CompanyRepository : JpaRepository<Company, Long>

@Repository
interface CompanyReviewRepository : JpaRepository<CompanyReview, Long>{
    fun findByCompany(@Param("company") company: Long): List<CompanyReview>
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface CurriculumRepository : JpaRepository<Curriculum, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Curriculum>
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface EducationRepository : JpaRepository<Education, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Education>
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface ExperienceRepository : JpaRepository<Experience, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Experience>
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface InterviewRepository : JpaRepository<Interview, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): List<Interview>
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface IAAnalysisRepository : JpaRepository<IAAnalysis, Long>{
    fun findByInterviewId(interviewId: Long): IAAnalysis?
    fun findByCurriculumId(curriculumId: Long): IAAnalysis?
}

@Repository
interface NotificationRepository : JpaRepository<Notification, Long>{
    fun findByUserId(@Param("userId") userId: Long): List<Notification>
}

@Repository
interface StreakRepository : JpaRepository<Streak, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): Streak?
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}

@Repository
interface StudentProgressRepository : JpaRepository<StudentProgress, Long>{
    fun findByStudentId(@Param("studentId") studentId: Long): StudentProgress?
    @Transactional
    fun deleteByStudentId(@Param("studentId") studentId: Long)
}


