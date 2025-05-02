package edu.backend.oportuniabackend
import org.mapstruct.*
import java.time.LocalDateTime


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
interface CompanyMapper {
    fun companyToCompanyResult(company: Company): CompanyResult
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

