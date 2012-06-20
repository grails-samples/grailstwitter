package org.grails.twitter

import geb.spock.GebReportingSpec
import org.grails.twitter.pages.LoginPage
import org.grails.twitter.pages.StatusPage


class LoginSpec extends GebReportingSpec {

    def 'test accessing the status page requires login'() {
        when:
        to StatusPage
        
        then:
        at LoginPage
        
        when:
        loginButton.click()
      
        then:
        at LoginPage
        
        when:
        userName.value('jeff')
        password.value('password')
        loginButton.click()
        
        then:
        at StatusPage
    }
}
