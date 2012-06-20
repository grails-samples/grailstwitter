package org.grails.twitter

import org.grails.twitter.pages.LoginPage
import org.grails.twitter.pages.SearchResultsPage
import org.grails.twitter.pages.StatusPage

import spock.lang.Stepwise

import geb.spock.GebReportingSpec


@Stepwise
class StatusCachingSpec extends GebReportingSpec {

    void 'access the status page as jeff'() {
        when:
        to StatusPage

        then:
        at LoginPage

        when:
        userName.value('jeff')
        password.value('password')
        loginButton.click()

        then:
        at StatusPage
    }
    
    def 'have jeff follow graeme'() {
        when:
        search.value('graeme')
        searchForUsersButton.click()
        
        then:
        at SearchResultsPage
        userSearchResult(0).realName == 'Graeme Rocher'
        
        when:
        userSearchResult(0).linkToFollow.click()
        
        then:
        at StatusPage
    }
    
    def 'post first status as jeff'() {
        when:
        newStatusMessage.value('My First Status')
        updateStatusButton.click()
        
        then:
        statusMessageDisplayed(0).message == 'My First Status'
        statusMessageDisplayed(0).author == 'Jeff Brown'
    }

    def 'post second status as jeff'() {
        when:
        newStatusMessage.value('My Second Status')
        updateStatusButton.click()
        
        then:
        statusMessageDisplayed(1).message == 'My First Status'
        statusMessageDisplayed(1).author == 'Jeff Brown'
        statusMessageDisplayed(0).message == 'My Second Status'
        statusMessageDisplayed(0).author == 'Jeff Brown'
    }
    
    def 'login as graeme'() {
        when:
        go 'logout'
        to StatusPage
        
        then:
        at LoginPage

        when:
        userName.value('graeme')
        password.value('password')
        loginButton.click()

        then:
        at StatusPage
    }
    
    def 'post first status as graeme'() {
        when:
        newStatusMessage.value('My First Status')
        updateStatusButton.click()
        
        then:
        statusMessageDisplayed(0).message == 'My First Status'
        statusMessageDisplayed(0).author == 'Graeme Rocher'
    }
    
    def 'login back in as jeff'() {
        when:
        go 'logout'
        to StatusPage
        
        then:
        at LoginPage

        when:
        userName.value('jeff')
        password.value('password')
        loginButton.click()

        then:
        at StatusPage
    }
    
    def "verify that graeme's status shows up, indicating that jeff's cache was cleared when graeme posted"() {
        when:
        to StatusPage
        
        then:
        at StatusPage
        statusMessageDisplayed(2).message == 'My First Status'
        statusMessageDisplayed(2).author == 'Jeff Brown'
        statusMessageDisplayed(1).message == 'My Second Status'
        statusMessageDisplayed(1).author == 'Jeff Brown'
        statusMessageDisplayed(0).message == 'My First Status'
        statusMessageDisplayed(0).author == 'Graeme Rocher'
    }
}
