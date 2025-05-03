package edu.backend.oportuniabackend

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.abilities}")
class AbilityController(private val abilityService: AbilityService) {

    @GetMapping
    @ResponseBody
    fun findAll() = abilityService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = abilityService.findById(id)

    @GetMapping("student/{studentId}")
    @ResponseBody
    fun findByStudentId(@PathVariable studentId: Long) = abilityService.findByStudentId(studentId)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createAbility(@RequestBody abilityInput: AbilityInput) : AbilityResult? {
        return abilityService.create(abilityInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        abilityService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.curriculums}")
class CurriculumController(private val curriculumService: CurriculumService) {

    @GetMapping
    @ResponseBody
    fun findAll() = curriculumService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = curriculumService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createCurriculum(@RequestBody curriculumInput: CurriculumInput) : CurriculumResult? {
        return curriculumService.create(curriculumInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody curriculumInput: CurriculumInput) : CurriculumResult? {
        return curriculumService.update(curriculumInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        curriculumService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.educations}")
class EducationController(private val educationService: EducationService) {

    @GetMapping
    @ResponseBody
    fun findAll() = educationService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = educationService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createEducation(@RequestBody educationInput: EducationInput) : EducationResult? {
        return educationService.create(educationInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody educationInput: EducationInput) : EducationResult? {
        return educationService.update(educationInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        educationService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.experiences}")
class ExperienceController(private val experienceService: ExperienceService) {

    @GetMapping
    @ResponseBody
    fun findAll() = experienceService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = experienceService.findById(id)

    @GetMapping("students/{studentId}")
    @ResponseBody
    fun findByStudentId(@PathVariable studentId: Long) = experienceService.findByStudentId(studentId)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createExperience(@RequestBody experienceInput: ExperienceInput) : ExperienceResult? {
        return experienceService.create(experienceInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody experienceInput: ExperienceInput) : ExperienceResult? {
        return experienceService.update(experienceInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        experienceService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.iaAnalyses}")
class IAAnalysisController(private val iaAnalysisService: IAAnalysisService) {

    @GetMapping
    @ResponseBody
    fun findAll() = iaAnalysisService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = iaAnalysisService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createIAAnalysisAbility(@RequestBody iaAnalysisInput: IAAnalysisInput) : IAAnalysisDetails? {
        return iaAnalysisService.create(iaAnalysisInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        iaAnalysisService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.interviews}")
class InterviewController(private val interviewService: InterviewService) {

    @GetMapping
    @ResponseBody
    fun findAll() = interviewService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = interviewService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createInterview(@RequestBody interviewInput: InterviewInput) : InterviewResult? {
        return interviewService.create(interviewInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        interviewService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.notifications}")
class NotificationController(private val notificationService: NotificationService) {

    @GetMapping
    @ResponseBody
    fun findAll() = notificationService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = notificationService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createInterview(@RequestBody notificationInput: NotificationInput) : NotificationResult? {
        return notificationService.create(notificationInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        notificationService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.streaks}")
class StreakController(private val streakService: StreakService) {

    @GetMapping
    @ResponseBody
    fun findAll() = streakService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = streakService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createExperience(@RequestBody streakInput: StreakInput) : StreakResult? {
        return streakService.create(streakInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody streakInput: StreakInput) : StreakResult? {
        return streakService.update(streakInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        streakService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.student_Progresses}")
class StudentProgressController(private val notificationService: StudentProgressService) {

    @GetMapping
    @ResponseBody
    fun findAll() = notificationService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = notificationService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createInterview(@RequestBody studentProgressInput: StudentProgressInput) : StudentProgressResult? {
        return notificationService.create(studentProgressInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        notificationService.deleteById(id)
    }
}