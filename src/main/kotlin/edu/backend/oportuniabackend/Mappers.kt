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

    fun userListToUserListResult (userList: List<User>) : List<UserResult>
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

    fun studentListToStudentListResult (studentList: List<Student>) : List<StudentResult>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ExperienceMapper {
    fun experienceToExperienceResult(experience: Experience): ExperienceResult
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "student", ignore = true)
    fun experienceInputToExperience(experienceInput: ExperienceInput): Experience

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun experienceInputToExperience(dto: ExperienceInput, @MappingTarget experience: Experience)

    fun experienceListToExperienceResultList(experiences: List<Experience>): List<ExperienceResult>
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "company", ignore = true)
    fun updateExperienceFromInput(experienceInput: ExperienceInput, @MappingTarget experience: Experience)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AbilityMapper {
    fun abilityToAbilityResult(ability: Ability): AbilityResult
    @Mapping(target = "student", ignore = true)
    fun abilityInputToAbility(abilityInput: AbilityInput): Ability
    fun abilityListToAbilityResultList(abilities: List<Ability>): List<AbilityResult>
    @Mapping(target = "student", ignore = true)
    fun updateAbilityFromInput(abilityInput: AbilityInput, @MappingTarget ability: Ability)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun abilityInputToAbility(dto: AbilityInput, @MappingTarget ability: Ability)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AdminMapper {
    fun adminToAdminInput(admin: Admin): AdminInput
    fun adminToAdminResult(admin: Admin): AdminResult
    fun adminInputToAdmin(adminInput: AdminInput): Admin
    fun adminListToAdminListResult (adminList: List<Admin>) : List<AdminInput>
    @Mapping(target = "user", ignore = true)
    fun updateAdminFromInput(adminInput: AdminInput, @MappingTarget admin: Admin)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserLoginMapper {
    fun userLoginInputToUser(userLoginInput: UserLoginInput): User
    fun userToUserLoginInput(user: User): UserLoginInput
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StreakMapper {
    fun streakToStreakResult(streak: Streak): StreakResult

    @Mapping(target = "student", ignore = true)
    fun streakInputToStreak(streakInput: StreakInput): Streak

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun streakInputToStreak(dto: StreakInput, @MappingTarget streak: Streak)

    fun streakListToStreakResultList(streaks: List<Streak>): List<StreakResult>
    @Mapping(target = "student", ignore = true)
    fun updateStreakFromInput(streakInput: StreakInput, @MappingTarget streak: Streak)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StudentProgressMapper {
    fun studentProgressToStudentProgressResult(studentProgress: StudentProgress): StudentProgressResult

    @Mapping(target = "student", ignore = true)
    fun studentProgressInputToStudentProgress(studentProgressInput: StudentProgressInput): StudentProgress

    fun studentProgressListToStudentProgressResultList(studentProgress: List<StudentProgress>): List<StudentProgressResult>
    @Mapping(target = "student", ignore = true)
    fun updateStudentProgressFromInput(studentProgressInput: StudentProgressInput, @MappingTarget studentProgress: StudentProgress)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NotificationMapper {
    fun notificationToNotificationResult(notification: Notification): NotificationResult
    @Mapping(target = "date", defaultExpression = "java(new java.util.Date())")
    fun notificationInputToNotification(notificationInput: NotificationInput): Notification
    fun notificationListToNotificationResultList(notifications: List<Notification>): List<NotificationResult>
    @Mapping(target = "user", ignore = true)
    fun updateNotificationFromInput(notificationInput: NotificationInput, @MappingTarget notification: Notification)
}

// COMPANIES

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CompanyMapper {

    fun companyToCompanyResult(company: Company): CompanyResult

    fun companyToCompanyResultLarge(company: Company): CompanyResultLarge

    @Mapping(target = "companyReviewList", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "experienceList", expression = "java(java.util.Collections.emptyList())")
    fun companyInputToCompany(companyInput: CompanyInput): Company

    fun companyListToCompanyListResult(companyList: List<Company>): List<CompanyResult>

    fun companyListToCompanyListResultLarge(companyList: List<Company>): List<CompanyResultLarge>

    @Mapping(target = "companyReviewList", ignore = true)
    @Mapping(target = "experienceList", ignore = true)
    fun updateCompanyFromInput(companyInput: CompanyInput, @MappingTarget company: Company)
}


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CompanyReviewMapper {
    fun companyReviewToCompanyReviewResult(companyReview: CompanyReview): CompanyReviewResult

    @Mapping(target = "createdAt", defaultExpression = "java(new java.util.Date())")
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "student", ignore = true)
    fun companyReviewInputToCompanyReview(companyReviewInput: CompanyReviewInput): CompanyReview

    @Mapping(target = "createdAt", defaultExpression = "java(new java.util.Date())")
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "company", ignore = true)
    fun updateCompanyReviewFromInput(companyReviewInput: CompanyReviewInput, @MappingTarget companyReview: CompanyReview)

    fun companyReviewListToCompanyReviewResultList(companyReviews: List<CompanyReview>): List<CompanyReviewResult>
}

//CV AND INTERVIEWS

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IAAnalysisMapper {
    fun iaAnalysisToIAAnalysisInput(iaAnalysis: IAAnalysis): IAAnalysisInput

    @Mapping(target = "date", defaultExpression = "java(new java.util.Date())")
    @Mapping(target = "interview", ignore = true)
    @Mapping(target = "curriculum", ignore = true)
    fun iaAnalysisInputToIAAnalysis(iaAnalysisInput: IAAnalysisInput): IAAnalysis

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun iaAnalysisInputToIAAnalysis(dto: IAAnalysisInput, @MappingTarget iaAnalysis: IAAnalysis)

    fun iaAnalysisListToIAAnalysisDetailsList(iaAnalysis: List<IAAnalysis>): List<IAAnalysisDetails>
    fun iaAnalysisToIAAnalysisDetails(iaAnalysis: IAAnalysis): IAAnalysisDetails

}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface InterviewMapper {
    fun interviewToInterviewResult(interview: Interview): InterviewResult
    @Mapping(target = "student", ignore = true)
    fun interviewInputToInterview(interviewInput: InterviewInput): Interview

    fun interviewListToInterviewResultList(interviews: List<Interview>): List<InterviewResult>
    fun updateInterviewFromInput(interviewInput: InterviewInput, @MappingTarget interview: Interview)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CurriculumMapper {
    fun curriculumToCurriculumResult(curriculum: Curriculum): CurriculumResult

    @Mapping(target = "student", ignore = true)
    fun curriculumInputToCurriculum(curriculumInput: CurriculumInput): Curriculum

    @Mapping(target = "student", ignore = true)
    fun curriculumInputToCurriculum(curriculumInput: CurriculumInput, @MappingTarget curriculum: Curriculum)

    fun curriculumListToCurriculumResultList(curriculums: List<Curriculum>): List<CurriculumResult>
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
    fun adviceInputToAdvice(adviceInput: AdviceInput): Advice
    fun adviceListToAdviceDetailsList(advices: List<Advice>): List<AdviceDetails>

}

// Education

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface EducationMapper {
    fun educationToEducationResult(education: Education): EducationResult

    @Mapping(target = "student", ignore = true)
    fun educationInputToEducation(educationInput: EducationInput): Education

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun educationInputToEducation(dto: EducationInput, @MappingTarget education: Education)

    fun educationListToEducationResultList(educations: List<Education>): List<EducationResult>

    @Mapping(target = "student", ignore = true)
    fun updateEducationFromInput(educationInput: EducationInput, @MappingTarget education: Education)
}
