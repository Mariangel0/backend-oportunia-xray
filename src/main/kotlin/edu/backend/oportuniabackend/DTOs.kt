package edu.backend.oportuniabackend

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class PriorityDetails(
    var id:Long? = null,
    var label: String? = null,
)

data class PrivilegeDetails (
    var id: Long? = null,
    var name: String? = null
)

data class PrivilegeDto(
    var id: Long? = null,
    var name: String? = null,
)

data class RoleDto(
    var id: Long? = null,
    var name: String? = null,
    var privileges: List<PrivilegeDto> = emptyList()
)

data class RoleDetails (
    var id: Long? = null,
    var name: String? = null,
    var privileges: List<PrivilegeDetails> = emptyList(),
)

// USERS

data class UserInput(
    var id: Long?=null,
    var firstName: String?=null,
    var lastName: String?=null,
    var email: String?=null,
    var password: String?=null,
    var enabled: Boolean?=null,
    var createDate: Date? =null,
    var roles: List<RoleDetails>?=null
)

data class UserLoginInput(
    var username: String = "",
    var password: String = "",
)

data class UserResult(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var enabled: Boolean?,
    var tokenExpired: Boolean?,
    var createDate: Date,
    var roles: List<RoleDto>,
)

data class UserReview(
    var id: Long,
    var firstName: String,
    var lastName: String,
)

data class StudentInput(
    var id: Long?=null,
    var description: String?=null,
    var premiun: Boolean?=null,
    var linkedinUrl: String?=null,
    var githubUrl: String?=null,
    var bornDate: String?=null,
    var location: String?=null,
    var user: UserInput?=null,
)

data class StudentResult(
    var id: Long,
    var description: String,
    var premiun: Boolean,
    var linkedinUrl: String,
    var githubUrl: String,
    var bornDate: String,
    var location: String
)

data class StudentReview(
    var id: Long,
    var user: UserReview?=null,
)

data class AdminInput(
    var id: Long?=null, // revisar si hay que pasarle el id
    var user: UserInput?=null,
)

data class AdminResult(
    var id: Long?=null,
    var user: UserInput?=null,
)

data class CompanyInput(
    var id: Long?=null,
    var name: String?=null,
    var description: String?=null,
    var type: String?=null,
    var location: String?=null,
    var employees: Int?=null,
    var websiteUrl: String?=null,
    var rating: Float?=null,
    var vision: String?=null,
    var mission: String?=null,
)

data class CompanyResult(
    var id: Long?=null,
    var name: String?=null,
    var description: String?=null,
    var rating: Float?=null,
)

data class CompanyResultLarge(
    var id: Long?=null,
    var name: String?=null,
    var description: String?=null,
    var rating: Float?=null,
    var type: String?=null,
    var location: String?=null,
    var employees: Int?=null,
    var websiteUrl: String?=null,
    var vision: String?=null,
    var mission: String?=null,
)

data class AbilityInput(
    var id: Long?=null,
    var name: String?=null,
    var student: StudentInput?=null,
)

data class AbilityResult(
    var id: Long?=null,
    var name: String?=null,
    var student: StudentResult? = null,
)

data class AdviceInput(
    var id: Long?=null,
    var question: String?=null,
    var answer: String?=null,
)

data class AdviceDetails(
    var id: Long,
    var question: String,
    var answer: String,
)

data class CompanyReviewInput(
    var id: Long?=null,
    var comment: String?=null,
    var rating: Float?=null,
    var company: Long,
    var student: StudentReview?=null,
    var createdAt: Date?=null,
)

data class CompanyReviewResult(
    var id: Long?=null,
    var comment: String?=null,
    var rating: Float?=null,
)

// INTERVIEW AND CV

data class IAAnalysisInput(
    var id: Long?=null,
    var recommendation: String?=null,
    var comment: String?=null,
    var score: Float?=null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var date: Date?=null,
    var interview: InterviewResult? = null
)

data class IAAnalysisDetails(
    var id: Long,
    var recommendation: String,
    var comment: String?=null,
    var score: Float,
    var date: Date,
    var interview: InterviewResult,
)

data class CurriculumInput(
    var id: Long?=null,
    var archiveUrl: String?=null,
    var s3Key: String? = null,
    var student: StudentInput?=null
    )

data class CurriculumResult(
    var id: Long? = null,
    var archiveUrl: String? = null,
    var s3Key: String? = null,
    var student: StudentResult? = null
)

data class CurriculumAnalysisInput(
    var id: Long? = null,
    var feedback: String? = null,
)


data class InterviewInput(
    var id: Long? = null,
    var date: Date? = null,
    var result: String? = null,
    var jobPosition: String? = null,
    var type: String? = null,
    var student: StudentInput? = null,
)

data class InterviewAnalysisInput(
    var id: Long? = null,
    var feedback: String? = null,
)

data class InterviewResult(
    var id: Long,
    var date: Date? = null,
    var result: String? = null,
    var jobPosition: String? = null,
    var type: String? = null,
    var student: StudentResult? = null,
)

// Education

data class EducationInput(
    var id: Long? = null,
    var student: StudentInput? = null,
    var name: String? = null,
    var institution: String? = null,
    var year: Int? = null,
)

data class EducationResult(
    var id: Long? = null,
    var student: StudentResult? = null,
    var name: String? = null,
    var institution: String? = null,
    var year: Int? = null,
)

// Experience

data class ExperienceInput(
    var id: Long? = null,
    var timeline: String? = null,
    var role: String? = null,
    var student: StudentInput? = null,
    var company: String? = null,
)

data class ExperienceResult(
    var id: Long? = null,
    var timeline: String? = null,
    var role: String? = null,
    var student: StudentResult? = null,
    var company: String? = null
)

// Notification

data class NotificationInput(
    var id: Long? = null,
    var type: String? = null,
    var read: Boolean? = null,
    var date: Date? = null,
    var message: String? = null,
    var user: UserInput? = null,
)

data class NotificationResult(
    var id: Long? = null,
    var message: String? = null,
    var type: String? = null,
    var date: Date? = null,
)

// Streak

data class StreakInput(
    var id: Long? = null,
    var days: Int? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var lastActivity: Date? = null,
    var bestStreak: Int? = null,
    var student: StudentInput? = null,
)

data class StreakResult(
    var id: Long? = null,
    var days: Int? = null,
    var lastActivity: Date? = null,
    var bestStreak: Int? = null
)

// StudentProgress

data class StudentProgressInput(
    var id: Long? = null,
    var progress: Int? = null,
    var student: StudentInput? = null,
    var totalInterviews: Int? = null,
    var averageScore: Float? = null,
    var uploadedCl: Int? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var lastActivity: Date? = null,
)

data class StudentProgressResult(
    var id: Long? = null,
    var progress: Int? = null,
    var totalInterviews: Int? = null,
    var averageScore: Float? = null,
    var uploadedCl: Int? = null,
    var lastActivity: Date? = null,
)

