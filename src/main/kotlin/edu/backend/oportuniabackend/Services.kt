package edu.backend.oportuniabackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.Optional

interface UserService {
    fun findAll(): List<UserResult>?

    fun findById(id: Long): UserResult?
}

@Service
class AbstractUserService (
    @Autowired
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
): UserService {
    override fun findAll(): List<UserResult>? {
        return userRepository.findAll().map { userMapper.userToUserResult(it) }
    }
    override fun findById(id: Long): UserResult? {
        return userRepository.findById(id).map { userMapper.userToUserResult(it) }.orElse(null)
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
): AbilityService {
    override fun findAll(): List<AbilityResult>? {
        return abilityMapper.abilityListToAbilityResultList(
            abilityRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): AbilityResult? {
        val ability: Optional<Ability> = abilityRepository.findById(id)
        if(ability.isPresent){
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
class AbstractCurriculumService (
    @Autowired
    val curriculumRepository: CurriculumRepository,
    @Autowired
    private val curriculumMapper: CurriculumMapper,
): CurriculumService {
    override fun findAll(): List<CurriculumResult>? {
        return curriculumMapper.curriculumListToCurriculumResultList(
            curriculumRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): CurriculumResult? {
        val curriculum: Optional<Curriculum> = curriculumRepository.findById(id)
        if (curriculum.isEmpty) {
            throw NoSuchElementException(
                String.format(
                    "The Curriculum with the id: %s not found!", id))
        }
        return curriculumMapper.curriculumToCurriculumResult(
            curriculum.get(),
        )
    }

    override fun create(curriculumInput: CurriculumInput): CurriculumResult? {
        val curriculum: Curriculum = curriculumMapper.curriculumInputToCurriculum(curriculumInput)
        return curriculumMapper.curriculumToCurriculumResult(
            curriculumRepository.save(curriculum)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun update(curriculumInput: CurriculumInput): CurriculumResult? {
        val curriculum: Optional<Curriculum> = curriculumRepository.findById(curriculumInput.id!!)
        if(curriculum.isEmpty){
            throw NoSuchElementException(String.format("The Curriculum with the id: %s not found!", curriculumInput.id))
        }
        val curriculumUpdated: Curriculum = curriculum.get()
        curriculumMapper.curriculumInputToCurriculum(curriculumInput, curriculumUpdated)
        return curriculumMapper.curriculumToCurriculumResult(curriculumRepository.save(curriculumUpdated)
        )
    }


    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if(!curriculumRepository.findById(id).isEmpty){
            curriculumRepository.deleteById(id)
        }else{
            throw NoSuchElementException(String.format("The Curriculum with the id: %s not found!", id))
        }
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
        return educationMapper.educationToEducationResult(
            educationRepository.save(education)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun update(educationInput: EducationInput): EducationResult? {
        val education: Optional<Education> = educationRepository.findById(educationInput.id!!)
        if(education.isEmpty){
            throw NoSuchElementException(String.format("The education with the id: %s not found!", educationInput.id))
        }
        val educationUpdated: Education = education.get()
        educationMapper.educationInputToEducation(educationInput, educationUpdated)
        return educationMapper.educationToEducationResult(educationRepository.save(educationUpdated)
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
): ExperienceService {
    override fun findAll(): List<ExperienceResult>? {
        return experienceMapper.experienceListToExperienceResultList(
            experienceRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): ExperienceResult? {
        val experience: Optional<Experience> = experienceRepository.findById(id)
        if(experience.isPresent){
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
        val experience: Experience = experienceMapper.experienceInputToExperience(experienceInput)
        return experienceMapper.experienceToExperienceResult(
            experienceRepository.save(experience)
        )
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
        val iaAnalysis: IAAnalysis = iaAnalysisMapper.iaAnalysisInputToIAAnalysis(iaAnalysisInput)
        return iaAnalysisMapper.iaAnalysisToIAAnalysisDetails(
            iaAnalysisRepository.save(iaAnalysis)
        )
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
): InterviewService {
    override fun findAll(): List<InterviewResult>? {
        return interviewMapper.interviewListToInterviewResultList(
            interviewRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): InterviewResult? {
        val interview: Optional<Interview> = interviewRepository.findById(id)
        if(interview.isPresent){
            throw NoSuchElementException(String.format("The Interview with the id: %s not found!", id))
        }
        return interviewMapper.interviewToInterviewResult(
            interview.get()
        )
    }

    override fun create(interviewInput: InterviewInput): InterviewResult? {
        val interview: Interview = interviewMapper.interviewInputToInterview(interviewInput)
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
        if(notification.isPresent){
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
        if(studentProgress.isPresent){
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



