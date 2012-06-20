package org.grails.twitter.pages

class SearchResultsPage extends geb.Page {

    static content = {
        userSearchResult { index -> module(UserSearchResult, $("div", id: "search_result_$index")) }
    }
    
    static at = {
        title == 'User Search Results'
    }
}
