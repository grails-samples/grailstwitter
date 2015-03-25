import org.grails.twitter.auth.Person

class BootStrap {

    def init = { servletContext ->
        new  Person(firstName: 'Jeff', lastName: 'Brown', userName: 'jeff').save(failOnError: true)
        new  Person(firstName: 'Lari', lastName: 'Hotari', userName: 'lari').save(failOnError: true)
        new  Person(firstName: 'Graeme', lastName: 'Rocher', userName: 'graeme').save(failOnError: true)
    }
    def destroy = {
    }
}
