package functionaltests.auth

import functionaltests.pages.HomePage
import functionaltests.pages.LoginPage
import functionaltests.pages.StatusPage
import functionaltests.pages.UserPage
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class LoginFunctionalSpec extends GebSpec{

    def setup() {
        go '/logout'
    }

    void 'test expected redirects'() {
        when:
        go '/'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at LoginPage

        when:
        go '/users'

        then:
        at LoginPage
    }

    void 'test authentication'() {
        when:
        go '/logout'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at LoginPage

        when:
        username = 'jeff'
        password = 'password'
        loginButton.click()

        then:
        at StatusPage

        when:
        go '/person/index'

        then:
        at UserPage

        when:
        go '/'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at StatusPage

        when:
        go '/logout'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at LoginPage
    }
}
