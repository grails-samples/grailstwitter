package twitter

import org.grails.twitter.auth.Person
import org.grails.twitter.auth.PersonRole
import org.grails.twitter.auth.Role

class BootStrap {

    def init = { servletContext ->
        Role roleUser = new Role(authority: 'ROLE_USER').save(failOnError: true)

        def createPerson = { String firstName, String lastName ->
            def person = new Person(firstName: firstName, lastName: lastName,
            	                     userName: firstName.toLowerCase(),
            	                     password: 'password').save(failOnError: true)
            PersonRole.create person, roleUser
        }

        createPerson 'Jeff', 'Brown'
        createPerson 'Lari', 'Hotari'
        createPerson 'Graeme', 'Rocher'
        createPerson 'Matthew', 'Moss'
    }

    def destroy = {
    }

}
