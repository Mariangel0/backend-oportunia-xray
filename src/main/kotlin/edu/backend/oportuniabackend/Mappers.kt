package edu.backend.oportuniabackend
import org.mapstruct.*
import java.time.LocalDateTime

//USERS

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    fun userToUserResult(user: User): UserResult
    fun userInputToUser(userInput: UserInput): User
    fun roleSetToRoleDetailsSet(roles: Set<Role>): Set<RoleDetails>
    fun roleToRoleDetails(role: Role): RoleDetails
    fun privilegeSetToPrivilegeDetailsSet(privileges: Set<Privilege>): Set<PrivilegeDetails>
    fun privilegeToPrivilegeDetails(privilege: Privilege): PrivilegeDetails
    @Mapping(target = "password", ignore = true)
    fun updateUserFromInput(userInput: UserInput, @MappingTarget user: User)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StudentMapper {
    fun studentToStudentResult(student: Student): StudentResult
    fun studentInputToStudent(studentInput: StudentInput): Student
    @Mapping(target = "user", ignore = true)
    fun updateStudentFromInput(studentInput: StudentInput, @MappingTarget student: Student)
    fun abilityListToAbilityResultList(abilities: List<Ability>): List<AbilityResult>
    fun companyReviewListToCompanyReviewResultList(companyReviews: List<CompanyReview>): List<CompanyReviewResult>
    fun curriculumListToCurriculumResultList(curriculums: List<Curriculum>): List<CurriculumResult>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ExperienceMapper {
    fun experienceToExperienceResult(experience: Experience): ExperienceResult
    fun experienceInputToExperience(experienceInput: ExperienceInput): Experience
    @Mapping(target = "company", ignore = true)
    fun updateExperienceFromInput(experienceInput: ExperienceInput, @MappingTarget experience: Experience)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AbilityMapper {
    fun abilityToAbilityResult(ability: Ability): AbilityResult
    fun abilityInputToAbility(abilityInput: AbilityInput): Ability
    @Mapping(target = "student", ignore = true)
    fun updateAbilityFromInput(abilityInput: AbilityInput, @MappingTarget ability: Ability)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AdminMapper {
    fun adminToAdminInput(admin: Admin): AdminInput
    fun adminInputToAdmin(adminInput: AdminInput): Admin
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserLoginMapper {
    fun userLoginInputToUser(userLoginInput: UserLoginInput): User
    fun userToUserLoginInput(user: User): UserLoginInput
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StreakMapper {
    fun streakToStreakResult(streak: Streak): StreakResult
    fun streakInputToStreak(streakInput: StreakInput): Streak
    @Mapping(target = "student", ignore = true)
    fun updateStreakFromInput(streakInput: StreakInput, @MappingTarget streak: Streak)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StudentProgressMapper {
    fun studentProgressToStudentProgressResult(studentProgress: StudentProgress): StudentProgressResult
    fun studentProgressInputToStudentProgress(studentProgressInput: StudentProgressInput): StudentProgress
    @Mapping(target = "student", ignore = true)
    fun updateStudentProgressFromInput(studentProgressInput: StudentProgressInput, @MappingTarget studentProgress: StudentProgress)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NotificationMapper {
    fun notificationToNotificationResult(notification: Notification): NotificationResult
    fun notificationInputToNotification(notificationInput: NotificationInput): Notification
    @Mapping(target = "user", ignore = true)
    fun updateNotificationFromInput(notificationInput: NotificationInput, @MappingTarget notification: Notification)
}

// COMPANIES

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CompanyMapper {
    fun companyToCompanyResult(company: Company): CompanyResult
    fun companyToCompanyResultLarge(company: Company): CompanyResultLarge
    fun companyInputToCompany(companyInput: CompanyInput): Company
    @Mapping(target = "companyReviewList", ignore = true)
    fun updateCompanyFromInput(companyInput: CompanyInput, @MappingTarget company: Company)
    fun companyReviewListToCompanyReviewResultList(companyReviews: List<CompanyReview>): List<CompanyReviewResult>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CompanyReviewMapper {
    fun companyReviewToCompanyReviewResult(companyReview: CompanyReview): CompanyReviewResult
    fun companyReviewInputToCompanyReview(companyReviewInput: CompanyReviewInput): CompanyReview
    @Mapping(target = "student", ignore = true)
    fun updateCompanyReviewFromInput(companyReviewInput: CompanyReviewInput, @MappingTarget companyReview: CompanyReview)
}

//CV AND INTERVIEWS

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IAAnalysisMapper {
    fun iaAnalysisToIAAnalysisInput(iaAnalysis: IAAnalysis): IAAnalysisInput
    fun iaAnalysisInputToIAAnalysis(iaAnalysisInput: IAAnalysisInput): IAAnalysis
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface InterviewMapper {
    fun interviewToInterviewResult(interview: Interview): InterviewResult
    fun interviewInputToInterview(interviewInput: InterviewInput): Interview
    @Mapping(target = "iaAnalysis", ignore = true)
    fun updateInterviewFromInput(interviewInput: InterviewInput, @MappingTarget interview: Interview)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CurriculumMapper {
    fun curriculumToCurriculumResult(curriculum: Curriculum): CurriculumResult
    fun curriculumInputToCurriculum(curriculumInput: CurriculumInput): Curriculum
    @Mapping(target = "iaAnalysis", ignore = true)
    fun updateCurriculumFromInput(curriculumInput: CurriculumInput, @MappingTarget curriculum: Curriculum)
}


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface InterviewAnalysisMapper {
    fun interviewAnalysisInputToIAAnalysis(interviewAnalysisInput: InterviewAnalysisInput): IAAnalysis
    fun iaAnalysisToInterviewAnalysisInput(iaAnalysis: IAAnalysis): InterviewAnalysisInput
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CurriculumAnalysisMapper {
    fun curriculumAnalysisInputToCurriculum(curriculumAnalysisInput: CurriculumAnalysisInput): Curriculum
    fun curriculumToCurriculumAnalysisInput(curriculum: Curriculum): CurriculumAnalysisInput
}


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AdviceMapper {
    fun adviceToAdviceDetails(advice: Advice): AdviceDetails
    //fun adviceInputToAdvice(adviceInput: AdviceInput): Advice
}

