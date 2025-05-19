package edu.backend.oportuniabackend

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    var password: String? = null,

    @Column(unique = true)
    var email: String? = null,

    @Column(name = "create_date")
    var createDate: Date? = null,

    var enabled: Boolean? = null,

    @Column(name = "token_expired")
    var tokenExpired: Boolean? = null,

    @OneToMany(mappedBy = "user")
    var notificationList: List<Notification>? = null,


    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Set<Role>? = null,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (enabled != other.enabled) return false
        if (tokenExpired != other.tokenExpired) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (password != other.password) return false
        if (email != other.email) return false
        if (createDate != other.createDate) return false
        if (roles != other.roles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + enabled.hashCode()
        result = 31 * result + tokenExpired.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + createDate.hashCode()
        result = 31 * result + roles.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', password='$password', email='$email', createDate=$createDate, enabled=$enabled, tokenExpired=$tokenExpired, roles=$roles)"
    }


}

@Entity
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String,
    // Entity Relationship
    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    var privilegeList: Set<Privilege>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (id != other.id) return false
        if (name != other.name) return false
        if (privilegeList != other.privilegeList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + privilegeList.hashCode()
        return result
    }

    override fun toString(): String {
        return "Role(id=$id, name='$name', privilegeList=$privilegeList)"
    }
}

@Entity
@Table(name = "privilege")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long? = null,
    var name: String,
    // Entity Relationship
    @ManyToMany(fetch = FetchType.LAZY)
    var userList: Set<User>,
    @ManyToMany(fetch = FetchType.LAZY)
    var roleList: Set<Role>,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Privilege

        if (id != other.id) return false
        if (name != other.name) return false
        if (userList != other.userList) return false
        if (roleList != other.roleList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + userList.hashCode()
        result = 31 * result + roleList.hashCode()
        return result
    }

    override fun toString(): String {
        return "Privilege(id=$id, name='$name', userList=$userList, roleList=$roleList)"
    }
}

@Entity
@Table(name = "student")
data class Student(
    @Id
    var id: Long? = null,

    var description: String? = null,
    var premiun: Boolean? = null,

    @Column(name = "linkedin_url")
    var linkedinUrl: String? = null,

    @Column(name = "github_url")
    var githubUrl: String? = null,

    @Column(name = "born_date")
    var bornDate: String? = null,

    var location: String? = null,

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    var user: User,

    @OneToMany(mappedBy = "student")
    var abilitiesList: List<Ability>? = null,

    @OneToMany(mappedBy = "student")
    var companyReviewList: List<CompanyReview>? = null,

    @OneToMany(mappedBy = "student")
    var curriculumList: List<Curriculum>? = null,

    @OneToMany(mappedBy = "student")
    var educationList: List<Education>? = null,

    @OneToMany(mappedBy = "student")
    var experienceList: List<Experience>? = null,

    @OneToMany(mappedBy = "student")
    var interviewList: List<Interview>? = null,

    @OneToOne(mappedBy = "student")
    var streak: Streak? = null,

    @OneToOne(mappedBy = "student")
    var studentProgress: StudentProgress? = null,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (id != other.id) return false
        if (premiun != other.premiun) return false
        if (description != other.description) return false
        if (linkedinUrl != other.linkedinUrl) return false
        if (githubUrl != other.githubUrl) return false
        if (bornDate != other.bornDate) return false
        if (location != other.location) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
//        var result = id.hashCode()
//        result = 31 * result + premiun.hashCode()
//        result = 31 * result + description.hashCode()
//        result = 31 * result + linkedinUrl.hashCode()
//        result = 31 * result + githubUrl.hashCode()
//        result = 31 * result + bornDate.hashCode()
//        result = 31 * result + location.hashCode()
//        result = 31 * result + user.hashCode()
//        return result
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Student(id=$id, description='$description', premiun=$premiun, linkedinUrl='$linkedinUrl', githubUrl='$githubUrl', bornDate='$bornDate', location='$location', user=$user)"
    }
}

@Entity
@Table(name = "admin")
data class Admin(
    @Id
    var id: Long,

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    var user: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Admin

        if (id != other.id) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

    override fun toString(): String {
        return "Admin(id=$id, user=$user)"
    }
}

@Entity
@Table(name = "companies")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var name: String,
    var description: String,
    var type: String,
    var location: String,
    var employees: Int,

    var vision: String,
    var mission: String,

    @Column(name = "website_url")
    var websiteUrl: String,

    var rating: Float,

    @OneToMany(mappedBy = "company")
    var companyReviewList: List<CompanyReview> = emptyList(),

    @OneToMany(mappedBy = "company")
    var experienceList: List<Experience> = emptyList()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (id != other.id) return false
        if (employees != other.employees) return false
        if (rating != other.rating) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (type != other.type) return false
        if (location != other.location) return false
        if (websiteUrl != other.websiteUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + employees
        result = 31 * result + rating.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + websiteUrl.hashCode()
        return result
    }

    override fun toString(): String {
        return "Company(id=$id, name='$name', description='$description', type='$type', ubication='$location', employees=$employees, websiteUrl='$websiteUrl', rating=$rating)"
    }
}

@Entity
@Table(name = "ability")
data class Ability(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var name: String,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
    var student: Student
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ability

        if (id != other.id) return false
        if (name != other.name) return false
        if (student != other.student) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + student.hashCode()
        return result
    }

    override fun toString(): String {
        return "Ability(id=$id, name='$name', student=$student)"
    }
}


@Entity
@Table(name = "advices")
data class Advice(
    @Id
    var id: Long,

    var question: String,

    var answer: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Advice

        if (id != other.id) return false
        if (question != other.question) return false
        if (answer != other.answer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + question.hashCode()
        result = 31 * result + answer.hashCode()
        return result
    }

    override fun toString(): String {
        return "Advice(id=$id, question='$question', answer='$answer')"
    }
}

@Entity
@Table(name = "companies_reviews")
data class CompanyReview(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var rating: Float,
    var comment: String,

    @Column(name = "created_at")
    var createdAt: Date,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
    var student: Student,

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id")
    var company: Company
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompanyReview

        if (id != other.id) return false
        if (rating != other.rating) return false
        if (comment != other.comment) return false
        if (createdAt != other.createdAt) return false
        if (student != other.student) return false
        if (company != other.company) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + rating.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + student.hashCode()
        result = 31 * result + company.hashCode()
        return result
    }

    override fun toString(): String {
        return "CompanyReview(id=$id, rating=$rating, comment='$comment', createdAt=$createdAt, student=$student, company=$company)"
    }
}

@Entity
@Table(name = "curriculums")
data class Curriculum(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "archive_url")
    var archiveUrl: String,

    var feedback: String,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
    var student: Student,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Curriculum

        if (id != other.id) return false
        if (archiveUrl != other.archiveUrl) return false
        if (feedback != other.feedback) return false
        if (student != other.student) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + archiveUrl.hashCode()
        result = 31 * result + feedback.hashCode()
        result = 31 * result + student.hashCode()
        return result
    }

    override fun toString(): String {
        return "Curriculum(id=$id, archiveUrl='$archiveUrl', feedback='$feedback', student=$student)"
    }
}

@Entity
@Table(name = "education")
data class Education(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var name: String,
    var institution: String,
    var year: Int,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
    var student: Student,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Education

        if (id != other.id) return false
        if (year != other.year) return false
        if (name != other.name) return false
        if (institution != other.institution) return false
        if (student != other.student) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + year
        result = 31 * result + name.hashCode()
        result = 31 * result + institution.hashCode()
        result = 31 * result + student.hashCode()
        return result
    }

    override fun toString(): String {
        return "Education(id=$id, name='$name', institution='$institution', year=$year, student=$student)"
    }
}

@Entity
@Table(name = "experiences")
data class Experience(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var role: String,
    var year: Int,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
    var student: Student,

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id")
    var company: Company
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Experience

        if (id != other.id) return false
        if (year != other.year) return false
        if (role != other.role) return false
        if (student != other.student) return false
        if (company != other.company) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + year
        result = 31 * result + role.hashCode()
        result = 31 * result + student.hashCode()
        result = 31 * result + company.hashCode()
        return result
    }

    override fun toString(): String {
        return "Experience(id=$id, role='$role', year=$year, student=$student, company=$company)"
    }
}

@Entity
@Table(name = "interview")
data class Interview(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var date: Date,
    var result: String,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
    var student: Student? = null,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Interview

        if (id != other.id) return false
        if (date != other.date) return false
        if (result != other.result) return false
        if (student != other.student) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = id?.hashCode() ?: 0
        result1 = 31 * result1 + date.hashCode()
        result1 = 31 * result1 + result.hashCode()
        result1 = 31 * result1 + student.hashCode()
        return result1
    }

    override fun toString(): String {
        return "Interview(id=$id, date=$date, result='$result', student=$student)"
    }
}


@Entity
@Table(name = "ia_analysis")
data class IAAnalysis(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var recommendation: String,
    var score: Float,

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    var date: Date,

    @OneToOne
    @JoinColumn(name = "interview_id", unique = true, referencedColumnName = "id")
    var interview: Interview? = null,

    @OneToOne
    @JoinColumn(name = "curriculum_id", unique = true, referencedColumnName = "id")
    var curriculum: Curriculum? = null,
) {
    override fun toString(): String {
        return "IAAnalysis(id=$id, recommendations='$recommendation, score=$score, date=$date, interview=$interview, curriculum=$curriculum)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IAAnalysis

        if (id != other.id) return false
        if (score != other.score) return false
        if (recommendation != other.recommendation) return false
        if (date != other.date) return false
        if (interview != other.interview) return false
        if (curriculum != other.curriculum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + score.hashCode()
        result = 31 * result + recommendation.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + interview.hashCode()
        result = 31 * result + curriculum.hashCode()
        return result
    }
}

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var type: String,
    var message: String,
    var readed: Boolean,
    var date: Date,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notification

        if (id != other.id) return false
        if (readed != other.readed) return false
        if (type != other.type) return false
        if (message != other.message) return false
        if (date != other.date) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + readed.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

    override fun toString(): String {
        return "Notification(id=$id, type='$type', message='$message', readed=$readed, date=$date, user=$user)"
    }
}

@Entity
@Table(name = "streaks")
data class Streak(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var days: Int,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "last_activity")
    var lastActivity: Date,

    @Column(name = "best_streak")
    var bestStreak: Int,

    @OneToOne
    @JoinColumn(name = "student_id")
    var student: Student? = null,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Streak

        if (id != other.id) return false
        if (days != other.days) return false
        if (bestStreak != other.bestStreak) return false
        if (lastActivity != other.lastActivity) return false
        if (student != other.student) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + days
        result = 31 * result + bestStreak
        result = 31 * result + lastActivity.hashCode()
        result = 31 * result + student.hashCode()
        return result
    }

    override fun toString(): String {
        return "Streak(id=$id, days=$days, lastActivity=$lastActivity, bestStreak=$bestStreak, student=$student)"
    }
}


@Entity
@Table(name = "student_progress")
data class StudentProgress(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "total_interviews")
    var totalInterviews: Int,

    @Column(name = "average_score")
    var averageScore: Float,

    @Column(name = "uploaded_cl")
    var uploadedCl: Int,

    @Column(name = "last_activity")
    var lastActivity: Date,

    @OneToOne
    @JoinColumn(name = "student_id")
    var student: Student
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StudentProgress

        if (id != other.id) return false
        if (totalInterviews != other.totalInterviews) return false
        if (averageScore != other.averageScore) return false
        if (uploadedCl != other.uploadedCl) return false
        if (lastActivity != other.lastActivity) return false
        if (student != other.student) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + totalInterviews
        result = 31 * result + averageScore.hashCode()
        result = 31 * result + uploadedCl
        result = 31 * result + lastActivity.hashCode()
        result = 31 * result + student.hashCode()
        return result
    }

    override fun toString(): String {
        return "StudentProgress(id=$id, totalInterviews=$totalInterviews, averageScore=$averageScore, uploadedCl=$uploadedCl, lastActivity=$lastActivity, student=$student)"
    }
}
