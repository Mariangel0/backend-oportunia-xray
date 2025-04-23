package edu.backend.oportuniabackend

import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "first_name")
    var firstName: String,

    @Column(name = "last_name")
    var lastName: String,

    var password: String,

    @Column(unique = true)
    var email: String,

    @Column(name = "create_date")
    var createDate: Date,

    var enabled: Boolean,

    @Column(name = "token_expired")
    var tokenExpired: Boolean,

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Set<Role> = emptySet()


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
    var id: Long,

    var description: String,
    var premiun: Boolean,

    @Column(name = "linkedin_url")
    var linkedinUrl: String,

    @Column(name = "github_url")
    var githubUrl: String,

    @Column(name = "born_date")
    var bornDate: String,

    var location: String,

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    var user: User
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
        var result = id.hashCode()
        result = 31 * result + premiun.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + linkedinUrl.hashCode()
        result = 31 * result + githubUrl.hashCode()
        result = 31 * result + bornDate.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + user.hashCode()
        return result
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
    var ubication: String,
    var employees: Int,

    @Column(name = "website_url")
    var websiteUrl: String,

    var rating: Float,

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
        if (ubication != other.ubication) return false
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
        result = 31 * result + ubication.hashCode()
        result = 31 * result + websiteUrl.hashCode()
        return result
    }

    override fun toString(): String {
        return "Company(id=$id, name='$name', description='$description', type='$type', ubication='$ubication', employees=$employees, websiteUrl='$websiteUrl', rating=$rating)"
    }
}
