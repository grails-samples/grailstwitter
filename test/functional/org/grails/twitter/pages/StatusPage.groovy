package org.grails.twitter.pages

class StatusPage extends geb.Page {
    
    static url = 'status'

    static content = {
        updateStatusForm { $('form#updateStatusForm') }
        userSearchForm { $('form#userSearchForm') }
        statusMessageDisplayed(wait: true) { index -> module(Status, $("div.statusMessage#message_$index" )) }
        updateStatusButton { $('#update_status_button') }
        newStatusMessage { updateStatusForm.message() }
        search { userSearchForm.q() }
        searchForUsersButton { $('#search_for_users_button') }
    }

    static at = { title == 'What Are You Doing?' }
}
